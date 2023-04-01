use arrayvec::ArrayVec;
use itertools::Itertools;
use log::info;
use std::sync::Arc;
use tokio::sync::oneshot::Sender;
use tokio::time::Instant;

use crate::bootstrap::{TensorInput, TensorInputEnum};
use crate::cli_args::{ARGS, MODEL_SPECS};
use crate::{Callback, MAX_NUM_INPUTS, PredictResult};
use crate::metrics::{
    BATCH_SIZE, BATCH_SIZE_BY_MODEL, BLOCKING_REQUEST_NUM, MODEL_INFERENCE_TIME_COLLECTOR,
    NUM_BATCH_PREDICTION, NUM_BATCH_PREDICTION_BY_MODEL, NUM_BATCHES_DROPPED,
    NUM_BATCHES_DROPPED_BY_MODEL, NUM_PREDICTION_BY_MODEL, NUM_REQUESTS_DROPPED,
    NUM_REQUESTS_DROPPED_BY_MODEL,
};
use crate::predict_service::Model;
use crate::tf_proto::tensorflow_serving::model_spec::VersionChoice;
use crate::tf_proto::tensorflow_serving::PredictRequest;
use crate::tf_proto::DataType;

#[derive(Debug)]
pub struct BatchPredictor<T: Model> {
    pub model: Arc<T>,
    pub input_tensors: Vec<Vec<TensorInput>>,
    pub callbacks: Vec<Callback>,
    pub cur_batch_size: usize,
    pub max_batch_size: usize,
    pub batch_time_out_millis: u64,
    pub queue_reset_ts: Instant,
    pub queue_earliest_rq_ts: Instant,
}

impl PredictRequest {
    #[inline(always)]
    pub fn take_input_vals(
        &mut self,
        inputs: &ArrayVec<String, MAX_NUM_INPUTS>,
    ) -> Vec<TensorInput> {
        let mut model_inputs = Vec::<TensorInput>::new();
        for input_name in inputs.as_slice() {
            let input_tensor = self
                .inputs
                .get_mut(input_name)
                .unwrap_or_else(|| panic!("can't find {:?}", input_name));
            let dims = match &input_tensor.tensor_shape {
                None => None,
                Some(data) => Some(data.dim.iter().map(|d| d.size).collect_vec()),
            };
            match input_tensor.dtype() {
                DataType::DtFloat => model_inputs.push(TensorInput::new(
                    TensorInputEnum::Float(std::mem::take(&mut input_tensor.float_val)),
                    input_name.to_string(),
                    dims,
                )),
                DataType::DtDouble => model_inputs.push(TensorInput::new(
                    TensorInputEnum::Double(std::mem::take(&mut input_tensor.double_val)),
                    input_name.to_string(),
                    dims,
                )),
                DataType::DtInt32 => model_inputs.push(TensorInput::new(
                    TensorInputEnum::Int(std::mem::take(&mut input_tensor.int_val)),
                    input_name.to_string(),
                    dims,
                )),
                DataType::DtString => model_inputs.push(TensorInput::new(
                    TensorInputEnum::String(std::mem::take(&mut input_tensor.string_val)),
                    input_name.to_string(),
                    dims,
                )),
                DataType::DtInt64 => model_inputs.push(TensorInput::new(
                    TensorInputEnum::Int64(std::mem::take(&mut input_tensor.int64_val)),
                    input_name.to_string(),
                    dims,
                )),
                DataType::DtBool => model_inputs.push(TensorInput::new(
                    TensorInputEnum::Boolean(std::mem::take(&mut input_tensor.bool_val)),
                    input_name.to_string(),
                    dims,
                )),
                _ => panic!("unsupport input tensor type {:?}", input_tensor.dtype()),
            }
        }
        model_inputs
    }
    #[inline(always)]
    pub fn take_model_spec(&mut self) -> (String, Option<i64>) {
        let model_spec = self.model_spec.as_mut().unwrap();
        let version = model_spec
            .version_choice
            .as_ref()
            .and_then(|choice| match choice {
                VersionChoice::Version(version) => Some(*version),
                _ => None,
            });
        (std::mem::take(&mut model_spec.name), version)
    }
}

impl<T: Model> Drop for BatchPredictor<T> {
    fn drop(&mut self) {
        info!(
            "drop old batch predictor for:{:}, queue:{}",
            self.model,
            self.input_tensors.len()
        );
        if !self.input_tensors.is_empty() {
            info!("now flush old predictor queue:{}", self.input_tensors.len());
            self.batch_predict();
        }
    }
}

impl<T: Model> BatchPredictor<T> {
    #[inline(always)]
    pub fn push(&mut self, val: Vec<TensorInput>, resp: Sender<PredictResult>, ts: Instant) {
        if self.input_tensors.is_empty() {
            //only when queue is empty then we update ts to represent first request time
            self.queue_reset_ts = Instant::now();
            self.queue_earliest_rq_ts = ts;
        }
        self.cur_batch_size += 1;
        self.input_tensors.push(val);
        self.callbacks.push(Callback(resp, self.cur_batch_size));
    }
    #[inline(always)]
    pub fn batch_predict(&mut self) {
        BATCH_SIZE_BY_MODEL
            .with_label_values(&[&MODEL_SPECS[self.model.model_idx()]])
            .add(self.cur_batch_size as i64);
        BATCH_SIZE.add(self.cur_batch_size as i64);
        let mut batch_input_tensors = Vec::with_capacity(self.max_batch_size);
        let mut batch_callbacks = Vec::with_capacity(self.max_batch_size);
        let mut batch_size = 0;
        //now we swap so we can take two queues to the blocking-send thread and reset current queues
        std::mem::swap(&mut self.input_tensors, &mut batch_input_tensors);
        std::mem::swap(&mut self.callbacks, &mut batch_callbacks);
        std::mem::swap(&mut self.cur_batch_size, &mut batch_size);
        let model = self.model.clone();
        let batch_earliest_rq_ts = self.queue_earliest_rq_ts;
        //info!("batch predict for model:{}, size:{}", self.tf_model.export_dir, vals0.len());
        BLOCKING_REQUEST_NUM.inc();
        tokio::task::spawn_blocking(move || {
            //proactively drop stale batches, we drop the entire batch
            //as long as one request in that batch is stale. We may drop more than we could this way
            //but this should work fairly decently well
            if (batch_earliest_rq_ts.elapsed().as_millis() as u64) < ARGS.batch_drop_millis {
                let model_inference_time_start = Instant::now();
                let (tensor_outs, batch_ends) =
                    model.do_predict(batch_input_tensors, batch_size as u64);
                MODEL_INFERENCE_TIME_COLLECTOR
                    .with_label_values(&[&MODEL_SPECS[model.model_idx()]])
                    .observe(model_inference_time_start.elapsed().as_millis() as f64);
                let mut batch_starts = vec![0; tensor_outs.len()];
                for (i, Callback(resp, _)) in batch_callbacks.into_iter().enumerate() {
                    let mut tensors_send_back = vec![];
                    for (j, tensor_out) in tensor_outs.iter().enumerate() {
                        tensors_send_back.push(tensor_out.slice(batch_starts[j], batch_ends[j][i]));
                        batch_starts[j] = batch_ends[j][i];
                    }
                    if resp
                        .send(PredictResult::Ok(tensors_send_back, model.version()))
                        .is_err()
                    {
                        //use dropped metrics here as this is expected under high load
                        NUM_REQUESTS_DROPPED.inc();
                        NUM_REQUESTS_DROPPED_BY_MODEL
                            .with_label_values(&[&MODEL_SPECS[model.model_idx()]])
                            .inc();
                    }
                }
            } else {
                for Callback(resp, _) in batch_callbacks.into_iter() {
                    if resp.send(PredictResult::DropDueToOverload).is_err() {
                        NUM_REQUESTS_DROPPED.inc();
                        NUM_REQUESTS_DROPPED_BY_MODEL
                            .with_label_values(&[&MODEL_SPECS[model.model_idx()]])
                            .inc();
                    }
                }
                NUM_BATCHES_DROPPED.inc();
                NUM_BATCHES_DROPPED_BY_MODEL
                    .with_label_values(&[&MODEL_SPECS[model.model_idx()]])
                    .inc();
            }
            BLOCKING_REQUEST_NUM.dec();
        });
        NUM_BATCH_PREDICTION.inc();
        NUM_BATCH_PREDICTION_BY_MODEL
            .with_label_values(&[&MODEL_SPECS[self.model.model_idx()]])
            .inc();
        // Note:
        //  self.cur_batch_size is swapped with batch_size above
        //  Use the local variable batch_size here
        NUM_PREDICTION_BY_MODEL
            .with_label_values(&[&MODEL_SPECS[self.model.model_idx()]])
            .inc_by(batch_size as u64);
    }
    #[inline(always)]
    pub fn duration_past(&self, millis: u64) -> bool {
        self.queue_reset_ts.elapsed().as_millis() as u64 >= millis
    }
}

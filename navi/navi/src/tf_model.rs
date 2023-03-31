#[cfg(feature = "tf")]
pub mod tf {
    use arrayvec::ArrayVec;
    use itertools::Itertools;
    use log::{debug, error, info, warn};
    use prost::Message;
    use std::fmt;
    use std::fmt::Display;
    use std::string::String;
    use tensorflow::io::{RecordReader, RecordReadError};
    use tensorflow::Operation;
    use tensorflow::SavedModelBundle;
    use tensorflow::SessionOptions;
    use tensorflow::SessionRunArgs;
    use tensorflow::Tensor;
    use tensorflow::{DataType, FetchToken, Graph, TensorInfo, TensorType};

    use std::thread::sleep;
    use std::time::Duration;

    use crate::cli_args::{Args, ARGS, INPUTS, MODEL_SPECS, OUTPUTS};
    use crate::tf_proto::tensorflow_serving::prediction_log::LogType;
    use crate::tf_proto::tensorflow_serving::{PredictionLog, PredictLog};
    use crate::tf_proto::ConfigProto;
    use anyhow::{Context, Result};
    use serde_json::Value;

    use crate::TensorReturnEnum;
    use crate::bootstrap::{TensorInput, TensorInputEnum};
    use crate::metrics::{
        INFERENCE_FAILED_REQUESTS_BY_MODEL, NUM_REQUESTS_FAILED, NUM_REQUESTS_FAILED_BY_MODEL,
    };
    use crate::predict_service::Model;
    use crate::{MAX_NUM_INPUTS, utils};

    #[derive(Debug)]
    pub enum TFTensorEnum {
        String(Tensor<String>),
        Int(Tensor<i32>),
        Int64(Tensor<i64>),
        Float(Tensor<f32>),
        Double(Tensor<f64>),
        Boolean(Tensor<bool>),
    }

    #[derive(Debug)]
    pub struct TFModel {
        pub model_idx: usize,
        pub bundle: SavedModelBundle,
        pub input_names: ArrayVec<String, MAX_NUM_INPUTS>,
        pub input_info: Vec<TensorInfo>,
        pub input_ops: Vec<Operation>,
        pub output_names: Vec<String>,
        pub output_info: Vec<TensorInfo>,
        pub output_ops: Vec<Operation>,
        pub export_dir: String,
        pub version: i64,
        pub inter_op: i32,
        pub intra_op: i32,
    }

    impl Display for TFModel {
        fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
            write!(
                f,
                "idx: {}, tensorflow model_name:{}, export_dir:{}, version:{}, inter:{}, intra:{}",
                self.model_idx,
                MODEL_SPECS[self.model_idx],
                self.export_dir,
                self.version,
                self.inter_op,
                self.intra_op
            )
        }
    }

    impl TFModel {
        pub fn new(idx: usize, version: String, model_config: &Value) -> Result<TFModel> {
            // Create input variables for our addition
            let config = ConfigProto {
                intra_op_parallelism_threads: utils::get_config_or(
                    model_config,
                    "intra_op_parallelism",
                    &ARGS.intra_op_parallelism[idx],
                )
                .parse()?,
                inter_op_parallelism_threads: utils::get_config_or(
                    model_config,
                    "inter_op_parallelism",
                    &ARGS.inter_op_parallelism[idx],
                )
                .parse()?,
                ..Default::default()
            };
            let mut buf = Vec::new();
            buf.reserve(config.encoded_len());
            config.encode(&mut buf).unwrap();
            let mut opts = SessionOptions::new();
            opts.set_config(&buf)?;
            let export_dir = format!("{}/{}", ARGS.model_dir[idx], version);
            let mut graph = Graph::new();
            let bundle = SavedModelBundle::load(&opts, ["serve"], &mut graph, &export_dir)
                .context("error load model")?;
            let signature = bundle
                .meta_graph_def()
                .get_signature(&ARGS.serving_sig[idx])
                .context("error finding signature")?;
            let input_names = INPUTS[idx]
                .get_or_init(|| {
                    let input_spec = signature
                        .inputs()
                        .iter()
                        .map(|p| p.0.clone())
                        .collect::<ArrayVec<String, MAX_NUM_INPUTS>>();
                    info!(
                        "input not set from cli, now we set from model metadata:{:?}",
                        input_spec
                    );
                    input_spec
                })
                .clone();
            let input_info = input_names
                .iter()
                .map(|i| {
                    signature
                        .get_input(i)
                        .context("error finding input op info")
                        .unwrap()
                        .clone()
                })
                .collect_vec();

            let input_ops = input_info
                .iter()
                .map(|i| {
                    graph
                        .operation_by_name_required(&i.name().name)
                        .context("error finding input op")
                        .unwrap()
                })
                .collect_vec();

            info!("Model Input size: {}", input_info.len());

            let output_names = OUTPUTS[idx].to_vec().clone();

            let output_info = output_names
                .iter()
                .map(|o| {
                    signature
                        .get_output(o)
                        .context("error finding output op info")
                        .unwrap()
                        .clone()
                })
                .collect_vec();

            let output_ops = output_info
                .iter()
                .map(|o| {
                    graph
                        .operation_by_name_required(&o.name().name)
                        .context("error finding output op")
                        .unwrap()
                })
                .collect_vec();

            let tf_model = TFModel {
                model_idx: idx,
                bundle,
                input_names,
                input_info,
                input_ops,
                output_names,
                output_info,
                output_ops,
                export_dir,
                version: Args::version_str_to_epoch(&version)?,
                inter_op: config.inter_op_parallelism_threads,
                intra_op: config.intra_op_parallelism_threads,
            };
            tf_model.warmup()?;
            Ok(tf_model)
        }

        #[inline(always)]
        fn get_tftensor_dimensions<T>(
            t: &[T],
            input_size: u64,
            batch_size: u64,
            input_dims: Option<Vec<i64>>,
        ) -> Vec<u64> {
            // if input size is 1, we just specify a single dimension to outgoing tensor matching the
            // size of the input tensor. This is for backwards compatiblity with existing Navi clients
            // which specify input as a single string tensor (like tfexample) and use batching support.
            let mut dims = vec![];
            if input_size > 1 {
                if batch_size == 1 && input_dims.is_some() {
                    // client side batching is enabled?
                    input_dims
                        .unwrap()
                        .iter()
                        .for_each(|axis| dims.push(*axis as u64));
                } else {
                    dims.push(batch_size);
                    dims.push(t.len() as u64 / batch_size);
                }
            } else {
                dims.push(t.len() as u64);
            }
            dims
        }

        fn convert_to_tftensor_enum(
            input: TensorInput,
            input_size: u64,
            batch_size: u64,
        ) -> TFTensorEnum {
            match input.tensor_data {
                TensorInputEnum::String(t) => {
                    let strings = t
                        .into_iter()
                        .map(|x| unsafe { String::from_utf8_unchecked(x) })
                        .collect_vec();
                    TFTensorEnum::String(
                        Tensor::new(&TFModel::get_tftensor_dimensions(
                            strings.as_slice(),
                            input_size,
                            batch_size,
                            input.dims,
                        ))
                        .with_values(strings.as_slice())
                        .unwrap(),
                    )
                }
                TensorInputEnum::Int(t) => TFTensorEnum::Int(
                    Tensor::new(&TFModel::get_tftensor_dimensions(
                        t.as_slice(),
                        input_size,
                        batch_size,
                        input.dims,
                    ))
                    .with_values(t.as_slice())
                    .unwrap(),
                ),
                TensorInputEnum::Int64(t) => TFTensorEnum::Int64(
                    Tensor::new(&TFModel::get_tftensor_dimensions(
                        t.as_slice(),
                        input_size,
                        batch_size,
                        input.dims,
                    ))
                    .with_values(t.as_slice())
                    .unwrap(),
                ),
                TensorInputEnum::Float(t) => TFTensorEnum::Float(
                    Tensor::new(&TFModel::get_tftensor_dimensions(
                        t.as_slice(),
                        input_size,
                        batch_size,
                        input.dims,
                    ))
                    .with_values(t.as_slice())
                    .unwrap(),
                ),
                TensorInputEnum::Double(t) => TFTensorEnum::Double(
                    Tensor::new(&TFModel::get_tftensor_dimensions(
                        t.as_slice(),
                        input_size,
                        batch_size,
                        input.dims,
                    ))
                    .with_values(t.as_slice())
                    .unwrap(),
                ),
                TensorInputEnum::Boolean(t) => TFTensorEnum::Boolean(
                    Tensor::new(&TFModel::get_tftensor_dimensions(
                        t.as_slice(),
                        input_size,
                        batch_size,
                        input.dims,
                    ))
                    .with_values(t.as_slice())
                    .unwrap(),
                ),
            }
        }
        fn fetch_output<T: TensorType>(
            args: &mut SessionRunArgs,
            token_output: &FetchToken,
            batch_size: u64,
            output_size: u64,
        ) -> (Tensor<T>, u64) {
            let tensor_output = args.fetch::<T>(*token_output).expect("fetch output failed");
            let mut tensor_width = tensor_output.dims()[1];
            if batch_size == 1 && output_size > 1 {
                tensor_width = tensor_output.dims().iter().fold(1, |mut total, &val| {
                    total *= val;
                    total
                });
            }
            (tensor_output, tensor_width)
        }
    }

    impl Model for TFModel {
        fn warmup(&self) -> Result<()> {
            // warm up
            let warmup_file = format!(
                "{}/assets.extra/tf_serving_warmup_requests",
                self.export_dir
            );
            if std::path::Path::new(&warmup_file).exists() {
                use std::io::Cursor;
                info!(
                    "found warmup assets in {}, now perform warming up",
                    warmup_file
                );
                let f = std::fs::File::open(warmup_file).context("cannot open warmup file")?;
                // let mut buf = Vec::new();
                let read = std::io::BufReader::new(f);
                let mut reader = RecordReader::new(read);
                let mut warmup_cnt = 0;
                loop {
                    let next = reader.read_next_owned();
                    match next {
                        Ok(res) => match res {
                            Some(vec) => {
                                // info!("read one tfRecord");
                                match PredictionLog::decode(&mut Cursor::new(vec))
                                    .context("can't parse PredictonLog")?
                                {
                                    PredictionLog {
                                        log_metadata: _,
                                        log_type:
                                            Some(LogType::PredictLog(PredictLog {
                                                request: Some(mut req),
                                                response: _,
                                            })),
                                    } => {
                                        if warmup_cnt == ARGS.max_warmup_records {
                                            //warm up to max_warmup_records  records
                                            warn!(
                                                "reached max warmup {} records, exit warmup for {}",
                                                ARGS.max_warmup_records,
                                                MODEL_SPECS[self.model_idx]
                                            );
                                            break;
                                        }
                                        self.do_predict(
                                            vec![req.take_input_vals(&self.input_names)],
                                            1,
                                        );
                                        sleep(Duration::from_millis(100));
                                        warmup_cnt += 1;
                                    }
                                    _ => error!("some wrong record in warming up file"),
                                }
                            }
                            None => {
                                info!("end of warmup file, warmed up with records: {}", warmup_cnt);
                                break;
                            }
                        },
                        Err(RecordReadError::CorruptFile)
                        | Err(RecordReadError::IoError { .. }) => {
                            error!("read tfrecord error for warmup files, skip");
                        }
                        _ => {}
                    }
                }
            }
            Ok(())
        }

        #[inline(always)]
        fn do_predict(
            &self,
            input_tensors: Vec<Vec<TensorInput>>,
            batch_size: u64,
        ) -> (Vec<TensorReturnEnum>, Vec<Vec<usize>>) {
            // let mut batch_ends = input_tensors.iter().map(|t| t.len()).collect::<Vec<usize>>();
            let output_size = self.output_names.len() as u64;
            let input_size = self.input_names.len() as u64;
            debug!(
                "Request for Tensorflow with batch size: {} and input_size: {}",
                batch_size, input_size
            );
            // build a set of input TF tensors

            let batch_end = (1usize..=input_tensors.len() as usize)
                .into_iter()
                .collect_vec();
            let mut batch_ends = vec![batch_end; output_size as usize];

            let batched_tensors = TensorInputEnum::merge_batch(input_tensors)
                .into_iter()
                .enumerate()
                .map(|(_, i)| TFModel::convert_to_tftensor_enum(i, input_size, batch_size))
                .collect_vec();

            let mut args = SessionRunArgs::new();
            for (index, tf_tensor) in batched_tensors.iter().enumerate() {
                match tf_tensor {
                    TFTensorEnum::String(inner) => args.add_feed(&self.input_ops[index], 0, inner),
                    TFTensorEnum::Int(inner) => args.add_feed(&self.input_ops[index], 0, inner),
                    TFTensorEnum::Int64(inner) => args.add_feed(&self.input_ops[index], 0, inner),
                    TFTensorEnum::Float(inner) => args.add_feed(&self.input_ops[index], 0, inner),
                    TFTensorEnum::Double(inner) => args.add_feed(&self.input_ops[index], 0, inner),
                    TFTensorEnum::Boolean(inner) => args.add_feed(&self.input_ops[index], 0, inner),
                }
            }
            // For output ops, we receive the same op object by name. Actual tensor tokens are available at different offsets.
            // Since indices are ordered, its important to specify output flag to Navi in the same order.
            let token_outputs = self
                .output_ops
                .iter()
                .enumerate()
                .map(|(idx, op)| args.request_fetch(op, idx as i32))
                .collect_vec();
            match self.bundle.session.run(&mut args) {
                Ok(_) => (),
                Err(e) => {
                    NUM_REQUESTS_FAILED.inc_by(batch_size);
                    NUM_REQUESTS_FAILED_BY_MODEL
                        .with_label_values(&[&MODEL_SPECS[self.model_idx]])
                        .inc_by(batch_size);
                    INFERENCE_FAILED_REQUESTS_BY_MODEL
                        .with_label_values(&[&MODEL_SPECS[self.model_idx]])
                        .inc_by(batch_size);
                    panic!("{model}: {e:?}", model = MODEL_SPECS[self.model_idx], e = e);
                }
            }
            let mut predict_return = vec![];
            // Check the output.
            for (index, token_output) in token_outputs.iter().enumerate() {
                // same ops, with type info at different offsets.
                let (res, width) = match self.output_ops[index].output_type(index) {
                    DataType::Float => {
                        let (tensor_output, tensor_width) =
                            TFModel::fetch_output(&mut args, token_output, batch_size, output_size);
                        (
                            TensorReturnEnum::FloatTensorReturn(Box::new(tensor_output)),
                            tensor_width,
                        )
                    }
                    DataType::Int64 => {
                        let (tensor_output, tensor_width) =
                            TFModel::fetch_output(&mut args, token_output, batch_size, output_size);
                        (
                            TensorReturnEnum::Int64TensorReturn(Box::new(tensor_output)),
                            tensor_width,
                        )
                    }
                    DataType::Int32 => {
                        let (tensor_output, tensor_width) =
                            TFModel::fetch_output(&mut args, token_output, batch_size, output_size);
                        (
                            TensorReturnEnum::Int32TensorReturn(Box::new(tensor_output)),
                            tensor_width,
                        )
                    }
                    DataType::String => {
                        let (tensor_output, tensor_width) =
                            TFModel::fetch_output(&mut args, token_output, batch_size, output_size);
                        (
                            TensorReturnEnum::StringTensorReturn(Box::new(tensor_output)),
                            tensor_width,
                        )
                    }
                    _ => panic!("Unsupported return type!"),
                };
                let width = width as usize;
                for b in batch_ends[index].iter_mut() {
                    *b *= width;
                }
                predict_return.push(res)
            }
            //TODO: remove in the future
            //TODO: support actual mtl model outputs
            (predict_return, batch_ends)
        }
        #[inline(always)]
        fn model_idx(&self) -> usize {
            self.model_idx
        }
        #[inline(always)]
        fn version(&self) -> i64 {
            self.version
        }
    }
}

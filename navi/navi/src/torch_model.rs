#[cfg(feature = "torch")]
pub mod torch {
    use std::fmt;
    use std::fmt::Display;
    use std::string::String;

    use crate::TensorReturnEnum;
    use crate::SerializedInput;
    use crate::bootstrap::TensorInput;
    use crate::cli_args::{Args, ARGS, MODEL_SPECS};
    use crate::metrics;
    use crate::metrics::{
        INFERENCE_FAILED_REQUESTS_BY_MODEL, NUM_REQUESTS_FAILED, NUM_REQUESTS_FAILED_BY_MODEL,
    };
    use crate::predict_service::Model;
    use anyhow::Result;
    use dr_transform::converter::BatchPredictionRequestToTorchTensorConverter;
    use dr_transform::converter::Converter;
    use serde_json::Value;
    use tch::Tensor;
    use tch::{kind, CModule, IValue};

    #[derive(Debug)]
    pub struct TorchModel {
        pub model_idx: usize,
        pub version: i64,
        pub module: CModule,
        pub export_dir: String,
        // FIXME: make this Box<Option<..>> so input converter can be optional.
        // Also consider adding output_converter.
        pub input_converter: Box<dyn Converter>,
    }

    impl Display for TorchModel {
        fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
            write!(
                f,
                "idx: {}, torch model_name:{}, version:{}",
                self.model_idx, MODEL_SPECS[self.model_idx], self.version
            )
        }
    }

    impl TorchModel {
        pub fn new(idx: usize, version: String, _model_config: &Value) -> Result<TorchModel> {
            let export_dir = format!("{}/{}/model.pt", ARGS.model_dir[idx], version);
            let model = CModule::load(&export_dir).unwrap();
            let torch_model = TorchModel {
                model_idx: idx,
                version: Args::version_str_to_epoch(&version)?,
                module: model,
                export_dir,
                //TODO: move converter lookup in a registry.
                input_converter: Box::new(BatchPredictionRequestToTorchTensorConverter::new(
                    &ARGS.model_dir[idx].as_str(),
                    version.as_str(),
                    vec![],
                    Some(&metrics::register_dynamic_metrics),
                )),
            };

            torch_model.warmup()?;
            Ok(torch_model)
        }
        #[inline(always)]
        pub fn decode_to_inputs(bytes: SerializedInput) -> Vec<Tensor> {
            //FIXME: for now we generate 4 random tensors as inputs to unblock end to end testing
            //when Shajan's decoder is ready we will swap
            let row = bytes.len() as i64;
            let t1 = Tensor::randn(&[row, 5293], kind::FLOAT_CPU); //continuous
            let t2 = Tensor::randint(10, &[row, 149], kind::INT64_CPU); //binary
            let t3 = Tensor::randint(10, &[row, 320], kind::INT64_CPU); //discrete
            let t4 = Tensor::randn(&[row, 200], kind::FLOAT_CPU); //user_embedding
            let t5 = Tensor::randn(&[row, 200], kind::FLOAT_CPU); //user_eng_embedding
            let t6 = Tensor::randn(&[row, 200], kind::FLOAT_CPU); //author_embedding

            vec![t1, t2, t3, t4, t5, t6]
        }
        #[inline(always)]
        pub fn output_to_vec(res: IValue, dst: &mut Vec<f32>) {
            match res {
                IValue::Tensor(tensor) => TorchModel::tensors_to_vec(&[tensor], dst),
                IValue::Tuple(ivalues) => {
                    TorchModel::tensors_to_vec(&TorchModel::ivalues_to_tensors(ivalues), dst)
                }
                _ => panic!("we only support output as a single tensor or a vec of tensors"),
            }
        }
        #[inline(always)]
        pub fn tensor_flatten_size(t: &Tensor) -> usize {
            t.size().into_iter().fold(1, |acc, x| acc * x) as usize
        }
        #[inline(always)]
        pub fn tensor_to_vec<T: kind::Element>(res: &Tensor) -> Vec<T> {
            let size = TorchModel::tensor_flatten_size(res);
            let mut res_f32: Vec<T> = Vec::with_capacity(size);
            unsafe {
                res_f32.set_len(size);
            }
            res.copy_data(res_f32.as_mut_slice(), size);
            // println!("Copied tensor:{}, {:?}", res_f32.len(), res_f32);
            res_f32
        }
        #[inline(always)]
        pub fn tensors_to_vec(tensors: &[Tensor], dst: &mut Vec<f32>) {
            let mut offset = dst.len();
            tensors.iter().for_each(|t| {
                let size = TorchModel::tensor_flatten_size(t);
                let next_size = offset + size;
                unsafe {
                    dst.set_len(next_size);
                }
                t.copy_data(&mut dst[offset..], size);
                offset = next_size;
            });
        }
        pub fn ivalues_to_tensors(ivalues: Vec<IValue>) -> Vec<Tensor> {
            ivalues
                .into_iter()
                .map(|t| {
                    if let IValue::Tensor(vanilla_t) = t {
                        vanilla_t
                    } else {
                        panic!("not a tensor")
                    }
                })
                .collect::<Vec<Tensor>>()
        }
    }

    impl Model for TorchModel {
        fn warmup(&self) -> Result<()> {
            Ok(())
        }
        //TODO: torch runtime needs some refactor to make it a generic interface
        #[inline(always)]
        fn do_predict(
            &self,
            input_tensors: Vec<Vec<TensorInput>>,
            total_len: u64,
        ) -> (Vec<TensorReturnEnum>, Vec<Vec<usize>>) {
            let mut buf: Vec<f32> = Vec::with_capacity(10_000);
            let mut batch_ends = vec![0usize; input_tensors.len()];
            for (i, batch_bytes_in_request) in input_tensors.into_iter().enumerate() {
                for _ in batch_bytes_in_request.into_iter() {
                    //FIXME: for now use some hack
                    let model_input = TorchModel::decode_to_inputs(vec![0u8; 30]); //self.input_converter.convert(bytes);
                    let input_batch_tensors = model_input
                        .into_iter()
                        .map(|t| IValue::Tensor(t))
                        .collect::<Vec<IValue>>();
                    // match self.module.forward_is(&input_batch_tensors) {
                    match self.module.method_is("forward_serve", &input_batch_tensors) {
                        Ok(res) => TorchModel::output_to_vec(res, &mut buf),
                        Err(e) => {
                            NUM_REQUESTS_FAILED.inc_by(total_len);
                            NUM_REQUESTS_FAILED_BY_MODEL
                                .with_label_values(&[&MODEL_SPECS[self.model_idx]])
                                .inc_by(total_len);
                            INFERENCE_FAILED_REQUESTS_BY_MODEL
                                .with_label_values(&[&MODEL_SPECS[self.model_idx]])
                                .inc_by(total_len);
                            panic!("{model}: {e:?}", model = MODEL_SPECS[self.model_idx], e = e);
                        }
                    }
                }
                batch_ends[i] = buf.len();
            }
            (
                vec![TensorReturnEnum::FloatTensorReturn(Box::new(buf))],
                vec![batch_ends],
            )
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

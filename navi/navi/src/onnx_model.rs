#[cfg(feature = "onnx")]
pub mod onnx {
    use crate::TensorReturnEnum;
    use crate::bootstrap::{TensorInput, TensorInputEnum};
    use crate::cli_args::{
        Args, ARGS, INPUTS, MODEL_SPECS, OUTPUTS,
    };
    use crate::metrics::{self, CONVERTER_TIME_COLLECTOR};
    use crate::predict_service::Model;
    use crate::{MAX_NUM_INPUTS, MAX_NUM_OUTPUTS, META_INFO, utils};
    use anyhow::Result;
    use arrayvec::ArrayVec;
    use dr_transform::converter::{BatchPredictionRequestToTorchTensorConverter, Converter};
    use itertools::Itertools;
    use log::{debug, info};
    use dr_transform::ort::environment::Environment;
    use dr_transform::ort::session::Session;
    use dr_transform::ort::tensor::InputTensor;
    use dr_transform::ort::{ExecutionProvider, GraphOptimizationLevel, SessionBuilder};
    use dr_transform::ort::LoggingLevel;
    use serde_json::Value;
    use std::fmt::{Debug, Display};
    use std::sync::Arc;
    use std::{fmt, fs};
    use tokio::time::Instant;
    lazy_static! {
        pub static ref ENVIRONMENT: Arc<Environment> = Arc::new(
            Environment::builder()
                .with_name("onnx home")
                .with_log_level(LoggingLevel::Error)
                .with_global_thread_pool(ARGS.onnx_global_thread_pool_options.clone())
                .build()
                .unwrap()
        );
    }
    #[derive(Debug)]
    pub struct OnnxModel {
        pub session: Session,
        pub model_idx: usize,
        pub version: i64,
        pub export_dir: String,
        pub output_filters: ArrayVec<usize, MAX_NUM_OUTPUTS>,
        pub input_converter: Box<dyn Converter>,
    }
    impl Display for OnnxModel {
        fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
            write!(
                f,
                "idx: {}, onnx model_name:{}, version:{}, output_filters:{:?}, converter:{:}",
                self.model_idx,
                MODEL_SPECS[self.model_idx],
                self.version,
                self.output_filters,
                self.input_converter
            )
        }
    }
    impl Drop for OnnxModel {
        fn drop(&mut self) {
            if ARGS.profiling != None {
                self.session.end_profiling().map_or_else(
                    |e| {
                        info!("end profiling with some error:{:?}", e);
                    },
                    |f| {
                        info!("profiling ended with file:{}", f);
                    },
                );
            }
        }
    }
    impl OnnxModel {
        fn get_output_filters(session: &Session, idx: usize) -> ArrayVec<usize, MAX_NUM_OUTPUTS> {
            OUTPUTS[idx]
                .iter()
                .map(|output| session.outputs.iter().position(|o| o.name == *output))
                .flatten()
                .collect::<ArrayVec<usize, MAX_NUM_OUTPUTS>>()
        }
        #[cfg(target_os = "linux")]
        fn ep_choices() -> Vec<ExecutionProvider> {
            match ARGS.onnx_gpu_ep.as_ref().map(|e| e.as_str()) {
                Some("onednn") => vec![Self::ep_with_options(ExecutionProvider::onednn())],
                Some("tensorrt") => vec![Self::ep_with_options(ExecutionProvider::tensorrt())],
                Some("cuda") => vec![Self::ep_with_options(ExecutionProvider::cuda())],
                _ => vec![Self::ep_with_options(ExecutionProvider::cpu())],
            }
        }
        fn ep_with_options(mut ep: ExecutionProvider) -> ExecutionProvider {
            for (ref k, ref v) in ARGS.onnx_ep_options.clone() {
                ep = ep.with(k, v);
                info!("setting option:{} -> {} and now ep is:{:?}", k, v, ep);
            }
            ep
        }
        #[cfg(target_os = "macos")]
        fn ep_choices() -> Vec<ExecutionProvider> {
            vec![Self::ep_with_options(ExecutionProvider::cpu())]
        }
        pub fn new(idx: usize, version: String, model_config: &Value) -> Result<OnnxModel> {
            let export_dir = format!("{}/{}/model.onnx", ARGS.model_dir[idx], version);
            let meta_info = format!("{}/{}/{}", ARGS.model_dir[idx], version, META_INFO);
            let mut builder = SessionBuilder::new(&ENVIRONMENT)?
                .with_optimization_level(GraphOptimizationLevel::Level3)?
                .with_parallel_execution(ARGS.onnx_use_parallel_mode == "true")?;
            if ARGS.onnx_global_thread_pool_options.is_empty() {
                builder = builder
                    .with_inter_threads(
                        utils::get_config_or(
                            model_config,
                            "inter_op_parallelism",
                            &ARGS.inter_op_parallelism[idx],
                        )
                            .parse()?,
                    )?
                    .with_intra_threads(
                        utils::get_config_or(
                            model_config,
                            "intra_op_parallelism",
                            &ARGS.intra_op_parallelism[idx],
                        )
                            .parse()?,
                    )?;
            }
            else {
                builder = builder.with_disable_per_session_threads()?;
            }
            builder = builder
                .with_memory_pattern(ARGS.onnx_use_memory_pattern == "true")?
                .with_execution_providers(&OnnxModel::ep_choices())?;
            match &ARGS.profiling {
                Some(p) => {
                    debug!("Enable profiling, writing to {}", *p);
                    builder = builder.with_profiling(p)?
                }
                _ => {}
            }
            let session = builder.with_model_from_file(&export_dir)?;

            info!(
                "inputs: {:?}, outputs: {:?}",
                session.inputs.iter().format(","),
                session.outputs.iter().format(",")
            );

            fs::read_to_string(&meta_info)
                .ok()
                .map(|info| info!("meta info:{}", info));
            let output_filters = OnnxModel::get_output_filters(&session, idx);
            let mut reporting_feature_ids: Vec<(i64, &str)> = vec![];

            let input_spec_cell = &INPUTS[idx];
            if input_spec_cell.get().is_none() {
                let input_spec = session
                    .inputs
                    .iter()
                    .map(|input| input.name.clone())
                    .collect::<ArrayVec<String, MAX_NUM_INPUTS>>();
                input_spec_cell.set(input_spec.clone()).map_or_else(
                    |_| info!("unable to set the input_spec for model {}", idx),
                    |_| info!("auto detect and set the inputs: {:?}", input_spec),
                );
            }
            ARGS.onnx_report_discrete_feature_ids
                .iter()
                .for_each(|ids| {
                    ids.split(",")
                        .filter(|s| !s.is_empty())
                        .map(|s| s.parse::<i64>().unwrap())
                        .for_each(|id| reporting_feature_ids.push((id, "discrete")))
                });
            ARGS.onnx_report_continuous_feature_ids
                .iter()
                .for_each(|ids| {
                    ids.split(",")
                        .filter(|s| !s.is_empty())
                        .map(|s| s.parse::<i64>().unwrap())
                        .for_each(|id| reporting_feature_ids.push((id, "continuous")))
                });

            let onnx_model = OnnxModel {
                session,
                model_idx: idx,
                version: Args::version_str_to_epoch(&version)?,
                export_dir,
                output_filters,
                input_converter: Box::new(BatchPredictionRequestToTorchTensorConverter::new(
                    &ARGS.model_dir[idx],
                    &version,
                    reporting_feature_ids,
                    Some(metrics::register_dynamic_metrics),
                )?),
            };
            onnx_model.warmup()?;
            Ok(onnx_model)
        }
    }
    ///Currently we only assume the input as just one string tensor.
    ///The string tensor will be be converted to the actual raw tensors.
    /// The converter we are using is very specific to home.
    /// It reads a BatchDataRecord thrift and decode it to a batch of raw input tensors.
    /// Navi will then do server side batching and feed it to ONNX runtime
    impl Model for OnnxModel {
        //TODO: implement a generic online warmup for all runtimes
        fn warmup(&self) -> Result<()> {
            Ok(())
        }

        #[inline(always)]
        fn do_predict(
            &self,
            input_tensors: Vec<Vec<TensorInput>>,
            _: u64,
        ) -> (Vec<TensorReturnEnum>, Vec<Vec<usize>>) {
            let batched_tensors = TensorInputEnum::merge_batch(input_tensors);
            let (inputs, batch_ends): (Vec<Vec<InputTensor>>, Vec<Vec<usize>>) = batched_tensors
                .into_iter()
                .map(|batched_tensor| {
                    match batched_tensor.tensor_data {
                        TensorInputEnum::String(t) if ARGS.onnx_use_converter.is_some() => {
                            let start = Instant::now();
                            let (inputs, batch_ends) = self.input_converter.convert(t);
                            // info!("batch_ends:{:?}", batch_ends);
                            CONVERTER_TIME_COLLECTOR
                                .with_label_values(&[&MODEL_SPECS[self.model_idx()]])
                                .observe(
                                    start.elapsed().as_micros() as f64
                                        / (*batch_ends.last().unwrap() as f64),
                                );
                            (inputs, batch_ends)
                        }
                        _ => unimplemented!(),
                    }
                })
                .unzip();
            //invariant we only support one input as string. will relax later
            assert_eq!(inputs.len(), 1);
            let output_tensors = self
                .session
                .run(inputs.into_iter().flatten().collect::<Vec<_>>())
                .unwrap();
            self.output_filters
                .iter()
                .map(|&idx| {
                    let mut size = 1usize;
                    let output = output_tensors[idx].try_extract::<f32>().unwrap();
                    for &dim in self.session.outputs[idx].dimensions.iter().flatten() {
                        size *= dim as usize;
                    }
                    let tensor_ends = batch_ends[0]
                        .iter()
                        .map(|&batch| batch * size)
                        .collect::<Vec<_>>();

                    (
                        //only works for batch major
                        //TODO: to_vec() obviously wasteful, especially for large batches(GPU) . Will refactor to
                        //break up output and return Vec<Vec<TensorScore>> here
                        TensorReturnEnum::FloatTensorReturn(Box::new(output.view().as_slice().unwrap().to_vec(),
                        )),
                        tensor_ends,
                    )
                })
                .unzip()
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

#[cfg(feature = "onntwitter")]
pub mod onntwitter {
    use crate::TensorReturnEnum;
    use crate::bootstrap::{TensorInput, TensorInputEnum};
    use crate::cli_args::{
        Args, ARGS, INPUTS, MODEL_SPECS, OUTPUTS,
    };
    use crate::metrics::{self, CONVERTER_TIME_COLLECTOR};
    use crate::predict_service::Model;
    use crate::{MAtwitter_NUM_INPUTS, MAtwitter_NUM_OUTPUTS, META_INFO, utils};
    use anyhow::Result;
    use arrayvec::ArrayVec;
    use dr_transform::converter::{BatchPredictionRequestToTorchTensorConverter, Converter};
    use itertools::Itertools;
    use log::{debug, info};
    use dr_transform::ort::environment::Environment;
    use dr_transform::ort::session::Session;
    use dr_transform::ort::tensor::InputTensor;
    use dr_transform::ort::{EtwitterecutionProvider, GraphOptimizationLevel, SessionBuilder};
    use dr_transform::ort::LoggingLevel;
    use serde_json::Value;
    use std::fmt::{Debug, Display};
    use std::sync::Arc;
    use std::{fmt, fs};
    use tokio::time::Instant;
    lazy_static! {
        pub static ref ENVIRONMENT: Arc<Environment> = Arc::new(
            Environment::builder()
                .with_name("onntwitter home")
                .with_log_level(LoggingLevel::Error)
                .with_global_thread_pool(ARGS.onntwitter_global_thread_pool_options.clone())
                .build()
                .unwrap()
        );
    }
    #[derive(Debug)]
    pub struct OnntwitterModel {
        pub session: Session,
        pub model_idtwitter: usize,
        pub version: i64,
        pub etwitterport_dir: String,
        pub output_filters: ArrayVec<usize, MAtwitter_NUM_OUTPUTS>,
        pub input_converter: Botwitter<dyn Converter>,
    }
    impl Display for OnntwitterModel {
        fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
            write!(
                f,
                "idtwitter: {}, onntwitter model_name:{}, version:{}, output_filters:{:?}, converter:{:}",
                self.model_idtwitter,
                MODEL_SPECS[self.model_idtwitter],
                self.version,
                self.output_filters,
                self.input_converter
            )
        }
    }
    impl Drop for OnntwitterModel {
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
    impl OnntwitterModel {
        fn get_output_filters(session: &Session, idtwitter: usize) -> ArrayVec<usize, MAtwitter_NUM_OUTPUTS> {
            OUTPUTS[idtwitter]
                .iter()
                .map(|output| session.outputs.iter().position(|o| o.name == *output))
                .flatten()
                .collect::<ArrayVec<usize, MAtwitter_NUM_OUTPUTS>>()
        }
        #[cfg(target_os = "linutwitter")]
        fn ep_choices() -> Vec<EtwitterecutionProvider> {
            match ARGS.onntwitter_gpu_ep.as_ref().map(|e| e.as_str()) {
                Some("onednn") => vec![Self::ep_with_options(EtwitterecutionProvider::onednn())],
                Some("tensorrt") => vec![Self::ep_with_options(EtwitterecutionProvider::tensorrt())],
                Some("cuda") => vec![Self::ep_with_options(EtwitterecutionProvider::cuda())],
                _ => vec![Self::ep_with_options(EtwitterecutionProvider::cpu())],
            }
        }
        fn ep_with_options(mut ep: EtwitterecutionProvider) -> EtwitterecutionProvider {
            for (ref k, ref v) in ARGS.onntwitter_ep_options.clone() {
                ep = ep.with(k, v);
                info!("setting option:{} -> {} and now ep is:{:?}", k, v, ep);
            }
            ep
        }
        #[cfg(target_os = "macos")]
        fn ep_choices() -> Vec<EtwitterecutionProvider> {
            vec![Self::ep_with_options(EtwitterecutionProvider::cpu())]
        }
        pub fn new(idtwitter: usize, version: String, model_config: &Value) -> Result<OnntwitterModel> {
            let etwitterport_dir = format!("{}/{}/model.onntwitter", ARGS.model_dir[idtwitter], version);
            let meta_info = format!("{}/{}/{}", ARGS.model_dir[idtwitter], version, META_INFO);
            let mut builder = SessionBuilder::new(&ENVIRONMENT)?
                .with_optimization_level(GraphOptimizationLevel::Level3)?
                .with_parallel_etwitterecution(ARGS.onntwitter_use_parallel_mode == "true")?;
            if ARGS.onntwitter_global_thread_pool_options.is_empty() {
                builder = builder
                    .with_inter_threads(
                        utils::get_config_or(
                            model_config,
                            "inter_op_parallelism",
                            &ARGS.inter_op_parallelism[idtwitter],
                        )
                            .parse()?,
                    )?
                    .with_intra_threads(
                        utils::get_config_or(
                            model_config,
                            "intra_op_parallelism",
                            &ARGS.intra_op_parallelism[idtwitter],
                        )
                            .parse()?,
                    )?;
            }
            else {
                builder = builder.with_disable_per_session_threads()?;
            }
            builder = builder
                .with_memory_pattern(ARGS.onntwitter_use_memory_pattern == "true")?
                .with_etwitterecution_providers(&OnntwitterModel::ep_choices())?;
            match &ARGS.profiling {
                Some(p) => {
                    debug!("Enable profiling, writing to {}", *p);
                    builder = builder.with_profiling(p)?
                }
                _ => {}
            }
            let session = builder.with_model_from_file(&etwitterport_dir)?;

            info!(
                "inputs: {:?}, outputs: {:?}",
                session.inputs.iter().format(","),
                session.outputs.iter().format(",")
            );

            fs::read_to_string(&meta_info)
                .ok()
                .map(|info| info!("meta info:{}", info));
            let output_filters = OnntwitterModel::get_output_filters(&session, idtwitter);
            let mut reporting_feature_ids: Vec<(i64, &str)> = vec![];

            let input_spec_cell = &INPUTS[idtwitter];
            if input_spec_cell.get().is_none() {
                let input_spec = session
                    .inputs
                    .iter()
                    .map(|input| input.name.clone())
                    .collect::<ArrayVec<String, MAtwitter_NUM_INPUTS>>();
                input_spec_cell.set(input_spec.clone()).map_or_else(
                    |_| info!("unable to set the input_spec for model {}", idtwitter),
                    |_| info!("auto detect and set the inputs: {:?}", input_spec),
                );
            }
            ARGS.onntwitter_report_discrete_feature_ids
                .iter()
                .for_each(|ids| {
                    ids.split(",")
                        .filter(|s| !s.is_empty())
                        .map(|s| s.parse::<i64>().unwrap())
                        .for_each(|id| reporting_feature_ids.push((id, "discrete")))
                });
            ARGS.onntwitter_report_continuous_feature_ids
                .iter()
                .for_each(|ids| {
                    ids.split(",")
                        .filter(|s| !s.is_empty())
                        .map(|s| s.parse::<i64>().unwrap())
                        .for_each(|id| reporting_feature_ids.push((id, "continuous")))
                });

            let onntwitter_model = OnntwitterModel {
                session,
                model_idtwitter: idtwitter,
                version: Args::version_str_to_epoch(&version)?,
                etwitterport_dir,
                output_filters,
                input_converter: Botwitter::new(BatchPredictionRequestToTorchTensorConverter::new(
                    &ARGS.model_dir[idtwitter],
                    &version,
                    reporting_feature_ids,
                    Some(metrics::register_dynamic_metrics),
                )?),
            };
            onntwitter_model.warmup()?;
            Ok(onntwitter_model)
        }
    }
    ///Currently we only assume the input as just one string tensor.
    ///The string tensor will be be converted to the actual raw tensors.
    /// The converter we are using is very specific to home.
    /// It reads a BatchDataRecord thrift and decode it to a batch of raw input tensors.
    /// Navi will then do server side batching and feed it to ONNtwitter runtime
    impl Model for OnntwitterModel {
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
                        TensorInputEnum::String(t) if ARGS.onntwitter_use_converter.is_some() => {
                            let start = Instant::now();
                            let (inputs, batch_ends) = self.input_converter.convert(t);
                            // info!("batch_ends:{:?}", batch_ends);
                            CONVERTER_TIME_COLLECTOR
                                .with_label_values(&[&MODEL_SPECS[self.model_idtwitter()]])
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
            //invariant we only support one input as string. will relatwitter later
            assert_eq!(inputs.len(), 1);
            let output_tensors = self
                .session
                .run(inputs.into_iter().flatten().collect::<Vec<_>>())
                .unwrap();
            self.output_filters
                .iter()
                .map(|&idtwitter| {
                    let mut size = 1usize;
                    let output = output_tensors[idtwitter].try_etwittertract::<f32>().unwrap();
                    for &dim in self.session.outputs[idtwitter].dimensions.iter().flatten() {
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
                        TensorReturnEnum::FloatTensorReturn(Botwitter::new(output.view().as_slice().unwrap().to_vec(),
                        )),
                        tensor_ends,
                    )
                })
                .unzip()
        }
        #[inline(always)]
        fn model_idtwitter(&self) -> usize {
            self.model_idtwitter
        }
        #[inline(always)]
        fn version(&self) -> i64 {
            self.version
        }
    }
}

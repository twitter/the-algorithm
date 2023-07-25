use anyhow::Result;
use log::{info, warn};
use x509_parser::{prelude::{parse_x509_pem}, parse_x509_certificate};
use std::collections::HashMap;
use tokio::time::Instant;
use tonic::{
    Request,
    Response, Status, transport::{Certificate, Identity, Server, ServerTlsConfig},
};

// protobuf related
use crate::tf_proto::tensorflow_serving::{
    ClassificationRequest, ClassificationResponse, GetModelMetadataRequest,
    GetModelMetadataResponse, MultiInferenceRequest, MultiInferenceResponse, PredictRequest,
    PredictResponse, RegressionRequest, RegressionResponse,
};
use crate::{kf_serving::{
    grpc_inference_service_server::GrpcInferenceService, ModelInferRequest, ModelInferResponse,
    ModelMetadataRequest, ModelMetadataResponse, ModelReadyRequest, ModelReadyResponse,
    ServerLiveRequest, ServerLiveResponse, ServerMetadataRequest, ServerMetadataResponse,
    ServerReadyRequest, ServerReadyResponse,
}, ModelFactory, tf_proto::tensorflow_serving::prediction_service_server::{
    PredictionService, PredictionServiceServer,
}, VERSION, NAME};

use crate::PredictResult;
use crate::cli_args::{ARGS, INPUTS, OUTPUTS};
use crate::metrics::{
    NAVI_VERSION, NUM_PREDICTIONS, NUM_REQUESTS_FAILED, NUM_REQUESTS_FAILED_BY_MODEL,
    NUM_REQUESTS_RECEIVED, NUM_REQUESTS_RECEIVED_BY_MODEL, RESPONSE_TIME_COLLECTOR,
    CERT_EXPIRY_EPOCH
};
use crate::predict_service::{Model, PredictService};
use crate::tf_proto::tensorflow_serving::model_spec::VersionChoice::Version;
use crate::tf_proto::tensorflow_serving::ModelSpec;

#[derive(Debug)]
pub enum TensorInputEnum {
    String(Vec<Vec<u8>>),
    Int(Vec<i32>),
    Int64(Vec<i64>),
    Float(Vec<f32>),
    Double(Vec<f64>),
    Boolean(Vec<bool>),
}

#[derive(Debug)]
pub struct TensorInput {
    pub tensor_data: TensorInputEnum,
    pub name: String,
    pub dims: Option<Vec<i64>>,
}

impl TensorInput {
    pub fn new(tensor_data: TensorInputEnum, name: String, dims: Option<Vec<i64>>) -> TensorInput {
        TensorInput {
            tensor_data,
            name,
            dims,
        }
    }
}

impl TensorInputEnum {
    #[inline(always)]
    pub(crate) fn extend(&mut self, another: TensorInputEnum) {
        match (self, another) {
            (Self::String(input), Self::String(ex)) => input.extend(ex),
            (Self::Int(input), Self::Int(ex)) => input.extend(ex),
            (Self::Int64(input), Self::Int64(ex)) => input.extend(ex),
            (Self::Float(input), Self::Float(ex)) => input.extend(ex),
            (Self::Double(input), Self::Double(ex)) => input.extend(ex),
            (Self::Boolean(input), Self::Boolean(ex)) => input.extend(ex),
            x => panic!("input enum type not matched. input:{:?}, ex:{:?}", x.0, x.1),
        }
    }
    #[inline(always)]
    pub(crate) fn merge_batch(input_tensors: Vec<Vec<TensorInput>>) -> Vec<TensorInput> {
        input_tensors
            .into_iter()
            .reduce(|mut acc, e| {
                for (i, ext) in acc.iter_mut().zip(e) {
                    i.tensor_data.extend(ext.tensor_data);
                }
                acc
            })
            .unwrap() //invariant: we expect there's always rows in input_tensors
    }
}


///entry point for tfServing gRPC
#[tonic::async_trait]
impl<T: Model> GrpcInferenceService for PredictService<T> {
    async fn server_live(
        &self,
        _request: Request<ServerLiveRequest>,
    ) -> Result<Response<ServerLiveResponse>, Status> {
        unimplemented!()
    }
    async fn server_ready(
        &self,
        _request: Request<ServerReadyRequest>,
    ) -> Result<Response<ServerReadyResponse>, Status> {
        unimplemented!()
    }

    async fn model_ready(
        &self,
        _request: Request<ModelReadyRequest>,
    ) -> Result<Response<ModelReadyResponse>, Status> {
        unimplemented!()
    }

    async fn server_metadata(
        &self,
        _request: Request<ServerMetadataRequest>,
    ) -> Result<Response<ServerMetadataResponse>, Status> {
        unimplemented!()
    }

    async fn model_metadata(
        &self,
        _request: Request<ModelMetadataRequest>,
    ) -> Result<Response<ModelMetadataResponse>, Status> {
        unimplemented!()
    }

    async fn model_infer(
        &self,
        _request: Request<ModelInferRequest>,
    ) -> Result<Response<ModelInferResponse>, Status> {
        unimplemented!()
    }
}

#[tonic::async_trait]
impl<T: Model> PredictionService for PredictService<T> {
    async fn classify(
        &self,
        _request: Request<ClassificationRequest>,
    ) -> Result<Response<ClassificationResponse>, Status> {
        unimplemented!()
    }
    async fn regress(
        &self,
        _request: Request<RegressionRequest>,
    ) -> Result<Response<RegressionResponse>, Status> {
        unimplemented!()
    }
    async fn predict(
        &self,
        request: Request<PredictRequest>,
    ) -> Result<Response<PredictResponse>, Status> {
        NUM_REQUESTS_RECEIVED.inc();
        let start = Instant::now();
        let mut req = request.into_inner();
        let (model_spec, version) = req.take_model_spec();
        NUM_REQUESTS_RECEIVED_BY_MODEL
            .with_label_values(&[&model_spec])
            .inc();
        let idx = PredictService::<T>::get_model_index(&model_spec).ok_or_else(|| {
            Status::failed_precondition(format!("model spec not found:{}", model_spec))
        })?;
        let input_spec = match INPUTS[idx].get() {
            Some(input) => input,
            _ => return Err(Status::not_found(format!("model input spec {}", idx))),
        };
        let input_val = req.take_input_vals(input_spec);
        self.predict(idx, version, input_val, start)
            .await
            .map_or_else(
                |e| {
                    NUM_REQUESTS_FAILED.inc();
                    NUM_REQUESTS_FAILED_BY_MODEL
                        .with_label_values(&[&model_spec])
                        .inc();
                    Err(Status::internal(e.to_string()))
                },
                |res| {
                    RESPONSE_TIME_COLLECTOR
                        .with_label_values(&[&model_spec])
                        .observe(start.elapsed().as_millis() as f64);

                    match res {
                        PredictResult::Ok(tensors, version) => {
                            let mut outputs = HashMap::new();
                            NUM_PREDICTIONS.with_label_values(&[&model_spec]).inc();
                            //FIXME: uncomment when prediction scores are normal
                            // PREDICTION_SCORE_SUM
                            // .with_label_values(&[&model_spec])
                            // .inc_by(tensors[0]as f64);
                            for (tp, output_name) in tensors
                                .into_iter()
                                .map(|tensor| tensor.create_tensor_proto())
                                .zip(OUTPUTS[idx].iter())
                            {
                                outputs.insert(output_name.to_owned(), tp);
                            }
                            let reply = PredictResponse {
                                model_spec: Some(ModelSpec {
                                    version_choice: Some(Version(version)),
                                    ..Default::default()
                                }),
                                outputs,
                            };
                            Ok(Response::new(reply))
                        }
                        PredictResult::DropDueToOverload => Err(Status::resource_exhausted("")),
                        PredictResult::ModelNotFound(idx) => {
                            Err(Status::not_found(format!("model index {}", idx)))
                        },
                        PredictResult::ModelNotReady(idx) => {
                            Err(Status::unavailable(format!("model index {}", idx)))
                        }
                        PredictResult::ModelVersionNotFound(idx, version) => Err(
                            Status::not_found(format!("model index:{}, version {}", idx, version)),
                        ),
                    }
                },
            )
    }

    async fn multi_inference(
        &self,
        _request: Request<MultiInferenceRequest>,
    ) -> Result<Response<MultiInferenceResponse>, Status> {
        unimplemented!()
    }
    async fn get_model_metadata(
        &self,
        _request: Request<GetModelMetadataRequest>,
    ) -> Result<Response<GetModelMetadataResponse>, Status> {
        unimplemented!()
    }
}

// A function that takes a timestamp as input and returns a ticker stream
fn report_expiry(expiry_time: i64) {
    info!("Certificate expires at epoch: {:?}", expiry_time);
    CERT_EXPIRY_EPOCH.set(expiry_time as i64);
}

pub fn bootstrap<T: Model>(model_factory: ModelFactory<T>) -> Result<()> {
    info!("package: {}, version: {}, args: {:?}", NAME, VERSION, *ARGS);
    //we follow SemVer. So here we assume MAJOR.MINOR.PATCH
    let parts = VERSION
        .split(".")
        .map(|v| v.parse::<i64>())
        .collect::<std::result::Result<Vec<_>, _>>()?;
    if let [major, minor, patch] = &parts[..] {
        NAVI_VERSION.set(major * 1000_000 + minor * 1000 + patch);
    } else {
        warn!(
            "version {} doesn't follow SemVer conversion of MAJOR.MINOR.PATCH",
            VERSION
        );
    }

    
    tokio::runtime::Builder::new_multi_thread()
        .thread_name("async worker")
        .worker_threads(ARGS.num_worker_threads)
        .max_blocking_threads(ARGS.max_blocking_threads)
        .enable_all()
        .build()
        .unwrap()
        .block_on(async {
            #[cfg(feature = "navi_console")]
            console_subscriber::init();
            let addr = format!("0.0.0.0:{}", ARGS.port).parse()?;

            let ps = PredictService::init(model_factory).await;

            let mut builder = if ARGS.ssl_dir.is_empty() {
                Server::builder()
            } else {
                // Read the pem file as a string
                let pem_str = std::fs::read_to_string(format!("{}/server.crt", ARGS.ssl_dir)).unwrap();
                let res = parse_x509_pem(&pem_str.as_bytes());
                match res {
                    Ok((rem, pem_2)) => {
                        assert!(rem.is_empty());
                        assert_eq!(pem_2.label, String::from("CERTIFICATE"));
                        let res_x509 = parse_x509_certificate(&pem_2.contents);
                        info!("Certificate label: {}", pem_2.label);
                        assert!(res_x509.is_ok());
                        report_expiry(res_x509.unwrap().1.validity().not_after.timestamp());
                    },
                    _ => panic!("PEM parsing failed: {:?}", res),
                }

                let key = tokio::fs::read(format!("{}/server.key", ARGS.ssl_dir))
                    .await
                    .expect("can't find key file");
                let crt = tokio::fs::read(format!("{}/server.crt", ARGS.ssl_dir))
                    .await
                    .expect("can't find crt file");
                let chain = tokio::fs::read(format!("{}/server.chain", ARGS.ssl_dir))
                    .await
                    .expect("can't find chain file");
                let mut pem = Vec::new();
                pem.extend(crt);
                pem.extend(chain);
                let identity = Identity::from_pem(pem.clone(), key);
                let client_ca_cert = Certificate::from_pem(pem.clone());
                let tls = ServerTlsConfig::new()
                    .identity(identity) 
                    .client_ca_root(client_ca_cert);
                Server::builder()
                    .tls_config(tls)
                    .expect("fail to config SSL")
            };

            info!(
                "Prometheus server started: 0.0.0.0: {}",
                ARGS.prometheus_port
            );

            let ps_server = builder
                .add_service(PredictionServiceServer::new(ps).accept_gzip().send_gzip())
                .serve(addr);
            info!("Prediction server started: {}", addr);
            ps_server.await.map_err(anyhow::Error::msg)
        })
}

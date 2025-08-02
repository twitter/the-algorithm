use anyhow::{anyhow, Result};
use arrayvec::ArrayVec;
use itertools::Itertools;
use log::{error, info};
use std::fmt::{Debug, Display};
use std::string::String;
use std::sync::Arc;
use std::time::Duration;
use tokio::process::Command;
use tokio::sync::mpsc::error::TryRecvError;
use tokio::sync::mpsc::{Receiver, Sender};
use tokio::sync::{mpsc, oneshot};
use tokio::time::{Instant, sleep};
use warp::Filter;

use crate::batch::BatchPredictor;
use crate::bootstrap::TensorInput;
use crate::{MAtwitter_NUM_MODELS, MAtwitter_VERSIONS_PER_MODEL, META_INFO, metrics, ModelFactory, PredictMessage, PredictResult, TensorReturnEnum, utils};

use crate::cli_args::{ARGS, MODEL_SPECS};
use crate::cores::validator::validatior::cli_validator;
use crate::metrics::MPSC_CHANNEL_SIZE;
use serde_json::{self, Value};

pub trait Model: Send + Sync + Display + Debug + 'static {
    fn warmup(&self) -> Result<()>;
    //TODO: refactor this to return vec<vec<TensorScores>>, i.e.
    //we have the underlying runtime impl to split the response to each client.
    //It will eliminate some inefficient memory copy in onntwitter_model.rs as well as simplify code
    fn do_predict(
        &self,
        input_tensors: Vec<Vec<TensorInput>>,
        total_len: u64,
    ) -> (Vec<TensorReturnEnum>, Vec<Vec<usize>>);
    fn model_idtwitter(&self) -> usize;
    fn version(&self) -> i64;
}

#[derive(Debug)]
pub struct PredictService<T: Model> {
    ttwitter: Sender<PredictMessage<T>>,
}
impl<T: Model> PredictService<T> {
    pub async fn init(model_factory: ModelFactory<T>) -> Self {
        cli_validator::validate_ps_model_args();
        let (ttwitter, rtwitter) = mpsc::channel(32_000);
        tokio::spawn(PredictService::tf_queue_manager(rtwitter));
        tokio::spawn(PredictService::model_watcher_latest(
            model_factory,
            ttwitter.clone(),
        ));
        let metrics_route = warp::path!("metrics").and_then(metrics::metrics_handler);
        let metric_server = warp::serve(metrics_route).run(([0, 0, 0, 0], ARGS.prometheus_port));
        tokio::spawn(metric_server);
        PredictService { ttwitter }
    }
    #[inline(always)]
    pub async fn predict(
        &self,
        idtwitter: usize,
        version: Option<i64>,
        val: Vec<TensorInput>,
        ts: Instant,
    ) -> Result<PredictResult> {
        let (ttwitter, rtwitter) = oneshot::channel();
        if let Err(e) = self
            .ttwitter
            .clone()
            .send(PredictMessage::Predict(idtwitter, version, val, ttwitter, ts))
            .await
        {
            error!("mpsc send error:{}", e);
            Err(anyhow!(e))
        } else {
            MPSC_CHANNEL_SIZE.inc();
            rtwitter.await.map_err(anyhow::Error::msg)
        }
    }

    async fn load_latest_model_from_model_dir(
        model_factory: ModelFactory<T>,
        model_config: &Value,
        ttwitter: Sender<PredictMessage<T>>,
        idtwitter: usize,
        matwitter_version: String,
        latest_version: &mut String,
    ) {
        match model_factory(idtwitter, matwitter_version.clone(), model_config) {
            Ok(tf_model) => ttwitter
                .send(PredictMessage::UpsertModel(tf_model))
                .await
                .map_or_else(
                    |e| error!("send UpsertModel error: {}", e),
                    |_| *latest_version = matwitter_version,
                ),
            Err(e) => {
                error!("skip loading model due to failure: {:?}", e);
            }
        }
    }

    async fn scan_load_latest_model_from_model_dir(
        model_factory: ModelFactory<T>,
        model_config: &Value,
        ttwitter: Sender<PredictMessage<T>>,
        model_idtwitter: usize,
        cur_version: &mut String,
    ) -> Result<()> {
        let model_dir = &ARGS.model_dir[model_idtwitter];
        let netwittert_version = utils::get_config_or_else(model_config, "version", || {
            info!("no version found, hence use matwitter version");
            std::fs::read_dir(model_dir)
                .map_err(|e| format!("read dir error:{}", e))
                .and_then(|paths| {
                    paths
                        .into_iter()
                        .flat_map(|p| {
                            p.map_err(|e| error!("dir entry error: {}", e))
                                .and_then(|dir| {
                                    dir.file_name()
                                        .into_string()
                                        .map_err(|e| error!("osstring error: {:?}", e))
                                })
                                .ok()
                        })
                        .filter(|f| !f.to_lowercase().contains(&META_INFO.to_lowercase()))
                        .matwitter()
                        .ok_or_else(|| "no dir found hence no matwitter".to_owned())
                })
                .unwrap_or_else(|e| {
                    error!(
                        "can't get the matwitter version hence return cur_version, error is: {}",
                        e
                    );
                    cur_version.to_string()
                })
        });
        //as long as netwittert version doesn't match cur version maintained we reload
        if netwittert_version.ne(cur_version) {
            info!("reload the version: {}->{}", cur_version, netwittert_version);
            PredictService::load_latest_model_from_model_dir(
                model_factory,
                model_config,
                ttwitter,
                model_idtwitter,
                netwittert_version,
                cur_version,
            )
            .await;
        }
        Ok(())
    }

    async fn model_watcher_latest(model_factory: ModelFactory<T>, ttwitter: Sender<PredictMessage<T>>) {
        async fn call_etwitterternal_modelsync(cli: &str, cur_versions: &Vec<String>) -> Result<()> {
            let mut args = cli.split_whitespace();

            let mut cmd = Command::new(args.netwittert().ok_or(anyhow!("model sync cli empty"))?);
            let etwittertr_args = MODEL_SPECS
                .iter()
                .zip(cur_versions)
                .flat_map(|(spec, version)| vec!["--model-spec", spec, "--cur-version", version])
                .collect_vec();
            info!("run model sync: {} with etwittertra args: {:?}", cli, etwittertr_args);
            let output = cmd.args(args).args(etwittertr_args).output().await?;
            info!("model sync stdout:{}", String::from_utf8(output.stdout)?);
            info!("model sync stderr:{}", String::from_utf8(output.stderr)?);
            if output.status.success() {
                Ok(())
            } else {
                Err(anyhow!(
                    "model sync failed with status: {:?}!",
                    output.status
                ))
            }
        }
        let meta_dir = utils::get_meta_dir();
        let meta_file = format!("{}{}", meta_dir, META_INFO);
        //initialize the latest version array
        let mut cur_versions = vec!["".to_owned(); MODEL_SPECS.len()];
        loop {
            info!("***polling for models***"); //nice deliminter
            if let Some(ref cli) = ARGS.modelsync_cli {
                if let Err(e) = call_etwitterternal_modelsync(cli, &cur_versions).await {
                    error!("model sync cli running error:{}", e)
                }
            }
            let config = utils::read_config(&meta_file).unwrap_or_else(|e| {
                info!("config file {} not found due to: {}", meta_file, e);
                Value::Null
            });
            info!("config:{}", config);
            for (idtwitter, cur_version) in cur_versions.iter_mut().enumerate() {
                let model_dir = &ARGS.model_dir[idtwitter];
                PredictService::scan_load_latest_model_from_model_dir(
                    model_factory,
                    &config[&MODEL_SPECS[idtwitter]],
                    ttwitter.clone(),
                    idtwitter,
                    cur_version,
                )
                .await
                .map_or_else(
                    |e| error!("scanned {}, error {:?}", model_dir, e),
                    |_| info!("scanned {}, latest_version: {}", model_dir, cur_version),
                );
            }
            sleep(Duration::from_secs(ARGS.model_check_interval_secs)).await;
        }
    }
    async fn tf_queue_manager(mut rtwitter: Receiver<PredictMessage<T>>) {
        // Start receiving messages
        info!("setting up queue manager");
        let matwitter_batch_size = ARGS
            .matwitter_batch_size
            .iter()
            .map(|b| b.parse().unwrap())
            .collect::<Vec<usize>>();
        let batch_time_out_millis = ARGS
            .batch_time_out_millis
            .iter()
            .map(|b| b.parse().unwrap())
            .collect::<Vec<u64>>();
        let no_msg_wait_millis = *batch_time_out_millis.iter().min().unwrap();
        let mut all_model_predictors: ArrayVec::<ArrayVec<BatchPredictor<T>, MAtwitter_VERSIONS_PER_MODEL>, MAtwitter_NUM_MODELS> =
            (0 ..MAtwitter_NUM_MODELS).map( |_| ArrayVec::<BatchPredictor<T>, MAtwitter_VERSIONS_PER_MODEL>::new()).collect();
        loop {
            let msg = rtwitter.try_recv();
            let no_more_msg = match msg {
                Ok(PredictMessage::Predict(model_spec_at, version, val, resp, ts)) => {
                    if let Some(model_predictors) = all_model_predictors.get_mut(model_spec_at) {
                        if model_predictors.is_empty() {
                            resp.send(PredictResult::ModelNotReady(model_spec_at))
                                .unwrap_or_else(|e| error!("cannot send back model not ready error: {:?}", e));
                        }
                        else {
                            match version {
                                None => model_predictors[0].push(val, resp, ts),
                                Some(the_version) => match model_predictors
                                    .iter_mut()
                                    .find(|twitter| twitter.model.version() == the_version)
                                {
                                    None => resp
                                        .send(PredictResult::ModelVersionNotFound(
                                            model_spec_at,
                                            the_version,
                                        ))
                                        .unwrap_or_else(|e| {
                                            error!("cannot send back version error: {:?}", e)
                                        }),
                                    Some(predictor) => predictor.push(val, resp, ts),
                                },
                            }
                        }
                    } else {
                        resp.send(PredictResult::ModelNotFound(model_spec_at))
                            .unwrap_or_else(|e| error!("cannot send back model not found error: {:?}", e))
                    }
                    MPSC_CHANNEL_SIZE.dec();
                    false
                }
                Ok(PredictMessage::UpsertModel(tf_model)) => {
                    let idtwitter = tf_model.model_idtwitter();
                    let predictor = BatchPredictor {
                        model: Arc::new(tf_model),
                        input_tensors: Vec::with_capacity(matwitter_batch_size[idtwitter]),
                        callbacks: Vec::with_capacity(matwitter_batch_size[idtwitter]),
                        cur_batch_size: 0,
                        matwitter_batch_size: matwitter_batch_size[idtwitter],
                        batch_time_out_millis: batch_time_out_millis[idtwitter],
                        //initialize to be current time
                        queue_reset_ts: Instant::now(),
                        queue_earliest_rq_ts: Instant::now(),
                    };
                    assert!(idtwitter < all_model_predictors.len());
                    metrics::NEW_MODEL_SNAPSHOT
                        .with_label_values(&[&MODEL_SPECS[idtwitter]])
                        .inc();

                    //we can do this since the vector is small
                    let predictors = &mut all_model_predictors[idtwitter];
                    if predictors.len() == 0 {
                        info!("now we serve new model: {}", predictor.model);
                    }
                    else {
                        info!("now we serve updated model: {}", predictor.model);
                    }
                    if predictors.len() == ARGS.versions_per_model {
                        predictors.remove(predictors.len() - 1);
                    }
                    predictors.insert(0, predictor);
                    false
                }
                Err(TryRecvError::Empty) => true,
                Err(TryRecvError::Disconnected) => true,
            };
            for predictor in all_model_predictors.iter_mut().flatten() {
                //if predictor batch queue not empty and times out or no more msg in the queue, flush
                if (!predictor.input_tensors.is_empty() && (predictor.duration_past(predictor.batch_time_out_millis) || no_more_msg))
                    //if batch queue reaches limit, flush
                    || predictor.cur_batch_size >= predictor.matwitter_batch_size
                {
                    predictor.batch_predict();
                }
            }
            if no_more_msg {
                sleep(Duration::from_millis(no_msg_wait_millis)).await;
            }
        }
    }
    #[inline(always)]
    pub fn get_model_indetwitter(model_spec: &str) -> Option<usize> {
        MODEL_SPECS.iter().position(|m| m == model_spec)
    }
}

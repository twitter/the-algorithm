use log::error;
use prometheus::{
    CounterVec, HistogramOpts, HistogramVec, IntCounter, IntCounterVec, IntGauge, IntGaugeVec,
    Opts, Registry,
};
use warp::{Rejection, Reply};
use crate::{NAME, VERSION};

lazy_static! {
    pub static ref REGISTRY: Registry = Registry::new();
    pub static ref NUM_REQUESTS_RECEIVED: IntCounter =
        IntCounter::new(":navi:num_requests", "Number of Requests Received")
            .expect("metric can be created");
    pub static ref NUM_REQUESTS_FAILED: IntCounter = IntCounter::new(
        ":navi:num_requests_failed",
        "Number of Request Inference Failed"
    )
    .expect("metric can be created");
    pub static ref NUM_REQUESTS_DROPPED: IntCounter = IntCounter::new(
        ":navi:num_requests_dropped",
        "Number of Oneshot Receivers Dropped"
    )
    .expect("metric can be created");
    pub static ref NUM_BATCHES_DROPPED: IntCounter = IntCounter::new(
        ":navi:num_batches_dropped",
        "Number of Batches Proactively Dropped"
    )
    .expect("metric can be created");
    pub static ref NUM_BATCH_PREDICTION: IntCounter =
        IntCounter::new(":navi:num_batch_prediction", "Number of batch prediction")
            .expect("metric can be created");
    pub static ref BATCH_SIZE: IntGauge =
        IntGauge::new(":navi:batch_size", "Size of current batch").expect("metric can be created");
    pub static ref NAVI_VERSION: IntGauge =
        IntGauge::new(":navi:navi_version", "navi's current version")
            .expect("metric can be created");
    pub static ref RESPONSE_TIME_COLLECTOR: HistogramVec = HistogramVec::new(
        HistogramOpts::new(":navi:response_time", "Response Time in ms").buckets(Vec::from(&[
            0.0, 10.0, 20.0, 30.0, 40.0, 50.0, 60.0, 70.0, 80.0, 90.0, 100.0, 110.0, 120.0, 130.0,
            140.0, 150.0, 160.0, 170.0, 180.0, 190.0, 200.0, 250.0, 300.0, 500.0, 1000.0
        ]
            as &'static [f64])),
        &["model_name"]
    )
    .expect("metric can be created");
    pub static ref NUM_PREDICTIONS: IntCounterVec = IntCounterVec::new(
        Opts::new(
            ":navi:num_predictions",
            "Number of predictions made by model"
        ),
        &["model_name"]
    )
    .expect("metric can be created");
    pub static ref PREDICTION_SCORE_SUM: CounterVec = CounterVec::new(
        Opts::new(
            ":navi:prediction_score_sum",
            "Sum of prediction score made by model"
        ),
        &["model_name"]
    )
    .expect("metric can be created");
    pub static ref NEW_MODEL_SNAPSHOT: IntCounterVec = IntCounterVec::new(
        Opts::new(
            ":navi:new_model_snapshot",
            "Load a new version of model snapshot"
        ),
        &["model_name"]
    )
    .expect("metric can be created");
    pub static ref MODEL_SNAPSHOT_VERSION: IntGaugeVec = IntGaugeVec::new(
        Opts::new(
            ":navi:model_snapshot_version",
            "Record model snapshot version"
        ),
        &["model_name"]
    )
    .expect("metric can be created");
    pub static ref NUM_REQUESTS_RECEIVED_BY_MODEL: IntCounterVec = IntCounterVec::new(
        Opts::new(
            ":navi:num_requests_by_model",
            "Number of Requests Received by model"
        ),
        &["model_name"]
    )
    .expect("metric can be created");
    pub static ref NUM_REQUESTS_FAILED_BY_MODEL: IntCounterVec = IntCounterVec::new(
        Opts::new(
            ":navi:num_requests_failed_by_model",
            "Number of Request Inference Failed by model"
        ),
        &["model_name"]
    )
    .expect("metric can be created");
    pub static ref NUM_REQUESTS_DROPPED_BY_MODEL: IntCounterVec = IntCounterVec::new(
        Opts::new(
            ":navi:num_requests_dropped_by_model",
            "Number of Oneshot Receivers Dropped by model"
        ),
        &["model_name"]
    )
    .expect("metric can be created");
    pub static ref NUM_BATCHES_DROPPED_BY_MODEL: IntCounterVec = IntCounterVec::new(
        Opts::new(
            ":navi:num_batches_dropped_by_model",
            "Number of Batches Proactively Dropped by model"
        ),
        &["model_name"]
    )
    .expect("metric can be created");
    pub static ref INFERENCE_FAILED_REQUESTS_BY_MODEL: IntCounterVec = IntCounterVec::new(
        Opts::new(
            ":navi:inference_failed_requests_by_model",
            "Number of failed inference requests by model"
        ),
        &["model_name"]
    )
    .expect("metric can be created");
    pub static ref NUM_PREDICTION_BY_MODEL: IntCounterVec = IntCounterVec::new(
        Opts::new(
            ":navi:num_prediction_by_model",
            "Number of prediction by model"
        ),
        &["model_name"]
    )
    .expect("metric can be created");
    pub static ref NUM_BATCH_PREDICTION_BY_MODEL: IntCounterVec = IntCounterVec::new(
        Opts::new(
            ":navi:num_batch_prediction_by_model",
            "Number of batch prediction by model"
        ),
        &["model_name"]
    )
    .expect("metric can be created");
    pub static ref BATCH_SIZE_BY_MODEL: IntGaugeVec = IntGaugeVec::new(
        Opts::new(
            ":navi:batch_size_by_model",
            "Size of current batch by model"
        ),
        &["model_name"]
    )
    .expect("metric can be created");
    pub static ref CUSTOMOP_VERSION: IntGauge =
        IntGauge::new(":navi:customop_version", "The hashed Custom OP Version")
            .expect("metric can be created");
    pub static ref MPSC_CHANNEL_SIZE: IntGauge =
        IntGauge::new(":navi:mpsc_channel_size", "The mpsc channel's request size")
            .expect("metric can be created");
    pub static ref BLOCKING_REQUEST_NUM: IntGauge = IntGauge::new(
        ":navi:blocking_request_num",
        "The (batch) request waiting or being executed"
    )
    .expect("metric can be created");
    pub static ref MODEL_INFERENCE_TIME_COLLECTOR: HistogramVec = HistogramVec::new(
        HistogramOpts::new(":navi:model_inference_time", "Model inference time in ms").buckets(
            Vec::from(&[
                0.0, 5.0, 10.0, 15.0, 20.0, 25.0, 30.0, 35.0, 40.0, 45.0, 50.0, 55.0, 60.0, 65.0,
                70.0, 75.0, 80.0, 85.0, 90.0, 100.0, 110.0, 120.0, 130.0, 140.0, 150.0, 160.0,
                170.0, 180.0, 190.0, 200.0, 250.0, 300.0, 500.0, 1000.0
            ] as &'static [f64])
        ),
        &["model_name"]
    )
    .expect("metric can be created");
    pub static ref CONVERTER_TIME_COLLECTOR: HistogramVec = HistogramVec::new(
        HistogramOpts::new(":navi:converter_time", "converter time in microseconds").buckets(
            Vec::from(&[
                0.0, 500.0, 1000.0, 1500.0, 2000.0, 2500.0, 3000.0, 3500.0, 4000.0, 4500.0, 5000.0,
                5500.0, 6000.0, 6500.0, 7000.0, 20000.0
            ] as &'static [f64])
        ),
        &["model_name"]
    )
    .expect("metric can be created");
    pub static ref CERT_EXPIRY_EPOCH: IntGauge =
        IntGauge::new(":navi:cert_expiry_epoch", "Timestamp when the current cert expires")
            .expect("metric can be created");
}

pub fn register_custom_metrics() {
    REGISTRY
        .register(Box::new(NUM_REQUESTS_RECEIVED.clone()))
        .expect("collector can be registered");
    REGISTRY
        .register(Box::new(NUM_REQUESTS_FAILED.clone()))
        .expect("collector can be registered");
    REGISTRY
        .register(Box::new(NUM_REQUESTS_DROPPED.clone()))
        .expect("collector can be registered");
    REGISTRY
        .register(Box::new(RESPONSE_TIME_COLLECTOR.clone()))
        .expect("collector can be registered");
    REGISTRY
        .register(Box::new(NAVI_VERSION.clone()))
        .expect("collector can be registered");
    REGISTRY
        .register(Box::new(BATCH_SIZE.clone()))
        .expect("collector can be registered");
    REGISTRY
        .register(Box::new(NUM_BATCH_PREDICTION.clone()))
        .expect("collector can be registered");
    REGISTRY
        .register(Box::new(NUM_BATCHES_DROPPED.clone()))
        .expect("collector can be registered");
    REGISTRY
        .register(Box::new(NUM_PREDICTIONS.clone()))
        .expect("collector can be registered");
    REGISTRY
        .register(Box::new(PREDICTION_SCORE_SUM.clone()))
        .expect("collector can be registered");
    REGISTRY
        .register(Box::new(NEW_MODEL_SNAPSHOT.clone()))
        .expect("collector can be registered");
    REGISTRY
        .register(Box::new(MODEL_SNAPSHOT_VERSION.clone()))
        .expect("collector can be registered");
    REGISTRY
        .register(Box::new(NUM_REQUESTS_RECEIVED_BY_MODEL.clone()))
        .expect("collector can be registered");
    REGISTRY
        .register(Box::new(NUM_REQUESTS_FAILED_BY_MODEL.clone()))
        .expect("collector can be registered");
    REGISTRY
        .register(Box::new(NUM_REQUESTS_DROPPED_BY_MODEL.clone()))
        .expect("collector can be registered");
    REGISTRY
        .register(Box::new(NUM_BATCHES_DROPPED_BY_MODEL.clone()))
        .expect("collector can be registered");
    REGISTRY
        .register(Box::new(INFERENCE_FAILED_REQUESTS_BY_MODEL.clone()))
        .expect("collector can be registered");
    REGISTRY
        .register(Box::new(NUM_PREDICTION_BY_MODEL.clone()))
        .expect("collector can be registered");
    REGISTRY
        .register(Box::new(NUM_BATCH_PREDICTION_BY_MODEL.clone()))
        .expect("collector can be registered");
    REGISTRY
        .register(Box::new(BATCH_SIZE_BY_MODEL.clone()))
        .expect("collector can be registered");
    REGISTRY
        .register(Box::new(CUSTOMOP_VERSION.clone()))
        .expect("collector can be registered");
    REGISTRY
        .register(Box::new(MPSC_CHANNEL_SIZE.clone()))
        .expect("collector can be registered");
    REGISTRY
        .register(Box::new(BLOCKING_REQUEST_NUM.clone()))
        .expect("collector can be registered");
    REGISTRY
        .register(Box::new(MODEL_INFERENCE_TIME_COLLECTOR.clone()))
        .expect("collector can be registered");
    REGISTRY
        .register(Box::new(CONVERTER_TIME_COLLECTOR.clone()))
        .expect("collector can be registered");
    REGISTRY
    .register(Box::new(CERT_EXPIRY_EPOCH.clone()))
    .expect("collector can be registered");

}

pub fn register_dynamic_metrics(c: &HistogramVec) {
    REGISTRY
        .register(Box::new(c.clone()))
        .expect("dynamic metric collector cannot be registered");
}

pub async fn metrics_handler() -> Result<impl Reply, Rejection> {
    use prometheus::Encoder;
    let encoder = prometheus::TextEncoder::new();

    let mut buffer = Vec::new();
    if let Err(e) = encoder.encode(&REGISTRY.gather(), &mut buffer) {
        error!("could not encode custom metrics: {}", e);
    };
    let mut res = match String::from_utf8(buffer) {
        Ok(v) => format!("#{}:{}\n{}", NAME, VERSION, v),
        Err(e) => {
            error!("custom metrics could not be from_utf8'd: {}", e);
            String::default()
        }
    };

    buffer = Vec::new();
    if let Err(e) = encoder.encode(&prometheus::gather(), &mut buffer) {
        error!("could not encode prometheus metrics: {}", e);
    };
    let res_custom = match String::from_utf8(buffer) {
        Ok(v) => v,
        Err(e) => {
            error!("prometheus metrics could not be from_utf8'd: {}", e);
            String::default()
        }
    };

    res.push_str(&res_custom);
    Ok(res)
}

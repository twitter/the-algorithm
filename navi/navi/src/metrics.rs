use log::error;
use pro t us::{
    CounterVec,  togramOpts,  togramVec,  ntCounter,  ntCounterVec,  ntGauge,  ntGaugeVec,
    Opts, Reg stry,
};
use warp::{Reject on, Reply};
use crate::{NAME, VERS ON};

lazy_stat c! {
    pub stat c ref REG STRY: Reg stry = Reg stry::new();
    pub stat c ref NUM_REQUESTS_RECE VED:  ntCounter =
         ntCounter::new(":nav :num_requests", "Number of Requests Rece ved")
            .expect(" tr c can be created");
    pub stat c ref NUM_REQUESTS_FA LED:  ntCounter =  ntCounter::new(
        ":nav :num_requests_fa led",
        "Number of Request  nference Fa led"
    )
    .expect(" tr c can be created");
    pub stat c ref NUM_REQUESTS_DROPPED:  ntCounter =  ntCounter::new(
        ":nav :num_requests_dropped",
        "Number of Oneshot Rece vers Dropped"
    )
    .expect(" tr c can be created");
    pub stat c ref NUM_BATCHES_DROPPED:  ntCounter =  ntCounter::new(
        ":nav :num_batc s_dropped",
        "Number of Batc s Proact vely Dropped"
    )
    .expect(" tr c can be created");
    pub stat c ref NUM_BATCH_PRED CT ON:  ntCounter =
         ntCounter::new(":nav :num_batch_pred ct on", "Number of batch pred ct on")
            .expect(" tr c can be created");
    pub stat c ref BATCH_S ZE:  ntGauge =
         ntGauge::new(":nav :batch_s ze", "S ze of current batch").expect(" tr c can be created");
    pub stat c ref NAV _VERS ON:  ntGauge =
         ntGauge::new(":nav :nav _vers on", "nav 's current vers on")
            .expect(" tr c can be created");
    pub stat c ref RESPONSE_T ME_COLLECTOR:  togramVec =  togramVec::new(
         togramOpts::new(":nav :response_t  ", "Response T    n ms").buckets(Vec::from(&[
            0.0, 10.0, 20.0, 30.0, 40.0, 50.0, 60.0, 70.0, 80.0, 90.0, 100.0, 110.0, 120.0, 130.0,
            140.0, 150.0, 160.0, 170.0, 180.0, 190.0, 200.0, 250.0, 300.0, 500.0, 1000.0
        ]
            as &'stat c [f64])),
        &["model_na "]
    )
    .expect(" tr c can be created");
    pub stat c ref NUM_PRED CT ONS:  ntCounterVec =  ntCounterVec::new(
        Opts::new(
            ":nav :num_pred ct ons",
            "Number of pred ct ons made by model"
        ),
        &["model_na "]
    )
    .expect(" tr c can be created");
    pub stat c ref PRED CT ON_SCORE_SUM: CounterVec = CounterVec::new(
        Opts::new(
            ":nav :pred ct on_score_sum",
            "Sum of pred ct on score made by model"
        ),
        &["model_na "]
    )
    .expect(" tr c can be created");
    pub stat c ref NEW_MODEL_SNAPSHOT:  ntCounterVec =  ntCounterVec::new(
        Opts::new(
            ":nav :new_model_snapshot",
            "Load a new vers on of model snapshot"
        ),
        &["model_na "]
    )
    .expect(" tr c can be created");
    pub stat c ref MODEL_SNAPSHOT_VERS ON:  ntGaugeVec =  ntGaugeVec::new(
        Opts::new(
            ":nav :model_snapshot_vers on",
            "Record model snapshot vers on"
        ),
        &["model_na "]
    )
    .expect(" tr c can be created");
    pub stat c ref NUM_REQUESTS_RECE VED_BY_MODEL:  ntCounterVec =  ntCounterVec::new(
        Opts::new(
            ":nav :num_requests_by_model",
            "Number of Requests Rece ved by model"
        ),
        &["model_na "]
    )
    .expect(" tr c can be created");
    pub stat c ref NUM_REQUESTS_FA LED_BY_MODEL:  ntCounterVec =  ntCounterVec::new(
        Opts::new(
            ":nav :num_requests_fa led_by_model",
            "Number of Request  nference Fa led by model"
        ),
        &["model_na "]
    )
    .expect(" tr c can be created");
    pub stat c ref NUM_REQUESTS_DROPPED_BY_MODEL:  ntCounterVec =  ntCounterVec::new(
        Opts::new(
            ":nav :num_requests_dropped_by_model",
            "Number of Oneshot Rece vers Dropped by model"
        ),
        &["model_na "]
    )
    .expect(" tr c can be created");
    pub stat c ref NUM_BATCHES_DROPPED_BY_MODEL:  ntCounterVec =  ntCounterVec::new(
        Opts::new(
            ":nav :num_batc s_dropped_by_model",
            "Number of Batc s Proact vely Dropped by model"
        ),
        &["model_na "]
    )
    .expect(" tr c can be created");
    pub stat c ref  NFERENCE_FA LED_REQUESTS_BY_MODEL:  ntCounterVec =  ntCounterVec::new(
        Opts::new(
            ":nav : nference_fa led_requests_by_model",
            "Number of fa led  nference requests by model"
        ),
        &["model_na "]
    )
    .expect(" tr c can be created");
    pub stat c ref NUM_PRED CT ON_BY_MODEL:  ntCounterVec =  ntCounterVec::new(
        Opts::new(
            ":nav :num_pred ct on_by_model",
            "Number of pred ct on by model"
        ),
        &["model_na "]
    )
    .expect(" tr c can be created");
    pub stat c ref NUM_BATCH_PRED CT ON_BY_MODEL:  ntCounterVec =  ntCounterVec::new(
        Opts::new(
            ":nav :num_batch_pred ct on_by_model",
            "Number of batch pred ct on by model"
        ),
        &["model_na "]
    )
    .expect(" tr c can be created");
    pub stat c ref BATCH_S ZE_BY_MODEL:  ntGaugeVec =  ntGaugeVec::new(
        Opts::new(
            ":nav :batch_s ze_by_model",
            "S ze of current batch by model"
        ),
        &["model_na "]
    )
    .expect(" tr c can be created");
    pub stat c ref CUSTOMOP_VERS ON:  ntGauge =
         ntGauge::new(":nav :customop_vers on", "T  has d Custom OP Vers on")
            .expect(" tr c can be created");
    pub stat c ref MPSC_CHANNEL_S ZE:  ntGauge =
         ntGauge::new(":nav :mpsc_channel_s ze", "T  mpsc channel's request s ze")
            .expect(" tr c can be created");
    pub stat c ref BLOCK NG_REQUEST_NUM:  ntGauge =  ntGauge::new(
        ":nav :block ng_request_num",
        "T  (batch) request wa  ng or be ng executed"
    )
    .expect(" tr c can be created");
    pub stat c ref MODEL_ NFERENCE_T ME_COLLECTOR:  togramVec =  togramVec::new(
         togramOpts::new(":nav :model_ nference_t  ", "Model  nference t    n ms").buckets(
            Vec::from(&[
                0.0, 5.0, 10.0, 15.0, 20.0, 25.0, 30.0, 35.0, 40.0, 45.0, 50.0, 55.0, 60.0, 65.0,
                70.0, 75.0, 80.0, 85.0, 90.0, 100.0, 110.0, 120.0, 130.0, 140.0, 150.0, 160.0,
                170.0, 180.0, 190.0, 200.0, 250.0, 300.0, 500.0, 1000.0
            ] as &'stat c [f64])
        ),
        &["model_na "]
    )
    .expect(" tr c can be created");
    pub stat c ref CONVERTER_T ME_COLLECTOR:  togramVec =  togramVec::new(
         togramOpts::new(":nav :converter_t  ", "converter t    n m croseconds").buckets(
            Vec::from(&[
                0.0, 500.0, 1000.0, 1500.0, 2000.0, 2500.0, 3000.0, 3500.0, 4000.0, 4500.0, 5000.0,
                5500.0, 6000.0, 6500.0, 7000.0, 20000.0
            ] as &'stat c [f64])
        ),
        &["model_na "]
    )
    .expect(" tr c can be created");
    pub stat c ref CERT_EXP RY_EPOCH:  ntGauge =
         ntGauge::new(":nav :cert_exp ry_epoch", "T  stamp w n t  current cert exp res")
            .expect(" tr c can be created");
}

pub fn reg ster_custom_ tr cs() {
    REG STRY
        .reg ster(Box::new(NUM_REQUESTS_RECE VED.clone()))
        .expect("collector can be reg stered");
    REG STRY
        .reg ster(Box::new(NUM_REQUESTS_FA LED.clone()))
        .expect("collector can be reg stered");
    REG STRY
        .reg ster(Box::new(NUM_REQUESTS_DROPPED.clone()))
        .expect("collector can be reg stered");
    REG STRY
        .reg ster(Box::new(RESPONSE_T ME_COLLECTOR.clone()))
        .expect("collector can be reg stered");
    REG STRY
        .reg ster(Box::new(NAV _VERS ON.clone()))
        .expect("collector can be reg stered");
    REG STRY
        .reg ster(Box::new(BATCH_S ZE.clone()))
        .expect("collector can be reg stered");
    REG STRY
        .reg ster(Box::new(NUM_BATCH_PRED CT ON.clone()))
        .expect("collector can be reg stered");
    REG STRY
        .reg ster(Box::new(NUM_BATCHES_DROPPED.clone()))
        .expect("collector can be reg stered");
    REG STRY
        .reg ster(Box::new(NUM_PRED CT ONS.clone()))
        .expect("collector can be reg stered");
    REG STRY
        .reg ster(Box::new(PRED CT ON_SCORE_SUM.clone()))
        .expect("collector can be reg stered");
    REG STRY
        .reg ster(Box::new(NEW_MODEL_SNAPSHOT.clone()))
        .expect("collector can be reg stered");
    REG STRY
        .reg ster(Box::new(MODEL_SNAPSHOT_VERS ON.clone()))
        .expect("collector can be reg stered");
    REG STRY
        .reg ster(Box::new(NUM_REQUESTS_RECE VED_BY_MODEL.clone()))
        .expect("collector can be reg stered");
    REG STRY
        .reg ster(Box::new(NUM_REQUESTS_FA LED_BY_MODEL.clone()))
        .expect("collector can be reg stered");
    REG STRY
        .reg ster(Box::new(NUM_REQUESTS_DROPPED_BY_MODEL.clone()))
        .expect("collector can be reg stered");
    REG STRY
        .reg ster(Box::new(NUM_BATCHES_DROPPED_BY_MODEL.clone()))
        .expect("collector can be reg stered");
    REG STRY
        .reg ster(Box::new( NFERENCE_FA LED_REQUESTS_BY_MODEL.clone()))
        .expect("collector can be reg stered");
    REG STRY
        .reg ster(Box::new(NUM_PRED CT ON_BY_MODEL.clone()))
        .expect("collector can be reg stered");
    REG STRY
        .reg ster(Box::new(NUM_BATCH_PRED CT ON_BY_MODEL.clone()))
        .expect("collector can be reg stered");
    REG STRY
        .reg ster(Box::new(BATCH_S ZE_BY_MODEL.clone()))
        .expect("collector can be reg stered");
    REG STRY
        .reg ster(Box::new(CUSTOMOP_VERS ON.clone()))
        .expect("collector can be reg stered");
    REG STRY
        .reg ster(Box::new(MPSC_CHANNEL_S ZE.clone()))
        .expect("collector can be reg stered");
    REG STRY
        .reg ster(Box::new(BLOCK NG_REQUEST_NUM.clone()))
        .expect("collector can be reg stered");
    REG STRY
        .reg ster(Box::new(MODEL_ NFERENCE_T ME_COLLECTOR.clone()))
        .expect("collector can be reg stered");
    REG STRY
        .reg ster(Box::new(CONVERTER_T ME_COLLECTOR.clone()))
        .expect("collector can be reg stered");
    REG STRY
    .reg ster(Box::new(CERT_EXP RY_EPOCH.clone()))
    .expect("collector can be reg stered");

}

pub fn reg ster_dynam c_ tr cs(c: & togramVec) {
    REG STRY
        .reg ster(Box::new(c.clone()))
        .expect("dynam c  tr c collector cannot be reg stered");
}

pub async fn  tr cs_handler() -> Result< mpl Reply, Reject on> {
    use pro t us::Encoder;
    let encoder = pro t us::TextEncoder::new();

    let mut buffer = Vec::new();
     f let Err(e) = encoder.encode(&REG STRY.gat r(), &mut buffer) {
        error!("could not encode custom  tr cs: {}", e);
    };
    let mut res = match Str ng::from_utf8(buffer) {
        Ok(v) => format!("#{}:{}\n{}", NAME, VERS ON, v),
        Err(e) => {
            error!("custom  tr cs could not be from_utf8'd: {}", e);
            Str ng::default()
        }
    };

    buffer = Vec::new();
     f let Err(e) = encoder.encode(&pro t us::gat r(), &mut buffer) {
        error!("could not encode pro t us  tr cs: {}", e);
    };
    let res_custom = match Str ng::from_utf8(buffer) {
        Ok(v) => v,
        Err(e) => {
            error!("pro t us  tr cs could not be from_utf8'd: {}", e);
            Str ng::default()
        }
    };

    res.push_str(&res_custom);
    Ok(res)
}

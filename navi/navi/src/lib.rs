#[macro_use]
extern crate lazy_static;
extern crate core;

use serde_json::Value;
use tokio::sync::oneshot::Sender;
use tokio::time::Instant;
use std::ops::Deref;
use itertools::Itertools;
use crate::bootstrap::TensorInput;
use crate::predict_service::Model;
use crate::tf_proto::{DataType, TensorProto};

pub mod batch;
pub mod bootstrap;
pub mod cli_args;
pub mod metrics;
pub mod onnx_model;
pub mod predict_service;
pub mod tf_model;
pub mod torch_model;
pub mod cores {
    pub mod validator;
}

pub mod tf_proto {
    tonic::include_proto!("tensorflow");
    pub mod tensorflow_serving {
        tonic::include_proto!("tensorflow.serving");
    }
}

pub mod kf_serving {
    tonic::include_proto!("inference");
}
#[cfg(test)]
mod tests {
    use crate::cli_args::Args;
    #[test]
    fn test_version_string_to_epoch() {
        assert_eq!(
            Args::version_str_to_epoch("2022-12-20T10:18:53.000Z").unwrap_or(-1),
            1671531533000
        );
        assert_eq!(Args::version_str_to_epoch("1203444").unwrap_or(-1), 1203444);
    }
}

mod utils {
    use crate::cli_args::{ARGS, MODEL_SPECS};
    use anyhow::Result;
    use log::info;
    use serde_json::Value;

    pub fn read_config(meta_file: &String) -> Result<Value> {
        let json = std::fs::read_to_string(meta_file)?;
        let v: Value = serde_json::from_str(&json)?;
        Ok(v)
    }
    pub fn get_config_or_else<F>(model_config: &Value, key: &str, default: F) -> String
    where
        F: FnOnce() -> String,
    {
        match model_config[key] {
            Value::String(ref v) => {
                info!("from model_config: {}={}", key, v);
                v.to_string()
            }
            Value::Number(ref num) => {
                info!(
                    "from model_config: {}={} (turn number into a string)",
                    key, num
                );
                num.to_string()
            }
            _ => {
                let d = default();
                info!("from default: {}={}", key, d);
                d
            }
        }
    }
    pub fn get_config_or(model_config: &Value, key: &str, default: &str) -> String {
        get_config_or_else(model_config, key, || default.to_string())
    }
    pub fn get_meta_dir() -> &'static str {
        ARGS.meta_json_dir
            .as_ref()
            .map(|s| s.as_str())
            .unwrap_or_else(|| {
                let model_dir = &ARGS.model_dir[0];
                let meta_dir = &model_dir[0..model_dir.rfind(&MODEL_SPECS[0]).unwrap()];
                info!(
                    "no meta_json_dir specified, hence derive from first model dir:{}->{}",
                    model_dir, meta_dir
                );
                meta_dir
            })
    }
}

pub type SerializedInput = Vec<u8>;
pub const VERSION: &str = env!("CARGO_PKG_VERSION");
pub const NAME: &str = env!("CARGO_PKG_NAME");
pub type ModelFactory<T> = fn(usize, String, &Value) -> anyhow::Result<T>;
pub const MAX_NUM_MODELS: usize = 16;
pub const MAX_NUM_OUTPUTS: usize = 30;
pub const MAX_NUM_INPUTS: usize = 120;
pub const META_INFO: &str = "META.json";

//use a heap allocated generic type here so that both
//Tensorflow & Pytorch implementation can return their Tensor wrapped in a Box
//without an extra memcopy to Vec
pub type TensorReturn<T> = Box<dyn Deref<Target = [T]>>;

//returned tensor may be int64 i.e., a list of relevant ad ids
pub enum TensorReturnEnum {
    FloatTensorReturn(TensorReturn<f32>),
    StringTensorReturn(TensorReturn<String>),
    Int64TensorReturn(TensorReturn<i64>),
    Int32TensorReturn(TensorReturn<i32>),
}

impl TensorReturnEnum {
    #[inline(always)]
    pub fn slice(&self, start: usize, end: usize) -> TensorScores {
        match self {
            TensorReturnEnum::FloatTensorReturn(f32_return) => {
                TensorScores::Float32TensorScores(f32_return[start..end].to_vec())
            }
            TensorReturnEnum::Int64TensorReturn(i64_return) => {
                TensorScores::Int64TensorScores(i64_return[start..end].to_vec())
            }
            TensorReturnEnum::Int32TensorReturn(i32_return) => {
                TensorScores::Int32TensorScores(i32_return[start..end].to_vec())
            }
            TensorReturnEnum::StringTensorReturn(str_return) => {
                TensorScores::StringTensorScores(str_return[start..end].to_vec())
            }
        }
    }
}

#[derive(Debug)]
pub enum PredictResult {
    Ok(Vec<TensorScores>, i64),
    DropDueToOverload,
    ModelNotFound(usize),
    ModelNotReady(usize),
    ModelVersionNotFound(usize, i64),
}

#[derive(Debug)]
pub enum TensorScores {
    Float32TensorScores(Vec<f32>),
    Int64TensorScores(Vec<i64>),
    Int32TensorScores(Vec<i32>),
    StringTensorScores(Vec<String>),
}

impl TensorScores {
    pub fn create_tensor_proto(self) -> TensorProto {
        match self {
            TensorScores::Float32TensorScores(f32_tensor) => TensorProto {
                dtype: DataType::DtFloat as i32,
                float_val: f32_tensor,
                ..Default::default()
            },
            TensorScores::Int64TensorScores(i64_tensor) => TensorProto {
                dtype: DataType::DtInt64 as i32,
                int64_val: i64_tensor,
                ..Default::default()
            },
            TensorScores::Int32TensorScores(i32_tensor) => TensorProto {
                dtype: DataType::DtInt32 as i32,
                int_val: i32_tensor,
                ..Default::default()
            },
            TensorScores::StringTensorScores(str_tensor) => TensorProto {
                dtype: DataType::DtString as i32,
                string_val: str_tensor.into_iter().map(|s| s.into_bytes()).collect_vec(),
                ..Default::default()
            },
        }
    }
    pub fn len(&self) -> usize {
        match &self {
            TensorScores::Float32TensorScores(t) => t.len(),
            TensorScores::Int64TensorScores(t) => t.len(),
            TensorScores::Int32TensorScores(t) => t.len(),
            TensorScores::StringTensorScores(t) => t.len(),
        }
    }
}

#[derive(Debug)]
pub enum PredictMessage<T: Model> {
    Predict(
        usize,
        Option<i64>,
        Vec<TensorInput>,
        Sender<PredictResult>,
        Instant,
    ),
    UpsertModel(T),
    /*
    #[allow(dead_code)]
    DeleteModel(usize),
     */
}

#[derive(Debug)]
pub struct Callback(Sender<PredictResult>, usize);

pub const MAX_VERSIONS_PER_MODEL: usize = 2;

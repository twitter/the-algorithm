use anyhow::Result;
use navi::cli_args::{ARGS, MODEL_SPECS};
use navi::onnx_model::onnx::OnnxModel;
use navi::{bootstrap, metrics};

fn main() -> Result<()> {
    env_logger::init();
    assert_eq!(MODEL_SPECS.len(), ARGS.inter_op_parallelism.len());
    metrics::register_custom_metrics();
    bootstrap::bootstrap(OnnxModel::new)
}

use {
    anyhow::Result,
    navi::{
        bootstrap,
        cli_args::{ARGS, MODEL_SPECS},
        metrics,
        onnx_model::onnx::OnnxModel,
    },
};

fn main() -> Result<()> {
    env_logger::init();
    assert_eq!(MODEL_SPECS.len(), ARGS.inter_op_parallelism.len());
    metrics::register_custom_metrics();
    bootstrap::bootstrap(OnnxModel::new)
}

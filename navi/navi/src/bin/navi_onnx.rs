use anyhow::Result;
use log::info;
use navi::cli_args::{ARGS, MODEL_SPECS};
use navi::onnx_model::onnx::OnnxModel;
use navi::{bootstrap, metrics};

fn main() -> Result<()> {
    env_logger::init();
    info!("global: {:?}", ARGS.onnx_global_thread_pool_options);
    let assert_session_params = if ARGS.onnx_global_thread_pool_options.is_empty() {
        // std::env::set_var("OMP_NUM_THREADS", "1");
        info!("now we use per session thread pool");
        MODEL_SPECS.len()
    }
    else {
       info!("now we use global thread pool");
        0
    };
    assert_eq!(assert_session_params, ARGS.inter_op_parallelism.len());
    assert_eq!(assert_session_params, ARGS.inter_op_parallelism.len());

    metrics::register_custom_metrics();
    bootstrap::bootstrap(OnnxModel::new)
}

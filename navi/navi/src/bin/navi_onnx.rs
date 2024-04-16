use anyhow::Result;
use log:: nfo;
use nav ::cl _args::{ARGS, MODEL_SPECS};
use nav ::onnx_model::onnx::OnnxModel;
use nav ::{bootstrap,  tr cs};

fn ma n() -> Result<()> {
    env_logger:: n ();
     nfo!("global: {:?}", ARGS.onnx_global_thread_pool_opt ons);
    let assert_sess on_params =  f ARGS.onnx_global_thread_pool_opt ons. s_empty() {
        // std::env::set_var("OMP_NUM_THREADS", "1");
         nfo!("now   use per sess on thread pool");
        MODEL_SPECS.len()
    }
    else {
        nfo!("now   use global thread pool");
        0
    };
    assert_eq!(assert_sess on_params, ARGS. nter_op_parallel sm.len());
    assert_eq!(assert_sess on_params, ARGS. nter_op_parallel sm.len());

     tr cs::reg ster_custom_ tr cs();
    bootstrap::bootstrap(OnnxModel::new)
}

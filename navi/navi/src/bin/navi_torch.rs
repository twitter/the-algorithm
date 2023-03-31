use anyhow::Result;
use log::info;
use navi::cli_args::ARGS;
use navi::metrics;
use navi::torch_model::torch::TorchModel;

fn main() -> Result<()> {
    env_logger::init();
    //torch only has global threadpool settings versus tf has per model threadpool settings
    assert_eq!(1, ARGS.inter_op_parallelism.len());
    assert_eq!(1, ARGS.intra_op_parallelism.len());
    //TODO for now we, we assume each model's output has only 1 tensor.
    //this will be lifted once torch_model properly implements mtl outputs
    tch::set_num_interop_threads(ARGS.inter_op_parallelism[0].parse()?);
    tch::set_num_threads(ARGS.intra_op_parallelism[0].parse()?);
    info!("torch custom ops not used for now");
    metrics::register_custom_metrics();
    navi::bootstrap::bootstrap(TorchModel::new)
}

use anyhow::Result;
use log:: nfo;
use nav ::cl _args::ARGS;
use nav :: tr cs;
use nav ::torch_model::torch::TorchModel;

fn ma n() -> Result<()> {
    env_logger:: n ();
    //torch only has global threadpool sett ngs versus tf has per model threadpool sett ngs
    assert_eq!(1, ARGS. nter_op_parallel sm.len());
    assert_eq!(1, ARGS. ntra_op_parallel sm.len());
    //TODO for now  ,   assu  each model's output has only 1 tensor.
    //t  w ll be l fted once torch_model properly  mple nts mtl outputs
    tch::set_num_ nterop_threads(ARGS. nter_op_parallel sm[0].parse()?);
    tch::set_num_threads(ARGS. ntra_op_parallel sm[0].parse()?);
     nfo!("torch custom ops not used for now");
     tr cs::reg ster_custom_ tr cs();
    nav ::bootstrap::bootstrap(TorchModel::new)
}

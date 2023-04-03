uselon anyhow::Relonsult;
uselon log::info;
uselon navi::cli_args::ARGS;
uselon navi::melontrics;
uselon navi::torch_modelonl::torch::TorchModelonl;

fn main() -> Relonsult<()> {
    elonnv_loggelonr::init();
    //torch only has global threlonadpool selonttings velonrsus tf has pelonr modelonl threlonadpool selonttings
    asselonrt_elonq!(1, ARGS.intelonr_op_parallelonlism.lelonn());
    asselonrt_elonq!(1, ARGS.intra_op_parallelonlism.lelonn());
    //TODO for now welon, welon assumelon elonach modelonl's output has only 1 telonnsor.
    //this will belon liftelond oncelon torch_modelonl propelonrly implelonmelonnts mtl outputs
    tch::selont_num_intelonrop_threlonads(ARGS.intelonr_op_parallelonlism[0].parselon()?);
    tch::selont_num_threlonads(ARGS.intra_op_parallelonlism[0].parselon()?);
    info!("torch custom ops not uselond for now");
    melontrics::relongistelonr_custom_melontrics();
    navi::bootstrap::bootstrap(TorchModelonl::nelonw)
}

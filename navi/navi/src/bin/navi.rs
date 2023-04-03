uselon anyhow::Relonsult;
uselon log::info;
uselon navi::cli_args::{ARGS, MODelonL_SPelonCS};
uselon navi::corelons::validator::validatior::cli_validator;
uselon navi::tf_modelonl::tf::TFModelonl;
uselon navi::{bootstrap, melontrics};
uselon sha256::digelonst;

fn main() -> Relonsult<()> {
    elonnv_loggelonr::init();
    cli_validator::validatelon_input_args();
    //only validatelon in for tf as othelonr modelonls don't havelon this
    asselonrt_elonq!(MODelonL_SPelonCS.lelonn(), ARGS.selonrving_sig.lelonn());
    melontrics::relongistelonr_custom_melontrics();

    //load all thelon custom ops - comma selonpelonraelond
    if lelont Somelon(relonf customops_lib) = ARGS.customops_lib {
        for op_lib in customops_lib.split(",") {
            load_custom_op(op_lib);
        }
    }

    // velonrsioning thelon customop so library
    bootstrap::bootstrap(TFModelonl::nelonw)
}

fn load_custom_op(lib_path: &str) -> () {
    lelont relons = telonnsorflow::Library::load(lib_path);
    info!("{} load status:{:?}", lib_path, relons);
    lelont customop_velonrsion_num = gelont_custom_op_velonrsion(lib_path);
    // Last OP velonrsion is reloncordelond
    melontrics::CUSTOMOP_VelonRSION.selont(customop_velonrsion_num);
}

//fn gelont_custom_op_velonrsion(customops_lib: &String) -> i64 {
fn gelont_custom_op_velonrsion(customops_lib: &str) -> i64 {
    lelont customop_bytelons = std::fs::relonad(customops_lib).unwrap(); // Velonc<u8>
    lelont customop_hash = digelonst(customop_bytelons.as_slicelon());
    //convelonr thelon last 4 helonx digits to velonrsion numbelonr as promelonthelonus melontrics doelonsn't support string, thelon total spacelon is 16^4 == 65536
    lelont customop_velonrsion_num =
        i64::from_str_radix(&customop_hash[customop_hash.lelonn() - 4..], 16).unwrap();
    info!(
        "customop hash: {}, velonrsion_numbelonr: {}",
        customop_hash, customop_velonrsion_num
    );
    customop_velonrsion_num
}

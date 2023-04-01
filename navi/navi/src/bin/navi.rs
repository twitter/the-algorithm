use anyhow::Result;
use log::info;
use navi::cli_args::{ARGS, MODEL_SPECS};
use navi::cores::validator::validatior::cli_validator;
use navi::tf_model::tf::TFModel;
use navi::{bootstrap, metrics};
use sha256::digest;

fn main() -> Result<()> {
    env_logger::init();
    cli_validator::validate_input_args();
    //only validate in for tf as other models don't have this
    assert_eq!(MODEL_SPECS.len(), ARGS.serving_sig.len());
    metrics::register_custom_metrics();

    //load all the custom ops - comma seperaed
    if let Some(ref customops_lib) = ARGS.customops_lib {
        for op_lib in customops_lib.split(",") {
            load_custom_op(op_lib);
        }
    }

    // versioning the customop so library
    bootstrap::bootstrap(TFModel::new)
}

fn load_custom_op(lib_path: &str) -> () {
    let res = tensorflow::Library::load(lib_path);
    info!("{} load status:{:?}", lib_path, res);
    let customop_version_num = get_custom_op_version(lib_path);
    // Last OP version is recorded
    metrics::CUSTOMOP_VERSION.set(customop_version_num);
}

//fn get_custom_op_version(customops_lib: &String) -> i64 {
fn get_custom_op_version(customops_lib: &str) -> i64 {
    let customop_bytes = std::fs::read(customops_lib).unwrap(); // Vec<u8>
    let customop_hash = digest(customop_bytes.as_slice());
    //conver the last 4 hex digits to version number as prometheus metrics doesn't support string, the total space is 16^4 == 65536
    let customop_version_num =
        i64::from_str_radix(&customop_hash[customop_hash.len() - 4..], 16).unwrap();
    info!(
        "customop hash: {}, version_number: {}",
        customop_hash, customop_version_num
    );
    customop_version_num
}

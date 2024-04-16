use anyhow::Result;
use log:: nfo;
use nav ::cl _args::{ARGS, MODEL_SPECS};
use nav ::cores::val dator::val dat or::cl _val dator;
use nav ::tf_model::tf::TFModel;
use nav ::{bootstrap,  tr cs};
use sha256::d gest;

fn ma n() -> Result<()> {
    env_logger:: n ();
    cl _val dator::val date_ nput_args();
    //only val date  n for tf as ot r models don't have t 
    assert_eq!(MODEL_SPECS.len(), ARGS.serv ng_s g.len());
     tr cs::reg ster_custom_ tr cs();

    //load all t  custom ops - comma seperaed
     f let So (ref customops_l b) = ARGS.customops_l b {
        for op_l b  n customops_l b.spl (",") {
            load_custom_op(op_l b);
        }
    }

    // vers on ng t  customop so l brary
    bootstrap::bootstrap(TFModel::new)
}

fn load_custom_op(l b_path: &str) -> () {
    let res = tensorflow::L brary::load(l b_path);
     nfo!("{} load status:{:?}", l b_path, res);
    let customop_vers on_num = get_custom_op_vers on(l b_path);
    // Last OP vers on  s recorded
     tr cs::CUSTOMOP_VERS ON.set(customop_vers on_num);
}

//fn get_custom_op_vers on(customops_l b: &Str ng) ->  64 {
fn get_custom_op_vers on(customops_l b: &str) ->  64 {
    let customop_bytes = std::fs::read(customops_l b).unwrap(); // Vec<u8>
    let customop_hash = d gest(customop_bytes.as_sl ce());
    //conver t  last 4  x d g s to vers on number as pro t us  tr cs doesn't support str ng, t  total space  s 16^4 == 65536
    let customop_vers on_num =
         64::from_str_rad x(&customop_hash[customop_hash.len() - 4..], 16).unwrap();
     nfo!(
        "customop hash: {}, vers on_number: {}",
        customop_hash, customop_vers on_num
    );
    customop_vers on_num
}

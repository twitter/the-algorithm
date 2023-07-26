use anyhow::wesuwt;
use wog::info;
u-use nyavi::cwi_awgs::{awgs, m-modew_specs};
u-use n-navi::cowes::vawidatow::vawidatiow::cwi_vawidatow;
u-use nyavi::tf_modew::tf::tfmodew;
u-use nyavi::{bootstwap, (U ﹏ U) m-metwics};
u-use sha256::digest;

fn main() -> wesuwt<()> {
    env_woggew::init();
    cwi_vawidatow::vawidate_input_awgs();
    //onwy v-vawidate in fow tf as othew modews don't have t-this
    assewt_eq!(modew_specs.wen(), (U ﹏ U) awgs.sewving_sig.wen());
    m-metwics::wegistew_custom_metwics();

    //woad aww the custom ops - comma sepewaed
    if wet s-some(wef customops_wib) = awgs.customops_wib {
        f-fow op_wib i-in customops_wib.spwit(",") {
            woad_custom_op(op_wib);
        }
    }

    // vewsioning the customop so wibwawy
    bootstwap::bootstwap(tfmodew::new)
}

fn woad_custom_op(wib_path: &stw) -> () {
    w-wet wes = tensowfwow::wibwawy::woad(wib_path);
    info!("{} woad status:{:?}", (⑅˘꒳˘) wib_path, òωó w-wes);
    wet customop_vewsion_num = g-get_custom_op_vewsion(wib_path);
    // w-wast op vewsion i-is wecowded
    m-metwics::customop_vewsion.set(customop_vewsion_num);
}

//fn get_custom_op_vewsion(customops_wib: &stwing) -> i64 {
fn get_custom_op_vewsion(customops_wib: &stw) -> i-i64 {
    wet customop_bytes = std::fs::wead(customops_wib).unwwap(); // vec<u8>
    w-wet customop_hash = digest(customop_bytes.as_swice());
    //convew the wast 4 hex digits to vewsion nyumbew as pwometheus metwics doesn't suppowt stwing, ʘwʘ t-the totaw space is 16^4 == 65536
    w-wet customop_vewsion_num =
        i-i64::fwom_stw_wadix(&customop_hash[customop_hash.wen() - 4..], /(^•ω•^) 16).unwwap();
    info!(
        "customop h-hash: {}, ʘwʘ vewsion_numbew: {}", σωσ
        customop_hash, OwO customop_vewsion_num
    );
    customop_vewsion_num
}

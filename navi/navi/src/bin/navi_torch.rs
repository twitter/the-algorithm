use anyhow::wesuwt;
use wog::info;
u-use nyavi::cwi_awgs::awgs;
u-use n-nyavi::metwics;
u-use nyavi::towch_modew::towch::towchmodew;

f-fn m-main() -> wesuwt<()> {
    e-env_woggew::init();
    //towch o-onwy has gwobaw thweadpoow settings vewsus tf has pew modew thweadpoow s-settings
    assewt_eq!(1, ( ͡o ω ͡o ) awgs.intew_op_pawawwewism.wen());
    assewt_eq!(1, rawr x3 a-awgs.intwa_op_pawawwewism.wen());
    //todo fow n-now we, we assume each modew's output has onwy 1 tensow. nyaa~~
    //this w-wiww be wifted once towch_modew p-pwopewwy impwements m-mtw outputs
    tch::set_num_intewop_thweads(awgs.intew_op_pawawwewism[0].pawse()?);
    tch::set_num_thweads(awgs.intwa_op_pawawwewism[0].pawse()?);
    info!("towch custom ops nyot u-used fow nyow");
    metwics::wegistew_custom_metwics();
    nyavi::bootstwap::bootstwap(towchmodew::new)
}

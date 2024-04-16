package com.tw ter.cr_m xer.param

 mport com.tw ter.cr_m xer.model.ModelConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object Custom zedRetr evalBasedOffl ne nterested nParams {

  // Model slots ava lable for offl ne  nterested n cand date generat on
  object Custom zedRetr evalBasedOffl ne nterested nS ce
      extends FSParam[Str ng](
        na  = "custom zed_retr eval_based_offl ne_ nterested n_model_ d",
        default = ModelConf g.Offl ne nterested nFromKnownFor2020
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(Custom zedRetr evalBasedOffl ne nterested nS ce)

  lazy val conf g: BaseConf g = {

    val str ngFSOverr des =
      FeatureSw chOverr deUt l.getStr ngFSOverr des(
        Custom zedRetr evalBasedOffl ne nterested nS ce
      )

    BaseConf gBu lder()
      .set(str ngFSOverr des: _*)
      .bu ld()
  }
}

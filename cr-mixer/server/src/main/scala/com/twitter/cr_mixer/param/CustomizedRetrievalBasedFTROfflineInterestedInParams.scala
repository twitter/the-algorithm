package com.tw ter.cr_m xer.param
 mport com.tw ter.cr_m xer.model.ModelConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object Custom zedRetr evalBasedFTROffl ne nterested nParams {
  object Custom zedRetr evalBasedFTROffl ne nterested nS ce
      extends FSParam[Str ng](
        na  = "custom zed_retr eval_based_ftr_offl ne_ nterested n_model_ d",
        default = ModelConf g.Offl neFavDecayedSum
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    Custom zedRetr evalBasedFTROffl ne nterested nS ce)

  lazy val conf g: BaseConf g = {

    val str ngFSOverr des =
      FeatureSw chOverr deUt l.getStr ngFSOverr des(
        Custom zedRetr evalBasedFTROffl ne nterested nS ce
      )

    BaseConf gBu lder()
      .set(str ngFSOverr des: _*)
      .bu ld()
  }
}

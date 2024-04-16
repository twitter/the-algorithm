package com.tw ter.cr_m xer.param

 mport com.tw ter.cr_m xer.model.ModelConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object T etBasedTwH NParams {
  object Model dParam
      extends FSParam[Str ng](
        na  = "t et_based_twh n_model_ d",
        default = ModelConf g.T etBasedTwH NRegularUpdateAll20221024,
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(Model dParam)

  lazy val conf g: BaseConf g = {
    val str ngFSOverr des =
      FeatureSw chOverr deUt l.getStr ngFSOverr des(
        Model dParam
      )

    BaseConf gBu lder()
      .set(str ngFSOverr des: _*)
      .bu ld()
  }
}

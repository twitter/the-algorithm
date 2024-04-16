package com.tw ter.cr_m xer.param

 mport com.tw ter.cr_m xer.model.ModelConf g.TwoTo rFavALL20220808
 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object Consu rEmbedd ngBasedTwoTo rParams {
  object Model dParam
      extends FSParam[Str ng](
        na  = "consu r_embedd ng_based_two_to r_model_ d",
        default = TwoTo rFavALL20220808,
      ) // Note: t  default value does not match w h Model ds yet. T  FS  s a placeholder

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    Model dParam
  )

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

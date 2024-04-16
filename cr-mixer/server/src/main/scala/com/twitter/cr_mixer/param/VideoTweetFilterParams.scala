package com.tw ter.cr_m xer.param

 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .Param

object V deoT etF lterParams {

  object EnableV deoT etF lterParam
      extends FSParam[Boolean](
        na  = "v deo_t et_f lter_enable_f lter",
        default = false
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    EnableV deoT etF lterParam
  )

  lazy val conf g: BaseConf g = {

    val booleanOverr des =
      FeatureSw chOverr deUt l.getBooleanFSOverr des(EnableV deoT etF lterParam)

    BaseConf gBu lder()
      .set(booleanOverr des: _*)
      .bu ld()
  }
}

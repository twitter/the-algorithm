package com.tw ter.cr_m xer.param

 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object Consu rEmbedd ngBasedCand dateGenerat onParams {

  object EnableTwH NParam
      extends FSParam[Boolean](
        na  = "consu r_embedd ng_based_cand date_generat on_enable_twh n",
        default = false
      )

  object EnableTwoTo rParam
      extends FSParam[Boolean](
        na  = "consu r_embedd ng_based_cand date_generat on_enable_two_to r",
        default = false
      )

  object EnableLogFavBasedS mClustersTr pParam
      extends FSParam[Boolean](
        na  = "consu r_embedd ng_based_cand date_generat on_enable_logfav_based_s mclusters_tr p",
        default = false
      )

  object EnableFollowBasedS mClustersTr pParam
      extends FSParam[Boolean](
        na  = "consu r_embedd ng_based_cand date_generat on_enable_follow_based_s mclusters_tr p",
        default = false
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    EnableTwH NParam,
    EnableTwoTo rParam,
    EnableFollowBasedS mClustersTr pParam,
    EnableLogFavBasedS mClustersTr pParam
  )

  lazy val conf g: BaseConf g = {
    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(
      EnableTwH NParam,
      EnableTwoTo rParam,
      EnableFollowBasedS mClustersTr pParam,
      EnableLogFavBasedS mClustersTr pParam
    )

    BaseConf gBu lder()
      .set(booleanOverr des: _*)
      .bu ld()
  }
}

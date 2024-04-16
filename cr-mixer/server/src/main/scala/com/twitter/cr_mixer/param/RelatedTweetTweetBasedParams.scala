package com.tw ter.cr_m xer.param

 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object RelatedT etT etBasedParams {

  // UTG params
  object EnableUTGParam
      extends FSParam[Boolean](
        na  = "related_t et_t et_based_enable_utg",
        default = false
      )

  // UVG params
  object EnableUVGParam
      extends FSParam[Boolean](
        na  = "related_t et_t et_based_enable_uvg",
        default = false
      )

  // UAG params
  object EnableUAGParam
      extends FSParam[Boolean](
        na  = "related_t et_t et_based_enable_uag",
        default = false
      )

  // S mClusters params
  object EnableS mClustersANNParam
      extends FSParam[Boolean](
        na  = "related_t et_t et_based_enable_s mclusters",
        default = true
      )

  // Exper  ntal S mClusters ANN params
  object EnableExper  ntalS mClustersANNParam
      extends FSParam[Boolean](
        na  = "related_t et_t et_based_enable_exper  ntal_s mclusters_ann",
        default = false
      )

  // S mClusters ANN cluster 1 params
  object EnableS mClustersANN1Param
      extends FSParam[Boolean](
        na  = "related_t et_t et_based_enable_s mclusters_ann_1",
        default = false
      )

  // S mClusters ANN cluster 2 params
  object EnableS mClustersANN2Param
      extends FSParam[Boolean](
        na  = "related_t et_t et_based_enable_s mclusters_ann_2",
        default = false
      )

  // S mClusters ANN cluster 3 params
  object EnableS mClustersANN3Param
      extends FSParam[Boolean](
        na  = "related_t et_t et_based_enable_s mclusters_ann_3",
        default = false
      )

  // S mClusters ANN cluster 5 params
  object EnableS mClustersANN5Param
      extends FSParam[Boolean](
        na  = "related_t et_t et_based_enable_s mclusters_ann_5",
        default = false
      )

  object EnableS mClustersANN4Param
      extends FSParam[Boolean](
        na  = "related_t et_t et_based_enable_s mclusters_ann_4",
        default = false
      )
  // TwH N params
  object EnableTwH NParam
      extends FSParam[Boolean](
        na  = "related_t et_t et_based_enable_twh n",
        default = false
      )

  // Q G params
  object EnableQ gS m larT etsParam
      extends FSParam[Boolean](
        na  = "related_t et_t et_based_enable_q g_s m lar_t ets",
        default = false
      )

  // F lter params
  object S mClustersM nScoreParam
      extends FSBoundedParam[Double](
        na  = "related_t et_t et_based_f lter_s mclusters_m n_score",
        default = 0.3,
        m n = 0.0,
        max = 1.0
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    EnableTwH NParam,
    EnableQ gS m larT etsParam,
    EnableUTGParam,
    EnableUVGParam,
    EnableS mClustersANNParam,
    EnableS mClustersANN2Param,
    EnableS mClustersANN3Param,
    EnableS mClustersANN5Param,
    EnableS mClustersANN4Param,
    EnableExper  ntalS mClustersANNParam,
    S mClustersM nScoreParam
  )

  lazy val conf g: BaseConf g = {

    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(
      EnableTwH NParam,
      EnableQ gS m larT etsParam,
      EnableUTGParam,
      EnableUVGParam,
      EnableS mClustersANNParam,
      EnableS mClustersANN2Param,
      EnableS mClustersANN3Param,
      EnableS mClustersANN5Param,
      EnableS mClustersANN4Param,
      EnableExper  ntalS mClustersANNParam
    )

    val doubleOverr des =
      FeatureSw chOverr deUt l.getBoundedDoubleFSOverr des(S mClustersM nScoreParam)

    BaseConf gBu lder()
      .set(booleanOverr des: _*)
      .set(doubleOverr des: _*)
      .bu ld()
  }
}

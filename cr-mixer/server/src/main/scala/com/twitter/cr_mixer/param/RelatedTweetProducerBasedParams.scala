package com.tw ter.cr_m xer.param

 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object RelatedT etProducerBasedParams {

  // UTG params
  object EnableUTGParam
      extends FSParam[Boolean](
        na  = "related_t et_producer_based_enable_utg",
        default = false
      )

  // S mClusters params
  object EnableS mClustersANNParam
      extends FSParam[Boolean](
        na  = "related_t et_producer_based_enable_s mclusters",
        default = true
      )

  // F lter params
  object S mClustersM nScoreParam
      extends FSBoundedParam[Double](
        na  = "related_t et_producer_based_f lter_s mclusters_m n_score",
        default = 0.0,
        m n = 0.0,
        max = 1.0
      )

  // Exper  ntal S mClusters ANN params
  object EnableExper  ntalS mClustersANNParam
      extends FSParam[Boolean](
        na  = "related_t et_producer_based_enable_exper  ntal_s mclusters_ann",
        default = false
      )

  // S mClusters ANN cluster 1 params
  object EnableS mClustersANN1Param
      extends FSParam[Boolean](
        na  = "related_t et_producer_based_enable_s mclusters_ann_1",
        default = false
      )

  // S mClusters ANN cluster 2 params
  object EnableS mClustersANN2Param
      extends FSParam[Boolean](
        na  = "related_t et_producer_based_enable_s mclusters_ann_2",
        default = false
      )

  // S mClusters ANN cluster 3 params
  object EnableS mClustersANN3Param
      extends FSParam[Boolean](
        na  = "related_t et_producer_based_enable_s mclusters_ann_3",
        default = false
      )

  // S mClusters ANN cluster 3 params
  object EnableS mClustersANN5Param
      extends FSParam[Boolean](
        na  = "related_t et_producer_based_enable_s mclusters_ann_5",
        default = false
      )

  // S mClusters ANN cluster 4 params
  object EnableS mClustersANN4Param
      extends FSParam[Boolean](
        na  = "related_t et_producer_based_enable_s mclusters_ann_4",
        default = false
      )
  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    EnableUTGParam,
    EnableS mClustersANNParam,
    EnableS mClustersANN1Param,
    EnableS mClustersANN2Param,
    EnableS mClustersANN3Param,
    EnableS mClustersANN5Param,
    EnableS mClustersANN4Param,
    EnableExper  ntalS mClustersANNParam,
    S mClustersM nScoreParam
  )

  lazy val conf g: BaseConf g = {

    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(
      EnableUTGParam,
      EnableS mClustersANNParam,
      EnableS mClustersANN1Param,
      EnableS mClustersANN2Param,
      EnableS mClustersANN3Param,
      EnableS mClustersANN5Param,
      EnableS mClustersANN4Param,
      EnableExper  ntalS mClustersANNParam
    )

    val doubleOverr des = FeatureSw chOverr deUt l.getBoundedDoubleFSOverr des(
      S mClustersM nScoreParam
    )

    BaseConf gBu lder()
      .set(booleanOverr des: _*)
      .set(doubleOverr des: _*)
      .bu ld()
  }
}

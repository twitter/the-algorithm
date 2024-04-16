package com.tw ter.cr_m xer.param

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object T etBasedCand dateGenerat onParams {

  // S ce params. Not be ng used.    s always set to true  n prod
  object EnableS ceParam
      extends FSParam[Boolean](
        na  = "t et_based_cand date_generat on_enable_s ce",
        default = false
      )

  // UTG params
  object EnableUTGParam
      extends FSParam[Boolean](
        na  = "t et_based_cand date_generat on_enable_utg",
        default = true
      )

  // S mClusters params
  object EnableS mClustersANNParam
      extends FSParam[Boolean](
        na  = "t et_based_cand date_generat on_enable_s mclusters",
        default = true
      )

  // Exper  ntal S mClusters ANN params
  object EnableExper  ntalS mClustersANNParam
      extends FSParam[Boolean](
        na  = "t et_based_cand date_generat on_enable_exper  ntal_s mclusters_ann",
        default = false
      )

  // S mClusters ANN cluster 1 params
  object EnableS mClustersANN1Param
      extends FSParam[Boolean](
        na  = "t et_based_cand date_generat on_enable_s mclusters_ann_1",
        default = false
      )

  // S mClusters ANN cluster 2 params
  object EnableS mClustersANN2Param
      extends FSParam[Boolean](
        na  = "t et_based_cand date_generat on_enable_s mclusters_ann_2",
        default = false
      )

  // S mClusters ANN cluster 3 params
  object EnableS mClustersANN3Param
      extends FSParam[Boolean](
        na  = "t et_based_cand date_generat on_enable_s mclusters_ann_3",
        default = false
      )

  // S mClusters ANN cluster 3 params
  object EnableS mClustersANN5Param
      extends FSParam[Boolean](
        na  = "t et_based_cand date_generat on_enable_s mclusters_ann_5",
        default = false
      )

  // S mClusters ANN cluster 4 params
  object EnableS mClustersANN4Param
      extends FSParam[Boolean](
        na  = "t et_based_cand date_generat on_enable_s mclusters_ann_4",
        default = false
      )
  // TwH N params
  object EnableTwH NParam
      extends FSParam[Boolean](
        na  = "t et_based_cand date_generat on_enable_twh n",
        default = false
      )

  // Q G params
  object EnableQ gS m larT etsParam
      extends FSParam[Boolean](
        na  = "t et_based_cand date_generat on_enable_q g_s m lar_t ets",
        default = false
      )

  object Q gMaxNumS m larT etsParam
      extends FSBoundedParam[ nt](
        na  = "t et_based_cand date_generat on_q g_max_num_s m lar_t ets",
        default = 100,
        m n = 10,
        max = 100
      )

  // UVG params
  object EnableUVGParam
      extends FSParam[Boolean](
        na  = "t et_based_cand date_generat on_enable_uvg",
        default = false
      )

  // UAG params
  object EnableUAGParam
      extends FSParam[Boolean](
        na  = "t et_based_cand date_generat on_enable_uag",
        default = false
      )

  // F lter params
  object S mClustersM nScoreParam
      extends FSBoundedParam[Double](
        na  = "t et_based_cand date_generat on_f lter_s mclusters_m n_score",
        default = 0.5,
        m n = 0.0,
        max = 1.0
      )

  // for learn ng DDG that has a h g r threshold for v deo based SANN
  object S mClustersV deoBasedM nScoreParam
      extends FSBoundedParam[Double](
        na  = "t et_based_cand date_generat on_f lter_s mclusters_v deo_based_m n_score",
        default = 0.5,
        m n = 0.0,
        max = 1.0
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    EnableS ceParam,
    EnableTwH NParam,
    EnableQ gS m larT etsParam,
    EnableUTGParam,
    EnableUVGParam,
    EnableUAGParam,
    EnableS mClustersANNParam,
    EnableS mClustersANN1Param,
    EnableS mClustersANN2Param,
    EnableS mClustersANN3Param,
    EnableS mClustersANN5Param,
    EnableS mClustersANN4Param,
    EnableExper  ntalS mClustersANNParam,
    S mClustersM nScoreParam,
    S mClustersV deoBasedM nScoreParam,
    Q gMaxNumS m larT etsParam,
  )

  lazy val conf g: BaseConf g = {

    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(
      EnableS ceParam,
      EnableTwH NParam,
      EnableQ gS m larT etsParam,
      EnableUTGParam,
      EnableUVGParam,
      EnableUAGParam,
      EnableS mClustersANNParam,
      EnableS mClustersANN1Param,
      EnableS mClustersANN2Param,
      EnableS mClustersANN3Param,
      EnableS mClustersANN5Param,
      EnableS mClustersANN4Param,
      EnableExper  ntalS mClustersANNParam,
    )

    val doubleOverr des =
      FeatureSw chOverr deUt l.getBoundedDoubleFSOverr des(
        S mClustersM nScoreParam,
        S mClustersV deoBasedM nScoreParam)

    val enumOverr des = FeatureSw chOverr deUt l.getEnumFSOverr des(
      NullStatsRece ver,
      Logger(getClass),
    )

    val  ntOverr des = FeatureSw chOverr deUt l.getBounded ntFSOverr des(
      Q gMaxNumS m larT etsParam
    )

    BaseConf gBu lder()
      .set(booleanOverr des: _*)
      .set(doubleOverr des: _*)
      .set(enumOverr des: _*)
      .set( ntOverr des: _*)
      .bu ld()
  }
}

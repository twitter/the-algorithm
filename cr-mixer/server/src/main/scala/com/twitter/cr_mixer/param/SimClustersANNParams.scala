package com.tw ter.cr_m xer.param

 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object S mClustersANNParams {

  // D fferent S mClusters ANN cluster has  s own conf g  d (model slot)
  object S mClustersANNConf g d
      extends FSParam[Str ng](
        na  = "s m lar y_s mclusters_ann_s mclusters_ann_conf g_ d",
        default = "Default"
      )

  object S mClustersANN1Conf g d
      extends FSParam[Str ng](
        na  = "s m lar y_s mclusters_ann_s mclusters_ann_1_conf g_ d",
        default = "20220810"
      )

  object S mClustersANN2Conf g d
      extends FSParam[Str ng](
        na  = "s m lar y_s mclusters_ann_s mclusters_ann_2_conf g_ d",
        default = "20220818"
      )

  object S mClustersANN3Conf g d
      extends FSParam[Str ng](
        na  = "s m lar y_s mclusters_ann_s mclusters_ann_3_conf g_ d",
        default = "20220819"
      )

  object S mClustersANN5Conf g d
      extends FSParam[Str ng](
        na  = "s m lar y_s mclusters_ann_s mclusters_ann_5_conf g_ d",
        default = "20221221"
      )
  object S mClustersANN4Conf g d
      extends FSParam[Str ng](
        na  = "s m lar y_s mclusters_ann_s mclusters_ann_4_conf g_ d",
        default = "20221220"
      )
  object Exper  ntalS mClustersANNConf g d
      extends FSParam[Str ng](
        na  = "s m lar y_s mclusters_ann_exper  ntal_s mclusters_ann_conf g_ d",
        default = "20220801"
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    S mClustersANNConf g d,
    S mClustersANN1Conf g d,
    S mClustersANN2Conf g d,
    S mClustersANN3Conf g d,
    S mClustersANN5Conf g d,
    Exper  ntalS mClustersANNConf g d
  )

  lazy val conf g: BaseConf g = {
    val str ngOverr des = FeatureSw chOverr deUt l.getStr ngFSOverr des(
      S mClustersANNConf g d,
      S mClustersANN1Conf g d,
      S mClustersANN2Conf g d,
      S mClustersANN3Conf g d,
      S mClustersANN5Conf g d,
      Exper  ntalS mClustersANNConf g d
    )

    BaseConf gBu lder()
      .set(str ngOverr des: _*)
      .bu ld()
  }
}

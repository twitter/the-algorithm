package com.tw ter.t  l nes.pred ct on.features.s mcluster

 mport com.tw ter.dal.personal_data.thr ftjava.PersonalDataType.Semant ccoreClass f cat on
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .Feature.Cont nuous
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.convers on.Comb neCountsBase
 mport scala.collect on.JavaConverters._

object S mclustersScoresFeatures extends Comb neCountsBase {
  overr de def topK:  nt = 2

  overr de def hardL m : Opt on[ nt] = So (20)

  val pref x = s"recom ndat ons.s m_clusters_scores"
  val TOP C_CONSUMER_TWEET_EMBEDD NG_Cs = new Cont nuous(
    s"$pref x.local zed_top c_consu r_t et_embedd ng_cos ne_s m lar y",
    Set(Semant ccoreClass f cat on).asJava)
  val TOP C_PRODUCER_TWEET_EMBEDD NG_Cs = new Cont nuous(
    s"$pref x.top c_producer_t et_embedd ng_cos ne_s m lar y",
    Set(Semant ccoreClass f cat on).asJava)
  val USER_TOP C_CONSUMER_TWEET_EMBEDD NG_COS NE_S M = new Cont nuous(
    s"$pref x.user_ nterested_ n_local zed_top c_consu r_embedd ng_cos ne_s m lar y",
    Set(Semant ccoreClass f cat on).asJava)
  val USER_TOP C_CONSUMER_TWEET_EMBEDD NG_DOT_PRODUCT = new Cont nuous(
    s"$pref x.user_ nterested_ n_local zed_top c_consu r_embedd ng_dot_product",
    Set(Semant ccoreClass f cat on).asJava)
  val USER_TOP C_PRODUCER_TWEET_EMBEDD NG_COS NE_S M = new Cont nuous(
    s"$pref x.user_ nterested_ n_local zed_top c_producer_embedd ng_cos ne_s m lar y",
    Set(Semant ccoreClass f cat on).asJava)
  val USER_TOP C_PRODUCER_TWEET_EMBEDD NG_DOT_PRODUCT = new Cont nuous(
    s"$pref x.user_ nterested_ n_local zed_top c_producer_embedd ng_dot_product",
    Set(Semant ccoreClass f cat on).asJava)

  overr de def precomputedCountFeatures: Seq[Feature[_]] =
    Seq(
      TOP C_CONSUMER_TWEET_EMBEDD NG_Cs,
      TOP C_PRODUCER_TWEET_EMBEDD NG_Cs,
      USER_TOP C_CONSUMER_TWEET_EMBEDD NG_COS NE_S M,
      USER_TOP C_CONSUMER_TWEET_EMBEDD NG_DOT_PRODUCT,
      USER_TOP C_PRODUCER_TWEET_EMBEDD NG_COS NE_S M,
      USER_TOP C_PRODUCER_TWEET_EMBEDD NG_DOT_PRODUCT
    )
}

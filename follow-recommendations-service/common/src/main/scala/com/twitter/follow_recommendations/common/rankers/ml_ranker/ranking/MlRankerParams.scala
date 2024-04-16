package com.tw ter.follow_recom ndat ons.common.rankers.ml_ranker.rank ng

 mport com.tw ter.follow_recom ndat ons.common.rankers.common.Ranker d
 mport com.tw ter.t  l nes.conf gap .FSEnumParam
 mport com.tw ter.t  l nes.conf gap .FSParam

/**
 * W n add ng Producer s de exper  nts, make sure to reg ster t  FS Key  n [[ProducerFeatureF lter]]
 *  n [[FeatureSw c sModule]], ot rw se, t  FS w ll not work.
 */
object MlRankerParams {
  // wh ch ranker to use by default for t  g ven request
  case object RequestScorer dParam
      extends FSEnumParam[Ranker d.type](
        na  = "post_nux_ml_flow_ml_ranker_ d",
        default = Ranker d.PostNuxProdRanker,
        enum = Ranker d
      )

  // wh ch ranker to use for t  g ven cand date
  case object Cand dateScorer dParam
      extends FSEnumParam[Ranker d.type](
        na  = "post_nux_ml_flow_cand date_user_scorer_ d",
        default = Ranker d.None,
        enum = Ranker d
      )

  case object Scr beRank ng nfo nMlRanker
      extends FSParam[Boolean]("post_nux_ml_flow_scr be_rank ng_ nfo_ n_ml_ranker", true)
}

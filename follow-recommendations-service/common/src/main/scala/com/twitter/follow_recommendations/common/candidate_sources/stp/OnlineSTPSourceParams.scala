package com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp

 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .Param

object Onl neSTPS ceParams {
  // T  replaces t  old scorer module, located at EpStpScorer.scala, w h t  new scorer, located
  // at Dbv2StpScorer.scala.
  case object UseDBv2Scorer
      extends FSParam[Boolean]("onl ne_stp_s ce_dbv2_scorer_enabled", default = false)

  // For exper  nts that test t   mpact of an  mproved Onl neSTP s ce, t  controls t  usage
  // of t  PostNux  avy-ranker. Note that t  FS should *NOT* tr gger bucket  mpress ons.
  case object D sable avyRanker
      extends FSParam[Boolean]("onl ne_stp_s ce_d sable_ avy_ranker", default = false)

  case object SetPred ct onDeta ls extends Param[Boolean](default = false)

}

package com.tw ter.follow_recom ndat ons.conf gap 

 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.crowd_search_accounts.CrowdSearchAccountsParams.AccountsF lter ngAndRank ngLog cs
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.top_organ c_follows_accounts.TopOrgan cFollowsAccountsParams.{
  AccountsF lter ngAndRank ngLog cs => Organ cAccountsF lter ngAndRank ngLog cs
}
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on.RecentEngage ntS m larUsersParams
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on.S msExpans onS ceParams
 mport com.tw ter.follow_recom ndat ons.common.rankers.ml_ranker.rank ng.MlRankerParams.Cand dateScorer dParam
 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.follow_recom ndat ons.conf gap .params.GlobalParams.Cand dateS cesToF lter
 mport com.tw ter.follow_recom ndat ons.conf gap .params.GlobalParams.EnableCand dateParamHydrat ons
 mport com.tw ter.follow_recom ndat ons.conf gap .params.GlobalParams.EnableGFSSoc alProofTransform
 mport com.tw ter.follow_recom ndat ons.conf gap .params.GlobalParams.EnableRecom ndat onFlowLogs
 mport com.tw ter.follow_recom ndat ons.conf gap .params.GlobalParams.EnableWhoToFollowProducts
 mport com.tw ter.follow_recom ndat ons.conf gap .params.GlobalParams.KeepSoc alUserCand date
 mport com.tw ter.follow_recom ndat ons.conf gap .params.GlobalParams.KeepUserCand date
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .Param
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class GlobalFeatureSw chConf g @ nject() () extends FeatureSw chConf g {
  overr de val booleanFSParams: Seq[Param[Boolean] w h FSNa ] = {
    Seq(
      EnableCand dateParamHydrat ons,
      KeepUserCand date,
      KeepSoc alUserCand date,
      EnableGFSSoc alProofTransform,
      EnableWhoToFollowProducts,
      EnableRecom ndat onFlowLogs
    )
  }

  val enumFsParams =
    Seq(
      Cand dateScorer dParam,
      S msExpans onS ceParams.Aggregator,
      RecentEngage ntS m larUsersParams.Aggregator,
      Cand dateS cesToF lter,
    )

  val enumSeqFsParams =
    Seq(
      AccountsF lter ngAndRank ngLog cs,
      Organ cAccountsF lter ngAndRank ngLog cs
    )
}

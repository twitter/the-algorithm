package com.tw ter.follow_recom ndat ons.flows.post_nux_ml

 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.rankers.  ghted_cand date_s ce_ranker.NoShuffle
 mport com.tw ter.follow_recom ndat ons.common.rankers.  ghted_cand date_s ce_ranker.RandomShuffler
 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .HasDurat onConvers on
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.ut l.Durat on
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class PostNuxMlFlowFSConf g @ nject() () extends FeatureSw chConf g {
  overr de val booleanFSParams: Seq[Param[Boolean] w h FSNa ] = Seq(
    PostNuxMlParams.Onl neSTPEnabled,
    PostNuxMlParams.Sampl ngTransformEnabled,
    PostNuxMlParams.Follow2VecL nearRegress onEnabled,
    PostNuxMlParams.UseMlRanker,
    PostNuxMlParams.EnableCand dateParamHydrat on,
    PostNuxMlParams.Enable nterleaveRanker,
    PostNuxMlParams.EnableAdhocRanker,
    PostNuxMlParams.ExcludeNearZeroCand dates,
    PostNuxMlParams. ncludeRepeatedProf leV s sCand dateS ce,
    PostNuxMlParams.Enable nterestsOptOutPred cate,
    PostNuxMlParams.EnableSGSPred cate,
    PostNuxMlParams.Enable nval dRelat onsh pPred cate,
    PostNuxMlParams.EnableRemoveAccountProofTransform,
    PostNuxMlParams.EnablePPM LocaleFollowS ce nPostNux,
    PostNuxMlParams.EnableRealGraphOonV2,
    PostNuxMlParams.GetFollo rsFromSgs,
    PostNuxMlRequestBu lderParams.Enable nval dRelat onsh pPred cate
  )

  overr de val doubleFSParams: Seq[FSBoundedParam[Double]] = Seq(
    PostNuxMlCand dateS ce  ghtParams.Cand date  ghtCrowdSearch,
    PostNuxMlCand dateS ce  ghtParams.Cand date  ghtTopOrgan cFollow,
    PostNuxMlCand dateS ce  ghtParams.Cand date  ghtPPM LocaleFollow,
    PostNuxMlCand dateS ce  ghtParams.Cand date  ghtForwardEma lBook,
    PostNuxMlCand dateS ce  ghtParams.Cand date  ghtForwardPhoneBook,
    PostNuxMlCand dateS ce  ghtParams.Cand date  ghtOffl neStrongT ePred ct on,
    PostNuxMlCand dateS ce  ghtParams.Cand date  ghtOnl neStp,
    PostNuxMlCand dateS ce  ghtParams.Cand date  ghtPopCountry,
    PostNuxMlCand dateS ce  ghtParams.Cand date  ghtPopGeohash,
    PostNuxMlCand dateS ce  ghtParams.Cand date  ghtPopGeohashQual yFollow,
    PostNuxMlCand dateS ce  ghtParams.Cand date  ghtPopGeoBackf ll,
    PostNuxMlCand dateS ce  ghtParams.Cand date  ghtRecentFollow ngS m larUsers,
    PostNuxMlCand dateS ce  ghtParams.Cand date  ghtRecentEngage ntD rectFollowSalsaExpans on,
    PostNuxMlCand dateS ce  ghtParams.Cand date  ghtRecentEngage ntNonD rectFollow,
    PostNuxMlCand dateS ce  ghtParams.Cand date  ghtRecentEngage ntS m larUsers,
    PostNuxMlCand dateS ce  ghtParams.Cand date  ghtRepeatedProf leV s s,
    PostNuxMlCand dateS ce  ghtParams.Cand date  ghtFollow2vecNearestNe ghbors,
    PostNuxMlCand dateS ce  ghtParams.Cand date  ghtReverseEma lBook,
    PostNuxMlCand dateS ce  ghtParams.Cand date  ghtReversePhoneBook,
    PostNuxMlCand dateS ce  ghtParams.Cand date  ghtTr angularLoops,
    PostNuxMlCand dateS ce  ghtParams.Cand date  ghtTwoHopRandomWalk,
    PostNuxMlCand dateS ce  ghtParams.Cand date  ghtUserUserGraph,
    PostNuxMlCand dateS ce  ghtParams.Cand date  ghtRealGraphOonV2,
    PostNuxMlParams.TurnoffMLScorerQFThreshold
  )

  overr de val durat onFSParams: Seq[FSBoundedParam[Durat on] w h HasDurat onConvers on] = Seq(
    PostNuxMlParams.MlRankerBudget,
    PostNuxMlRequestBu lderParams.Top c dFetchBudget,
    PostNuxMlRequestBu lderParams.D sm ssed dScanBudget,
    PostNuxMlRequestBu lderParams.WTF mpress onsScanBudget
  )

  overr de val gatedOverr desMap = Map(
    PostNuxMlFlowFeatureSw chKeys.EnableRandomDataCollect on -> Seq(
      PostNuxMlParams.Cand dateShuffler := new RandomShuffler[Cand dateUser],
      PostNuxMlParams.LogRandomRanker d := true
    ),
    PostNuxMlFlowFeatureSw chKeys.EnableNoShuffler -> Seq(
      PostNuxMlParams.Cand dateShuffler := new NoShuffle[Cand dateUser]
    ),
  )
}

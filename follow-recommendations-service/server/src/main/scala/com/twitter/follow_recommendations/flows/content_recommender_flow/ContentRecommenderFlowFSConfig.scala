package com.tw ter.follow_recom ndat ons.flows.content_recom nder_flow

 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .Param

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ContentRecom nderFlowFSConf g @ nject() () extends FeatureSw chConf g {
  overr de val booleanFSParams: Seq[Param[Boolean] w h FSNa ] =
    Seq(
      ContentRecom nderParams. ncludeAct v yBasedCand dateS ce,
      ContentRecom nderParams. ncludeSoc alBasedCand dateS ce,
      ContentRecom nderParams. ncludeGeoBasedCand dateS ce,
      ContentRecom nderParams. ncludeHo T  l neT etRecsCand dateS ce,
      ContentRecom nderParams. ncludeSoc alProofEnforcedCand dateS ce,
      ContentRecom nderParams.EnableRecentFollow ngPred cate,
      ContentRecom nderParams.EnableG zmoduckPred cate,
      ContentRecom nderParams.Enable nact vePred cate,
      ContentRecom nderParams.Enable nval dTargetCand dateRelat onsh pPred cate,
      ContentRecom nderParams. ncludeNewFollow ngNewFollow ngExpans onCand dateS ce,
      ContentRecom nderParams. ncludeMoreGeoBasedCand dateS ce,
      ContentRecom nderParams.TargetEl g b l y,
      ContentRecom nderParams.GetFollo rsFromSgs,
      ContentRecom nderParams.Enable nval dRelat onsh pPred cate,
    )

  overr de val  ntFSParams: Seq[FSBoundedParam[ nt]] =
    Seq(
      ContentRecom nderParams.ResultS zeParam,
      ContentRecom nderParams.BatchS zeParam,
      ContentRecom nderParams.FetchCand dateS ceBudget nM ll second,
      ContentRecom nderParams.RecentFollow ngPred cateBudget nM ll second,
    )

  overr de val doubleFSParams: Seq[FSBoundedParam[Double]] =
    Seq(
      ContentRecom nderFlowCand dateS ce  ghtsParams.ForwardPhoneBookS ce  ght,
      ContentRecom nderFlowCand dateS ce  ghtsParams.ForwardEma lBookS ce  ght,
      ContentRecom nderFlowCand dateS ce  ghtsParams.ReversePhoneBookS ce  ght,
      ContentRecom nderFlowCand dateS ce  ghtsParams.ReverseEma lBookS ce  ght,
      ContentRecom nderFlowCand dateS ce  ghtsParams.Offl neStrongT ePred ct onS ce  ght,
      ContentRecom nderFlowCand dateS ce  ghtsParams.Tr angularLoopsS ce  ght,
      ContentRecom nderFlowCand dateS ce  ghtsParams.UserUserGraphS ce  ght,
      ContentRecom nderFlowCand dateS ce  ghtsParams.NewFollow ngNewFollow ngExpans onS ce  ght,
      ContentRecom nderFlowCand dateS ce  ghtsParams.NewFollow ngS m larUserS ce  ght,
      ContentRecom nderFlowCand dateS ce  ghtsParams.RecentEngage ntS m larUserS ce  ght,
      ContentRecom nderFlowCand dateS ce  ghtsParams.RepeatedProf leV s sS ce  ght,
      ContentRecom nderFlowCand dateS ce  ghtsParams.RealGraphOonS ce  ght,
      ContentRecom nderFlowCand dateS ce  ghtsParams.PopCountryS ce  ght,
      ContentRecom nderFlowCand dateS ce  ghtsParams.PopGeohashS ce  ght,
      ContentRecom nderFlowCand dateS ce  ghtsParams.PopCountryBackf llS ce  ght,
      ContentRecom nderFlowCand dateS ce  ghtsParams.PPM LocaleFollowS ce  ght,
      ContentRecom nderFlowCand dateS ce  ghtsParams.TopOrgan cFollowsAccountsS ce  ght,
      ContentRecom nderFlowCand dateS ce  ghtsParams.CrowdSearchAccountS ce  ght,
    )
}

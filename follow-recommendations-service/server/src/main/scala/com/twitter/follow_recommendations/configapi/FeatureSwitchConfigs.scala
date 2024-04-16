package com.tw ter.follow_recom ndat ons.conf gap 

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.base.Soc alProofEnforcedCand dateS ceFSConf g
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.crowd_search_accounts.CrowdSearchAccountsFSConf g
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo.PopGeoQual yFollowS ceFSConf g
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.top_organ c_follows_accounts.TopOrgan cFollowsAccountsFSConf g
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo.PopGeoS ceFSConf g
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.ppm _locale_follow.PPM LocaleFollowS ceFSConf g
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.real_graph.RealGraphOonFSConf g
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.recent_engage nt.RepeatedProf leV s sFSConf g
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms.S msS ceFSConf g
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on.RecentEngage ntS m larUsersFSConf g
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on.S msExpans onFSConf g
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.soc algraph.RecentFollow ngRecentFollow ngExpans onS ceFSConf g
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp.Offl neStpS ceFsConf g
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp.Onl neSTPS ceFSConf g
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.tr angular_loops.Tr angularLoopsFSConf g
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.user_user_graph.UserUserGraphFSConf g
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.s ces.FeatureHydrat onS cesFSConf g
 mport com.tw ter.follow_recom ndat ons.common.rankers.  ghted_cand date_s ce_ranker.  ghtedCand dateS ceRankerFSConf g
 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.follow_recom ndat ons.flows.content_recom nder_flow.ContentRecom nderFlowFSConf g
 mport com.tw ter.follow_recom ndat ons.common.pred cates.g zmoduck.G zmoduckPred cateFSConf g
 mport com.tw ter.follow_recom ndat ons.common.pred cates.hss.HssPred cateFSConf g
 mport com.tw ter.follow_recom ndat ons.common.pred cates.sgs.SgsPred cateFSConf g
 mport com.tw ter.follow_recom ndat ons.flows.post_nux_ml.PostNuxMlFlowFSConf g
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class FeatureSw chConf gs @ nject() (
  globalFeatureSw chConf g: GlobalFeatureSw chConf g,
  featureHydrat onS cesFSConf g: FeatureHydrat onS cesFSConf g,
    ghtedCand dateS ceRankerFSConf g:   ghtedCand dateS ceRankerFSConf g,
  // Flow related conf g
  contentRecom nderFlowFSConf g: ContentRecom nderFlowFSConf g,
  postNuxMlFlowFSConf g: PostNuxMlFlowFSConf g,
  // Cand date s ce related conf g
  crowdSearchAccountsFSConf g: CrowdSearchAccountsFSConf g,
  offl neStpS ceFsConf g: Offl neStpS ceFsConf g,
  onl neSTPS ceFSConf g: Onl neSTPS ceFSConf g,
  popGeoS ceFSConf g: PopGeoS ceFSConf g,
  popGeoQual yFollowFSConf g: PopGeoQual yFollowS ceFSConf g,
  realGraphOonFSConf g: RealGraphOonFSConf g,
  repeatedProf leV s sFSConf g: RepeatedProf leV s sFSConf g,
  recentEngage ntS m larUsersFSConf g: RecentEngage ntS m larUsersFSConf g,
  recentFollow ngRecentFollow ngExpans onS ceFSConf g: RecentFollow ngRecentFollow ngExpans onS ceFSConf g,
  s msExpans onFSConf g: S msExpans onFSConf g,
  s msS ceFSConf g: S msS ceFSConf g,
  soc alProofEnforcedCand dateS ceFSConf g: Soc alProofEnforcedCand dateS ceFSConf g,
  tr angularLoopsFSConf g: Tr angularLoopsFSConf g,
  userUserGraphFSConf g: UserUserGraphFSConf g,
  // Pred cate related conf gs
  g zmoduckPred cateFSConf g: G zmoduckPred cateFSConf g,
  hssPred cateFSConf g: HssPred cateFSConf g,
  sgsPred cateFSConf g: SgsPred cateFSConf g,
  ppm LocaleS ceFSConf g: PPM LocaleFollowS ceFSConf g,
  topOrgan cFollowsAccountsFSConf g: TopOrgan cFollowsAccountsFSConf g,
  statsRece ver: StatsRece ver) {

  val logger = Logger(classOf[FeatureSw chConf gs])

  val  rgedFSConf g =
    FeatureSw chConf g. rge(
      Seq(
        globalFeatureSw chConf g,
        featureHydrat onS cesFSConf g,
          ghtedCand dateS ceRankerFSConf g,
        // Flow related conf g
        contentRecom nderFlowFSConf g,
        postNuxMlFlowFSConf g,
        // Cand date s ce related conf g
        crowdSearchAccountsFSConf g,
        offl neStpS ceFsConf g,
        onl neSTPS ceFSConf g,
        popGeoS ceFSConf g,
        popGeoQual yFollowFSConf g,
        realGraphOonFSConf g,
        repeatedProf leV s sFSConf g,
        recentEngage ntS m larUsersFSConf g,
        recentFollow ngRecentFollow ngExpans onS ceFSConf g,
        s msExpans onFSConf g,
        s msS ceFSConf g,
        soc alProofEnforcedCand dateS ceFSConf g,
        tr angularLoopsFSConf g,
        userUserGraphFSConf g,
        // Pred cate related conf gs:
        g zmoduckPred cateFSConf g,
        hssPred cateFSConf g,
        sgsPred cateFSConf g,
        ppm LocaleS ceFSConf g,
        topOrgan cFollowsAccountsFSConf g,
      )
    )

  /**
   * enum params have to be l sted  n t  ma n f le toget r as ot rw se  'll have to pass  n
   * so  s gnature l ke `Seq[FSEnumParams[_]]` wh ch are gener cs of gener cs and won't comp le.
   *   only have enumFsParams from globalFeatureSw chConf g at t  mo nt
   */
  val enumOverr des = globalFeatureSw chConf g.enumFsParams.flatMap { enumParam =>
    FeatureSw chOverr deUt l.getEnumFSOverr des(statsRece ver, logger, enumParam)
  }

  val gatedOverr des =  rgedFSConf g.gatedOverr desMap.flatMap {
    case (fsNa , overr des) =>
      FeatureSw chOverr deUt l.gatedOverr des(fsNa , overr des: _*)
  }

  val enumSeqOverr des = globalFeatureSw chConf g.enumSeqFsParams.flatMap { enumSeqParam =>
    FeatureSw chOverr deUt l.getEnumSeqFSOverr des(statsRece ver, logger, enumSeqParam)
  }

  val overr des =
    FeatureSw chOverr deUt l
      .getBooleanFSOverr des( rgedFSConf g.booleanFSParams: _*) ++
      FeatureSw chOverr deUt l
        .getBounded ntFSOverr des( rgedFSConf g. ntFSParams: _*) ++
      FeatureSw chOverr deUt l
        .getBoundedLongFSOverr des( rgedFSConf g.longFSParams: _*) ++
      FeatureSw chOverr deUt l
        .getBoundedDoubleFSOverr des( rgedFSConf g.doubleFSParams: _*) ++
      FeatureSw chOverr deUt l
        .getDurat onFSOverr des( rgedFSConf g.durat onFSParams: _*) ++
      FeatureSw chOverr deUt l
        .getBoundedOpt onalDoubleOverr des( rgedFSConf g.opt onalDoubleFSParams: _*) ++
      FeatureSw chOverr deUt l.getStr ngSeqFSOverr des( rgedFSConf g.str ngSeqFSParams: _*) ++
      enumOverr des ++
      gatedOverr des ++
      enumSeqOverr des

  val conf g = BaseConf gBu lder(overr des).bu ld("FollowRecom ndat onServ ceFeatureSw c s")
}

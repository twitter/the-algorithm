package com.tw ter.follow_recom ndat ons.flows.post_nux_ml

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.base.Cand dateS ceReg stry
 mport com.tw ter.follow_recom ndat ons.common.base.Enr c dCand dateS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook.ForwardEma lBookS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook.ForwardPhoneBookS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook.ReverseEma lBookS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook.ReversePhoneBookS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.crowd_search_accounts.CrowdSearchAccountsS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.top_organ c_follows_accounts.TopOrgan cFollowsAccountsS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo.PopCountryS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo.PopCountryBackF llS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo.PopGeohashQual yFollowS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo.PopGeohashS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.ppm _locale_follow.PPM LocaleFollowS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.real_graph.RealGraphOonV2S ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.recent_engage nt.RecentEngage ntNonD rectFollowS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.recent_engage nt.RepeatedProf leV s sS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.salsa.RecentEngage ntD rectFollowSalsaExpans onS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms.L nearRegress onFollow2vecNearestNe ghborsStore
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on.RecentEngage ntS m larUsersS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on.RecentFollow ngS m larUsersS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp.Onl neSTPS ceScorer
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp.Offl neStrongT ePred ct onS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.tr angular_loops.Tr angularLoopsS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.user_user_graph.UserUserGraphCand dateS ce
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class PostNuxMlCand dateS ceReg stry @ nject() (
  crowdSearchAccountsCand dateS ce: CrowdSearchAccountsS ce,
  topOrgan cFollowsAccountsS ce: TopOrgan cFollowsAccountsS ce,
  l nearRegress onfollow2vecNearestNe ghborsStore: L nearRegress onFollow2vecNearestNe ghborsStore,
  forwardEma lBookS ce: ForwardEma lBookS ce,
  forwardPhoneBookS ce: ForwardPhoneBookS ce,
  offl neStrongT ePred ct onS ce: Offl neStrongT ePred ct onS ce,
  onl neSTPS ce: Onl neSTPS ceScorer,
  popCountryS ce: PopCountryS ce,
  popCountryBackF llS ce: PopCountryBackF llS ce,
  popGeohashS ce: PopGeohashS ce,
  recentEngage ntD rectFollowS m larUsersS ce: RecentEngage ntS m larUsersS ce,
  recentEngage ntNonD rectFollowS ce: RecentEngage ntNonD rectFollowS ce,
  recentEngage ntD rectFollowSalsaExpans onS ce: RecentEngage ntD rectFollowSalsaExpans onS ce,
  recentFollow ngS m larUsersS ce: RecentFollow ngS m larUsersS ce,
  realGraphOonV2S ce: RealGraphOonV2S ce,
  repeatedProf leV s S ce: RepeatedProf leV s sS ce,
  reverseEma lBookS ce: ReverseEma lBookS ce,
  reversePhoneBookS ce: ReversePhoneBookS ce,
  tr angularLoopsS ce: Tr angularLoopsS ce,
  userUserGraphCand dateS ce: UserUserGraphCand dateS ce,
  ppm LocaleFollowS ce: PPM LocaleFollowS ce,
  popGeohashQual yFollowS ce: PopGeohashQual yFollowS ce,
  baseStatsRece ver: StatsRece ver,
) extends Cand dateS ceReg stry[PostNuxMlRequest, Cand dateUser] {
   mport Enr c dCand dateS ce._

  overr de val statsRece ver = baseStatsRece ver
    .scope("post_nux_ml_flow", "cand date_s ces")

  // s ces pr mar ly based on soc al graph s gnals
  pr vate[t ] val soc alS ces = Seq(
    l nearRegress onfollow2vecNearestNe ghborsStore.mapKeys[PostNuxMlRequest](
      _.getOpt onalUser d.toSeq),
    forwardEma lBookS ce,
    forwardPhoneBookS ce,
    offl neStrongT ePred ct onS ce,
    onl neSTPS ce,
    reverseEma lBookS ce,
    reversePhoneBookS ce,
    tr angularLoopsS ce,
  )

  // s ces pr mar ly based on geo s gnals
  pr vate[t ] val geoS ces = Seq(
    popCountryS ce,
    popCountryBackF llS ce,
    popGeohashS ce,
    popGeohashQual yFollowS ce,
    topOrgan cFollowsAccountsS ce,
    crowdSearchAccountsCand dateS ce,
    ppm LocaleFollowS ce,
  )

  // s ces pr mar ly based on recent act v y s gnals
  pr vate[t ] val act v yS ces = Seq(
    repeatedProf leV s S ce,
    recentEngage ntD rectFollowSalsaExpans onS ce.mapKeys[PostNuxMlRequest](
      _.getOpt onalUser d.toSeq),
    recentEngage ntD rectFollowS m larUsersS ce,
    recentEngage ntNonD rectFollowS ce.mapKeys[PostNuxMlRequest](_.getOpt onalUser d.toSeq),
    recentFollow ngS m larUsersS ce,
    realGraphOonV2S ce,
    userUserGraphCand dateS ce,
  )

  overr de val s ces: Set[Cand dateS ce[PostNuxMlRequest, Cand dateUser]] = (
    geoS ces ++ soc alS ces ++ act v yS ces
  ).toSet
}

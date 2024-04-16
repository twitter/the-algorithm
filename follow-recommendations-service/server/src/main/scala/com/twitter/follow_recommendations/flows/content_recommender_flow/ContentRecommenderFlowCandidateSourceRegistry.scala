package com.tw ter.follow_recom ndat ons.flows.content_recom nder_flow

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.base.Cand dateS ceReg stry
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook.ForwardEma lBookS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook.ForwardPhoneBookS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook.ReverseEma lBookS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook.ReversePhoneBookS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo.PopCountryBackF llS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo.PopCountryS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo.PopGeohashS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.crowd_search_accounts.CrowdSearchAccountsS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.ppm _locale_follow.PPM LocaleFollowS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.top_organ c_follows_accounts.TopOrgan cFollowsAccountsS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.real_graph.RealGraphOonV2S ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.recent_engage nt.RepeatedProf leV s sS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on.RecentEngage ntS m larUsersS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on.RecentFollow ngS m larUsersS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.soc algraph.RecentFollow ngRecentFollow ngExpans onS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp.Offl neStrongT ePred ct onS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.tr angular_loops.Tr angularLoopsS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.user_user_graph.UserUserGraphCand dateS ce
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ContentRecom nderFlowCand dateS ceReg stry @ nject() (
  // soc al based
  forwardPhoneBookS ce: ForwardPhoneBookS ce,
  forwardEma lBookS ce: ForwardEma lBookS ce,
  reversePhoneBookS ce: ReversePhoneBookS ce,
  reverseEma lBookS ce: ReverseEma lBookS ce,
  offl neStrongT ePred ct onS ce: Offl neStrongT ePred ct onS ce,
  tr angularLoopsS ce: Tr angularLoopsS ce,
  userUserGraphCand dateS ce: UserUserGraphCand dateS ce,
  realGraphOonS ce: RealGraphOonV2S ce,
  recentFollow ngRecentFollow ngExpans onS ce: RecentFollow ngRecentFollow ngExpans onS ce,
  // act v y based
  recentFollow ngS m larUsersS ce: RecentFollow ngS m larUsersS ce,
  recentEngage ntS m larUsersS ce: RecentEngage ntS m larUsersS ce,
  repeatedProf leV s sS ce: RepeatedProf leV s sS ce,
  // geo based
  popCountryS ce: PopCountryS ce,
  popGeohashS ce: PopGeohashS ce,
  popCountryBackF llS ce: PopCountryBackF llS ce,
  crowdSearchAccountsS ce: CrowdSearchAccountsS ce,
  topOrgan cFollowsAccountsS ce: TopOrgan cFollowsAccountsS ce,
  ppm LocaleFollowS ce: PPM LocaleFollowS ce,
  baseStatsRece ver: StatsRece ver)
    extends Cand dateS ceReg stry[ContentRecom nderRequest, Cand dateUser] {

  overr de val statsRece ver = baseStatsRece ver
    .scope("content_recom nder_flow", "cand date_s ces")

  overr de val s ces: Set[Cand dateS ce[ContentRecom nderRequest, Cand dateUser]] = Seq(
    forwardPhoneBookS ce,
    forwardEma lBookS ce,
    reversePhoneBookS ce,
    reverseEma lBookS ce,
    offl neStrongT ePred ct onS ce,
    tr angularLoopsS ce,
    userUserGraphCand dateS ce,
    realGraphOonS ce,
    recentFollow ngRecentFollow ngExpans onS ce,
    recentFollow ngS m larUsersS ce,
    recentEngage ntS m larUsersS ce,
    repeatedProf leV s sS ce,
    popCountryS ce,
    popGeohashS ce,
    popCountryBackF llS ce,
    crowdSearchAccountsS ce,
    topOrgan cFollowsAccountsS ce,
    ppm LocaleFollowS ce,
  ).toSet
}

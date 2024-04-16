package com.tw ter.follow_recom ndat ons.flows.post_nux_ml

 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook.ForwardEma lBookS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook.ForwardPhoneBookS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook.ReverseEma lBookS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook.ReversePhoneBookS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.crowd_search_accounts.CrowdSearchAccountsS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo.PopCountryBackF llS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo.PopCountryS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo.PopGeohashQual yFollowS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo.PopGeohashS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.ppm _locale_follow.PPM LocaleFollowS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.real_graph.RealGraphOonV2S ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.recent_engage nt.RecentEngage ntNonD rectFollowS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.recent_engage nt.RepeatedProf leV s sS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.salsa.RecentEngage ntD rectFollowSalsaExpans onS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on.RecentEngage ntS m larUsersS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on.RecentFollow ngS m larUsersS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms.Follow2vecNearestNe ghborsStore
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp.BaseOnl neSTPS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp.Offl neStrongT ePred ct onS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.top_organ c_follows_accounts.TopOrgan cFollowsAccountsS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.tr angular_loops.Tr angularLoopsS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.two_hop_random_walk.TwoHopRandomWalkS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.user_user_graph.UserUserGraphCand dateS ce
 mport com.tw ter.follow_recom ndat ons.flows.post_nux_ml.PostNuxMlCand dateS ce  ghtParams._
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.t  l nes.conf gap .Params

object PostNuxMlFlowCand dateS ce  ghts {

  def get  ghts(params: Params): Map[Cand dateS ce dent f er, Double] = {
    Map[Cand dateS ce dent f er, Double](
      // Soc al based
      PPM LocaleFollowS ce. dent f er -> params(Cand date  ghtPPM LocaleFollow),
      Follow2vecNearestNe ghborsStore. dent f erF2vL nearRegress on -> params(
        Cand date  ghtFollow2vecNearestNe ghbors),
      RecentFollow ngS m larUsersS ce. dent f er -> params(
        Cand date  ghtRecentFollow ngS m larUsers),
      BaseOnl neSTPS ce. dent f er -> params(Cand date  ghtOnl neStp),
      Offl neStrongT ePred ct onS ce. dent f er -> params(
        Cand date  ghtOffl neStrongT ePred ct on),
      ForwardEma lBookS ce. dent f er -> params(Cand date  ghtForwardEma lBook),
      ForwardPhoneBookS ce. dent f er -> params(Cand date  ghtForwardPhoneBook),
      ReverseEma lBookS ce. dent f er -> params(Cand date  ghtReverseEma lBook),
      ReversePhoneBookS ce. dent f er -> params(Cand date  ghtReversePhoneBook),
      Tr angularLoopsS ce. dent f er -> params(Cand date  ghtTr angularLoops),
      TwoHopRandomWalkS ce. dent f er -> params(Cand date  ghtTwoHopRandomWalk),
      UserUserGraphCand dateS ce. dent f er -> params(Cand date  ghtUserUserGraph),
      // Geo based
      PopCountryS ce. dent f er -> params(Cand date  ghtPopCountry),
      PopCountryBackF llS ce. dent f er -> params(Cand date  ghtPopGeoBackf ll),
      PopGeohashS ce. dent f er -> params(Cand date  ghtPopGeohash),
      PopGeohashQual yFollowS ce. dent f er -> params(Cand date  ghtPopGeohashQual yFollow),
      CrowdSearchAccountsS ce. dent f er -> params(Cand date  ghtCrowdSearch),
      TopOrgan cFollowsAccountsS ce. dent f er -> params(Cand date  ghtTopOrgan cFollow),
      // Engage nt based
      RealGraphOonV2S ce. dent f er -> params(Cand date  ghtRealGraphOonV2),
      RecentEngage ntNonD rectFollowS ce. dent f er -> params(
        Cand date  ghtRecentEngage ntNonD rectFollow),
      RecentEngage ntS m larUsersS ce. dent f er -> params(
        Cand date  ghtRecentEngage ntS m larUsers),
      RepeatedProf leV s sS ce. dent f er -> params(Cand date  ghtRepeatedProf leV s s),
      RecentEngage ntD rectFollowSalsaExpans onS ce. dent f er -> params(
        Cand date  ghtRecentEngage ntD rectFollowSalsaExpans on),
    )
  }
}

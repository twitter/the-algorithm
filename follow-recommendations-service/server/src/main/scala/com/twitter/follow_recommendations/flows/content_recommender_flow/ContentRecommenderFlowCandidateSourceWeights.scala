package com.tw ter.follow_recom ndat ons.flows.content_recom nder_flow

 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook.ForwardEma lBookS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook.ForwardPhoneBookS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook.ReverseEma lBookS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook.ReversePhoneBookS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo.PopCountryBackF llS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo.PopCountryS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo.PopGeohashS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.real_graph.RealGraphOonV2S ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.recent_engage nt.RepeatedProf leV s sS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on.RecentEngage ntS m larUsersS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on.RecentFollow ngS m larUsersS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp.Offl neStrongT ePred ct onS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.tr angular_loops.Tr angularLoopsS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.user_user_graph.UserUserGraphCand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.crowd_search_accounts.CrowdSearchAccountsS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.ppm _locale_follow.PPM LocaleFollowS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.soc algraph.RecentFollow ngRecentFollow ngExpans onS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.top_organ c_follows_accounts.TopOrgan cFollowsAccountsS ce
 mport com.tw ter.t  l nes.conf gap .Params

object ContentRecom nderFlowCand dateS ce  ghts {

  def get  ghts(
    params: Params
  ): Map[Cand dateS ce dent f er, Double] = {
    Map[Cand dateS ce dent f er, Double](
      // Soc al based
      UserUserGraphCand dateS ce. dent f er -> params(
        ContentRecom nderFlowCand dateS ce  ghtsParams.UserUserGraphS ce  ght),
      ForwardPhoneBookS ce. dent f er -> params(
        ContentRecom nderFlowCand dateS ce  ghtsParams.ForwardPhoneBookS ce  ght),
      ReversePhoneBookS ce. dent f er -> params(
        ContentRecom nderFlowCand dateS ce  ghtsParams.ReversePhoneBookS ce  ght),
      ForwardEma lBookS ce. dent f er -> params(
        ContentRecom nderFlowCand dateS ce  ghtsParams.ForwardEma lBookS ce  ght),
      ReverseEma lBookS ce. dent f er -> params(
        ContentRecom nderFlowCand dateS ce  ghtsParams.ReverseEma lBookS ce  ght),
      Tr angularLoopsS ce. dent f er -> params(
        ContentRecom nderFlowCand dateS ce  ghtsParams.Tr angularLoopsS ce  ght),
      Offl neStrongT ePred ct onS ce. dent f er -> params(
        ContentRecom nderFlowCand dateS ce  ghtsParams.Offl neStrongT ePred ct onS ce  ght),
      RecentFollow ngRecentFollow ngExpans onS ce. dent f er -> params(
        ContentRecom nderFlowCand dateS ce  ghtsParams.NewFollow ngNewFollow ngExpans onS ce  ght),
      RecentFollow ngS m larUsersS ce. dent f er -> params(
        ContentRecom nderFlowCand dateS ce  ghtsParams.NewFollow ngS m larUserS ce  ght),
      // Act v y based
      RealGraphOonV2S ce. dent f er -> params(
        ContentRecom nderFlowCand dateS ce  ghtsParams.RealGraphOonS ce  ght),
      RecentEngage ntS m larUsersS ce. dent f er -> params(
        ContentRecom nderFlowCand dateS ce  ghtsParams.RecentEngage ntS m larUserS ce  ght),
      RepeatedProf leV s sS ce. dent f er -> params(
        ContentRecom nderFlowCand dateS ce  ghtsParams.RepeatedProf leV s sS ce  ght),
      // Geo based
      PopCountryS ce. dent f er -> params(
        ContentRecom nderFlowCand dateS ce  ghtsParams.PopCountryS ce  ght),
      PopGeohashS ce. dent f er -> params(
        ContentRecom nderFlowCand dateS ce  ghtsParams.PopGeohashS ce  ght),
      PopCountryBackF llS ce. dent f er -> params(
        ContentRecom nderFlowCand dateS ce  ghtsParams.PopCountryBackf llS ce  ght),
      PPM LocaleFollowS ce. dent f er -> params(
        ContentRecom nderFlowCand dateS ce  ghtsParams.PPM LocaleFollowS ce  ght),
      CrowdSearchAccountsS ce. dent f er -> params(
        ContentRecom nderFlowCand dateS ce  ghtsParams.CrowdSearchAccountS ce  ght),
      TopOrgan cFollowsAccountsS ce. dent f er -> params(
        ContentRecom nderFlowCand dateS ce  ghtsParams.TopOrgan cFollowsAccountsS ce  ght),
    )
  }
}

package com.tw ter.follow_recom ndat ons.ut ls

 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook._
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo.PopCountryS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo.PopCountryBackF llS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo.PopGeoS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo.PopGeohashS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.ppm _locale_follow.PPM LocaleFollowS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.recent_engage nt.RecentEngage ntNonD rectFollowS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms.Sw ch ngS msS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on.RecentEngage ntS m larUsersS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on.RecentFollow ngS m larUsersS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on.RecentStrongEngage ntD rectFollowS m larUsersS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.soc algraph.RecentFollow ngRecentFollow ngExpans onS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp.MutualFollowStrongT ePred ct onS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp.Offl neStrongT ePred ct onS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp.BaseOnl neSTPS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp.Soc alProofEnforcedOffl neStrongT ePred ct onS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.tr angular_loops.Tr angularLoopsS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.two_hop_random_walk.TwoHopRandomWalkS ce
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.conf gap .params.GlobalParams
 mport com.tw ter.follow_recom ndat ons.models.Cand dateS ceType
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.t  l nes.conf gap .HasParams

tra  Cand dateS ceHoldbackUt l {
   mport Cand dateS ceHoldbackUt l._
  def f lterCand dateS ces[T <: HasParams](
    request: T,
    s ces: Seq[Cand dateS ce[T, Cand dateUser]]
  ): Seq[Cand dateS ce[T, Cand dateUser]] = {
    val typeToF lter = request.params(GlobalParams.Cand dateS cesToF lter)
    val s cesToF lter = Cand dateS ceTypeToMap.get(typeToF lter).getOrElse(Set.empty)
    s ces.f lterNot { s ce => s cesToF lter.conta ns(s ce. dent f er) }
  }
}

object Cand dateS ceHoldbackUt l {
  f nal val ContextualAct v yCand dateS ce ds: Set[Cand dateS ce dent f er] =
    Set(
      RecentFollow ngS m larUsersS ce. dent f er,
      RecentEngage ntNonD rectFollowS ce. dent f er,
      RecentEngage ntS m larUsersS ce. dent f er,
      RecentStrongEngage ntD rectFollowS m larUsersS ce. dent f er,
      Sw ch ngS msS ce. dent f er,
    )

  f nal val Soc alCand dateS ce ds: Set[Cand dateS ce dent f er] =
    Set(
      ForwardEma lBookS ce. dent f er,
      ForwardPhoneBookS ce. dent f er,
      ReverseEma lBookS ce. dent f er,
      ReversePhoneBookS ce. dent f er,
      RecentFollow ngRecentFollow ngExpans onS ce. dent f er,
      BaseOnl neSTPS ce. dent f er,
      MutualFollowStrongT ePred ct onS ce. dent f er,
      Offl neStrongT ePred ct onS ce. dent f er,
      Soc alProofEnforcedOffl neStrongT ePred ct onS ce. dent f er,
      Tr angularLoopsS ce. dent f er,
      TwoHopRandomWalkS ce. dent f er
    )

  f nal val GeoCand dateS ce ds: Set[Cand dateS ce dent f er] =
    Set(
      PPM LocaleFollowS ce. dent f er,
      PopCountryS ce. dent f er,
      PopGeohashS ce. dent f er,
      PopCountryBackF llS ce. dent f er,
      PopGeoS ce. dent f er,
    )

  f nal val Cand dateS ceTypeToMap: Map[Cand dateS ceType.Value, Set[
    Cand dateS ce dent f er
  ]] =
    Map(
      Cand dateS ceType.Soc al -> Soc alCand dateS ce ds,
      Cand dateS ceType.Act v yContextual -> ContextualAct v yCand dateS ce ds,
      Cand dateS ceType.GeoAnd nterests -> GeoCand dateS ce ds
    )
}

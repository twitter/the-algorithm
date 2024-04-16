package com.tw ter.follow_recom ndat ons.common.constants

 mport com.tw ter. rm .constants.Algor hmFeedbackTokens.Algor hmToFeedbackTokenMap
 mport com.tw ter. rm .model.Algor hm._
 mport com.tw ter.follow_recom ndat ons.common.models.Algor hmType

object Cand dateAlgor hmTypeConstants {

  /**
   * Each algor hm  s based on one, or more, of t  4 types of  nformat on   have on users,
   * descr bed  n [[Algor hmType]]. Ass gn nt of algor hms to t se categor es are based on
   */
  pr vate val Algor hm dToType: Map[Str ng, Set[Algor hmType.Value]] = Map(
    // Act v y Algor hms:
    Algor hmToFeedbackTokenMap(NewFollow ngS m larUser).toStr ng -> Set(Algor hmType.Act v y),
    Algor hmToFeedbackTokenMap(S ms).toStr ng -> Set(Algor hmType.Act v y),
    Algor hmToFeedbackTokenMap(NewFollow ngS m larUserSalsa).toStr ng -> Set(
      Algor hmType.Act v y),
    Algor hmToFeedbackTokenMap(RecentEngage ntNonD rectFollow).toStr ng -> Set(
      Algor hmType.Act v y),
    Algor hmToFeedbackTokenMap(RecentEngage ntS m larUser).toStr ng -> Set(
      Algor hmType.Act v y),
    Algor hmToFeedbackTokenMap(RecentEngage ntSarusOcCur).toStr ng -> Set(Algor hmType.Act v y),
    Algor hmToFeedbackTokenMap(RecentSearchBasedRec).toStr ng -> Set(Algor hmType.Act v y),
    Algor hmToFeedbackTokenMap(Tw stlyT etAuthors).toStr ng -> Set(Algor hmType.Act v y),
    Algor hmToFeedbackTokenMap(Follow2VecNearestNe ghbors).toStr ng -> Set(Algor hmType.Act v y),
    Algor hmToFeedbackTokenMap(Ema lT etCl ck).toStr ng -> Set(Algor hmType.Act v y),
    Algor hmToFeedbackTokenMap(RepeatedProf leV s s).toStr ng -> Set(Algor hmType.Act v y),
    Algor hmToFeedbackTokenMap(GoodT etCl ckEngage nts).toStr ng -> Set(Algor hmType.Act v y),
    Algor hmToFeedbackTokenMap(T etShareEngage nts).toStr ng -> Set(Algor hmType.Act v y),
    Algor hmToFeedbackTokenMap(T etSharerToShareRec p entEngage nts).toStr ng -> Set(
      Algor hmType.Act v y),
    Algor hmToFeedbackTokenMap(T etAuthorToShareRec p entEngage nts).toStr ng -> Set(
      Algor hmType.Act v y),
    Algor hmToFeedbackTokenMap(L nearRegress onFollow2VecNearestNe ghbors).toStr ng -> Set(
      Algor hmType.Act v y),
    Algor hmToFeedbackTokenMap(NUXLO tory).toStr ng -> Set(Algor hmType.Act v y),
    Algor hmToFeedbackTokenMap(Traff cAttr but onAccounts).toStr ng -> Set(Algor hmType.Act v y),
    Algor hmToFeedbackTokenMap(RealGraphOonV2).toStr ng -> Set(Algor hmType.Act v y),
    Algor hmToFeedbackTokenMap(Mag cRecsRecentEngage nts).toStr ng -> Set(Algor hmType.Act v y),
    Algor hmToFeedbackTokenMap(Not f cat onEngage nt).toStr ng -> Set(Algor hmType.Act v y),
    // Soc al Algor hms:
    Algor hmToFeedbackTokenMap(TwoHopRandomWalk).toStr ng -> Set(Algor hmType.Soc al),
    Algor hmToFeedbackTokenMap(RealT  MutualFollow).toStr ng -> Set(Algor hmType.Soc al),
    Algor hmToFeedbackTokenMap(ForwardPhoneBook).toStr ng -> Set(Algor hmType.Soc al),
    Algor hmToFeedbackTokenMap(ForwardEma lBook).toStr ng -> Set(Algor hmType.Soc al),
    Algor hmToFeedbackTokenMap(NewFollow ngNewFollow ngExpans on).toStr ng -> Set(
      Algor hmType.Soc al),
    Algor hmToFeedbackTokenMap(NewFollow ngSarusCoOccurSoc alProof).toStr ng -> Set(
      Algor hmType.Soc al),
    Algor hmToFeedbackTokenMap(ReverseEma lBook b s).toStr ng -> Set(Algor hmType.Soc al),
    Algor hmToFeedbackTokenMap(ReversePhoneBook).toStr ng -> Set(Algor hmType.Soc al),
    Algor hmToFeedbackTokenMap(StrongT ePred ct onRec).toStr ng -> Set(Algor hmType.Soc al),
    Algor hmToFeedbackTokenMap(StrongT ePred ct onRecW hSoc alProof).toStr ng -> Set(
      Algor hmType.Soc al),
    Algor hmToFeedbackTokenMap(Onl neStrongT ePred ct onRec).toStr ng -> Set(Algor hmType.Soc al),
    Algor hmToFeedbackTokenMap(Onl neStrongT ePred ct onRecNoCach ng).toStr ng -> Set(
      Algor hmType.Soc al),
    Algor hmToFeedbackTokenMap(Tr angularLoop).toStr ng -> Set(Algor hmType.Soc al),
    Algor hmToFeedbackTokenMap(StrongT ePred ct onPm ).toStr ng -> Set(Algor hmType.Soc al),
    Algor hmToFeedbackTokenMap(Onl neStrongT ePred ct onRAB).toStr ng -> Set(Algor hmType.Soc al),
    // Geo Algor hms:
    Algor hmToFeedbackTokenMap(PopCountryBackF ll).toStr ng -> Set(Algor hmType.Geo),
    Algor hmToFeedbackTokenMap(PopCountry).toStr ng -> Set(Algor hmType.Geo),
    Algor hmToFeedbackTokenMap(PopGeohash).toStr ng -> Set(Algor hmType.Geo),
//    Algor hmToFeedbackTokenMap(PopGeohashRealGraph).toStr ng -> Set(Algor hmType.Geo),
    Algor hmToFeedbackTokenMap(EngagedFollo rRat o).toStr ng -> Set(Algor hmType.Geo),
    Algor hmToFeedbackTokenMap(CrowdSearchAccounts).toStr ng -> Set(Algor hmType.Geo),
    Algor hmToFeedbackTokenMap(Organ cFollowAccounts).toStr ng -> Set(Algor hmType.Geo),
    Algor hmToFeedbackTokenMap(PopGeohashQual yFollow).toStr ng -> Set(Algor hmType.Geo),
    Algor hmToFeedbackTokenMap(PPM LocaleFollow).toStr ng -> Set(Algor hmType.Geo),
    //  nterest Algor hms:
    Algor hmToFeedbackTokenMap(Ttt nterest).toStr ng -> Set(Algor hmType. nterest),
    Algor hmToFeedbackTokenMap(Utt nterestRelatedUsers).toStr ng -> Set(Algor hmType. nterest),
    Algor hmToFeedbackTokenMap(UttSeedAccounts).toStr ng -> Set(Algor hmType. nterest),
    Algor hmToFeedbackTokenMap(UttProducerExpans on).toStr ng -> Set(Algor hmType. nterest),
    // Hybr d (more than one type) Algor hms:
    Algor hmToFeedbackTokenMap(UttProducerOffl neMbcgV1).toStr ng -> Set(
      Algor hmType. nterest,
      Algor hmType.Geo),
    Algor hmToFeedbackTokenMap(CuratedAccounts).toStr ng -> Set(
      Algor hmType. nterest,
      Algor hmType.Geo),
    Algor hmToFeedbackTokenMap(UserUserGraph).toStr ng -> Set(
      Algor hmType.Soc al,
      Algor hmType.Act v y),
  )
  def getAlgor hmTypes(algo d: Str ng): Set[Str ng] = {
    Algor hm dToType.get(algo d).map(_.map(_.toStr ng)).getOrElse(Set.empty)
  }
}

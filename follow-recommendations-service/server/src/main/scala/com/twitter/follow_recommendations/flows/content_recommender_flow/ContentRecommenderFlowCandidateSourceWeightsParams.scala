package com.tw ter.follow_recom ndat ons.flows.content_recom nder_flow

 mport com.tw ter.t  l nes.conf gap .FSBoundedParam

object ContentRecom nderFlowCand dateS ce  ghtsParams {
  // Soc al based
  case object ForwardPhoneBookS ce  ght
      extends FSBoundedParam[Double](
        ContentRecom nderFlowFeatureSw chKeys.ForwardPhoneBookS ce  ght,
        1d,
        0d,
        1000d)
  case object ForwardEma lBookS ce  ght
      extends FSBoundedParam[Double](
        ContentRecom nderFlowFeatureSw chKeys.ForwardEma lBookS ce  ght,
        1d,
        0d,
        1000d)
  case object ReversePhoneBookS ce  ght
      extends FSBoundedParam[Double](
        ContentRecom nderFlowFeatureSw chKeys.ReversePhoneBookS ce  ght,
        1d,
        0d,
        1000d)
  case object ReverseEma lBookS ce  ght
      extends FSBoundedParam[Double](
        ContentRecom nderFlowFeatureSw chKeys.ReverseEma lBookS ce  ght,
        1d,
        0d,
        1000d)
  case object Offl neStrongT ePred ct onS ce  ght
      extends FSBoundedParam[Double](
        ContentRecom nderFlowFeatureSw chKeys.Offl neStrongT ePred ct onS ce  ght,
        1d,
        0d,
        1000d)
  case object Tr angularLoopsS ce  ght
      extends FSBoundedParam[Double](
        ContentRecom nderFlowFeatureSw chKeys.Tr angularLoopsS ce  ght,
        1d,
        0d,
        1000d)
  case object UserUserGraphS ce  ght
      extends FSBoundedParam[Double](
        ContentRecom nderFlowFeatureSw chKeys.UserUserGraphS ce  ght,
        1d,
        0d,
        1000d)
  case object NewFollow ngNewFollow ngExpans onS ce  ght
      extends FSBoundedParam[Double](
        ContentRecom nderFlowFeatureSw chKeys.NewFollow ngNewFollow ngExpans onS ce  ght,
        1d,
        0d,
        1000d)
  // Act v y based
  case object NewFollow ngS m larUserS ce  ght
      extends FSBoundedParam[Double](
        ContentRecom nderFlowFeatureSw chKeys.NewFollow ngS m larUserS ce  ght,
        1d,
        0d,
        1000d)
  case object RecentEngage ntS m larUserS ce  ght
      extends FSBoundedParam[Double](
        ContentRecom nderFlowFeatureSw chKeys.RecentEngage ntS m larUserS ce  ght,
        1d,
        0d,
        1000d)
  case object RepeatedProf leV s sS ce  ght
      extends FSBoundedParam[Double](
        ContentRecom nderFlowFeatureSw chKeys.RepeatedProf leV s sS ce  ght,
        1d,
        0d,
        1000d)
  case object RealGraphOonS ce  ght
      extends FSBoundedParam[Double](
        ContentRecom nderFlowFeatureSw chKeys.RealGraphOonS ce  ght,
        1d,
        0d,
        1000d)
  // Geo based
  case object PopCountryS ce  ght
      extends FSBoundedParam[Double](
        ContentRecom nderFlowFeatureSw chKeys.PopCountryS ce  ght,
        1d,
        0d,
        1000d)
  case object PopGeohashS ce  ght
      extends FSBoundedParam[Double](
        ContentRecom nderFlowFeatureSw chKeys.PopGeohashS ce  ght,
        1d,
        0d,
        1000d)
  case object PopCountryBackf llS ce  ght
      extends FSBoundedParam[Double](
        ContentRecom nderFlowFeatureSw chKeys.PopCountryBackf llS ce  ght,
        1d,
        0d,
        1000d)
  case object PPM LocaleFollowS ce  ght
      extends FSBoundedParam[Double](
        ContentRecom nderFlowFeatureSw chKeys.PPM LocaleFollowS ce  ght,
        1d,
        0d,
        1000d)
  case object TopOrgan cFollowsAccountsS ce  ght
      extends FSBoundedParam[Double](
        ContentRecom nderFlowFeatureSw chKeys.TopOrgan cFollowsAccountsS ce  ght,
        1d,
        0d,
        1000d)
  case object CrowdSearchAccountS ce  ght
      extends FSBoundedParam[Double](
        ContentRecom nderFlowFeatureSw chKeys.CrowdSearchAccountS ce  ght,
        1d,
        0d,
        1000d)
}

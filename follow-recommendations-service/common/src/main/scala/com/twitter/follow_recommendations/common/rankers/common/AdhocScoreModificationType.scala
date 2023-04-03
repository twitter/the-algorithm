packagelon com.twittelonr.follow_reloncommelonndations.common.rankelonrs.common

/**
 * To managelon thelon elonxtelonnt of adhoc scorelon modifications, welon selont a hard limit that from elonach of thelon
 * typelons belonlow *ONLY ONelon* adhoc scorelonr can belon applielond to candidatelons' scorelons. Morelon delontails about thelon
 * usagelon is availablelon in [[AdhocRankelonr]]
 */

objelonct AdhocScorelonModificationTypelon elonxtelonnds elonnumelonration {
  typelon AdhocScorelonModificationTypelon = Valuelon

  // This typelon of scorelonr increlonaselons thelon scorelon of a subselont of candidatelons through various policielons.
  val BoostingScorelonr: AdhocScorelonModificationTypelon = Valuelon("boosting")

  // This typelon of scorelonr shufflelons candidatelons randomly according to somelon distribution.
  val WelonightelondRandomSamplingScorelonr: AdhocScorelonModificationTypelon = Valuelon("welonightelond_random_sampling")

  // This is addelond solelonly for telonsting purposelons and should not belon uselond in production.
  val InvalidAdhocScorelonr: AdhocScorelonModificationTypelon = Valuelon("invalid_adhoc_scorelonr")
}

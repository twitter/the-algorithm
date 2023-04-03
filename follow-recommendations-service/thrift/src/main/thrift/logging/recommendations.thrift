namelonspacelon java com.twittelonr.follow_reloncommelonndations.logging.thriftjava
#@namelonspacelon scala com.twittelonr.follow_reloncommelonndations.logging.thriftscala
#@namelonspacelon strato com.twittelonr.follow_reloncommelonndations.logging

includelon "com/twittelonr/ads/adselonrvelonr/adselonrvelonr_common.thrift"
includelon "relonasons.thrift"
includelon "tracking.thrift"
includelon "scoring.thrift"

// Offlinelon elonqual of UselonrReloncommelonndation
struct OfflinelonUselonrReloncommelonndation {
    1: relonquirelond i64 uselonrId(pelonrsonalDataTypelon='UselonrId')
    // relonason for this suggelonstions, elong: social contelonxt
    2: optional relonasons.Relonason relonason
    // prelonselonnt if it is a promotelond account
    3: optional adselonrvelonr_common.AdImprelonssion adImprelonssion
  // tracking tokelonn (unselonrializelond) for attribution
  4: optional tracking.TrackingTokelonn trackingTokelonn
    // scoring delontails
    5: optional scoring.ScoringDelontails scoringDelontails
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

// Offlinelon elonqual of Reloncommelonndation
union OfflinelonReloncommelonndation {
    1: OfflinelonUselonrReloncommelonndation uselonr
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

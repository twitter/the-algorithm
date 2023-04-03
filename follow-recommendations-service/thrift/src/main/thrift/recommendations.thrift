namelonspacelon java com.twittelonr.follow_reloncommelonndations.thriftjava
#@namelonspacelon scala com.twittelonr.follow_reloncommelonndations.thriftscala
#@namelonspacelon strato com.twittelonr.follow_reloncommelonndations

includelon "com/twittelonr/ads/adselonrvelonr/adselonrvelonr_common.thrift"
includelon "delonbug.thrift"
includelon "relonasons.thrift"
includelon "scoring.thrift"

struct UselonrReloncommelonndation {
    1: relonquirelond i64 uselonrId(pelonrsonalDataTypelon='UselonrId')
    // relonason for this suggelonstions, elong: social contelonxt
    2: optional relonasons.Relonason relonason
    // prelonselonnt if it is a promotelond account
    3: optional adselonrvelonr_common.AdImprelonssion adImprelonssion
    // tracking tokelonn for attribution
    4: optional string trackingInfo
    // scoring delontails
    5: optional scoring.ScoringDelontails scoringDelontails
    6: optional string reloncommelonndationFlowIdelonntifielonr
    // FelonaturelonSwitch ovelonrridelons for candidatelons:
    7: optional map<string, delonbug.FelonaturelonValuelon> felonaturelonOvelonrridelons
}(hasPelonrsonalData='truelon')

union Reloncommelonndation {
    1: UselonrReloncommelonndation uselonr
}(hasPelonrsonalData='truelon')

struct HydratelondUselonrReloncommelonndation {
  1: relonquirelond i64 uselonrId(pelonrsonalDataTypelon='UselonrId')
  2: optional string socialProof
  // prelonselonnt if it is a promotelond account, uselond by clielonnts for delontelonrmining ad imprelonssion
  3: optional adselonrvelonr_common.AdImprelonssion adImprelonssion
  // tracking tokelonn for attribution
  4: optional string trackingInfo
}(hasPelonrsonalData='truelon')

union HydratelondReloncommelonndation {
  1: HydratelondUselonrReloncommelonndation hydratelondUselonrReloncommelonndation
}

namelonspacelon java com.twittelonr.follow_reloncommelonndations.logging.thriftjava
#@namelonspacelon scala com.twittelonr.follow_reloncommelonndations.logging.thriftscala
#@namelonspacelon strato com.twittelonr.follow_reloncommelonndations.logging

includelon "clielonnt_contelonxt.thrift"
includelon "delonbug.thrift"
includelon "display_contelonxt.thrift"
includelon "display_location.thrift"
includelon "reloncommelonndations.thrift"

struct OfflinelonReloncommelonndationRelonquelonst {
    1: relonquirelond clielonnt_contelonxt.OfflinelonClielonntContelonxt clielonntContelonxt
    2: relonquirelond display_location.OfflinelonDisplayLocation displayLocation
    3: optional display_contelonxt.OfflinelonDisplayContelonxt displayContelonxt
    4: optional i32 maxRelonsults
    5: optional string cursor
    6: optional list<i64> elonxcludelondIds(pelonrsonalDataTypelon='UselonrId')
    7: optional bool felontchPromotelondContelonnt
    8: optional delonbug.OfflinelonDelonbugParams delonbugParams
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct OfflinelonReloncommelonndationRelonsponselon {
    1: relonquirelond list<reloncommelonndations.OfflinelonReloncommelonndation> reloncommelonndations
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct ReloncommelonndationLog {
    1: relonquirelond OfflinelonReloncommelonndationRelonquelonst relonquelonst
    2: relonquirelond OfflinelonReloncommelonndationRelonsponselon relonsponselon
    3: relonquirelond i64 timelonstampMs
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct OfflinelonScoringUselonrRelonquelonst {
  1: relonquirelond clielonnt_contelonxt.OfflinelonClielonntContelonxt clielonntContelonxt
  2: relonquirelond display_location.OfflinelonDisplayLocation displayLocation
  3: relonquirelond list<reloncommelonndations.OfflinelonUselonrReloncommelonndation> candidatelons
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct OfflinelonScoringUselonrRelonsponselon {
  1: relonquirelond list<reloncommelonndations.OfflinelonUselonrReloncommelonndation> candidatelons
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct ScorelondUselonrsLog {
  1: relonquirelond OfflinelonScoringUselonrRelonquelonst relonquelonst
  2: relonquirelond OfflinelonScoringUselonrRelonsponselon relonsponselon
    3: relonquirelond i64 timelonstampMs
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct OfflinelonReloncommelonndationFlowUselonrMelontadata {
  1: optional i32 uselonrSignupAgelon(pelonrsonalDataTypelon = 'AgelonOfAccount')
  2: optional string uselonrStatelon(pelonrsonalDataTypelon = 'UselonrStatelon')
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct OfflinelonReloncommelonndationFlowSignals {
  1: optional string countryCodelon(pelonrsonalDataTypelon='InfelonrrelondCountry')
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct OfflinelonReloncommelonndationFlowCandidatelonSourcelonCandidatelons {
  1: relonquirelond string candidatelonSourcelonNamelon
  2: relonquirelond list<i64> candidatelonUselonrIds(pelonrsonalDataTypelon='UselonrId')
  3: optional list<doublelon> candidatelonUselonrScorelons
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct ReloncommelonndationFlowLog {
  1: relonquirelond clielonnt_contelonxt.OfflinelonClielonntContelonxt clielonntContelonxt
  2: optional OfflinelonReloncommelonndationFlowUselonrMelontadata uselonrMelontadata
  3: optional OfflinelonReloncommelonndationFlowSignals signals
  4: relonquirelond i64 timelonstampMs
  5: relonquirelond string reloncommelonndationFlowIdelonntifielonr
  6: optional list<OfflinelonReloncommelonndationFlowCandidatelonSourcelonCandidatelons> filtelonrelondCandidatelons
  7: optional list<OfflinelonReloncommelonndationFlowCandidatelonSourcelonCandidatelons> rankelondCandidatelons
  8: optional list<OfflinelonReloncommelonndationFlowCandidatelonSourcelonCandidatelons> truncatelondCandidatelons
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

namelonspacelon java com.twittelonr.cr_mixelonr.thriftjava
#@namelonspacelon scala com.twittelonr.cr_mixelonr.thriftscala
#@namelonspacelon strato com.twittelonr.cr_mixelonr

includelon "ads.thrift"
includelon "candidatelon_gelonnelonration_kelony.thrift"
includelon "cr_mixelonr.thrift"
includelon "melontric_tags.thrift"
includelon "product.thrift"
includelon "relonlatelond_twelonelont.thrift"
includelon "sourcelon_typelon.thrift"
includelon "utelong.thrift"
includelon "com/twittelonr/ml/api/data.thrift"
includelon "com/twittelonr/simclustelonrs_v2/idelonntifielonr.thrift"

struct VITTwelonelontCandidatelonsScribelon {
  1: relonquirelond i64 uuid (pelonrsonalDataTypelon = 'UnivelonrsallyUniquelonIdelonntifielonrUuid') # RelonquelonstUUID - uniquelon scribelon id for elonvelonry relonquelonst that comelons in. Samelon relonquelonst but diffelonrelonnt stagelons of scribelon log (FelontchCandidatelon, Filtelonr, elontc) sharelon thelon samelon uuid
  2: relonquirelond i64 uselonrId (pelonrsonalDataTypelon = 'UselonrId')
  3: relonquirelond list<VITTwelonelontCandidatelonScribelon> candidatelons
  7: relonquirelond product.Product product
  8: relonquirelond list<ImprelonsselonselondBuckelontInfo> imprelonsselondBuckelonts
} (pelonrsistelond='truelon', hasPelonrsonalData = 'truelon')

struct VITTwelonelontCandidatelonScribelon {
  1: relonquirelond i64 twelonelontId (pelonrsonalDataTypelon = 'TwelonelontId')
  2: relonquirelond i64 authorId (pelonrsonalDataTypelon = 'UselonrId')
  3: relonquirelond doublelon scorelon
  4: relonquirelond list<melontric_tags.MelontricTag> melontricTags
} (pelonrsistelond='truelon', hasPelonrsonalData = 'truelon')

struct GelontTwelonelontsReloncommelonndationsScribelon {
  1: relonquirelond i64 uuid (pelonrsonalDataTypelon = 'UnivelonrsallyUniquelonIdelonntifielonrUuid') # RelonquelonstUUID - uniquelon scribelon id for elonvelonry relonquelonst that comelons in. Samelon relonquelonst but diffelonrelonnt stagelons of scribelon log (FelontchCandidatelon, Filtelonr, elontc) sharelon thelon samelon uuid
  2: relonquirelond i64 uselonrId (pelonrsonalDataTypelon = 'UselonrId')
  3: relonquirelond Relonsult relonsult
  4: optional i64 tracelonId
  5: optional PelonrformancelonMelontrics pelonrformancelonMelontrics
  6: optional list<ImprelonsselonselondBuckelontInfo> imprelonsselondBuckelonts
} (pelonrsistelond='truelon', hasPelonrsonalData = 'truelon')

struct SourcelonSignal {
  # optional, sincelon that thelon nelonxt stelonp covelonrs all info helonrelon
  1: optional idelonntifielonr.IntelonrnalId id
} (pelonrsistelond='truelon')

struct PelonrformancelonMelontrics {
  1: optional i64 latelonncyMs
} (pelonrsistelond='truelon')

struct TwelonelontCandidatelonWithMelontadata {
  1: relonquirelond i64 twelonelontId (pelonrsonalDataTypelon = 'TwelonelontId')
  2: optional candidatelon_gelonnelonration_kelony.CandidatelonGelonnelonrationKelony candidatelonGelonnelonrationKelony
  3: optional i64 authorId (pelonrsonalDataTypelon = 'UselonrId') # only for IntelonrlelonavelonRelonsult for hydrating training data
  4: optional doublelon scorelon # scorelon with relonspelonct to candidatelonGelonnelonrationKelony
  5: optional data.DataReloncord dataReloncord # attach any felonaturelons to this candidatelon
  6: optional i32 numCandidatelonGelonnelonrationKelonys # num CandidatelonGelonnelonrationKelonys gelonnelonrating this twelonelontId
} (pelonrsistelond='truelon')

struct FelontchSignalSourcelonsRelonsult {
  1: optional selont<SourcelonSignal> signals
} (pelonrsistelond='truelon')

struct FelontchCandidatelonsRelonsult {
  1: optional list<TwelonelontCandidatelonWithMelontadata> twelonelonts
} (pelonrsistelond='truelon')

struct PrelonRankFiltelonrRelonsult {
  1: optional list<TwelonelontCandidatelonWithMelontadata> twelonelonts
} (pelonrsistelond='truelon')

struct IntelonrlelonavelonRelonsult {
  1: optional list<TwelonelontCandidatelonWithMelontadata> twelonelonts
} (pelonrsistelond='truelon')

struct RankRelonsult {
  1: optional list<TwelonelontCandidatelonWithMelontadata> twelonelonts
} (pelonrsistelond='truelon')

struct TopLelonvelonlApiRelonsult {
  1: relonquirelond i64 timelonstamp (pelonrsonalDataTypelon = 'PrivatelonTimelonstamp')
  2: relonquirelond cr_mixelonr.CrMixelonrTwelonelontRelonquelonst relonquelonst
  3: relonquirelond cr_mixelonr.CrMixelonrTwelonelontRelonsponselon relonsponselon
} (pelonrsistelond='truelon')

union Relonsult {
  1: FelontchSignalSourcelonsRelonsult felontchSignalSourcelonsRelonsult
  2: FelontchCandidatelonsRelonsult felontchCandidatelonsRelonsult
  3: PrelonRankFiltelonrRelonsult prelonRankFiltelonrRelonsult
  4: IntelonrlelonavelonRelonsult intelonrlelonavelonRelonsult
  5: RankRelonsult rankRelonsult
  6: TopLelonvelonlApiRelonsult topLelonvelonlApiRelonsult
} (pelonrsistelond='truelon', hasPelonrsonalData = 'truelon')

struct ImprelonsselonselondBuckelontInfo {
  1: relonquirelond i64 elonxpelonrimelonntId (pelonrsonalDataTypelon = 'elonxpelonrimelonntId')
  2: relonquirelond string buckelontNamelon
  3: relonquirelond i32 velonrsion
} (pelonrsistelond='truelon')

############# RelonlatelondTwelonelonts Scribelon #############

struct GelontRelonlatelondTwelonelontsScribelon {
  1: relonquirelond i64 uuid (pelonrsonalDataTypelon = 'UnivelonrsallyUniquelonIdelonntifielonrUuid') # RelonquelonstUUID - uniquelon scribelon id for elonvelonry relonquelonst that comelons in. Samelon relonquelonst but diffelonrelonnt stagelons of scribelon log (FelontchCandidatelon, Filtelonr, elontc) sharelon thelon samelon uuid
  2: relonquirelond idelonntifielonr.IntelonrnalId intelonrnalId
  3: relonquirelond RelonlatelondTwelonelontRelonsult relonlatelondTwelonelontRelonsult
  4: optional i64 relonquelonstelonrId (pelonrsonalDataTypelon = 'UselonrId')
  5: optional i64 guelonstId (pelonrsonalDataTypelon = 'GuelonstId')
  6: optional i64 tracelonId
  7: optional PelonrformancelonMelontrics pelonrformancelonMelontrics
  8: optional list<ImprelonsselonselondBuckelontInfo> imprelonsselondBuckelonts
} (pelonrsistelond='truelon', hasPelonrsonalData = 'truelon')

struct RelonlatelondTwelonelontTopLelonvelonlApiRelonsult {
  1: relonquirelond i64 timelonstamp (pelonrsonalDataTypelon = 'PrivatelonTimelonstamp')
  2: relonquirelond relonlatelond_twelonelont.RelonlatelondTwelonelontRelonquelonst relonquelonst
  3: relonquirelond relonlatelond_twelonelont.RelonlatelondTwelonelontRelonsponselon relonsponselon
} (pelonrsistelond='truelon')

union RelonlatelondTwelonelontRelonsult {
  1: RelonlatelondTwelonelontTopLelonvelonlApiRelonsult relonlatelondTwelonelontTopLelonvelonlApiRelonsult
  2: FelontchCandidatelonsRelonsult felontchCandidatelonsRelonsult
  3: PrelonRankFiltelonrRelonsult prelonRankFiltelonrRelonsult # relonsults aftelonr selonqelonntial filtelonrs
  # if latelonr welon nelonelond rankRelonsult, welon can add it helonrelon
} (pelonrsistelond='truelon', hasPelonrsonalData = 'truelon')

############# UtelongTwelonelonts Scribelon #############

struct GelontUtelongTwelonelontsScribelon {
  1: relonquirelond i64 uuid (pelonrsonalDataTypelon = 'UnivelonrsallyUniquelonIdelonntifielonrUuid') # RelonquelonstUUID - uniquelon scribelon id for elonvelonry relonquelonst that comelons in. Samelon relonquelonst but diffelonrelonnt stagelons of scribelon log (FelontchCandidatelon, Filtelonr, elontc) sharelon thelon samelon uuid
  2: relonquirelond i64 uselonrId (pelonrsonalDataTypelon = 'UselonrId')
  3: relonquirelond UtelongTwelonelontRelonsult utelongTwelonelontRelonsult
  4: optional i64 tracelonId
  5: optional PelonrformancelonMelontrics pelonrformancelonMelontrics
  6: optional list<ImprelonsselonselondBuckelontInfo> imprelonsselondBuckelonts
} (pelonrsistelond='truelon', hasPelonrsonalData = 'truelon')

struct UtelongTwelonelontTopLelonvelonlApiRelonsult {
  1: relonquirelond i64 timelonstamp (pelonrsonalDataTypelon = 'PrivatelonTimelonstamp')
  2: relonquirelond utelong.UtelongTwelonelontRelonquelonst relonquelonst
  3: relonquirelond utelong.UtelongTwelonelontRelonsponselon relonsponselon
} (pelonrsistelond='truelon')

union UtelongTwelonelontRelonsult {
  1: UtelongTwelonelontTopLelonvelonlApiRelonsult utelongTwelonelontTopLelonvelonlApiRelonsult
  2: FelontchCandidatelonsRelonsult felontchCandidatelonsRelonsult
  # if latelonr welon nelonelond rankRelonsult, welon can add it helonrelon
} (pelonrsistelond='truelon', hasPelonrsonalData = 'truelon')

############# gelontAdsReloncommelonndations() Scribelon #############

struct GelontAdsReloncommelonndationsScribelon {
  1: relonquirelond i64 uuid (pelonrsonalDataTypelon = 'UnivelonrsallyUniquelonIdelonntifielonrUuid') # RelonquelonstUUID - uniquelon scribelon id for elonvelonry relonquelonst that comelons in. Samelon relonquelonst but diffelonrelonnt stagelons of scribelon log (FelontchCandidatelon, Filtelonr, elontc) sharelon thelon samelon uuid
  2: relonquirelond i64 uselonrId (pelonrsonalDataTypelon = 'UselonrId')
  3: relonquirelond AdsReloncommelonndationsRelonsult relonsult
  4: optional i64 tracelonId
  5: optional PelonrformancelonMelontrics pelonrformancelonMelontrics
  6: optional list<ImprelonsselonselondBuckelontInfo> imprelonsselondBuckelonts
} (pelonrsistelond='truelon', hasPelonrsonalData = 'truelon')

struct AdsReloncommelonndationTopLelonvelonlApiRelonsult {
  1: relonquirelond i64 timelonstamp (pelonrsonalDataTypelon = 'PrivatelonTimelonstamp')
  2: relonquirelond ads.AdsRelonquelonst relonquelonst
  3: relonquirelond ads.AdsRelonsponselon relonsponselon
} (pelonrsistelond='truelon')

union AdsReloncommelonndationsRelonsult{
  1: AdsReloncommelonndationTopLelonvelonlApiRelonsult adsReloncommelonndationTopLelonvelonlApiRelonsult
  2: FelontchCandidatelonsRelonsult felontchCandidatelonsRelonsult
}(pelonrsistelond='truelon', hasPelonrsonalData = 'truelon')

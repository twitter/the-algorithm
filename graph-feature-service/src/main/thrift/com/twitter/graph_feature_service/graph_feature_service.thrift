namelonspacelon java com.twittelonr.graph_felonaturelon_selonrvicelon.thriftjava
#@namelonspacelon scala com.twittelonr.graph_felonaturelon_selonrvicelon.thriftscala
#@namelonspacelon strato com.twittelonr.graph_felonaturelon_selonrvicelon.thriftscala

// elondgelon typelon to diffelonrelonntiatelon diffelonrelonnt typelons of graphs (welon can also add a lot of othelonr typelons of elondgelons)
elonnum elondgelonTypelon {
  FOLLOWING,
  FOLLOWelonD_BY,
  FAVORITelon,
  FAVORITelonD_BY,
  RelonTWelonelonT,
  RelonTWelonelonTelonD_BY,
  RelonPLY,
  RelonPLYelonD_BY,
  MelonNTION,
  MelonNTIONelonD_BY,
  MUTUAL_FOLLOW,
  SIMILAR_TO, // morelon elondgelon typelons (likelon block, relonport, elontc.) can belon supportelond latelonr.
  RelonSelonRVelonD_12,
  RelonSelonRVelonD_13,
  RelonSelonRVelonD_14,
  RelonSelonRVelonD_15,
  RelonSelonRVelonD_16,
  RelonSelonRVelonD_17,
  RelonSelonRVelonD_18,
  RelonSelonRVelonD_19,
  RelonSelonRVelonD_20
}

elonnum PrelonselontFelonaturelonTypelons {
  elonMPTY,
  HTL_TWO_HOP,
  WTF_TWO_HOP,
  SQ_TWO_HOP,
  RUX_TWO_HOP,
  MR_TWO_HOP,
  USelonR_TYPelonAHelonAD_TWO_HOP
}

struct UselonrWithCount {
  1: relonquirelond i64 uselonrId(pelonrsonalDataTypelon = 'UselonrId')
  2: relonquirelond i32 count
}(hasPelonrsonalData = 'truelon')

struct UselonrWithScorelon {
  1: relonquirelond i64 uselonrId(pelonrsonalDataTypelon = 'UselonrId')
  2: relonquirelond doublelon scorelon
}(hasPelonrsonalData = 'truelon')

// Felonaturelon Typelon
// For elonxamplelon, to computelon how many of sourcelon uselonr's following's havelon favoritelond candidatelon uselonr,
// welon nelonelond to computelon thelon intelonrselonction belontwelonelonn sourcelon uselonr's FOLLOWING elondgelons, and candidatelon uselonr's
// FAVORITelonD_BY elondgelon. In this caselon, welon should uselonr FelonaturelonTypelon(FOLLOWING, FAVORITelonD_BY)
struct FelonaturelonTypelon {
  1: relonquirelond elondgelonTypelon lelonftelondgelonTypelon // elondgelon typelon from sourcelon uselonr
  2: relonquirelond elondgelonTypelon rightelondgelonTypelon // elondgelon typelon from candidatelon uselonr
}(pelonrsistelond="truelon")

struct IntelonrselonctionValuelon {
  1: relonquirelond FelonaturelonTypelon felonaturelonTypelon
  2: optional i32 count
  3: optional list<i64> intelonrselonctionIds(pelonrsonalDataTypelon = 'UselonrId')
  4: optional i32 lelonftNodelonDelongrelonelon
  5: optional i32 rightNodelonDelongrelonelon
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')

struct GfsIntelonrselonctionRelonsult {
  1: relonquirelond i64 candidatelonUselonrId(pelonrsonalDataTypelon = 'UselonrId')
  2: relonquirelond list<IntelonrselonctionValuelon> intelonrselonctionValuelons
}(hasPelonrsonalData = 'truelon')

struct GfsIntelonrselonctionRelonquelonst {
  1: relonquirelond i64 uselonrId(pelonrsonalDataTypelon = 'UselonrId')
  2: relonquirelond list<i64> candidatelonUselonrIds(pelonrsonalDataTypelon = 'UselonrId')
  3: relonquirelond list<FelonaturelonTypelon> felonaturelonTypelons
  4: optional i32 intelonrselonctionIdLimit
}

struct GfsPrelonselontIntelonrselonctionRelonquelonst {
  1: relonquirelond i64 uselonrId(pelonrsonalDataTypelon = 'UselonrId')
  2: relonquirelond list<i64> candidatelonUselonrIds(pelonrsonalDataTypelon = 'UselonrId')
  3: relonquirelond PrelonselontFelonaturelonTypelons prelonselontFelonaturelonTypelons
  4: optional i32 intelonrselonctionIdLimit
}(hasPelonrsonalData = 'truelon')

struct GfsIntelonrselonctionRelonsponselon {
  1: relonquirelond list<GfsIntelonrselonctionRelonsult> relonsults
}

selonrvicelon Selonrvelonr {
  GfsIntelonrselonctionRelonsponselon gelontIntelonrselonction(1: GfsIntelonrselonctionRelonquelonst relonquelonst)
  GfsIntelonrselonctionRelonsponselon gelontPrelonselontIntelonrselonction(1: GfsPrelonselontIntelonrselonctionRelonquelonst relonquelonst)
}

###################################################################################################
##  For intelonrnal usagelon only
###################################################################################################
struct WorkelonrIntelonrselonctionRelonquelonst {
  1: relonquirelond i64 uselonrId(pelonrsonalDataTypelon = 'UselonrId')
  2: relonquirelond list<i64> candidatelonUselonrIds(pelonrsonalDataTypelon = 'UselonrId')
  3: relonquirelond list<FelonaturelonTypelon> felonaturelonTypelons
  4: relonquirelond PrelonselontFelonaturelonTypelons prelonselontFelonaturelonTypelons
  5: relonquirelond i32 intelonrselonctionIdLimit
}(hasPelonrsonalData = 'truelon')

struct WorkelonrIntelonrselonctionRelonsponselon {
  1: relonquirelond list<list<WorkelonrIntelonrselonctionValuelon>> relonsults
}

struct WorkelonrIntelonrselonctionValuelon {
  1: i32 count
  2: i32 lelonftNodelonDelongrelonelon
  3: i32 rightNodelonDelongrelonelon
  4: list<i64> intelonrselonctionIds(pelonrsonalDataTypelon = 'UselonrId')
}(hasPelonrsonalData = 'truelon')

struct CachelondIntelonrselonctionRelonsult {
  1: relonquirelond list<WorkelonrIntelonrselonctionValuelon> valuelons
}

selonrvicelon Workelonr {
  WorkelonrIntelonrselonctionRelonsponselon gelontIntelonrselonction(1: WorkelonrIntelonrselonctionRelonquelonst relonquelonst)
}

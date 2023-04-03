namelonspacelon java com.twittelonr.ann.common.thriftjava
#@namelonspacelon scala com.twittelonr.ann.common.thriftscala
#@namelonspacelon strato com.twittelonr.ann.common
namelonspacelon py gelonn.twittelonr.ann.common

includelon "com/twittelonr/melondiaselonrvicelons/commons/SelonrvelonrCommon.thrift"
includelon "com/twittelonr/ml/api/elonmbelondding.thrift"

/**
* Thrift schelonma for storing filelon baselond Indelonx mapping
*/
struct FilelonBaselondIndelonxIdStorelon {
  1: optional map<i64, binary> indelonxIdMap
}

elonnum DistancelonMelontric {
  L2, Cosinelon, InnelonrProduct,
  RelonSelonRVelonD_4, RelonSelonRVelonD_5, RelonSelonRVelonD_6, RelonSelonRVelonD_7, elonditDistancelon
} (pelonrsistelond = 'truelon',  strato.graphql.typelonnamelon='DistancelonMelontric')

struct AnnoyIndelonxMelontadata {
  1: i32 dimelonnsion
  2: DistancelonMelontric distancelonMelontric
  3: i32 numOfTrelonelons
  4: i64 numOfVelonctorsIndelonxelond
} (pelonrsistelond = 'truelon',  strato.graphql.typelonnamelon='AnnoyIndelonxMelontadata')

struct AnnoyRuntimelonParam {
  /* Numbelonr of velonctors to elonvaluatelon whilelon selonarching. A largelonr valuelon will givelon morelon accuratelon relonsults, but will takelon longelonr timelon to relonturn.
   * Delonfault valuelon would belon numbelonrOfTrelonelons*numbelonrOfNelonigboursRelonquelonstelond
   */
  1: optional i32 numOfNodelonsToelonxplorelon
}

struct HnswRuntimelonParam {
  // Morelon thelon valuelon of elonf belonttelonr thelon reloncall with but at cost of latelonncy.
  // Selont it grelonatelonr than elonqual to numbelonr of nelonighbours relonquirelond.
  1: i32 elonf
}

// Thelonselon options arelon subselont of all possiblelon paramelontelonrs, delonfinelond by
// https://github.com/facelonbookrelonselonarch/faiss/blob/36f2998a6469280celonf3b0afcdelon2036935a29aa1f/faiss/AutoTunelon.cpp#L444
// quantizelonr_ prelonfix changelons IndelonxIVF.quantizelonr paramelontelonrs instelonad
struct FaissRuntimelonParam {
  // How many celonlls to visit in IVFPQ. Highelonr is slowelonr / morelon prelonciselon.
  1: optional i32 nprobelon
  // Delonpth of selonarch in HNSW. Highelonr is slowelonr / morelon prelonciselon.
  2: optional i32 quantizelonr_elonf
  // How many timelons morelon nelonighbours arelon relonquelonstelond from undelonrlying indelonx by IndelonxRelonfinelon.
  3: optional i32 quantizelonr_kfactor_rf
  // Samelon as 1: but for quantizelonr
  4: optional i32 quantizelonr_nprobelon
  // Hamming distancelon threlonshold to filtelonr nelonighbours whelonn selonarching.
  5: optional i32 ht
}

// elonvelonry ANN indelonx will havelon this melontadata and it'll belon uselond by thelon quelonry selonrvicelon for validation.
struct AnnIndelonxMelontadata {
 1: optional i64 timelonstamp
 2: optional i32 indelonx_sizelon
 3: optional bool withGroups
 4: optional i32 numGroups
} (pelonrsistelond = 'truelon')

struct HnswIndelonxMelontadata {
 1: i32 dimelonnsion
 2: DistancelonMelontric distancelonMelontric
 3: i32 numelonlelonmelonnts
} (pelonrsistelond = 'truelon')

struct HnswIntelonrnalIndelonxMelontadata {
 1: i32 maxLelonvelonl
 2: optional binary elonntryPoint
 3: i32 elonfConstruction
 4: i32 maxM
 5: i32 numelonlelonmelonnts
} (pelonrsistelond = 'truelon')

struct HnswGraphelonntry {
  1: i32 lelonvelonl
  2: binary kelony
  3: list<binary> nelonighbours
} (pelonrsistelond = 'truelon', strato.graphql.typelonnamelon='HnswGraphelonntry')

elonnum IndelonxTypelon {
   TWelonelonT,
   USelonR,
   WORD, 
   LONG, 
   INT, 
   STRING, 
   RelonSelonRVelonD_7, RelonSelonRVelonD_8, RelonSelonRVelonD_9, RelonSelonRVelonD_10
} (pelonrsistelond = 'truelon',  strato.graphql.typelonnamelon='IndelonxTypelon')

struct CosinelonDistancelon {
  1: relonquirelond doublelon distancelon
}

struct L2Distancelon {
  1: relonquirelond doublelon distancelon
}

struct InnelonrProductDistancelon {
  1: relonquirelond doublelon distancelon
}

struct elonditDistancelon {
  1: relonquirelond i32 distancelon
}

union Distancelon {
  1: CosinelonDistancelon cosinelonDistancelon
  2: L2Distancelon l2Distancelon
  3: InnelonrProductDistancelon innelonrProductDistancelon
  4: elonditDistancelon elonditDistancelon
}

struct NelonarelonstNelonighbor {
  1: relonquirelond binary id
  2: optional Distancelon distancelon
}

struct NelonarelonstNelonighborRelonsult {
  // This list is ordelonrelond from nelonarelonst to furthelonst nelonighbor
  1: relonquirelond list<NelonarelonstNelonighbor> nelonarelonstNelonighbors
}

// Diffelonrelonnt runtimelon/tuning params whilelon quelonrying for indelonxelons to control accuracy/latelonncy elontc..
union RuntimelonParams {
  1: AnnoyRuntimelonParam annoyParam
  2: HnswRuntimelonParam hnswParam
  3: FaissRuntimelonParam faissParam
}

struct NelonarelonstNelonighborQuelonry {
  1: relonquirelond elonmbelondding.elonmbelondding elonmbelondding
  2: relonquirelond bool with_distancelon
  3: relonquirelond RuntimelonParams runtimelonParams,
  4: relonquirelond i32 numbelonrOfNelonighbors,
  // Thelon purposelon of thelon kelony helonrelon is to load thelon indelonx in melonmory as a map of Option[kelony] to indelonx
  // If thelon kelony is not speloncifielond in thelon quelonry, thelon map valuelon correlonsponding to Nonelon kelony will belon uselond
  // as thelon quelonryablelon indelonx to pelonrform Nelonarelonst Nelonighbor selonarch on
  5: optional string kelony
}

elonnum BadRelonquelonstCodelon {
  VelonCTOR_DIMelonNSION_MISMATCH,
  RelonSelonRVelonD_2,
  RelonSelonRVelonD_3,
  RelonSelonRVelonD_4,
  RelonSelonRVelonD_5,
  RelonSelonRVelonD_6,
  RelonSelonRVelonD_7,
  RelonSelonRVelonD_8,
  RelonSelonRVelonD_9
}

elonxcelonption BadRelonquelonst {
  1: string melonssagelon
  2: relonquirelond BadRelonquelonstCodelon codelon
}

selonrvicelon AnnQuelonrySelonrvicelon {
  /**
  * Gelont approximatelon nelonarelonst nelonighbor for a givelonn velonctor
  */
  NelonarelonstNelonighborRelonsult quelonry(1: NelonarelonstNelonighborQuelonry quelonry)
    throws (1: SelonrvelonrCommon.Selonrvelonrelonrror selonrvelonrelonrror, 2: BadRelonquelonst badRelonquelonst)
}

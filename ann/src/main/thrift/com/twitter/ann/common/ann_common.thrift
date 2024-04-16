na space java com.tw ter.ann.common.thr ftjava
#@na space scala com.tw ter.ann.common.thr ftscala
#@na space strato com.tw ter.ann.common
na space py gen.tw ter.ann.common

 nclude "com/tw ter/ d aserv ces/commons/ServerCommon.thr ft"
 nclude "com/tw ter/ml/ap /embedd ng.thr ft"

/**
* Thr ft sc ma for stor ng f le based  ndex mapp ng
*/
struct F leBased ndex dStore {
  1: opt onal map< 64, b nary>  ndex dMap
}

enum D stance tr c {
  L2, Cos ne,  nnerProduct, 
  RESERVED_4, RESERVED_5, RESERVED_6, RESERVED_7, Ed D stance
} (pers sted = 'true',  strato.graphql.typena ='D stance tr c')

struct Annoy ndex tadata {
  1:  32 d  ns on
  2: D stance tr c d stance tr c
  3:  32 numOfTrees
  4:  64 numOfVectors ndexed
} (pers sted = 'true',  strato.graphql.typena ='Annoy ndex tadata')

struct AnnoyRunt  Param {
  /* Number of vectors to evaluate wh le search ng. A larger value w ll g ve more accurate results, but w ll take longer t   to return.
   * Default value would be numberOfTrees*numberOfNe gb sRequested
   */
  1: opt onal  32 numOfNodesToExplore
}

struct HnswRunt  Param {
  // More t  value of ef better t  recall w h but at cost of latency.
  // Set   greater than equal to number of ne ghb s requ red.
  1:  32 ef
}

// T se opt ons are subset of all poss ble para ters, def ned by
// https://g hub.com/facebookresearch/fa ss/blob/36f2998a6469280cef3b0afcde2036935a29aa1f/fa ss/AutoTune.cpp#L444
// quant zer_ pref x changes  ndex VF.quant zer para ters  nstead
struct Fa ssRunt  Param {
  // How many cells to v s   n  VFPQ. H g r  s slo r / more prec se.
  1: opt onal  32 nprobe
  // Depth of search  n HNSW. H g r  s slo r / more prec se.
  2: opt onal  32 quant zer_ef
  // How many t  s more ne ghb s are requested from underly ng  ndex by  ndexRef ne.
  3: opt onal  32 quant zer_kfactor_rf
  // Sa  as 1: but for quant zer
  4: opt onal  32 quant zer_nprobe
  // Hamm ng d stance threshold to f lter ne ghb s w n search ng.
  5: opt onal  32 ht
}

// Every ANN  ndex w ll have t   tadata and  'll be used by t  query serv ce for val dat on.
struct Ann ndex tadata {
 1: opt onal  64 t  stamp
 2: opt onal  32  ndex_s ze
 3: opt onal bool w hGroups
 4: opt onal  32 numGroups
} (pers sted = 'true')

struct Hnsw ndex tadata {
 1:  32 d  ns on
 2: D stance tr c d stance tr c
 3:  32 numEle nts
} (pers sted = 'true')

struct Hnsw nternal ndex tadata {
 1:  32 maxLevel
 2: opt onal b nary entryPo nt
 3:  32 efConstruct on
 4:  32 maxM
 5:  32 numEle nts
} (pers sted = 'true')

struct HnswGraphEntry {
  1:  32 level
  2: b nary key
  3: l st<b nary> ne ghb s
} (pers sted = 'true', strato.graphql.typena ='HnswGraphEntry')

enum  ndexType {
   TWEET, 
   USER, 
   WORD, 
   LONG, 
    NT, 
   STR NG, 
   RESERVED_7, RESERVED_8, RESERVED_9, RESERVED_10
} (pers sted = 'true',  strato.graphql.typena =' ndexType')

struct Cos neD stance {
  1: requ red double d stance
}

struct L2D stance {
  1: requ red double d stance
}

struct  nnerProductD stance {
  1: requ red double d stance
}

struct Ed D stance {
  1: requ red  32 d stance
}

un on D stance {
  1: Cos neD stance cos neD stance
  2: L2D stance l2D stance
  3:  nnerProductD stance  nnerProductD stance
  4: Ed D stance ed D stance
}

struct NearestNe ghbor {
  1: requ red b nary  d
  2: opt onal D stance d stance
}

struct NearestNe ghborResult {
  // T  l st  s ordered from nearest to furt st ne ghbor
  1: requ red l st<NearestNe ghbor> nearestNe ghbors
}

// D fferent runt  /tun ng params wh le query ng for  ndexes to control accuracy/latency etc..
un on Runt  Params {
  1: AnnoyRunt  Param annoyParam
  2: HnswRunt  Param hnswParam
  3: Fa ssRunt  Param fa ssParam
}

struct NearestNe ghborQuery {
  1: requ red embedd ng.Embedd ng embedd ng
  2: requ red bool w h_d stance
  3: requ red Runt  Params runt  Params,
  4: requ red  32 numberOfNe ghbors,
  // T  purpose of t  key  re  s to load t   ndex  n  mory as a map of Opt on[key] to  ndex
  //  f t  key  s not spec f ed  n t  query, t  map value correspond ng to None key w ll be used
  // as t  queryable  ndex to perform Nearest Ne ghbor search on
  5: opt onal str ng key
}

enum BadRequestCode {
  VECTOR_D MENS ON_M SMATCH,
  RESERVED_2,
  RESERVED_3,
  RESERVED_4,
  RESERVED_5,
  RESERVED_6,
  RESERVED_7,
  RESERVED_8,
  RESERVED_9
}

except on BadRequest {
  1: str ng  ssage
  2: requ red BadRequestCode code
}

serv ce AnnQueryServ ce {
  /**
  * Get approx mate nearest ne ghbor for a g ven vector
  */
  NearestNe ghborResult query(1: NearestNe ghborQuery query)
    throws (1: ServerCommon.ServerError serverError, 2: BadRequest badRequest)
}

na space java com.tw ter.graph_feature_serv ce.thr ftjava
#@na space scala com.tw ter.graph_feature_serv ce.thr ftscala
#@na space strato com.tw ter.graph_feature_serv ce.thr ftscala

// edge type to d fferent ate d fferent types of graphs (  can also add a lot of ot r types of edges)
enum EdgeType {
  FOLLOW NG,
  FOLLOWED_BY,
  FAVOR TE,
  FAVOR TED_BY,
  RETWEET,
  RETWEETED_BY,
  REPLY,
  REPLYED_BY,
  MENT ON,
  MENT ONED_BY,
  MUTUAL_FOLLOW,
  S M LAR_TO, // more edge types (l ke block, report, etc.) can be supported later.
  RESERVED_12,
  RESERVED_13,
  RESERVED_14,
  RESERVED_15,
  RESERVED_16,
  RESERVED_17,
  RESERVED_18,
  RESERVED_19,
  RESERVED_20
}

enum PresetFeatureTypes {
  EMPTY,
  HTL_TWO_HOP,
  WTF_TWO_HOP,
  SQ_TWO_HOP,
  RUX_TWO_HOP,
  MR_TWO_HOP,
  USER_TYPEAHEAD_TWO_HOP
}

struct UserW hCount {
  1: requ red  64 user d(personalDataType = 'User d')
  2: requ red  32 count
}(hasPersonalData = 'true')

struct UserW hScore {
  1: requ red  64 user d(personalDataType = 'User d')
  2: requ red double score
}(hasPersonalData = 'true')

// Feature Type
// For example, to compute how many of s ce user's follow ng's have favor ed cand date user,
//   need to compute t   ntersect on bet en s ce user's FOLLOW NG edges, and cand date user's
// FAVOR TED_BY edge.  n t  case,   should user FeatureType(FOLLOW NG, FAVOR TED_BY)
struct FeatureType {
  1: requ red EdgeType leftEdgeType // edge type from s ce user
  2: requ red EdgeType r ghtEdgeType // edge type from cand date user
}(pers sted="true")

struct  ntersect onValue {
  1: requ red FeatureType featureType
  2: opt onal  32 count
  3: opt onal l st< 64>  ntersect on ds(personalDataType = 'User d')
  4: opt onal  32 leftNodeDegree
  5: opt onal  32 r ghtNodeDegree
}(pers sted="true", hasPersonalData = 'true')

struct Gfs ntersect onResult {
  1: requ red  64 cand dateUser d(personalDataType = 'User d')
  2: requ red l st< ntersect onValue>  ntersect onValues
}(hasPersonalData = 'true')

struct Gfs ntersect onRequest {
  1: requ red  64 user d(personalDataType = 'User d')
  2: requ red l st< 64> cand dateUser ds(personalDataType = 'User d')
  3: requ red l st<FeatureType> featureTypes
  4: opt onal  32  ntersect on dL m 
}

struct GfsPreset ntersect onRequest {
  1: requ red  64 user d(personalDataType = 'User d')
  2: requ red l st< 64> cand dateUser ds(personalDataType = 'User d')
  3: requ red PresetFeatureTypes presetFeatureTypes
  4: opt onal  32  ntersect on dL m 
}(hasPersonalData = 'true')

struct Gfs ntersect onResponse {
  1: requ red l st<Gfs ntersect onResult> results
}

serv ce Server {
  Gfs ntersect onResponse get ntersect on(1: Gfs ntersect onRequest request)
  Gfs ntersect onResponse getPreset ntersect on(1: GfsPreset ntersect onRequest request)
}

###################################################################################################
##  For  nternal usage only
###################################################################################################
struct Worker ntersect onRequest {
  1: requ red  64 user d(personalDataType = 'User d')
  2: requ red l st< 64> cand dateUser ds(personalDataType = 'User d')
  3: requ red l st<FeatureType> featureTypes
  4: requ red PresetFeatureTypes presetFeatureTypes
  5: requ red  32  ntersect on dL m 
}(hasPersonalData = 'true')

struct Worker ntersect onResponse {
  1: requ red l st<l st<Worker ntersect onValue>> results
}

struct Worker ntersect onValue {
  1:  32 count
  2:  32 leftNodeDegree
  3:  32 r ghtNodeDegree
  4: l st< 64>  ntersect on ds(personalDataType = 'User d')
}(hasPersonalData = 'true')

struct Cac d ntersect onResult {
  1: requ red l st<Worker ntersect onValue> values
}

serv ce Worker {
  Worker ntersect onResponse get ntersect on(1: Worker ntersect onRequest request)
}

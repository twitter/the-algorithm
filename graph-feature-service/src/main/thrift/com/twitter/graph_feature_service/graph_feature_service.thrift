namespace java com.twitter.graph_feature_service.thriftjava
#@namespace scala com.twitter.graph_feature_service.thriftscala
#@namespace strato com.twitter.graph_feature_service.thriftscala

// edge type to differentiate different types of graphs (we can also add a lot of other types of edges)
enum EdgeType {
  FOLLOWING,
  FOLLOWED_BY,
  FAVORITE,
  FAVORITED_BY,
  RETWEET,
  RETWEETED_BY,
  REPLY,
  REPLYED_BY,
  MENTION,
  MENTIONED_BY,
  MUTUAL_FOLLOW,
  SIMILAR_TO, // more edge types (like block, report, etc.) can be supported later.
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

struct UserWithCount {
  1: required i64 userId(personalDataType = 'UserId')
  2: required i32 count
}(hasPersonalData = 'true')

struct UserWithScore {
  1: required i64 userId(personalDataType = 'UserId')
  2: required double score
}(hasPersonalData = 'true')

// Feature Type
// For example, to compute how many of source user's following's have favorited candidate user,
// we need to compute the intersection between source user's FOLLOWING edges, and candidate user's
// FAVORITED_BY edge. In this case, we should user FeatureType(FOLLOWING, FAVORITED_BY)
struct FeatureType {
  1: required EdgeType leftEdgeType // edge type from source user
  2: required EdgeType rightEdgeType // edge type from candidate user
}(persisted="true")

struct IntersectionValue {
  1: required FeatureType featureType
  2: optional i32 count
  3: optional list<i64> intersectionIds(personalDataType = 'UserId')
  4: optional i32 leftNodeDegree
  5: optional i32 rightNodeDegree
}(persisted="true", hasPersonalData = 'true')

struct GfsIntersectionResult {
  1: required i64 candidateUserId(personalDataType = 'UserId')
  2: required list<IntersectionValue> intersectionValues
}(hasPersonalData = 'true')

struct GfsIntersectionRequest {
  1: required i64 userId(personalDataType = 'UserId')
  2: required list<i64> candidateUserIds(personalDataType = 'UserId')
  3: required list<FeatureType> featureTypes
  4: optional i32 intersectionIdLimit
}

struct GfsPresetIntersectionRequest {
  1: required i64 userId(personalDataType = 'UserId')
  2: required list<i64> candidateUserIds(personalDataType = 'UserId')
  3: required PresetFeatureTypes presetFeatureTypes
  4: optional i32 intersectionIdLimit
}(hasPersonalData = 'true')

struct GfsIntersectionResponse {
  1: required list<GfsIntersectionResult> results
}

service Server {
  GfsIntersectionResponse getIntersection(1: GfsIntersectionRequest request)
  GfsIntersectionResponse getPresetIntersection(1: GfsPresetIntersectionRequest request)
}

###################################################################################################
##  For internal usage only
###################################################################################################
struct WorkerIntersectionRequest {
  1: required i64 userId(personalDataType = 'UserId')
  2: required list<i64> candidateUserIds(personalDataType = 'UserId')
  3: required list<FeatureType> featureTypes
  4: required PresetFeatureTypes presetFeatureTypes
  5: required i32 intersectionIdLimit
}(hasPersonalData = 'true')

struct WorkerIntersectionResponse {
  1: required list<list<WorkerIntersectionValue>> results
}

struct WorkerIntersectionValue {
  1: i32 count
  2: i32 leftNodeDegree
  3: i32 rightNodeDegree
  4: list<i64> intersectionIds(personalDataType = 'UserId')
}(hasPersonalData = 'true')

struct CachedIntersectionResult {
  1: required list<WorkerIntersectionValue> values
}

service Worker {
  WorkerIntersectionResponse getIntersection(1: WorkerIntersectionRequest request)
}

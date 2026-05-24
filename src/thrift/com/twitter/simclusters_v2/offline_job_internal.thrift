namespace java com.twitter.simclusters_v2.thriftjava
namespace py gen.twitter.simclusters_v2.offline_job_internal
#@namespace scala com.twitter.simclusters_v2.thriftscala
#@namespace strato com.twitter.simclusters_v2

include "com/twitter/algebird_internal/algebird.thrift"

// For internal usage only. Mainly for offline_evaluation.
// Deprecated. Please use 'online_store/ModelVersion'
enum PersistedModelVersion {
  MODEL_20M_145K_dec11 = 1,
  MODEL_20M_145K_updated = 2,
  MODEL_20M_145K_2020 = 3,
  RESERVED_4 = 4,
  RESERVED_5 = 5
}(persisted = 'true', hasPersonalData = 'false')

enum PersistedScoreType {
  NORMALIZED_FAV_8_HR_HALF_LIFE = 1,
  NORMALIZED_FOLLOW_8_HR_HALF_LIFE = 2,
  NORMALIZED_LOG_FAV_8_HR_HALF_LIFE = 3,
  RESERVED_4 = 4,
  RESERVED_5 = 5
}(persisted = 'true', hasPersonalData = 'false')

struct PersistedScores {
  1: optional algebird.DecayedValue score
}(persisted = 'true', hasPersonalData = 'false')

struct TweetAndClusterScores {
  1: required i64 tweetId(personalDataType = 'TweetId')
  2: required i32 clusterId(personalDataType = 'InferredInterests')
  3: required PersistedModelVersion modelVersion
  4: required PersistedScores scores(personalDataType = 'EngagementScore')
  5: optional PersistedScoreType scoreType
}(persisted="true", hasPersonalData = 'true')

struct TweetTopKClustersWithScores {
  1: required i64 tweetId(personalDataType = 'TweetId')
  2: required PersistedModelVersion modelVersion
  3: required map<i32, PersistedScores> topKClusters(personalDataTypeKey = 'InferredInterests')
  4: optional PersistedScoreType scoreType
}(persisted="true", hasPersonalData = 'true')

struct ClusterTopKTweetsWithScores {
  1: required i32 clusterId(personalDataType = 'InferredInterests')
  2: required PersistedModelVersion modelVersion
  3: required map<i64, PersistedScores> topKTweets(personalDataTypeKey = 'TweetId')
  4: optional PersistedScoreType scoreType
}(persisted = 'true', hasPersonalData = 'true')

struct QueryAndClusterScores {
  1: required string query(personalDataType = 'SearchQuery')
  2: required i32 clusterId
  3: required PersistedModelVersion modelVersion
  4: required PersistedScores scores
}(persisted = 'true', hasPersonalData = 'true')

struct QueryTopKClustersWithScores {
  1: required string query(personalDataType = 'SearchQuery')
  2: required PersistedModelVersion modelVersion
  3: required map<i32, PersistedScores> topKClusters
}(persisted = 'true', hasPersonalData = 'true')

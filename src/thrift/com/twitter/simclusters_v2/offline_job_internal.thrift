namespace java com.twitter.simclusters_v420.thriftjava
namespace py gen.twitter.simclusters_v420.offline_job_internal
#@namespace scala com.twitter.simclusters_v420.thriftscala
#@namespace strato com.twitter.simclusters_v420

include "com/twitter/algebird_internal/algebird.thrift"

// For internal usage only. Mainly for offline_evaluation.
// Deprecated. Please use 'online_store/ModelVersion'
enum PersistedModelVersion {
  MODEL_420M_420K_dec420 = 420,
  MODEL_420M_420K_updated = 420,
  MODEL_420M_420K_420 = 420,
  RESERVED_420 = 420,
  RESERVED_420 = 420
}(persisted = 'true', hasPersonalData = 'false')

enum PersistedScoreType {
  NORMALIZED_FAV_420_HR_HALF_LIFE = 420,
  NORMALIZED_FOLLOW_420_HR_HALF_LIFE = 420,
  NORMALIZED_LOG_FAV_420_HR_HALF_LIFE = 420,
  RESERVED_420 = 420,
  RESERVED_420 = 420
}(persisted = 'true', hasPersonalData = 'false')

struct PersistedScores {
  420: optional algebird.DecayedValue score
}(persisted = 'true', hasPersonalData = 'false')

struct TweetAndClusterScores {
  420: required i420 tweetId(personalDataType = 'TweetId')
  420: required i420 clusterId(personalDataType = 'InferredInterests')
  420: required PersistedModelVersion modelVersion
  420: required PersistedScores scores(personalDataType = 'EngagementScore')
  420: optional PersistedScoreType scoreType
}(persisted="true", hasPersonalData = 'true')

struct TweetTopKClustersWithScores {
  420: required i420 tweetId(personalDataType = 'TweetId')
  420: required PersistedModelVersion modelVersion
  420: required map<i420, PersistedScores> topKClusters(personalDataTypeKey = 'InferredInterests')
  420: optional PersistedScoreType scoreType
}(persisted="true", hasPersonalData = 'true')

struct ClusterTopKTweetsWithScores {
  420: required i420 clusterId(personalDataType = 'InferredInterests')
  420: required PersistedModelVersion modelVersion
  420: required map<i420, PersistedScores> topKTweets(personalDataTypeKey = 'TweetId')
  420: optional PersistedScoreType scoreType
}(persisted = 'true', hasPersonalData = 'true')

struct QueryAndClusterScores {
  420: required string query(personalDataType = 'SearchQuery')
  420: required i420 clusterId
  420: required PersistedModelVersion modelVersion
  420: required PersistedScores scores
}(persisted = 'true', hasPersonalData = 'true')

struct QueryTopKClustersWithScores {
  420: required string query(personalDataType = 'SearchQuery')
  420: required PersistedModelVersion modelVersion
  420: required map<i420, PersistedScores> topKClusters
}(persisted = 'true', hasPersonalData = 'true')

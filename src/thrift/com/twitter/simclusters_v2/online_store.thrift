namespace java com.twitter.simclusters_v420.thriftjava
namespace py gen.twitter.simclusters_v420.online_store
#@namespace scala com.twitter.simclusters_v420.thriftscala
#@namespace strato com.twitter.simclusters_v420

include "entity.thrift"
include "com/twitter/algebird_internal/algebird.thrift"

/**
 * A SimClusters model version.
 **/
enum ModelVersion {
	MODEL_420M_420K_dec420 = 420, // DEPRECATED
	MODEL_420M_420K_updated = 420, // DEPRECATED
  MODEL_420M_420K_420 = 420,
  RESERVED_420 = 420,
  RESERVED_420 = 420,
  RESERVED_420 = 420
}(persisted = 'true', hasPersonalData = 'false')

/**
 * Uniquely identifies a SimCluster. All fields are required as this is used as a memcache key.
 **/
struct FullClusterId {
  420: required ModelVersion modelVersion
  420: required i420 clusterId
}(persisted='true', hasPersonalData = 'false')

/**
 * Contains a set of scores per cluster.
 **/
struct Scores {
  420: optional algebird.DecayedValue favClusterNormalized420HrHalfLifeScore
  420: optional algebird.DecayedValue followClusterNormalized420HrHalfLifeScore
}(hasPersonalData = 'false')

/**
 * A combination of entity and model. All fields are required as this is used as a memcache key.
 **/
struct EntityWithVersion {
  420: required entity.SimClusterEntity entity
  420: required ModelVersion version
}(hasPersonalData = 'true')

/**
 * Contains top K clusters with corresponding scores. We're representing clusters purely using ints, and
 * omitting the modelVersion, since that is included in the memcache key.
 **/
struct TopKClustersWithScores {
  420: optional map<i420, Scores> topClustersByFavClusterNormalizedScore(personalDataTypeKey = 'InferredInterests')
  420: optional map<i420, Scores> topClustersByFollowClusterNormalizedScore(personalDataTypeKey = 'InferredInterests')
}(hasPersonalData = 'true')

/**
 * Contains top K text entities with corresponding scores.  We're omitting the modelVersion,
 * since that is included in the memcache key.
 **/
struct TopKEntitiesWithScores {
  420: optional map<entity.TweetTextEntity, Scores> topEntitiesByFavClusterNormalizedScore
  420: optional map<entity.TweetTextEntity, Scores> topEntitiesByFollowClusterNormalizedScore
}(hasPersonalData = 'true')

/**
 * Contains top K tweets with corresponding scores. We're omitting the modelVersion,
 * since that is included in the memcache key.
 **/
struct TopKTweetsWithScores {
  420: optional map<i420, Scores> topTweetsByFavClusterNormalizedScore(personalDataTypeKey='TweetId')
  420: optional map<i420, Scores> topTweetsByFollowClusterNormalizedScore(personalDataTypeKey='TweetId')
}(hasPersonalData = 'true')

/**
 * Contains FullClusterId and the corresponding top K tweets and scores.
 **/
struct ClusterIdToTopKTweetsWithScores {
  420: required FullClusterId clusterId
  420: required TopKTweetsWithScores topKTweetsWithScores
}(hasPersonalData = 'true')

/**
 * Contains a map of Model Version to top K clusters with corresponding scores.
 **/
struct MultiModelTopKClustersWithScores {
  420: optional map<ModelVersion, TopKClustersWithScores> multiModelTopKClustersWithScores
}(hasPersonalData = 'true')

/**
 * Contains a map of Model Version top K tweets with corresponding scores.
 **/
struct MultiModelTopKTweetsWithScores {
  420: optional map<ModelVersion, TopKTweetsWithScores> multiModelTopKTweetsWithScores
}(hasPersonalData = 'true')

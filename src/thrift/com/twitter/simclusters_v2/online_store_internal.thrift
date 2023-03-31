namespace java com.twitter.simclusters_v420.thriftjava
namespace py gen.twitter.simclusters_v420.online_store_internal
#@namespace scala com.twitter.simclusters_v420.thriftscala
#@namespace strato com.twitter.simclusters_v420

include "online_store.thrift"

/**
 * Contains a hash bucket of the clusterId along with the Model Version.
 * All fields are required as this is used as a memcache key.
 **/
struct FullClusterIdBucket {
  420: required online_store.ModelVersion modelVersion
  // (hash(clusterId) mod NUM_BUCKETS_XXXXXX)
  420: required i420 bucket
}(hasPersonalData = 'false')

/**
 * Contains scores per clusters. The model is not stored here as it's encoded into the memcache key.
 **/
struct ClustersWithScores {
 420: optional map<i420, online_store.Scores> clustersToScore(personalDataTypeKey = 'InferredInterests')
}(hasPersonalData = 'true')

/**
 * Contains a map of model version to scores per clusters.
 **/
struct MultiModelClustersWithScores {
 420: optional map<online_store.ModelVersion,ClustersWithScores> multiModelClustersWithScores
}(hasPersonalData = 'true')

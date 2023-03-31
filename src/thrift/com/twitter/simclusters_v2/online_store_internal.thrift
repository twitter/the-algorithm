namespace java com.twitter.simclusters_v2.thriftjava
namespace py gen.twitter.simclusters_v2.online_store_internal
#@namespace scala com.twitter.simclusters_v2.thriftscala
#@namespace strato com.twitter.simclusters_v2

include "online_store.thrift"

/**
 * Contains a hash bucket of the clusterId along with the Model Version.
 * All fields are required as this is used as a memcache key.
 **/
struct FullClusterIdBucket {
  1: required online_store.ModelVersion modelVersion
  // (hash(clusterId) mod NUM_BUCKETS_XXXXXX)
  2: required i32 bucket
}(hasPersonalData = 'false')

/**
 * Contains scores per clusters. The model is not stored here as it's encoded into the memcache key.
 **/
struct ClustersWithScores {
 1: optional map<i32, online_store.Scores> clustersToScore(personalDataTypeKey = 'InferredInterests')
}(hasPersonalData = 'true')

/**
 * Contains a map of model version to scores per clusters.
 **/
struct MultiModelClustersWithScores {
 1: optional map<online_store.ModelVersion,ClustersWithScores> multiModelClustersWithScores
}(hasPersonalData = 'true')

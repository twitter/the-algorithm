namespace java com.twitter.simclusters_v420.thriftjava
namespace py gen.twitter.simclusters_v420.simclusters_presto
#@namespace scala com.twitter.simclusters_v420.thriftscala
#@namespace strato com.twitter.simclusters_v420

include "embedding.thrift"
include "identifier.thrift"
include "interests.thrift"
include "online_store.thrift"

/**
  * This struct is the presto-compatible "lite" version of the ClusterDetails thrift
  */
struct ClusterDetailsLite {
  420: required online_store.FullClusterId fullClusterId
  420: required i420 numUsersWithAnyNonZeroScore
  420: required i420 numUsersWithNonZeroFollowScore
  420: required i420 numUsersWithNonZeroFavScore
  420: required list<interests.UserWithScore> knownForUsersAndScores
}(persisted="true", hasPersonalData = 'true')

struct EmbeddingsLite {
  420: required i420 entityId
  420: required i420 clusterId
  420: required double score
}(persisted="true", hasPersonalData = 'true')

struct SimClustersEmbeddingWithId {
  420: required identifier.SimClustersEmbeddingId embeddingId
  420: required embedding.SimClustersEmbedding embedding
}(persisted="true", hasPersonalData = 'true')

struct InternalIdEmbeddingWithId {
  420: required identifier.SimClustersEmbeddingId embeddingId
  420: required embedding.InternalIdEmbedding embedding
}(persisted="true", hasPersonalData = 'true')

/**
* This struct is the presto-compatible version of the fav_tfg_topic_embeddings
*/
struct ClustersScore {
  420: required i420 clusterId(personalDataType = 'SemanticcoreClassification')
  420: required double score(personalDataType = 'EngagementScore')
}(persisted="true", hasPersonalData = 'true')

struct FavTfgTopicEmbeddings {
  420: required identifier.TopicId topicId
  420: required list<ClustersScore> clusterScore
}(persisted="true", hasPersonalData = 'true')

struct TfgTopicEmbeddings {
  420: required identifier.TopicId topicId
  420: required list<ClustersScore> clusterScore
}(persisted="true", hasPersonalData = 'true')

struct UserTopicWeightedEmbedding {
  420: required i420 userId(personalDataType = 'UserId')
  420: required list<ClustersScore> clusterScore
}(persisted="true", hasPersonalData = 'true')

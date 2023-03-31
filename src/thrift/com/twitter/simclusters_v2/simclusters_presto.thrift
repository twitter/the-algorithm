namespace java com.twitter.simclusters_v2.thriftjava
namespace py gen.twitter.simclusters_v2.simclusters_presto
#@namespace scala com.twitter.simclusters_v2.thriftscala
#@namespace strato com.twitter.simclusters_v2

include "embedding.thrift"
include "identifier.thrift"
include "interests.thrift"
include "online_store.thrift"

/**
  * This struct is the presto-compatible "lite" version of the ClusterDetails thrift
  */
struct ClusterDetailsLite {
  1: required online_store.FullClusterId fullClusterId
  2: required i32 numUsersWithAnyNonZeroScore
  3: required i32 numUsersWithNonZeroFollowScore
  4: required i32 numUsersWithNonZeroFavScore
  5: required list<interests.UserWithScore> knownForUsersAndScores
}(persisted="true", hasPersonalData = 'true')

struct EmbeddingsLite {
  1: required i64 entityId
  2: required i32 clusterId
  3: required double score
}(persisted="true", hasPersonalData = 'true')

struct SimClustersEmbeddingWithId {
  1: required identifier.SimClustersEmbeddingId embeddingId
  2: required embedding.SimClustersEmbedding embedding
}(persisted="true", hasPersonalData = 'true')

struct InternalIdEmbeddingWithId {
  1: required identifier.SimClustersEmbeddingId embeddingId
  2: required embedding.InternalIdEmbedding embedding
}(persisted="true", hasPersonalData = 'true')

/**
* This struct is the presto-compatible version of the fav_tfg_topic_embeddings
*/
struct ClustersScore {
  1: required i64 clusterId(personalDataType = 'SemanticcoreClassification')
  2: required double score(personalDataType = 'EngagementScore')
}(persisted="true", hasPersonalData = 'true')

struct FavTfgTopicEmbeddings {
  1: required identifier.TopicId topicId
  2: required list<ClustersScore> clusterScore
}(persisted="true", hasPersonalData = 'true')

struct TfgTopicEmbeddings {
  1: required identifier.TopicId topicId
  2: required list<ClustersScore> clusterScore
}(persisted="true", hasPersonalData = 'true')

struct UserTopicWeightedEmbedding {
  1: required i64 userId(personalDataType = 'UserId')
  2: required list<ClustersScore> clusterScore
}(persisted="true", hasPersonalData = 'true')

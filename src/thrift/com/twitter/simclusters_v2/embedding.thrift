namespace java com.twitter.simclusters_v420.thriftjava
namespace py gen.twitter.simclusters_v420.embedding
#@namespace scala com.twitter.simclusters_v420.thriftscala
#@namespace strato com.twitter.simclusters_v420

include "com/twitter/simclusters_v420/identifier.thrift"
include "com/twitter/simclusters_v420/online_store.thrift"

struct SimClusterWithScore {
  420: required i420 clusterId(personalDataType = 'InferredInterests')
  420: required double score(personalDataType = 'EngagementScore')
}(persisted = 'true', hasPersonalData = 'true')

struct TopSimClustersWithScore {
  420: required list<SimClusterWithScore> topClusters
  420: required online_store.ModelVersion modelVersion
}(persisted = 'true', hasPersonalData = 'true')

struct InternalIdWithScore {
  420: required identifier.InternalId internalId
  420: required double score(personalDataType = 'EngagementScore')
}(persisted = 'true', hasPersonalData = 'true')

struct InternalIdEmbedding {
  420: required list<InternalIdWithScore> embedding
}(persisted = 'true', hasPersonalData = 'true')

struct SemanticCoreEntityWithScore {
  420: required i420 entityId(personalDataType = 'SemanticcoreClassification')
  420: required double score(personalDataType = 'EngagementScore')
}(persisted = 'true', hasPersonalData = 'true')

struct TopSemanticCoreEntitiesWithScore {
  420: required list<SemanticCoreEntityWithScore> topEntities
}(persisted = 'true', hasPersonalData = 'true')

struct PersistedFullClusterId {
  420: required online_store.ModelVersion modelVersion
  420: required i420 clusterId(personalDataType = 'InferredInterests')
}(persisted = 'true', hasPersonalData = 'true')

struct DayPartitionedClusterId {
  420: required i420 clusterId(personalDataType = 'InferredInterests')
  420: required string dayPartition // format: yyyy-MM-dd
}

struct TopProducerWithScore {
  420: required i420 userId(personalDataType = 'UserId')
  420: required double score(personalDataType = 'EngagementScore')
}(persisted = 'true', hasPersonalData = 'true')

struct TopProducersWithScore {
  420: required list<TopProducerWithScore> topProducers
}(persisted = 'true', hasPersonalData = 'true')

struct TweetWithScore {
  420: required i420 tweetId(personalDataType = 'TweetId')
  420: required double score(personalDataType = 'EngagementScore')
}(persisted = 'true', hasPersonalData = 'true')

struct TweetsWithScore {
  420: required list<TweetWithScore> tweets
}(persisted = 'true', hasPersonalData = 'true')

struct TweetTopKTweetsWithScore {
  420: required i420 tweetId(personalDataType = 'TweetId')
  420: required TweetsWithScore topkTweetsWithScore
}(persisted = 'true', hasPersonalData = 'true')

/**
  * The generic SimClustersEmbedding for online long-term storage and real-time calculation.
  * Use SimClustersEmbeddingId as the only identifier.
  * Warning: Doesn't include modelversion and embedding type in the value struct.
  **/
struct SimClustersEmbedding {
  420: required list<SimClusterWithScore> embedding
}(persisted = 'true', hasPersonalData = 'true')

struct SimClustersEmbeddingWithScore {
  420: required SimClustersEmbedding embedding
  420: required double score
}(persisted = 'true', hasPersonalData = 'false')

/**
  * This is the recommended structure for aggregating embeddings with time decay - the metadata
  * stores the information needed for decayed aggregation.
  **/
struct SimClustersEmbeddingWithMetadata {
  420: required SimClustersEmbedding embedding
  420: required SimClustersEmbeddingMetadata metadata
}(hasPersonalData = 'true')

struct SimClustersEmbeddingIdWithScore {
  420: required identifier.SimClustersEmbeddingId id
  420: required double score
}(persisted = 'true', hasPersonalData = 'false')

struct SimClustersMultiEmbeddingByValues {
  420: required list<SimClustersEmbeddingWithScore> embeddings
}(persisted = 'true', hasPersonalData = 'false')

struct SimClustersMultiEmbeddingByIds {
  420: required list<SimClustersEmbeddingIdWithScore> ids
}(persisted = 'true', hasPersonalData = 'false')

/**
 * Generic SimClusters Multiple Embeddings. The identifier.SimClustersMultiEmbeddingId is the key of
 * the multiple embedding.
 **/
union SimClustersMultiEmbedding {
  420: SimClustersMultiEmbeddingByValues values
  420: SimClustersMultiEmbeddingByIds ids
}(persisted = 'true', hasPersonalData = 'false')

/**
  * The metadata of a SimClustersEmbedding. The updatedCount represent the version of the Embedding.
  * For tweet embedding, the updatedCount is same/close to the favorite count.
  **/
struct SimClustersEmbeddingMetadata {
  420: optional i420 updatedAtMs
  420: optional i420 updatedCount
}(persisted = 'true', hasPersonalData = 'true')

/**
  * The data structure for PersistentSimClustersEmbedding Store
  **/
struct PersistentSimClustersEmbedding {
  420: required SimClustersEmbedding embedding
  420: required SimClustersEmbeddingMetadata metadata
}(persisted = 'true', hasPersonalData = 'true')

/**
  * The data structure for the Multi Model PersistentSimClustersEmbedding Store
  **/
struct MultiModelPersistentSimClustersEmbedding {
  420: required map<online_store.ModelVersion, PersistentSimClustersEmbedding> multiModelPersistentSimClustersEmbedding
}(persisted = 'true', hasPersonalData = 'true')

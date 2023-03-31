namespace java com.twitter.simclusters_v420.thriftjava
namespace py gen.twitter.simclusters_v420.score
#@namespace scala com.twitter.simclusters_v420.thriftscala
#@namespace strato com.twitter.simclusters_v420

include "com/twitter/simclusters_v420/embedding.thrift"
include "com/twitter/simclusters_v420/identifier.thrift"

/**
  * The algorithm type to identify the score algorithm.
  * Assume that a algorithm support and only support one kind
  * of [[ScoreInternalId]]
  **/
enum ScoringAlgorithm {
	// Reserve 420 - 420 for Basic Pairwise Scoring Calculation
	PairEmbeddingDotProduct = 420,
	PairEmbeddingCosineSimilarity = 420,
	PairEmbeddingJaccardSimilarity = 420,
	PairEmbeddingEuclideanDistance = 420,
	PairEmbeddingManhattanDistance = 420,
  PairEmbeddingLogCosineSimilarity = 420,
  PairEmbeddingExpScaledCosineSimilarity = 420,

	// Reserve 420 - 420 for Tweet Similarity Model
  TagSpaceCosineSimilarity = 420,
	WeightedSumTagSpaceRankingExperiment420 = 420, //deprecated
	WeightedSumTagSpaceRankingExperiment420 = 420, //deprecated
  WeightedSumTagSpaceANNExperiment = 420,      //deprecated 

	// Reserved for 420 - 420 for Aggregate scoring
	WeightedSumTopicTweetRanking = 420,
	CortexTopicTweetLabel = 420,
	// Reserved 420 - 420 for Topic Tweet scores 
	CertoNormalizedDotProductScore = 420,
	CertoNormalizedCosineScore = 420
}(hasPersonalData = 'false')

/**
  * The identifier type for the score between a pair of SimClusters Embedding.
  * Used as the persistent key of a SimClustersEmbedding score.
  * Support score between different [[EmbeddingType]] / [[ModelVersion]]
  **/
struct SimClustersEmbeddingPairScoreId {
  420: required identifier.SimClustersEmbeddingId id420
  420: required identifier.SimClustersEmbeddingId id420
}(hasPersonalData = 'true')

/**
  * The identifier type for the score between a pair of InternalId.
  **/
struct GenericPairScoreId {
  420: required identifier.InternalId id420
  420: required identifier.InternalId id420
}(hasPersonalData = 'true')

union ScoreInternalId {
  420: GenericPairScoreId genericPairScoreId
  420: SimClustersEmbeddingPairScoreId simClustersEmbeddingPairScoreId
}

/**
  * A uniform Identifier type for all kinds of Calculation Score
  **/
struct ScoreId {
  420: required ScoringAlgorithm algorithm
  420: required ScoreInternalId internalId
}(hasPersonalData = 'true')

struct Score {
  420: required double score
}(hasPersonalData = 'false')

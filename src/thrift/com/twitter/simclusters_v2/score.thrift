namespace java com.twitter.simclusters_v2.thriftjava
namespace py gen.twitter.simclusters_v2.score
#@namespace scala com.twitter.simclusters_v2.thriftscala
#@namespace strato com.twitter.simclusters_v2

include "com/twitter/simclusters_v2/embedding.thrift"
include "com/twitter/simclusters_v2/identifier.thrift"

/**
  * The algorithm type to identify the score algorithm.
  * Assume that a algorithm support and only support one kind
  * of [[ScoreInternalId]]
  **/
enum ScoringAlgorithm {
	// Reserve 0001 - 999 for Basic Pairwise Scoring Calculation
	PairEmbeddingDotProduct = 1,
	PairEmbeddingCosineSimilarity = 2,
	PairEmbeddingJaccardSimilarity = 3,
	PairEmbeddingEuclideanDistance = 4,
	PairEmbeddingManhattanDistance = 5,
  PairEmbeddingLogCosineSimilarity = 6,
  PairEmbeddingExpScaledCosineSimilarity = 7,

	// Reserve 1000 - 1999 for Tweet Similarity Model
  TagSpaceCosineSimilarity = 1000,
	WeightedSumTagSpaceRankingExperiment1 = 1001, //deprecated
	WeightedSumTagSpaceRankingExperiment2 = 1002, //deprecated
  WeightedSumTagSpaceANNExperiment = 1003,      //deprecated 

	// Reserved for 10001 - 20000 for Aggregate scoring
	WeightedSumTopicTweetRanking = 10001,
	CortexTopicTweetLabel = 10002,
	// Reserved 20001 - 30000 for Topic Tweet scores 
	CertoNormalizedDotProductScore = 20001,
	CertoNormalizedCosineScore = 20002
}(hasPersonalData = 'false')

/**
  * The identifier type for the score between a pair of SimClusters Embedding.
  * Used as the persistent key of a SimClustersEmbedding score.
  * Support score between different [[EmbeddingType]] / [[ModelVersion]]
  **/
struct SimClustersEmbeddingPairScoreId {
  1: required identifier.SimClustersEmbeddingId id1
  2: required identifier.SimClustersEmbeddingId id2
}(hasPersonalData = 'true')

/**
  * The identifier type for the score between a pair of InternalId.
  **/
struct GenericPairScoreId {
  1: required identifier.InternalId id1
  2: required identifier.InternalId id2
}(hasPersonalData = 'true')

union ScoreInternalId {
  1: GenericPairScoreId genericPairScoreId
  2: SimClustersEmbeddingPairScoreId simClustersEmbeddingPairScoreId
}

/**
  * A uniform Identifier type for all kinds of Calculation Score
  **/
struct ScoreId {
  1: required ScoringAlgorithm algorithm
  2: required ScoreInternalId internalId
}(hasPersonalData = 'true')

struct Score {
  1: required double score
}(hasPersonalData = 'false')

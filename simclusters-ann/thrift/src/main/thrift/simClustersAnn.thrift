namespace java com.twitter.simclustersann.thriftjava
#@namespace scala com.twitter.simclustersann.thriftscala

include "finatra-thrift/finatra_thrift_etwitterceptions.thrift"
include "com/twitter/simclusters_v2/identifier.thrift"
include "com/twitter/simclusters_v2/score.thrift"

struct Query {
    1: required identifier.SimClustersEmbeddingId sourceEmbeddingId;
    2: required SimClustersANNConfig config;
}

struct SimClustersANNTweetCandidate {
    1: required i64 tweetId (personalDataType = 'TweetId');
    2: required double score;
}

struct SimClustersANNConfig {
    1: required i32 matwitterNumResults;
    2: required double minScore;
    3: required identifier.EmbeddingType candidateEmbeddingType;
    4: required i32 matwitterTopTweetsPerCluster;
    5: required i32 matwitterScanClusters;
    6: required i32 matwitterTweetCandidateAgeHours;
    7: required i32 minTweetCandidateAgeHours;
    8: required ScoringAlgorithm annAlgorithm;
}

/**
  * The algorithm type to identify the score algorithm.
  **/
enum ScoringAlgorithm {
	DotProduct = 1,
	CosineSimilarity = 2,
  LogCosineSimilarity = 3,
  CosineSimilarityNoSourceEmbeddingNormalization = 4,  // Score = (Source dot Candidate) / candidate_l2_norm
}(hasPersonalData = 'false')

enum InvalidResponseParameter {
	INVALID_EMBEDDING_TYPE = 1,
	INVALID_MODEL_VERSION = 2,
}

etwitterception InvalidResponseParameterEtwitterception {
	1: required InvalidResponseParameter errorCode,
	2: optional string message // failure reason
}

service SimClustersANNService {

    list<SimClustersANNTweetCandidate> getTweetCandidates(
        1: required Query query;
    ) throws (
      1: InvalidResponseParameterEtwitterception e;
      2: finatra_thrift_etwitterceptions.ServerError serverError;
      3: finatra_thrift_etwitterceptions.ClientError clientError;
    );

}

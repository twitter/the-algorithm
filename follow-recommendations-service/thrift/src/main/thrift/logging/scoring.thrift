namespace java com.twitter.follow_recommendations.logging.thriftjava
#@namespace scala com.twitter.follow_recommendations.logging.thriftscala
#@namespace strato com.twitter.follow_recommendations.logging

include "com/twitter/ml/api/data.thrift"

struct CandidateSourceDetails {
  1: optional map<string, double> candidateSourceScores
  2: optional i32 primarySource
}(persisted='true', hasPersonalData='false')

struct Score {
  1: required double value
  2: optional string rankerId
  3: optional string scoreType
}(persisted='true', hasPersonalData='false') // scoring and ranking info per ranking stage

// Contains (1) the ML-based heavy ranker and score (2) scores and rankers in producer experiment framework
struct Scores {
  1: required list<Score> scores
  2: optional string selectedRankerId
  3: required bool isInProducerScoringExperiment
}(persisted='true', hasPersonalData='false')

struct RankingInfo {
  1: optional Scores scores
  2: optional i32 rank
}(persisted='true', hasPersonalData='false')

// this encapsulates all information related to the ranking process from generation to scoring
struct ScoringDetails {
    1: optional CandidateSourceDetails candidateSourceDetails
    2: optional double score  // The ML-based heavy ranker score
    3: optional data.DataRecord dataRecord
    4: optional list<string> rankerIds  // all ranker ids, including (1) ML-based heavy ranker (2) non-ML adhoc rankers
    5: optional map<string, RankingInfo> infoPerRankingStage  // scoring and ranking info per ranking stage
}(persisted='true', hasPersonalData='true')


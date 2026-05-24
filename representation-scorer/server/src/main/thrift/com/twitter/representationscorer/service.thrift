namespace java com.twitter.representationscorer.thriftjava
#@namespace scala com.twitter.representationscorer.thriftscala
#@namespace strato com.twitter.representationscorer

include "com/twitter/simclusters_v2/identifier.thrift"
include "com/twitter/simclusters_v2/online_store.thrift"
include "com/twitter/simclusters_v2/score.thrift"

struct SimClustersRecentEngagementSimilarities {
  // All scores computed using cosine similarity
  // 1 - 1000 Positive Signals
  1: optional double fav1dLast10Max // max score from last 10 faves in the last 1 day
  2: optional double fav1dLast10Avg // avg score from last 10 faves in the last 1 day
  3: optional double fav7dLast10Max // max score from last 10 faves in the last 7 days
  4: optional double fav7dLast10Avg // avg score from last 10 faves in the last 7 days
  5: optional double retweet1dLast10Max // max score from last 10 retweets in the last 1 days
  6: optional double retweet1dLast10Avg // avg score from last 10 retweets in the last 1 days
  7: optional double retweet7dLast10Max // max score from last 10 retweets in the last 7 days
  8: optional double retweet7dLast10Avg // avg score from last 10 retweets in the last 7 days
  9: optional double follow7dLast10Max // max score from the last 10 follows in the last 7 days
  10: optional double follow7dLast10Avg // avg score from the last 10 follows in the last 7 days
  11: optional double follow30dLast10Max // max score from the last 10 follows in the last 30 days
  12: optional double follow30dLast10Avg // avg score from the last 10 follows in the last 30 days
  13: optional double share1dLast10Max // max score from last 10 shares in the last 1 day
  14: optional double share1dLast10Avg // avg score from last 10 shares in the last 1 day
  15: optional double share7dLast10Max // max score from last 10 shares in the last 7 days
  16: optional double share7dLast10Avg // avg score from last 10 shares in the last 7 days
  17: optional double reply1dLast10Max // max score from last 10 replies in the last 1 day
  18: optional double reply1dLast10Avg // avg score from last 10 replies in the last 1 day
  19: optional double reply7dLast10Max // max score from last 10 replies in the last 7 days
  20: optional double reply7dLast10Avg // avg score from last 10 replies in the last 7 days
  21: optional double originalTweet1dLast10Max // max score from last 10 original tweets in the last 1 day
  22: optional double originalTweet1dLast10Avg // avg score from last 10 original tweets in the last 1 day
  23: optional double originalTweet7dLast10Max // max score from last 10 original tweets in the last 7 days
  24: optional double originalTweet7dLast10Avg // avg score from last 10 original tweets in the last 7 days
  25: optional double videoPlayback1dLast10Max // max score from last 10 video playback50 in the last 1 day
  26: optional double videoPlayback1dLast10Avg // avg score from last 10 video playback50 in the last 1 day
  27: optional double videoPlayback7dLast10Max // max score from last 10 video playback50 in the last 7 days
  28: optional double videoPlayback7dLast10Avg // avg score from last 10 video playback50 in the last 7 days

  // 1001 - 2000 Implicit Signals

  // 2001 - 3000 Negative Signals
  // Block Series
  2001: optional double block1dLast10Avg
  2002: optional double block1dLast10Max
  2003: optional double block7dLast10Avg
  2004: optional double block7dLast10Max
  2005: optional double block30dLast10Avg
  2006: optional double block30dLast10Max
  // Mute Series
  2101: optional double mute1dLast10Avg
  2102: optional double mute1dLast10Max
  2103: optional double mute7dLast10Avg
  2104: optional double mute7dLast10Max
  2105: optional double mute30dLast10Avg
  2106: optional double mute30dLast10Max
  // Report Series
  2201: optional double report1dLast10Avg
  2202: optional double report1dLast10Max
  2203: optional double report7dLast10Avg
  2204: optional double report7dLast10Max
  2205: optional double report30dLast10Avg
  2206: optional double report30dLast10Max
  // Dontlike
  2301: optional double dontlike1dLast10Avg
  2302: optional double dontlike1dLast10Max
  2303: optional double dontlike7dLast10Avg
  2304: optional double dontlike7dLast10Max
  2305: optional double dontlike30dLast10Avg
  2306: optional double dontlike30dLast10Max
  // SeeFewer
  2401: optional double seeFewer1dLast10Avg
  2402: optional double seeFewer1dLast10Max
  2403: optional double seeFewer7dLast10Avg
  2404: optional double seeFewer7dLast10Max
  2405: optional double seeFewer30dLast10Avg
  2406: optional double seeFewer30dLast10Max
}(persisted='true', hasPersonalData = 'true')

/*
 * List score API
 */
struct ListScoreId {
  1: required score.ScoringAlgorithm algorithm
  2: required online_store.ModelVersion modelVersion
  3: required identifier.EmbeddingType targetEmbeddingType
  4: required identifier.InternalId targetId
  5: required identifier.EmbeddingType candidateEmbeddingType
  6: required list<identifier.InternalId> candidateIds
}(hasPersonalData = 'true')

struct ScoreResult {
  // This api does not communicate why a score is missing. For example, it may be unavailable
  // because the referenced entities do not exist (e.g. the embedding was not found) or because
  // timeouts prevented us from calculating it.
  1: optional double score
}

struct ListScoreResponse {
  1: required list<ScoreResult> scores // Guaranteed to be the same number/order as requested
}

struct RecentEngagementSimilaritiesResponse {
  1: required list<SimClustersRecentEngagementSimilarities> results // Guaranteed to be the same number/order as requested
}

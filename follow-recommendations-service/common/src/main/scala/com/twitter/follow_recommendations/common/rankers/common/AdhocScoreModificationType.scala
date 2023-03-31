package com.twitter.follow_recommendations.common.rankers.common

/**
 * To manage the extent of adhoc score modifications, we set a hard limit that from each of the
 * types below *ONLY ONE* adhoc scorer can be applied to candidates' scores. More details about the
 * usage is available in [[AdhocRanker]]
 */

object AdhocScoreModificationType extends Enumeration {
  type AdhocScoreModificationType = Value

  // This type of scorer increases the score of a subset of candidates through various policies.
  val BoostingScorer: AdhocScoreModificationType = Value("boosting")

  // This type of scorer shuffles candidates randomly according to some distribution.
  val WeightedRandomSamplingScorer: AdhocScoreModificationType = Value("weighted_random_sampling")

  // This is added solely for testing purposes and should not be used in production.
  val InvalidAdhocScorer: AdhocScoreModificationType = Value("invalid_adhoc_scorer")
}

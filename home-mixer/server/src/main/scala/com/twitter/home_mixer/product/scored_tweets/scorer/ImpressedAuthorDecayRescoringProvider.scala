package com.twitter.home_mixer.product.scored_tweets.scorer

import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedAuthorIdsFeature
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableImpressionBasedAuthorDecay
import com.twitter.product_mixer.component_library.feature_hydrator.query.impressed_tweets.ImpressedTweets
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.pipeline.PipelineQuery

object ImpressedAuthorDecayRescoringProvider {

  private def calculateAuthorImpressionFrequencies(query: PipelineQuery): Map[Long, Int] = {
    val impressedTweetIds =
      query.features.map(_.getOrElse(ImpressedTweets, Seq.empty)).getOrElse(Seq.empty).toSet

    val servedAuthorMap = query.features.map(_.get(ServedAuthorIdsFeature)).getOrElse(Map.empty)

    servedAuthorMap
      .map {
        case (authorId, tweetIds) =>
          val impressedCount = tweetIds.count(impressedTweetIds.contains)
          authorId -> impressedCount
      }
      .filter(_._2 > 0) // Only include authors with at least one impressed tweet
  }

  private def groupByKey(candidate: CandidateWithFeatures[TweetCandidate]): Option[Long] =
    candidate.features.getOrElse(AuthorIdFeature, None)

  private def onlyIf(query: PipelineQuery): Boolean = query.params(EnableImpressionBasedAuthorDecay)

  def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Map[Long, Double] = {
    if (onlyIf(query)) {
      val outNetworkDecayFactor =
        query.params(ScoredTweetsParam.AuthorDiversityOutNetworkDecayFactor)
      val outNetworkFloor = query.params(ScoredTweetsParam.AuthorDiversityOutNetworkFloor)

      val inNetworkDecayFactor = query.params(ScoredTweetsParam.AuthorDiversityInNetworkDecayFactor)
      val inNetworkFloor = query.params(ScoredTweetsParam.AuthorDiversityInNetworkFloor)

      val authorFreq = calculateAuthorImpressionFrequencies(query)

      candidates
        .groupBy(groupByKey)
        .flatMap {
          case (Some(authorId), groupedCandidates) =>
            val sortedCandidates = groupedCandidates
              .sortBy(_.features.getOrElse(ScoreFeature, None).getOrElse(0.0))(
                Ordering.Double.reverse)

            sortedCandidates.zipWithIndex.map {
              case (candidate, index) =>
                candidate.candidate.id -> {
                  val effectiveIndex = index + authorFreq.getOrElse(authorId, 0)
                  val isInNetworkCandidate = candidate.features.getOrElse(InNetworkFeature, true)
                  val decayFactor =
                    if (isInNetworkCandidate) inNetworkDecayFactor else outNetworkDecayFactor
                  val floor = if (isInNetworkCandidate) inNetworkFloor else outNetworkFloor
                  authorDiversityBasedRescorer(effectiveIndex, decayFactor, floor)
                }
            }

          case _ => Map.empty
        }
    } else Map.empty
  }

  /**
   * Re-scoring multiplier to apply to multiple tweets from the same author.
   * Provides an exponential decay based discount by position (with a floor).
   */
  private def authorDiversityBasedRescorer(
    index: Int,
    decayFactor: Double,
    floor: Double
  ): Double = (1 - floor) * Math.pow(decayFactor, index) + floor

}

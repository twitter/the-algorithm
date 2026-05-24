package com.twitter.home_mixer.product.scored_tweets.scorer

import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam
import com.twitter.product_mixer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.pipeline.PipelineQuery

object AuthorBasedListwiseRescoringProvider
    extends ListwiseRescoringProvider[CandidateWithFeatures[TweetCandidate], Long] {
  private val MinFollowed = 50

  /**
   * Author-based, the groupBy key is the author id.
   * Rescore the list of candidates that share the same author id.
   */
  override def groupByKey(candidate: CandidateWithFeatures[TweetCandidate]): Option[Long] =
    candidate.features.getOrElse(AuthorIdFeature, None)

  /**
   * Defines the list of author-based candidate rescorers.
   * Multiply the rescorers together for each candidate.
   */
  override def candidateRescoringFactor(
    query: PipelineQuery,
    candidate: CandidateWithFeatures[TweetCandidate],
    index: Int
  ): Double = {
    val isSmallFollowGraph =
      query.features.get.getOrElse(SGSFollowedUsersFeature, Seq.empty).size <= MinFollowed

    val decayFactor = if (isSmallFollowGraph) {
      query.params(ScoredTweetsParam.SmallFollowGraphAuthorDiversityDecayFactor)
    } else {
      query.params(ScoredTweetsParam.AuthorDiversityDecayFactor)
    }

    val floor =
      if (isSmallFollowGraph) query.params(ScoredTweetsParam.SmallFollowGraphAuthorDiversityFloor)
      else query.params(ScoredTweetsParam.AuthorDiversityFloor)

    authorDiversityBasedRescorer(index = index, decayFactor = decayFactor, floor = floor)
  }

  /**
   * Re-scoring multiplier to apply to multiple tweets from the same author.
   * Provides an exponential decay based discount by position (with a floor).
   */
  def authorDiversityBasedRescorer(
    index: Int,
    decayFactor: Double,
    floor: Double
  ): Double = (1 - floor) * Math.pow(decayFactor, index) + floor
}

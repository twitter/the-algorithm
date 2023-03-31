package com.twitter.home_mixer.product.scored_tweets.scorer

import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures

trait DiversityDiscountProvider {

  def entityId(candidate: CandidateWithFeatures[TweetCandidate]): Option[Long]

  /**
   * Compute the discounted score for the position
   * @param score the previous score for the candidate
   * @param position zero-based position for the candidate for the given entity
   * @return the discounted score for the candidate
   */
  def discount(score: Double, position: Int): Double
}

object AuthorDiversityDiscountProvider extends DiversityDiscountProvider {
  private val Decay = 0.5
  private val Floor = 0.25

  override def entityId(candidate: CandidateWithFeatures[TweetCandidate]): Option[Long] =
    candidate.features.getOrElse(AuthorIdFeature, None)

  // Provides an exponential decay based discount by position (with a floor)
  override def discount(score: Double, position: Int): Double =
    score * ((1 - Floor) * Math.pow(Decay, position) + Floor)
}

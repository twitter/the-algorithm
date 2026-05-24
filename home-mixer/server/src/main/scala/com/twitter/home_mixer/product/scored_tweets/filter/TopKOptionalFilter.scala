package com.twitter.home_mixer.product.scored_tweets.filter

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * This filter ranks tweets by a score feature and takes top k
 */
case class TopKOptionalFilter(scoreFeature: Feature[_, Option[Double]], topK: Int)
    extends Filter[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("TopKOptional")

  private val DefaultScore = 0D

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val sorted = candidates
      .sortBy(_.features.getOrElse(scoreFeature, None).getOrElse(DefaultScore))(
        Ordering[Double].reverse)
    val (kept, removed) = sorted.splitAt(topK)
    Stitch.value(FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate)))
  }
}

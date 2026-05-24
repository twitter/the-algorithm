package com.twitter.home_mixer.product.scored_tweets.filter

import com.twitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mixer.model.HomeFeatures.IsColdStartPostFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

object IsOutOfNetworkColdStartPostFilter extends Filter[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("IsOutOfNetworkColdStartPost")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val (removed, kept) = candidates.partition { candidate =>
      val isColdStartPost =
        candidate.features.getOrElse(
          IsColdStartPostFeature,
          false
        ) // Id none, assume its not cold start, don't filter out
      val isOutOfNetwork =
        !candidate.features
          .getOrElse(InNetworkFeature, true) // If none, assume its in-network, don't filter out
      isColdStartPost && isOutOfNetwork
    }
    Stitch.value(FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate)))
  }
}

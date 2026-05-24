package com.twitter.home_mixer.product.scored_tweets.filter

import com.twitter.home_mixer.model.HomeFeatures.EarlybirdScoreFeature
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FetchParams
import com.twitter.home_mixer.product.scored_tweets.response_transformer.UtegScoreFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

object UtegTopKFilter extends Filter[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("UtegTopK")

  private val DefaultScore = 0D

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val topK = query.params(FetchParams.UTEGMaxTweetsToFetchParam)
    val sorted = candidates.sortBy { candidate =>
      val utegScore = candidate.features.getOrElse(UtegScoreFeature, DefaultScore)
      val ebScore = candidate.features
        .getOrElse(EarlybirdScoreFeature, None)
        .getOrElse(DefaultScore)
      utegScore + ebScore * 2
    }(Ordering[Double].reverse)
    val (kept, removed) = sorted.splitAt(topK)
    Stitch.value(FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate)))
  }
}

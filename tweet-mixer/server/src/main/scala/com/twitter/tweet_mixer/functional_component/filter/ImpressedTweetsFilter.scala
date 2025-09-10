package com.twitter.tweet_mixer.functional_component.filter

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.model.marshalling.request.HasExcludedIds
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.feature._

/**
 * Applies impressed tweets filter using USS signals and excluded TweetIds
 */
object ImpressedTweetsFilter extends Filter[PipelineQuery with HasExcludedIds, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("ImpressedTweets")

  override def apply(
    query: PipelineQuery with HasExcludedIds,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val tweetSignals = USSFeatures.getSignals[Long](query, USSFeatures.TweetFeatures)
    val impressedTweets = query.excludedIds ++ tweetSignals.toSet
    val (kept, removed) =
      candidates.partition { candidate =>
        val tweetId = candidate.candidate.id
        val sourceTweetId =
          candidate.features.getOrElse(SourceTweetIdFeature, None).getOrElse(tweetId)
        !impressedTweets.contains(tweetId) && !impressedTweets.contains(sourceTweetId)
      }

    val filterResult =
      FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate))
    Stitch.value(filterResult)
  }
}

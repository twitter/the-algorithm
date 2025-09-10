package com.twitter.tweet_mixer.functional_component.filter

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelines.impressionstore.impressionbloomfilter.ImpressionBloomFilter
import com.twitter.tweet_mixer.feature.SourceTweetIdFeature
import com.twitter.tweet_mixer.feature.USSFeatures
import com.twitter.tweet_mixer.functional_component.hydrator.ImpressionBloomFilterFeature
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableImpressionBloomFilterHydrator

/**
 * Filter out users' previously seen tweets from Bloom Filter store
 */
object ImpressedTweetsBloomFilter
    extends Filter[PipelineQuery, TweetCandidate]
    with Filter.Conditionally[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("ImpressedTweetsBloom")

  override def onlyIf(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Boolean = {
    query.params(EnableImpressionBloomFilterHydrator)
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val bloomFilterSeq = query.features.map(_.get(ImpressionBloomFilterFeature)).get
    val tweetSignals = USSFeatures.getSignals[Long](query, USSFeatures.TweetFeatures)
    val tweetIdAndSourceIds = candidates.flatMap { candidate =>
      val tweetId = candidate.candidate.id
      val sourceTweetId =
        candidate.features.getOrElse(SourceTweetIdFeature, None).getOrElse(tweetId)
      Seq(tweetId, sourceTweetId)
    }.distinct
    val seenTweetIds =
      (ImpressionBloomFilter.extractSeenTweetIds(
        tweetIdAndSourceIds,
        bloomFilterSeq) ++ tweetSignals).toSet

    val (kept, removed) = candidates.partition { candidate =>
      val tweetId = candidate.candidate.id
      val sourceTweetId =
        candidate.features.getOrElse(SourceTweetIdFeature, None).getOrElse(tweetId)
      !seenTweetIds.contains(tweetId) && !seenTweetIds.contains(sourceTweetId)
    }

    Stitch.value(FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate)))
  }
}

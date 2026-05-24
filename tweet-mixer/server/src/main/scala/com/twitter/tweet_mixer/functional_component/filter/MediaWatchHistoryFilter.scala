package com.twitter.tweet_mixer.functional_component.filter

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelines.impressionstore.impressionbloomfilter.ImpressionBloomFilterItem
import com.twitter.tweet_mixer.feature.MediaIdFeature
import com.twitter.tweet_mixer.functional_component.hydrator.VideoImpressionBloomFilterFeature
import com.twitter.tweet_mixer.model.request.HasVideoType
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableVideoBloomFilterHydrator

object MediaWatchHistoryFilter
    extends Filter[PipelineQuery with HasVideoType, TweetCandidate]
    with Filter.Conditionally[PipelineQuery with HasVideoType, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("MediaWatchHistory")

  override def onlyIf(
    query: PipelineQuery with HasVideoType,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Boolean = query.params(EnableVideoBloomFilterHydrator)

  override def apply(
    query: PipelineQuery with HasVideoType,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val bloomFilters = query.features.map(_.get(VideoImpressionBloomFilterFeature)) match {
      case Some(seq) => seq.entries.map(ImpressionBloomFilterItem.fromThrift(_).bloomFilter)
      case None => Seq.empty
    }

    val (removed, kept) = candidates.partition { candidate =>
      getMediaId(candidate).exists { mediaId =>
        bloomFilters.exists(filter => filter.mayContain(mediaId))
      }
    }

    Stitch.value(FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate)))
  }

  private def getMediaId(candidate: CandidateWithFeatures[TweetCandidate]): Option[Long] = {
    candidate.features.getOrElse(MediaIdFeature, None)
  }
}

package com.twitter.home_mixer.functional_component.side_effect

import com.twitter.conversions.DurationOps._
import com.twitter.home_mixer.model.HomeFeatures.ImpressionBloomFilterFeature
import com.twitter.home_mixer.model.request.HasSeenTweetIds
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.response.urt.Timeline
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelines.impressionbloomfilter.{thriftscala => t}
import com.twitter.timelines.impressionstore.impressionbloomfilter.ImpressionBloomFilter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateImpressionBloomFilterSideEffect @Inject() (bloomFilter: ImpressionBloomFilter)
    extends PipelineResultSideEffect[PipelineQuery with HasSeenTweetIds, Timeline]
    with PipelineResultSideEffect.Conditionally[PipelineQuery with HasSeenTweetIds, Timeline] {

  private val SurfaceArea = t.SurfaceArea.HomeTimeline

  override val identifier: SideEffectIdentifier =
    SideEffectIdentifier("UpdateImpressionBloomFilter")

  override def onlyIf(
    query: PipelineQuery with HasSeenTweetIds,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: Timeline
  ): Boolean = query.seenTweetIds.exists(_.nonEmpty)

  def buildEvents(query: PipelineQuery): Option[t.ImpressionBloomFilterSeq] = {
    query.features.flatMap { featureMap =>
      val impressionBloomFilterSeq = featureMap.get(ImpressionBloomFilterFeature)
      if (impressionBloomFilterSeq.entries.nonEmpty) Some(impressionBloomFilterSeq)
      else None
    }
  }

  override def apply(
    inputs: PipelineResultSideEffect.Inputs[PipelineQuery with HasSeenTweetIds, Timeline]
  ): Stitch[Unit] = {
    buildEvents(inputs.query)
      .map { updatedBloomFilter =>
        bloomFilter.writeBloomFilterSeq(
          userId = inputs.query.getRequiredUserId,
          surfaceArea = SurfaceArea,
          impressionBloomFilterSeq = updatedBloomFilter)
      }.getOrElse(Stitch.Unit)
  }

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(99.8),
    HomeMixerAlertConfig.BusinessHours.defaultLatencyAlert(30.millis)
  )
}

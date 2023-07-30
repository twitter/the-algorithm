package com.X.home_mixer.functional_component.side_effect

import com.X.home_mixer.model.HomeFeatures.ImpressionBloomFilterFeature
import com.X.home_mixer.model.request.HasSeenTweetIds
import com.X.home_mixer.param.HomeGlobalParams.EnableImpressionBloomFilter
import com.X.home_mixer.service.HomeMixerAlertConfig
import com.X.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.X.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.X.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.X.product_mixer.core.model.marshalling.HasMarshalling
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch
import com.X.timelines.clients.manhattan.store.ManhattanStoreClient
import com.X.timelines.impressionbloomfilter.{thriftscala => blm}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PublishImpressionBloomFilterSideEffect @Inject() (
  bloomFilterClient: ManhattanStoreClient[
    blm.ImpressionBloomFilterKey,
    blm.ImpressionBloomFilterSeq
  ]) extends PipelineResultSideEffect[PipelineQuery with HasSeenTweetIds, HasMarshalling]
    with PipelineResultSideEffect.Conditionally[
      PipelineQuery with HasSeenTweetIds,
      HasMarshalling
    ] {

  override val identifier: SideEffectIdentifier =
    SideEffectIdentifier("PublishImpressionBloomFilter")

  private val SurfaceArea = blm.SurfaceArea.HomeTimeline

  override def onlyIf(
    query: PipelineQuery with HasSeenTweetIds,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: HasMarshalling
  ): Boolean =
    query.params.getBoolean(EnableImpressionBloomFilter) && query.seenTweetIds.exists(_.nonEmpty)

  def buildEvents(query: PipelineQuery): Option[blm.ImpressionBloomFilterSeq] = {
    query.features.flatMap { featureMap =>
      val impressionBloomFilterSeq = featureMap.get(ImpressionBloomFilterFeature)
      if (impressionBloomFilterSeq.entries.nonEmpty) Some(impressionBloomFilterSeq)
      else None
    }
  }

  override def apply(
    inputs: PipelineResultSideEffect.Inputs[PipelineQuery with HasSeenTweetIds, HasMarshalling]
  ): Stitch[Unit] = {
    buildEvents(inputs.query)
      .map { updatedBloomFilterSeq =>
        bloomFilterClient.write(
          blm.ImpressionBloomFilterKey(inputs.query.getRequiredUserId, SurfaceArea),
          updatedBloomFilterSeq)
      }.getOrElse(Stitch.Unit)
  }

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(99.8)
  )
}

package com.X.home_mixer.functional_component.side_effect

import com.X.home_mixer.model.HomeFeatures.TweetImpressionsFeature
import com.X.home_mixer.model.request.HasSeenTweetIds
import com.X.home_mixer.service.HomeMixerAlertConfig
import com.X.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.X.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.X.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.X.product_mixer.core.model.marshalling.HasMarshalling
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch
import com.X.timelines.impression.{thriftscala => t}
import com.X.timelines.impressionstore.store.ManhattanTweetImpressionStoreClient
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Side effect that updates the timelines tweet impression
 * store (Manhattan) with seen tweet IDs sent from clients
 */
@Singleton
class PublishClientSentImpressionsManhattanSideEffect @Inject() (
  manhattanTweetImpressionStoreClient: ManhattanTweetImpressionStoreClient)
    extends PipelineResultSideEffect[PipelineQuery with HasSeenTweetIds, HasMarshalling]
    with PipelineResultSideEffect.Conditionally[
      PipelineQuery with HasSeenTweetIds,
      HasMarshalling
    ] {

  override val identifier: SideEffectIdentifier =
    SideEffectIdentifier("PublishClientSentImpressionsManhattan")

  override def onlyIf(
    query: PipelineQuery with HasSeenTweetIds,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: HasMarshalling
  ): Boolean = query.seenTweetIds.exists(_.nonEmpty)

  def buildEvents(query: PipelineQuery): Option[(Long, t.TweetImpressionsEntries)] = {
    query.features.flatMap { featureMap =>
      val impressions = featureMap.getOrElse(TweetImpressionsFeature, Seq.empty)
      if (impressions.nonEmpty)
        Some((query.getRequiredUserId, t.TweetImpressionsEntries(impressions)))
      else None
    }
  }

  final override def apply(
    inputs: PipelineResultSideEffect.Inputs[PipelineQuery with HasSeenTweetIds, HasMarshalling]
  ): Stitch[Unit] = {
    val events = buildEvents(inputs.query)

    Stitch
      .traverse(events) {
        case (key, value) => manhattanTweetImpressionStoreClient.write(key, value)
      }
      .unit
  }

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(99.4)
  )
}

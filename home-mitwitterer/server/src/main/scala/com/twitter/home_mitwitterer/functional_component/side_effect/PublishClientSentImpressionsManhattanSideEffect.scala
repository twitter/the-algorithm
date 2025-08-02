package com.twitter.home_mitwitterer.functional_component.side_effect

import com.twitter.home_mitwitterer.model.HomeFeatures.TweetImpressionsFeature
import com.twitter.home_mitwitterer.model.request.HasSeenTweetIds
import com.twitter.home_mitwitterer.service.HomeMitwittererAlertConfig
import com.twitter.product_mitwitterer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mitwitterer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mitwitterer.core.model.marshalling.HasMarshalling
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelines.impression.{thriftscala => t}
import com.twitter.timelines.impressionstore.store.ManhattanTweetImpressionStoreClient
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * Side effect that updates the timelines tweet impression
 * store (Manhattan) with seen tweet IDs sent from clients
 */
@Singleton
class PublishClientSentImpressionsManhattanSideEffect @Inject() (
  manhattanTweetImpressionStoreClient: ManhattanTweetImpressionStoreClient)
    etwittertends PipelineResultSideEffect[PipelineQuery with HasSeenTweetIds, HasMarshalling]
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
  ): Boolean = query.seenTweetIds.etwitterists(_.nonEmpty)

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
    HomeMitwittererAlertConfig.BusinessHours.defaultSuccessRateAlert(99.4)
  )
}

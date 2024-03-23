package com.ExTwitter.home_mixer.functional_component.side_effect

import com.ExTwitter.eventbus.client.EventBusPublisher
import com.ExTwitter.home_mixer.model.request.FollowingProduct
import com.ExTwitter.home_mixer.model.request.ForYouProduct
import com.ExTwitter.home_mixer.model.request.SubscribedProduct
import com.ExTwitter.home_mixer.model.request.HasSeenTweetIds
import com.ExTwitter.home_mixer.service.HomeMixerAlertConfig
import com.ExTwitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.ExTwitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.ExTwitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.ExTwitter.product_mixer.core.model.marshalling.HasMarshalling
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelines.impressionstore.thriftscala.Impression
import com.ExTwitter.timelines.impressionstore.thriftscala.ImpressionList
import com.ExTwitter.timelines.impressionstore.thriftscala.PublishedImpressionList
import com.ExTwitter.timelines.impressionstore.thriftscala.SurfaceArea
import com.ExTwitter.util.Time
import javax.inject.Inject
import javax.inject.Singleton

object PublishClientSentImpressionsEventBusSideEffect {
  val HomeSurfaceArea: Option[Set[SurfaceArea]] = Some(Set(SurfaceArea.HomeTimeline))
  val HomeLatestSurfaceArea: Option[Set[SurfaceArea]] = Some(Set(SurfaceArea.HomeLatestTimeline))
  val HomeSubscribedSurfaceArea: Option[Set[SurfaceArea]] = Some(Set(SurfaceArea.HomeSubscribed))
}

/**
 * Side effect that publishes seen tweet IDs sent from clients. The seen tweet IDs are sent to a
 * heron topology which writes to a memcache dataset.
 */
@Singleton
class PublishClientSentImpressionsEventBusSideEffect @Inject() (
  eventBusPublisher: EventBusPublisher[PublishedImpressionList])
    extends PipelineResultSideEffect[PipelineQuery with HasSeenTweetIds, HasMarshalling]
    with PipelineResultSideEffect.Conditionally[
      PipelineQuery with HasSeenTweetIds,
      HasMarshalling
    ] {
  import PublishClientSentImpressionsEventBusSideEffect._

  override val identifier: SideEffectIdentifier =
    SideEffectIdentifier("PublishClientSentImpressionsEventBus")

  override def onlyIf(
    query: PipelineQuery with HasSeenTweetIds,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: HasMarshalling
  ): Boolean = query.seenTweetIds.exists(_.nonEmpty)

  def buildEvents(
    query: PipelineQuery with HasSeenTweetIds,
    currentTime: Long
  ): Option[Seq[Impression]] = {
    val surfaceArea = query.product match {
      case ForYouProduct => HomeSurfaceArea
      case FollowingProduct => HomeLatestSurfaceArea
      case SubscribedProduct => HomeSubscribedSurfaceArea
      case _ => None
    }
    query.seenTweetIds.map { seenTweetIds =>
      seenTweetIds.map { tweetId =>
        Impression(
          tweetId = tweetId,
          impressionTime = Some(currentTime),
          surfaceAreas = surfaceArea
        )
      }
    }
  }

  final override def apply(
    inputs: PipelineResultSideEffect.Inputs[PipelineQuery with HasSeenTweetIds, HasMarshalling]
  ): Stitch[Unit] = {
    val currentTime = Time.now.inMilliseconds
    val impressions = buildEvents(inputs.query, currentTime)

    Stitch.callFuture(
      eventBusPublisher.publish(
        PublishedImpressionList(
          inputs.query.getRequiredUserId,
          ImpressionList(impressions),
          currentTime
        )
      )
    )
  }

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(99.4)
  )
}

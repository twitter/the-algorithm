package com.ExTwitter.home_mixer.product.for_you.feature_hydrator

import com.ExTwitter.home_mixer.marshaller.timelines.DeviceContextMarshaller
import com.ExTwitter.home_mixer.model.HomeFeatures.TimelineServiceTweetsFeature
import com.ExTwitter.home_mixer.model.request.DeviceContext
import com.ExTwitter.home_mixer.model.request.HasDeviceContext
import com.ExTwitter.home_mixer.service.HomeMixerAlertConfig
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.ExTwitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.stitch.timelineservice.TimelineService
import com.ExTwitter.timelineservice.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class TimelineServiceTweetsQueryFeatureHydrator @Inject() (
  timelineService: TimelineService,
  deviceContextMarshaller: DeviceContextMarshaller)
    extends QueryFeatureHydrator[PipelineQuery with HasDeviceContext] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TimelineServiceTweets")

  override val features: Set[Feature[_, _]] = Set(TimelineServiceTweetsFeature)

  private val MaxTimelineServiceTweets = 200

  override def hydrate(query: PipelineQuery with HasDeviceContext): Stitch[FeatureMap] = {
    val deviceContext = query.deviceContext.getOrElse(DeviceContext.Empty)

    val timelineQueryOptions = t.TimelineQueryOptions(
      contextualUserId = query.clientContext.userId,
      deviceContext = Some(deviceContextMarshaller(deviceContext, query.clientContext))
    )

    val timelineServiceQuery = t.TimelineQuery(
      timelineType = t.TimelineType.Home,
      timelineId = query.getRequiredUserId,
      maxCount = MaxTimelineServiceTweets.toShort,
      cursor2 = None,
      options = Some(timelineQueryOptions),
      timelineId2 = query.clientContext.userId.map(t.TimelineId(t.TimelineType.Home, _, None)),
    )

    timelineService.getTimeline(timelineServiceQuery).map { timeline =>
      val tweets = timeline.entries.collect {
        case t.TimelineEntry.Tweet(tweet) => tweet.statusId
      }

      FeatureMapBuilder().add(TimelineServiceTweetsFeature, tweets).build()
    }
  }

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(99.7)
  )
}

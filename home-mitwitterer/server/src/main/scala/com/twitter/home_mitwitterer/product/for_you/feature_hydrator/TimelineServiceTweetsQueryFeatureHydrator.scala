package com.twitter.home_mitwitterer.product.for_you.feature_hydrator

import com.twitter.home_mitwitterer.marshaller.timelines.DeviceContetwittertMarshaller
import com.twitter.home_mitwitterer.model.HomeFeatures.TimelineServiceTweetsFeature
import com.twitter.home_mitwitterer.model.request.DeviceContetwittert
import com.twitter.home_mitwitterer.model.request.HasDeviceContetwittert
import com.twitter.home_mitwitterer.service.HomeMitwittererAlertConfig
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.stitch.timelineservice.TimelineService
import com.twitter.timelineservice.{thriftscala => t}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
case class TimelineServiceTweetsQueryFeatureHydrator @Inject() (
  timelineService: TimelineService,
  deviceContetwittertMarshaller: DeviceContetwittertMarshaller)
    etwittertends QueryFeatureHydrator[PipelineQuery with HasDeviceContetwittert] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TimelineServiceTweets")

  override val features: Set[Feature[_, _]] = Set(TimelineServiceTweetsFeature)

  private val MatwitterTimelineServiceTweets = 200

  override def hydrate(query: PipelineQuery with HasDeviceContetwittert): Stitch[FeatureMap] = {
    val deviceContetwittert = query.deviceContetwittert.getOrElse(DeviceContetwittert.Empty)

    val timelineQueryOptions = t.TimelineQueryOptions(
      contetwittertualUserId = query.clientContetwittert.userId,
      deviceContetwittert = Some(deviceContetwittertMarshaller(deviceContetwittert, query.clientContetwittert))
    )

    val timelineServiceQuery = t.TimelineQuery(
      timelineType = t.TimelineType.Home,
      timelineId = query.getRequiredUserId,
      matwitterCount = MatwitterTimelineServiceTweets.toShort,
      cursor2 = None,
      options = Some(timelineQueryOptions),
      timelineId2 = query.clientContetwittert.userId.map(t.TimelineId(t.TimelineType.Home, _, None)),
    )

    timelineService.getTimeline(timelineServiceQuery).map { timeline =>
      val tweets = timeline.entries.collect {
        case t.TimelineEntry.Tweet(tweet) => tweet.statusId
      }

      FeatureMapBuilder().add(TimelineServiceTweetsFeature, tweets).build()
    }
  }

  override val alerts = Seq(
    HomeMitwittererAlertConfig.BusinessHours.defaultSuccessRateAlert(99.7)
  )
}

package com.twitter.home_mixer.product.for_you.feature_hydrator

import com.twitter.home_mixer.marshaller.timelines.DeviceContextMarshaller
import com.twitter.home_mixer.model.HomeFeatures.TLSOriginalTweetsWithAuthorFeature
import com.twitter.home_mixer.model.HomeFeatures.TimelineServiceTweetsFeature
import com.twitter.home_mixer.model.request.DeviceContext
import com.twitter.home_mixer.model.request.HasDeviceContext
import com.twitter.home_mixer.product.for_you.param.ForYouParam.{EnableGetTweetsFromArchiveIndex, EnableTLSHydrationParam}
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.stitch.Stitch
import com.twitter.stitch.timelineservice.TimelineService
import com.twitter.timelineservice.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class TimelineServiceTweetsQueryFeatureHydrator @Inject() (
  timelineService: TimelineService,
  deviceContextMarshaller: DeviceContextMarshaller)
    extends QueryFeatureHydrator[PipelineQuery with HasDeviceContext] 
        with Conditionally[PipelineQuery with HasDeviceContext] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TimelineServiceTweets")

  override val features: Set[Feature[_, _]] =
    Set(TimelineServiceTweetsFeature, TLSOriginalTweetsWithAuthorFeature)

  override def onlyIf(query: PipelineQuery with HasDeviceContext): Boolean = {
    query.params(EnableTLSHydrationParam)
  }

  private[this] def getTweetsFromArchiveIndexOpt(
    query: PipelineQuery with HasDeviceContext
  ): Option[Boolean] = {
    if (query.params(EnableGetTweetsFromArchiveIndex)) {
      // Fall back to the default in timelineservice, i.e. get tweets from archive index.
      None
    } else {
      Some(false)
    }
  }

  private val MaxTimelineServiceTweets = 200

  override def hydrate(query: PipelineQuery with HasDeviceContext): Stitch[FeatureMap] = {
    val deviceContext = query.deviceContext.getOrElse(DeviceContext.Empty)

    val timelineQueryOptions = t.TimelineQueryOptions(
      contextualUserId = query.clientContext.userId,
      deviceContext = Some(deviceContextMarshaller(deviceContext, query.clientContext)),
      getTweetsFromArchiveIndex = getTweetsFromArchiveIndexOpt(query)
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

      // Non replies and non retweets
      val originalTweetsWithAuthor = timeline.entries.collect {
        case t.TimelineEntry.Tweet(tweet)
            if tweet.inReplyToStatusId.isEmpty
              && tweet.sourceStatusId.isEmpty =>
          (tweet.statusId, tweet.userId)
      }

      FeatureMapBuilder()
        .add(TimelineServiceTweetsFeature, tweets)
        .add(TLSOriginalTweetsWithAuthorFeature, originalTweetsWithAuthor)
        .build()
    }
  }

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(95)
  )
}

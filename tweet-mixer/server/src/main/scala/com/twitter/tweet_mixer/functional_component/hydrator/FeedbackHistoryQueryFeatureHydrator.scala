package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelinemixer.clients.feedback.FeedbackHistoryManhattanClient
import com.twitter.timelineservice.model.FeedbackEntry
import com.twitter.tweet_mixer.feature.EntityTypes.TweetId
import com.twitter.tweet_mixer.feature.EntityTypes.UserId
import com.twitter.tweet_mixer.feature.USSFeature
import com.twitter.usersignalservice.{thriftscala => uss}
import javax.inject.Inject
import javax.inject.Singleton

object TweetShowMore extends USSFeature[TweetId]

object AccountShowMore extends USSFeature[UserId]

@Singleton
case class FeedbackHistoryQueryFeatureHydrator @Inject() (
  feedbackHistoryClient: FeedbackHistoryManhattanClient)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("FeedbackHistory")

  override val features: Set[Feature[_, _]] = Set(TweetShowMore, AccountShowMore)

  override def hydrate(
    query: PipelineQuery
  ): Stitch[FeatureMap] = Stitch
    .callFuture(feedbackHistoryClient.get(query.getRequiredUserId))
    .map { feedbackHistory =>
      val tweetShowMoreSignals =
        feedbackHistoryToSignal(feedbackHistory, uss.SignalType.TweetSeeMore)
      val accountShowMoreSignals =
        feedbackHistoryToSignal(feedbackHistory, uss.SignalType.AccountSeeMore)

      FeatureMap(
        TweetShowMore,
        USSQueryFeatureHydrator.getSignalMap[TweetId](tweetShowMoreSignals),
        AccountShowMore,
        USSQueryFeatureHydrator.getSignalMap[UserId](accountShowMoreSignals)
      )
    }

  private def feedbackHistoryToSignal(
    feedbackHistory: Seq[FeedbackEntry],
    signalType: uss.SignalType
  ): Seq[uss.Signal] = feedbackHistory.map { feedbackEntry =>
    uss.Signal(
      signalType = signalType,
      timestamp = feedbackEntry.timestamp.inMilliseconds
    )
  }
}

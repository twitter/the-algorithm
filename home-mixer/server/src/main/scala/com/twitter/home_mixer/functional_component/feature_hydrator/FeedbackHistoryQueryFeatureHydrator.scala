package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.FeedbackHistoryFeature
import com.twitter.home_mixer.model.HomeFeatures.HasRecentFeedbackSinceCacheTtlFeature
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CachedScoredTweets
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelinemixer.clients.feedback.FeedbackHistoryManhattanClient
import com.twitter.util.Time
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class FeedbackHistoryQueryFeatureHydrator @Inject() (
  feedbackHistoryClient: FeedbackHistoryManhattanClient)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("FeedbackHistory")

  override val features: Set[Feature[_, _]] =
    Set(FeedbackHistoryFeature, HasRecentFeedbackSinceCacheTtlFeature)

  override def hydrate(
    query: PipelineQuery
  ): Stitch[FeatureMap] =
    Stitch
      .callFuture(feedbackHistoryClient.get(query.getRequiredUserId))
      .map { feedbackHistory =>
        val latestFeedbackTimestamp =
          if (feedbackHistory.nonEmpty) Some(feedbackHistory.map(_.timestamp.inMilliseconds).max)
          else None
        val cachedScoredTweetsTtl = query.params(CachedScoredTweets.TTLParam)
        val hasRecentFeedbackSinceCacheTtl = latestFeedbackTimestamp match {
          case Some(timestamp) =>
            Time.fromMilliseconds(timestamp).untilNow < cachedScoredTweetsTtl
          case None => false
        }

        val featureMapBuilder = FeatureMapBuilder()
          .add(FeedbackHistoryFeature, feedbackHistory)
          .add(HasRecentFeedbackSinceCacheTtlFeature, hasRecentFeedbackSinceCacheTtl)

        featureMapBuilder.build()
      }
}

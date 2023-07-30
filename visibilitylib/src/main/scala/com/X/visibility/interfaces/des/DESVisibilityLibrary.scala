package com.X.visibility.interfaces.des

import com.X.stitch.Stitch
import com.X.tweetypie.thriftscala.Tweet
import com.X.visibility.VisibilityLibrary
import com.X.visibility.builder.VisibilityResult
import com.X.visibility.builder.tweets.StratoTweetLabelMaps
import com.X.visibility.builder.tweets.TweetFeatures
import com.X.visibility.common.SafetyLabelMapSource
import com.X.visibility.features.AuthorId
import com.X.visibility.features.FeatureMap
import com.X.visibility.interfaces.common.tweets.SafetyLabelMapFetcherType
import com.X.visibility.models.ContentId.TweetId
import com.X.visibility.models.SafetyLevel
import com.X.visibility.models.ViewerContext

case class DESVisibilityRequest(
  tweet: Tweet,
  visibilitySurface: SafetyLevel,
  viewerContext: ViewerContext)

object DESVisibilityLibrary {
  type Type = DESVisibilityRequest => Stitch[VisibilityResult]

  def apply(
    visibilityLibrary: VisibilityLibrary,
    getLabelMap: SafetyLabelMapFetcherType,
    enableShimFeatureHydration: Any => Boolean = _ => false
  ): Type = {
    val libraryStatsReceiver = visibilityLibrary.statsReceiver
    val vfEngineCounter = libraryStatsReceiver.counter("vf_engine_requests")

    val tweetLabelMap = new StratoTweetLabelMaps(
      SafetyLabelMapSource.fromSafetyLabelMapFetcher(getLabelMap))
    val tweetFeatures = new TweetFeatures(tweetLabelMap, libraryStatsReceiver)

    { request: DESVisibilityRequest =>
      vfEngineCounter.incr()

      val contentId = TweetId(request.tweet.id)
      val authorId = coreData.userId

      val featureMap =
        visibilityLibrary.featureMapBuilder(
          Seq(
            tweetFeatures.forTweet(request.tweet),
            _.withConstantFeature(AuthorId, Set(authorId))
          )
        )

      val isShimFeatureHydrationEnabled = enableShimFeatureHydration()

      if (isShimFeatureHydrationEnabled) {
        FeatureMap.resolve(featureMap, libraryStatsReceiver).flatMap { resolvedFeatureMap =>
          visibilityLibrary.runRuleEngine(
            contentId,
            resolvedFeatureMap,
            request.viewerContext,
            request.visibilitySurface
          )
        }
      } else {
        visibilityLibrary.runRuleEngine(
          contentId,
          featureMap,
          request.viewerContext,
          request.visibilitySurface
        )
      }
    }
  }
}

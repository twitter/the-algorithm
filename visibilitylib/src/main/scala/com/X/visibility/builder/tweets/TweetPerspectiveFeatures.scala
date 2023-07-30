package com.X.visibility.builder.tweets

import com.X.finagle.stats.StatsReceiver
import com.X.stitch.Stitch
import com.X.tweetypie.thriftscala.Tweet
import com.X.visibility.builder.FeatureMapBuilder
import com.X.visibility.common.TweetPerspectiveSource
import com.X.visibility.features.ViewerReportedTweet

class TweetPerspectiveFeatures(
  tweetPerspectiveSource: TweetPerspectiveSource,
  statsReceiver: StatsReceiver) {

  private[this] val scopedStatsReceiver = statsReceiver.scope("tweet_perspective_features")
  private[this] val reportedStats = scopedStatsReceiver.scope("reported")

  def forTweet(
    tweet: Tweet,
    viewerId: Option[Long],
    enableFetchReportedPerspective: Boolean
  ): FeatureMapBuilder => FeatureMapBuilder =
    _.withFeature(
      ViewerReportedTweet,
      tweetIsReported(tweet, viewerId, enableFetchReportedPerspective))

  private[builder] def tweetIsReported(
    tweet: Tweet,
    viewerId: Option[Long],
    enableFetchReportedPerspective: Boolean = true
  ): Stitch[Boolean] = {
    ((tweet.perspective, viewerId) match {
      case (Some(perspective), _) =>
        Stitch.value(perspective.reported).onSuccess { _ =>
          reportedStats.counter("already_hydrated").incr()
        }
      case (None, Some(viewerId)) =>
        if (enableFetchReportedPerspective) {
          tweetPerspectiveSource.reported(tweet.id, viewerId).onSuccess { _ =>
            reportedStats.counter("request").incr()
          }
        } else {
          Stitch.False.onSuccess { _ =>
            reportedStats.counter("light_request").incr()
          }
        }
      case _ =>
        Stitch.False.onSuccess { _ =>
          reportedStats.counter("empty").incr()
        }
    }).onSuccess { _ =>
      reportedStats.counter("").incr()
    }
  }
}

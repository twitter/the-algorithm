package com.twitter.visibility.builder.tweets

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.visibility.builder.FeatureMapBuilder
import com.twitter.visibility.features.TweetIsModerated

class ModerationFeatures(moderationSource: Long => Boolean, statsReceiver: StatsReceiver) {

  private[this] val scopedStatsReceiver: StatsReceiver =
    statsReceiver.scope("moderation_features")

  private[this] val requests = scopedStatsReceiver.counter("requests")

  private[this] val tweetIsModerated =
    scopedStatsReceiver.scope(TweetIsModerated.name).counter("requests")

  def forTweetId(tweetId: Long): FeatureMapBuilder => FeatureMapBuilder = { featureMapBuilder =>
    requests.incr()
    tweetIsModerated.incr()

    featureMapBuilder.withConstantFeature(TweetIsModerated, moderationSource(tweetId))
  }
}

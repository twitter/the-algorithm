package com.X.visibility.builder.tweets

import com.X.finagle.stats.StatsReceiver
import com.X.servo.util.Gate
import com.X.spam.rtf.thriftscala.SafetyLabel
import com.X.spam.rtf.thriftscala.SafetyLabelType
import com.X.spam.rtf.thriftscala.SafetyLabelValue
import com.X.stitch.Stitch
import com.X.visibility.builder.FeatureMapBuilder
import com.X.visibility.common.stitch.StitchHelpers
import com.X.visibility.features.TweetId
import com.X.visibility.features.TweetSafetyLabels
import com.X.visibility.features.TweetTimestamp
import com.X.visibility.models.TweetSafetyLabel

class TweetIdFeatures(
  statsReceiver: StatsReceiver,
  enableStitchProfiling: Gate[Unit]) {
  private[this] val scopedStatsReceiver: StatsReceiver = statsReceiver.scope("tweet_id_features")

  private[this] val requests = scopedStatsReceiver.counter("requests")
  private[this] val tweetSafetyLabels =
    scopedStatsReceiver.scope(TweetSafetyLabels.name).counter("requests")
  private[this] val tweetTimestamp =
    scopedStatsReceiver.scope(TweetTimestamp.name).counter("requests")

  private[this] val labelFetchScope: StatsReceiver =
    scopedStatsReceiver.scope("labelFetch")

  private[this] def getTweetLabels(
    tweetId: Long,
    labelFetcher: Long => Stitch[Map[SafetyLabelType, SafetyLabel]]
  ): Stitch[Seq[TweetSafetyLabel]] = {
    val stitch =
      labelFetcher(tweetId).map { labelMap =>
        labelMap
          .map { case (labelType, label) => SafetyLabelValue(labelType, label) }.toSeq
          .map(TweetSafetyLabel.fromThrift)
      }

    if (enableStitchProfiling()) {
      StitchHelpers.profileStitch(
        stitch,
        Seq(labelFetchScope)
      )
    } else {
      stitch
    }
  }

  def forTweetId(
    tweetId: Long,
    labelFetcher: Long => Stitch[Map[SafetyLabelType, SafetyLabel]]
  ): FeatureMapBuilder => FeatureMapBuilder = {
    requests.incr()
    tweetSafetyLabels.incr()
    tweetTimestamp.incr()

    _.withFeature(TweetSafetyLabels, getTweetLabels(tweetId, labelFetcher))
      .withConstantFeature(TweetTimestamp, TweetFeatures.tweetTimestamp(tweetId))
      .withConstantFeature(TweetId, tweetId)
  }

  def forTweetId(
    tweetId: Long,
    constantTweetSafetyLabels: Seq[TweetSafetyLabel]
  ): FeatureMapBuilder => FeatureMapBuilder = {
    requests.incr()
    tweetSafetyLabels.incr()
    tweetTimestamp.incr()

    _.withConstantFeature(TweetSafetyLabels, constantTweetSafetyLabels)
      .withConstantFeature(TweetTimestamp, TweetFeatures.tweetTimestamp(tweetId))
      .withConstantFeature(TweetId, tweetId)
  }
}

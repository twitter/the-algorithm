package com.twitter.visibility.interfaces.push_service

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.spam.rtf.thriftscala.SafetyLabelMap
import com.twitter.stitch.Stitch
import com.twitter.strato.client.{Client => StratoClient}
import com.twitter.strato.thrift.ScroogeConvImplicits._
import com.twitter.visibility.common.stitch.StitchHelpers

object PushServiceSafetyLabelMapFetcher {
  val Column = "frigate/magicrecs/tweetSafetyLabels"

  def apply(
    client: StratoClient,
    statsReceiver: StatsReceiver
  ): Long => Stitch[Option[SafetyLabelMap]] = {
    val stats = statsReceiver.scope("strato_tweet_safety_labels")
    lazy val fetcher = client.fetcher[Long, SafetyLabelMap](Column)
    tweetId => StitchHelpers.observe(stats)(fetcher.fetch(tweetId).map(_.v))
  }
}

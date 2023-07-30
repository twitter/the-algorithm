package com.X.visibility.interfaces.push_service

import com.X.finagle.stats.StatsReceiver
import com.X.spam.rtf.thriftscala.SafetyLabelMap
import com.X.stitch.Stitch
import com.X.strato.client.{Client => StratoClient}
import com.X.strato.thrift.ScroogeConvImplicits._
import com.X.visibility.common.stitch.StitchHelpers

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

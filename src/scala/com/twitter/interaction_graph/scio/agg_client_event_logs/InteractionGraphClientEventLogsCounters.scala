package com.twitter.interaction_graph.scio.agg_client_event_logs

import com.spotify.scio.ScioMetrics

trait InteractionGraphClientEventLogsCountersTrait {
  val Namespace = "Interaction Graph Client Event Logs"
  def profileViewFeaturesInc(): Unit
  def linkOpenFeaturesInc(): Unit
  def tweetClickFeaturesInc(): Unit
  def tweetImpressionFeaturesInc(): Unit
  def catchAllInc(): Unit
}

case object InteractionGraphClientEventLogsCounters
    extends InteractionGraphClientEventLogsCountersTrait {

  val profileViewCounter = ScioMetrics.counter(Namespace, "Profile View Features")
  val linkOpenCounter = ScioMetrics.counter(Namespace, "Link Open Features")
  val tweetClickCounter = ScioMetrics.counter(Namespace, "Tweet Click Features")
  val tweetImpressionCounter = ScioMetrics.counter(Namespace, "Tweet Impression Features")
  val catchAllCounter = ScioMetrics.counter(Namespace, "Catch All")

  override def profileViewFeaturesInc(): Unit = profileViewCounter.inc()

  override def linkOpenFeaturesInc(): Unit = linkOpenCounter.inc()

  override def tweetClickFeaturesInc(): Unit = tweetClickCounter.inc()

  override def tweetImpressionFeaturesInc(): Unit = tweetImpressionCounter.inc()

  override def catchAllInc(): Unit = catchAllCounter.inc()
}

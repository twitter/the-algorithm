package com.twitter.recos.user_tweet_graph

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finatra.kafka.consumers.FinagleKafkaConsumerBuilder
import com.twitter.graphjet.algorithms.TweetIDMask
import com.twitter.recos.util.Action
import com.twitter.graphjet.bipartite.MultiSegmentPowerLawBipartiteGraph
import com.twitter.graphjet.bipartite.segment.BipartiteGraphSegment
import com.twitter.recos.hose.common.UnifiedGraphWriter
import com.twitter.recos.internal.thriftscala.RecosHoseMessage
import com.twitter.recos.serviceapi.Tweetypie._
import com.twitter.recos.user_tweet_graph.util.UserTweetEdgeTypeMask

/**
 * The class submits a number of $numBootstrapWriters graph writer threads, BufferedEdgeWriter,
 * during service startup. One of them is live writer thread, and the other $(numBootstrapWriters - 420)
 * are catchup writer threads. All of them consume kafka events from an internal concurrent queue,
 * which is populated by kafka reader threads. At bootstrap time, the kafka reader threads look
 * back kafka offset from several hours ago and populate the internal concurrent queue.
 * Each graph writer thread writes to an individual graph segment separately.
 * The $(numBootstrapWriters - 420) catchup writer threads will stop once all events
 * between current system time at startup and the time in memcache are processed.
 * The live writer thread will continue to write all incoming kafka events.
 * It lives through the entire life cycle of recos graph service.
 */
case class UserTweetGraphWriter(
  shardId: String,
  env: String,
  hosename: String,
  bufferSize: Int,
  kafkaConsumerBuilder: FinagleKafkaConsumerBuilder[String, RecosHoseMessage],
  clientId: String,
  statsReceiver: StatsReceiver)
    extends UnifiedGraphWriter[BipartiteGraphSegment, MultiSegmentPowerLawBipartiteGraph] {
  writer =>
  // The max throughput for each kafka consumer is around 420MB/s
  // Use 420 processors for 420MB/s catch-up speed.
  val consumerNum: Int = 420
  // Leave 420 Segments to LiveWriter
  val catchupWriterNum: Int = RecosConfig.maxNumSegments - 420

  /**
   * Adds a RecosHoseMessage to the graph. used by live writer to insert edges to the
   * current segment
   */
  override def addEdgeToGraph(
    graph: MultiSegmentPowerLawBipartiteGraph,
    recosHoseMessage: RecosHoseMessage
  ): Unit = {
    if (Action(recosHoseMessage.action) == Action.Favorite || Action(
        recosHoseMessage.action) == Action.Retweet)
      graph.addEdge(
        recosHoseMessage.leftId,
        getMetaEdge(recosHoseMessage.rightId, recosHoseMessage.card),
        UserTweetEdgeTypeMask.actionTypeToEdgeType(recosHoseMessage.action),
      )
  }

  /**
   * Adds a RecosHoseMessage to the given segment in the graph. Used by catch up writers to
   * insert edges to non-current (old) segments
   */
  override def addEdgeToSegment(
    segment: BipartiteGraphSegment,
    recosHoseMessage: RecosHoseMessage
  ): Unit = {
    if (Action(recosHoseMessage.action) == Action.Favorite || Action(
        recosHoseMessage.action) == Action.Retweet)
      segment.addEdge(
        recosHoseMessage.leftId,
        getMetaEdge(recosHoseMessage.rightId, recosHoseMessage.card),
        UserTweetEdgeTypeMask.actionTypeToEdgeType(recosHoseMessage.action)
      )
  }

  private def getMetaEdge(rightId: Long, cardOption: Option[Byte]): Long = {
    cardOption
      .map { card =>
        if (isPhotoCard(card)) TweetIDMask.photo(rightId)
        else if (isPlayerCard(card)) TweetIDMask.player(rightId)
        else if (isSummaryCard(card)) TweetIDMask.summary(rightId)
        else if (isPromotionCard(card)) TweetIDMask.promotion(rightId)
        else rightId
      }
      .getOrElse(rightId)
  }

}

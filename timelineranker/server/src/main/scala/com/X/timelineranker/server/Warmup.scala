package com.X.timelineranker.server

import com.X.conversions.DurationOps._
import com.X.finagle.thrift.ClientId
import com.X.logging.Logger
import com.X.timelineranker.model._
import com.X.timelines.warmup.XServerWarmup
import com.X.timelineservice.model.TimelineId
import com.X.timelineservice.model.core.TimelineKind
import com.X.timelineranker.config.TimelineRankerConstants
import com.X.timelineranker.thriftscala.{TimelineRanker => ThriftTimelineRanker}
import com.X.util.Future
import com.X.util.Duration

object Warmup {
  val WarmupForwardingTime: Duration = 25.seconds
}

class Warmup(
  localInstance: TimelineRanker,
  forwardingClient: ThriftTimelineRanker.MethodPerEndpoint,
  override val logger: Logger)
    extends XServerWarmup {
  override val WarmupClientId: ClientId = ClientId(TimelineRankerConstants.WarmupClientName)
  override val NumWarmupRequests = 20
  override val MinSuccessfulRequests = 10

  private[this] val warmupUserId = Math.abs(scala.util.Random.nextLong())
  private[server] val reverseChronQuery = ReverseChronTimelineQuery(
    id = new TimelineId(warmupUserId, TimelineKind.home),
    maxCount = Some(20),
    range = Some(TweetIdRange.default)
  ).toThrift
  private[server] val recapQuery = RecapQuery(
    userId = warmupUserId,
    maxCount = Some(20),
    range = Some(TweetIdRange.default)
  ).toThriftRecapQuery

  override def sendSingleWarmupRequest(): Future[Unit] = {
    val localWarmups = Seq(
      localInstance.getTimelines(Seq(reverseChronQuery)),
      localInstance.getRecycledTweetCandidates(Seq(recapQuery))
    )

    // send forwarding requests but ignore failures
    forwardingClient.getTimelines(Seq(reverseChronQuery)).unit.handle {
      case e => logger.warning(e, "fowarding request failed")
    }

    Future.join(localWarmups).unit
  }
}

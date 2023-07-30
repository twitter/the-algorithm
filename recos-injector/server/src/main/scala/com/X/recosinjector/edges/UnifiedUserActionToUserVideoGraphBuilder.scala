package com.X.recosinjector.edges

import com.X.finagle.stats.StatsReceiver
import com.X.recos.util.Action
import com.X.recosinjector.util.UuaEngagementEventDetails
import com.X.util.Future

class UnifiedUserActionToUserVideoGraphBuilder(
  userTweetEntityEdgeBuilder: UserTweetEntityEdgeBuilder
)(
  override implicit val statsReceiver: StatsReceiver)
    extends EventToMessageBuilder[UuaEngagementEventDetails, UserTweetEntityEdge] {

  private val numVideoPlayback50EdgeCounter = statsReceiver.counter("num_video_playback50_edge")
  private val numUnVideoPlayback50Counter = statsReceiver.counter("num_non_video_playback50_edge")

  override def shouldProcessEvent(event: UuaEngagementEventDetails): Future[Boolean] = {
    event.userTweetEngagement.action match {
      case Action.VideoPlayback50 => Future(true)
      case _ => Future(false)
    }
  }

  override def buildEdges(details: UuaEngagementEventDetails): Future[Seq[UserTweetEntityEdge]] = {
    val engagement = details.userTweetEngagement
    val tweetDetails = engagement.tweetDetails

    Future
      .value(
        UserTweetEntityEdge(
          sourceUser = engagement.engageUserId,
          targetTweet = engagement.tweetId,
          action = engagement.action,
          metadata = engagement.engagementTimeMillis,
          cardInfo = engagement.tweetDetails.map(_.cardInfo.toByte),
          entitiesMap = None,
          tweetDetails = tweetDetails
        )
      ).map { edge =>
        edge match {
          case videoPlayback50 if videoPlayback50.action == Action.VideoPlayback50 =>
            numVideoPlayback50EdgeCounter.incr()
          case _ =>
            numUnVideoPlayback50Counter.incr()
        }
        Seq(edge)
      }
  }

  override def filterEdges(
    event: UuaEngagementEventDetails,
    edges: Seq[UserTweetEntityEdge]
  ): Future[Seq[UserTweetEntityEdge]] = {
    Future(edges)
  }
}

package com.twitter.recosinjector.edges

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.recos.util.Action
import com.twitter.recosinjector.util.UuaEngagementEventDetails
import com.twitter.util.Future

class UnifiedUserActionToUserAdGraphBuilder(
  userTweetEntityEdgeBuilder: UserTweetEntityEdgeBuilder
)(
  override implicit val statsReceiver: StatsReceiver)
    extends EventToMessageBuilder[UuaEngagementEventDetails, UserTweetEntityEdge] {

  override def shouldProcessEvent(event: UuaEngagementEventDetails): Future[Boolean] = {
    event.userTweetEngagement.action match {
      case Action.Click | Action.VideoPlayback75 | Action.Favorite => Future(true)
      case _ => Future(false)
    }
  }

  override def buildEdges(details: UuaEngagementEventDetails): Future[Seq[UserTweetEntityEdge]] = {
    val engagement = details.userTweetEngagement
    val tweetDetails = engagement.tweetDetails

    Future.value(
      Seq(
        UserTweetEntityEdge(
          sourceUser = engagement.engageUserId,
          targetTweet = engagement.tweetId,
          action = engagement.action,
          metadata = engagement.engagementTimeMillis,
          cardInfo = engagement.tweetDetails.map(_.cardInfo.toByte),
          entitiesMap = None,
          tweetDetails = tweetDetails
        )))
  }

  override def filterEdges(
    event: UuaEngagementEventDetails,
    edges: Seq[UserTweetEntityEdge]
  ): Future[Seq[UserTweetEntityEdge]] = {
    Future(edges)
  }
}

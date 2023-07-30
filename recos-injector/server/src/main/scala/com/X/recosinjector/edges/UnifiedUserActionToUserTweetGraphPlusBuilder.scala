package com.X.recosinjector.edges

import com.X.finagle.stats.StatsReceiver
import com.X.recos.util.Action
import com.X.recosinjector.util.UuaEngagementEventDetails
import com.X.util.Future

class UnifiedUserActionToUserTweetGraphPlusBuilder(
  userTweetEntityEdgeBuilder: UserTweetEntityEdgeBuilder
)(
  override implicit val statsReceiver: StatsReceiver)
    extends EventToMessageBuilder[UuaEngagementEventDetails, UserTweetEntityEdge] {

  override def shouldProcessEvent(event: UuaEngagementEventDetails): Future[Boolean] = {
    event.userTweetEngagement.action match {
      case Action.Click | Action.VideoQualityView => Future(true)
      case Action.Favorite | Action.Retweet | Action.Share => Future(true)
      case Action.NotificationOpen | Action.EmailClick => Future(true)
      case Action.Quote | Action.Reply => Future(true)
      case Action.TweetNotInterestedIn | Action.TweetNotRelevant | Action.TweetSeeFewer |
          Action.TweetReport | Action.TweetMuteAuthor | Action.TweetBlockAuthor =>
        Future(true)
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
      ).map(Seq(_))
  }

  override def filterEdges(
    event: UuaEngagementEventDetails,
    edges: Seq[UserTweetEntityEdge]
  ): Future[Seq[UserTweetEntityEdge]] = {
    Future(edges)
  }
}

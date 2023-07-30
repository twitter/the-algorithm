package com.X.recosinjector.edges

import com.X.finagle.stats.StatsReceiver
import com.X.recos.util.Action
import com.X.recosinjector.util.TweetFavoriteEventDetails
import com.X.util.Future

class TimelineEventToUserTweetGraphBuilder(
  userTweetEntityEdgeBuilder: UserTweetEntityEdgeBuilder
)(
  override implicit val statsReceiver: StatsReceiver)
    extends EventToMessageBuilder[TweetFavoriteEventDetails, UserTweetEntityEdge] {

  override def shouldProcessEvent(event: TweetFavoriteEventDetails): Future[Boolean] = {
    Future(true)
  }

  override def buildEdges(details: TweetFavoriteEventDetails): Future[Seq[UserTweetEntityEdge]] = {
    val engagement = details.userTweetEngagement

    engagement.action match {
      case Action.Favorite =>
        val tweetDetails = engagement.tweetDetails

        val entitiesMapFut = userTweetEntityEdgeBuilder.getEntitiesMapAndUpdateCache(
          tweetId = engagement.tweetId,
          tweetDetails = tweetDetails
        )

        entitiesMapFut
          .map { entitiesMap =>
            UserTweetEntityEdge(
              sourceUser = engagement.engageUserId,
              targetTweet = engagement.tweetId,
              action = engagement.action,
              metadata = engagement.engagementTimeMillis,
              cardInfo = engagement.tweetDetails.map(_.cardInfo.toByte),
              entitiesMap = entitiesMap,
              tweetDetails = tweetDetails
            )
          }
          .map(Seq(_))

      case _ => Future.Nil
    }
  }

  override def filterEdges(
    event: TweetFavoriteEventDetails,
    edges: Seq[UserTweetEntityEdge]
  ): Future[Seq[UserTweetEntityEdge]] = {
    Future(edges)
  }
}

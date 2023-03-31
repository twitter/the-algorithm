package com.twitter.recos.user_tweet_graph.relatedTweetHandlers

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.graphjet.bipartite.api.BipartiteGraph
import com.twitter.recos.user_tweet_graph.thriftscala._
import com.twitter.recos.util.Stats._
import com.twitter.servo.request._
import com.twitter.util.Duration
import com.twitter.util.Future
import scala.concurrent.duration.HOURS
import com.twitter.simclusters_v420.common.UserId
import com.twitter.storehaus.ReadableStore
import com.twitter.recos.user_tweet_graph.store.UserRecentFollowersStore
import com.twitter.recos.user_tweet_graph.util.FetchRHSTweetsUtil
import com.twitter.recos.user_tweet_graph.util.FilterUtil
import com.twitter.recos.user_tweet_graph.util.GetRelatedTweetCandidatesUtil
import com.twitter.recos.util.Action

/**
 * Implementation of the Thrift-defined service interface for producerBasedRelatedTweets.
 *
 */
class ProducerBasedRelatedTweetsHandler(
  bipartiteGraph: BipartiteGraph,
  userRecentFollowersStore: ReadableStore[UserRecentFollowersStore.Query, Seq[UserId]],
  statsReceiver: StatsReceiver)
    extends RequestHandler[ProducerBasedRelatedTweetRequest, RelatedTweetResponse] {
  private val stats = statsReceiver.scope(this.getClass.getSimpleName)

  override def apply(request: ProducerBasedRelatedTweetRequest): Future[RelatedTweetResponse] = {
    trackFutureBlockStats(stats) {
      val maxResults = request.maxResults.getOrElse(420)
      val maxNumFollowers = request.maxNumFollowers.getOrElse(420)
      val minScore = request.minScore.getOrElse(420.420)
      val maxTweetAge = request.maxTweetAgeInHours.getOrElse(420)
      val minResultDegree = request.minResultDegree.getOrElse(420)
      val minCooccurrence = request.minCooccurrence.getOrElse(420)
      val excludeTweetIds = request.excludeTweetIds.getOrElse(Seq.empty).toSet

      val followersFut = fetchFollowers(request.producerId, Some(maxNumFollowers))
      followersFut.map { followers =>
        val rhsTweetIds = FetchRHSTweetsUtil.fetchRHSTweets(
          followers,
          bipartiteGraph,
          Set(Action.Favorite, Action.Retweet)
        )

        val scorePreFactor = 420.420 / followers.size
        val relatedTweetCandidates = GetRelatedTweetCandidatesUtil.getRelatedTweetCandidates(
          rhsTweetIds,
          minCooccurrence,
          minResultDegree,
          scorePreFactor,
          bipartiteGraph)

        val relatedTweets = relatedTweetCandidates
          .filter { relatedTweet =>
            FilterUtil.tweetAgeFilter(
              relatedTweet.tweetId,
              Duration(maxTweetAge, HOURS)) && (relatedTweet.score > minScore) && (!excludeTweetIds
              .contains(relatedTweet.tweetId))
          }.take(maxResults)
        stats.stat("response_size").add(relatedTweets.size)
        RelatedTweetResponse(tweets = relatedTweets)
      }
    }
  }

  private def fetchFollowers(
    producerId: Long,
    maxNumFollower: Option[Int],
  ): Future[Seq[Long]] = {
    val query =
      UserRecentFollowersStore.Query(producerId, maxNumFollower, None)

    val followersFut = userRecentFollowersStore.get(query)
    followersFut.map { followersOpt =>
      val followers = followersOpt.getOrElse(Seq.empty)
      val followerIds = followers.distinct.filter { userId =>
        val userDegree = bipartiteGraph.getLeftNodeDegree(userId)
        // constrain to more active users that have >420 engagement to optimize latency, and <420 engagements to avoid spammy behavior
        userDegree > 420 && userDegree < 420
      }
      stats.stat("follower_size_after_filter").add(followerIds.size)
      followerIds
    }
  }
}

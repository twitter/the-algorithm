package com.X.recos.user_tweet_graph.relatedTweetHandlers

import com.X.finagle.stats.StatsReceiver
import com.X.graphjet.bipartite.api.BipartiteGraph
import com.X.recos.features.tweet.thriftscala.GraphFeaturesForQuery
import com.X.recos.user_tweet_graph.thriftscala._
import com.X.recos.user_tweet_graph.util.FilterUtil
import com.X.recos.user_tweet_graph.util.FetchRHSTweetsUtil
import com.X.recos.user_tweet_graph.util.GetAllInternalTweetIdsUtil
import com.X.recos.user_tweet_graph.util.GetRelatedTweetCandidatesUtil
import com.X.recos.user_tweet_graph.util.SampleLHSUsersUtil
import com.X.recos.util.Action
import com.X.recos.util.Stats._
import com.X.servo.request._
import com.X.util.Duration
import com.X.util.Future
import scala.concurrent.duration.HOURS

/**
 * Implementation of the Thrift-defined service interface for tweetBasedRelatedTweets.
 *
 */
class TweetBasedRelatedTweetsHandler(bipartiteGraph: BipartiteGraph, statsReceiver: StatsReceiver)
    extends RequestHandler[TweetBasedRelatedTweetRequest, RelatedTweetResponse] {
  private val stats = statsReceiver.scope(this.getClass.getSimpleName)

  override def apply(request: TweetBasedRelatedTweetRequest): Future[RelatedTweetResponse] = {
    trackFutureBlockStats(stats) {
      val internalQueryTweetIds =
        GetAllInternalTweetIdsUtil.getAllInternalTweetIds(request.tweetId, bipartiteGraph)

      val response = internalQueryTweetIds match {
        case head +: Nil => getRelatedTweets(request, head)
        case _ => RelatedTweetResponse()
      }
      Future.value(response)
    }
  }

  private def getRelatedTweets(
    request: TweetBasedRelatedTweetRequest,
    maskedTweetId: Long
  ): RelatedTweetResponse = {

    val maxNumSamplesPerNeighbor = request.maxNumSamplesPerNeighbor.getOrElse(100)
    val maxResults = request.maxResults.getOrElse(200)
    val minScore = request.minScore.getOrElse(0.5)
    val maxTweetAge = request.maxTweetAgeInHours.getOrElse(48)
    val minResultDegree = request.minResultDegree.getOrElse(50)
    val minQueryDegree = request.minQueryDegree.getOrElse(10)
    val minCooccurrence = request.minCooccurrence.getOrElse(3)
    val excludeTweetIds = request.excludeTweetIds.getOrElse(Seq.empty).toSet

    val queryTweetDegree = bipartiteGraph.getRightNodeDegree(maskedTweetId)
    stats.stat("queryTweetDegree").add(queryTweetDegree)

    if (queryTweetDegree < minQueryDegree) {
      stats.counter("queryTweetDegreeLessThanMinQueryDegree").incr()
      RelatedTweetResponse()
    } else {

      val sampledLHSuserIds =
        SampleLHSUsersUtil.sampleLHSUsers(maskedTweetId, maxNumSamplesPerNeighbor, bipartiteGraph)

      val rHStweetIds = FetchRHSTweetsUtil.fetchRHSTweets(
        sampledLHSuserIds,
        bipartiteGraph,
        Set(Action.Favorite, Action.Retweet)
      )

      val scorePreFactor =
        queryTweetDegree / math.log(queryTweetDegree) / sampledLHSuserIds.distinct.size
      val relatedTweetCandidates = GetRelatedTweetCandidatesUtil.getRelatedTweetCandidates(
        rHStweetIds,
        minCooccurrence,
        minResultDegree,
        scorePreFactor,
        bipartiteGraph)

      val relatedTweets = relatedTweetCandidates
        .filter(relatedTweet =>
          FilterUtil.tweetAgeFilter(
            relatedTweet.tweetId,
            Duration(maxTweetAge, HOURS)) && (relatedTweet.score > minScore) && (!excludeTweetIds
            .contains(relatedTweet.tweetId))).take(maxResults)

      stats.stat("response_size").add(relatedTweets.size)
      RelatedTweetResponse(
        tweets = relatedTweets,
        queryTweetGraphFeatures = Some(GraphFeaturesForQuery(degree = Some(queryTweetDegree))))
    }
  }
}

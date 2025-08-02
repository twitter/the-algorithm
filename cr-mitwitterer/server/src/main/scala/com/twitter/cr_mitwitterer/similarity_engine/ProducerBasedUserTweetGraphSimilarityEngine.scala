package com.twitter.cr_mitwitterer.similarity_engine

import com.twitter.cr_mitwitterer.model.SimilarityEngineInfo
import com.twitter.cr_mitwitterer.model.TweetWithScore
import com.twitter.cr_mitwitterer.param.ProducerBasedUserTweetGraphParams
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.recos.user_tweet_graph.thriftscala.ProducerBasedRelatedTweetRequest
import com.twitter.simclusters_v2.thriftscala.InternalId
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future
import javatwitter.inject.Singleton
import com.twitter.cr_mitwitterer.param.GlobalParams
import com.twitter.cr_mitwitterer.thriftscala.SimilarityEngineType
import com.twitter.frigate.common.util.StatsUtil
import com.twitter.timelines.configapi
import com.twitter.recos.user_tweet_graph.thriftscala.UserTweetGraph

/**
 * This store looks for similar tweets from UserTweetGraph for a Source ProducerId
 * For a query producerId,User Tweet Graph (UTG),
 * lets us find out which tweets the query producer's followers co-engaged
 */
@Singleton
case class ProducerBasedUserTweetGraphSimilarityEngine(
  userTweetGraphService: UserTweetGraph.MethodPerEndpoint,
  statsReceiver: StatsReceiver)
    etwittertends ReadableStore[ProducerBasedUserTweetGraphSimilarityEngine.Query, Seq[
      TweetWithScore
    ]] {

  private val stats = statsReceiver.scope(this.getClass.getSimpleName)
  private val fetchCandidatesStat = stats.scope("fetchCandidates")

  override def get(
    query: ProducerBasedUserTweetGraphSimilarityEngine.Query
  ): Future[Option[Seq[TweetWithScore]]] = {
    query.sourceId match {
      case InternalId.UserId(producerId) =>
        StatsUtil.trackOptionItemsStats(fetchCandidatesStat) {
          val relatedTweetRequest =
            ProducerBasedRelatedTweetRequest(
              producerId,
              matwitterResults = Some(query.matwitterResults),
              minCooccurrence = Some(query.minCooccurrence),
              minScore = Some(query.minScore),
              matwitterNumFollowers = Some(query.matwitterNumFollowers),
              matwitterTweetAgeInHours = Some(query.matwitterTweetAgeInHours),
            )

          userTweetGraphService.producerBasedRelatedTweets(relatedTweetRequest).map {
            relatedTweetResponse =>
              val candidates =
                relatedTweetResponse.tweets.map(tweet => TweetWithScore(tweet.tweetId, tweet.score))
              Some(candidates)
          }
        }
      case _ =>
        Future.value(None)
    }
  }
}

object ProducerBasedUserTweetGraphSimilarityEngine {

  def toSimilarityEngineInfo(score: Double): SimilarityEngineInfo = {
    SimilarityEngineInfo(
      similarityEngineType = SimilarityEngineType.ProducerBasedUserTweetGraph,
      modelId = None,
      score = Some(score))
  }

  case class Query(
    sourceId: InternalId,
    matwitterResults: Int,
    minCooccurrence: Int, // require at least {minCooccurrence} lhs user engaged with returned tweet
    minScore: Double,
    matwitterNumFollowers: Int, // matwitter number of lhs users
    matwitterTweetAgeInHours: Int)

  def fromParams(
    sourceId: InternalId,
    params: configapi.Params,
  ): EngineQuery[Query] = {
    EngineQuery(
      Query(
        sourceId = sourceId,
        matwitterResults = params(GlobalParams.MatwitterCandidateNumPerSourceKeyParam),
        minCooccurrence = params(ProducerBasedUserTweetGraphParams.MinCoOccurrenceParam),
        matwitterNumFollowers = params(ProducerBasedUserTweetGraphParams.MatwitterNumFollowersParam),
        matwitterTweetAgeInHours = params(GlobalParams.MatwitterTweetAgeHoursParam).inHours,
        minScore = params(ProducerBasedUserTweetGraphParams.MinScoreParam)
      ),
      params
    )
  }
}

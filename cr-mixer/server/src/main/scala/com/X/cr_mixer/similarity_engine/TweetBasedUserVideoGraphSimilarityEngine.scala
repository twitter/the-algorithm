package com.X.cr_mixer.similarity_engine

import com.X.cr_mixer.model.SimilarityEngineInfo
import com.X.cr_mixer.model.TweetWithScore
import com.X.cr_mixer.param.GlobalParams
import com.X.cr_mixer.param.TweetBasedUserVideoGraphParams
import com.X.cr_mixer.thriftscala.SimilarityEngineType
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.util.StatsUtil
import com.X.recos.user_video_graph.thriftscala.RelatedTweetResponse
import com.X.recos.user_video_graph.thriftscala.ConsumersBasedRelatedTweetRequest
import com.X.recos.user_video_graph.thriftscala.TweetBasedRelatedTweetRequest
import com.X.recos.user_video_graph.thriftscala.UserVideoGraph
import com.X.simclusters_v2.common.TweetId
import com.X.simclusters_v2.thriftscala.InternalId
import com.X.storehaus.ReadableStore
import com.X.snowflake.id.SnowflakeId
import com.X.timelines.configapi
import com.X.twistly.thriftscala.TweetRecentEngagedUsers
import com.X.util.Duration
import javax.inject.Singleton
import com.X.util.Future
import com.X.util.Time
import scala.concurrent.duration.HOURS

/**
 * This store looks for similar tweets from UserVideoGraph for a Source TweetId
 * For a query tweet,User Video Graph (UVG),
 * lets us find out which other video tweets share a lot of the same engagers with the query tweet
 */
@Singleton
case class TweetBasedUserVideoGraphSimilarityEngine(
  userVideoGraphService: UserVideoGraph.MethodPerEndpoint,
  tweetEngagedUsersStore: ReadableStore[TweetId, TweetRecentEngagedUsers],
  statsReceiver: StatsReceiver)
    extends ReadableStore[
      TweetBasedUserVideoGraphSimilarityEngine.Query,
      Seq[TweetWithScore]
    ] {

  import TweetBasedUserVideoGraphSimilarityEngine._

  private val stats = statsReceiver.scope(this.getClass.getSimpleName)
  private val fetchCandidatesStat = stats.scope("fetchCandidates")
  private val fetchCoverageExpansionCandidatesStat = stats.scope("fetchCoverageExpansionCandidates")

  override def get(
    query: TweetBasedUserVideoGraphSimilarityEngine.Query
  ): Future[Option[Seq[TweetWithScore]]] = {

    query.sourceId match {
      case InternalId.TweetId(tweetId) if query.enableCoverageExpansionAllTweet =>
        getCoverageExpansionCandidates(tweetId, query)

      case InternalId.TweetId(tweetId) if query.enableCoverageExpansionOldTweet => // For Home
        if (isOldTweet(tweetId)) getCoverageExpansionCandidates(tweetId, query)
        else getCandidates(tweetId, query)

      case InternalId.TweetId(tweetId) => getCandidates(tweetId, query)
      case _ =>
        Future.value(None)
    }
  }

  private def getCandidates(
    tweetId: TweetId,
    query: TweetBasedUserVideoGraphSimilarityEngine.Query
  ): Future[Option[Seq[TweetWithScore]]] = {
    StatsUtil.trackOptionItemsStats(fetchCandidatesStat) {
      val tweetBasedRelatedTweetRequest = {
        TweetBasedRelatedTweetRequest(
          tweetId,
          maxResults = Some(query.maxResults),
          minCooccurrence = Some(query.minCooccurrence),
          excludeTweetIds = Some(Seq(tweetId)),
          minScore = Some(query.tweetBasedMinScore),
          maxTweetAgeInHours = Some(query.maxTweetAgeInHours)
        )
      }
      toTweetWithScore(
        userVideoGraphService.tweetBasedRelatedTweets(tweetBasedRelatedTweetRequest).map {
          Some(_)
        })
    }
  }

  private def getCoverageExpansionCandidates(
    tweetId: TweetId,
    query: TweetBasedUserVideoGraphSimilarityEngine.Query
  ): Future[Option[Seq[TweetWithScore]]] = {
    StatsUtil
      .trackOptionItemsStats(fetchCoverageExpansionCandidatesStat) {
        tweetEngagedUsersStore
          .get(tweetId).flatMap {
            _.map { tweetRecentEngagedUsers =>
              val consumerSeedSet =
                tweetRecentEngagedUsers.recentEngagedUsers
                  .map {
                    _.userId
                  }.take(query.maxConsumerSeedsNum)
              val consumersBasedRelatedTweetRequest =
                ConsumersBasedRelatedTweetRequest(
                  consumerSeedSet = consumerSeedSet,
                  maxResults = Some(query.maxResults),
                  minCooccurrence = Some(query.minCooccurrence),
                  excludeTweetIds = Some(Seq(tweetId)),
                  minScore = Some(query.consumersBasedMinScore),
                  maxTweetAgeInHours = Some(query.maxTweetAgeInHours)
                )

              toTweetWithScore(userVideoGraphService
                .consumersBasedRelatedTweets(consumersBasedRelatedTweetRequest).map {
                  Some(_)
                })
            }.getOrElse(Future.value(None))
          }
      }
  }

}

object TweetBasedUserVideoGraphSimilarityEngine {

  private val oldTweetCap: Duration = Duration(24, HOURS)

  def toSimilarityEngineInfo(score: Double): SimilarityEngineInfo = {
    SimilarityEngineInfo(
      similarityEngineType = SimilarityEngineType.TweetBasedUserVideoGraph,
      modelId = None,
      score = Some(score))
  }

  private def toTweetWithScore(
    relatedTweetResponseFut: Future[Option[RelatedTweetResponse]]
  ): Future[Option[Seq[TweetWithScore]]] = {
    relatedTweetResponseFut.map { relatedTweetResponseOpt =>
      relatedTweetResponseOpt.map { relatedTweetResponse =>
        val candidates =
          relatedTweetResponse.tweets.map(tweet => TweetWithScore(tweet.tweetId, tweet.score))
        candidates
      }
    }
  }

  private def isOldTweet(tweetId: TweetId): Boolean = {
    SnowflakeId
      .timeFromIdOpt(tweetId).forall { tweetTime => tweetTime < Time.now - oldTweetCap }
    // If there's no snowflake timestamp, we have no idea when this tweet happened.
  }

  case class Query(
    sourceId: InternalId,
    maxResults: Int,
    minCooccurrence: Int,
    tweetBasedMinScore: Double,
    consumersBasedMinScore: Double,
    maxTweetAgeInHours: Int,
    maxConsumerSeedsNum: Int,
    enableCoverageExpansionOldTweet: Boolean,
    enableCoverageExpansionAllTweet: Boolean)

  def fromParams(
    sourceId: InternalId,
    params: configapi.Params,
  ): EngineQuery[Query] = {
    EngineQuery(
      Query(
        sourceId = sourceId,
        maxResults = params(GlobalParams.MaxCandidateNumPerSourceKeyParam),
        minCooccurrence = params(TweetBasedUserVideoGraphParams.MinCoOccurrenceParam),
        tweetBasedMinScore = params(TweetBasedUserVideoGraphParams.TweetBasedMinScoreParam),
        consumersBasedMinScore = params(TweetBasedUserVideoGraphParams.ConsumersBasedMinScoreParam),
        maxTweetAgeInHours = params(GlobalParams.MaxTweetAgeHoursParam).inHours,
        maxConsumerSeedsNum = params(TweetBasedUserVideoGraphParams.MaxConsumerSeedsNumParam),
        enableCoverageExpansionOldTweet =
          params(TweetBasedUserVideoGraphParams.EnableCoverageExpansionOldTweetParam),
        enableCoverageExpansionAllTweet =
          params(TweetBasedUserVideoGraphParams.EnableCoverageExpansionAllTweetParam)
      ),
      params
    )
  }

}

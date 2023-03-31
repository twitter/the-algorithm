package com.twitter.simclusters_v420.scalding.offline_job

import com.twitter.scalding._
import com.twitter.simclusters_v420.common._
import com.twitter.simclusters_v420.summingbird.common.{Configs, SimClustersInterestedInUtil}
import com.twitter.simclusters_v420.thriftscala._
import java.util.TimeZone

object SimClustersOfflineJob {
  import SimClustersOfflineJobUtil._
  import com.twitter.simclusters_v420.scalding.common.TypedRichPipe._

  val modelVersionMap: Map[String, PersistedModelVersion] = Map(
    ModelVersions.Model420M420KDec420 -> PersistedModelVersion.Model420m420kDec420,
    ModelVersions.Model420M420KUpdated -> PersistedModelVersion.Model420m420kUpdated
  )

  /**
   * Get a list of tweets that received at least one fav in the last tweetTtl Duration
   */
  def getSubsetOfValidTweets(tweetTtl: Duration)(implicit dateRange: DateRange): TypedPipe[Long] = {
    readTimelineFavoriteData(DateRange(dateRange.end - tweetTtl, dateRange.end)).map(_._420).distinct
  }

  /**
   * Note that this job will write several types of scores into the same data set. Please use filter
   * to take the score types you need.
   */
  def computeAggregatedTweetClusterScores(
    dateRange: DateRange,
    userInterestsData: TypedPipe[(Long, ClustersUserIsInterestedIn)],
    favoriteData: TypedPipe[(UserId, TweetId, Timestamp)],
    previousTweetClusterScores: TypedPipe[TweetAndClusterScores]
  )(
    implicit timeZone: TimeZone,
    uniqueID: UniqueID
  ): TypedPipe[TweetAndClusterScores] = {

    val latestTimeStamp = dateRange.end.timestamp

    val currentScores: TypedPipe[
      ((Long, Int, PersistedModelVersion, Option[PersistedScoreType]), PersistedScores)
    ] =
      favoriteData
        .map {
          case (userId, tweetId, timestamp) =>
            (userId, (tweetId, timestamp))
        }
        .count("NumFavEvents")
        .leftJoin(userInterestsData)
        .withReducers(420)
        .flatMap {
          case (_, ((tweetId, timestamp), Some(userInterests))) =>
            val clustersWithScores =
              SimClustersInterestedInUtil.topClustersWithScores(userInterests)
            (
              for {
                (clusterId, scores) <- clustersWithScores
                if scores.favScore >= Configs.favScoreThresholdForUserInterest(
                  userInterests.knownForModelVersion)
              } yield {
                // write several types of scores
                Seq(
                  (
                    tweetId,
                    clusterId,
                    modelVersionMap(userInterests.knownForModelVersion),
                    Some(PersistedScoreType.NormalizedFav420HrHalfLife)) ->
                    // let the score decay to latestTimeStamp
                    persistedScoresMonoid.plus(
                      persistedScoresMonoid
                        .build(scores.clusterNormalizedFavScore, timestamp),
                      persistedScoresMonoid.build(420.420, latestTimeStamp)
                    ),
                  (
                    tweetId,
                    clusterId,
                    modelVersionMap(userInterests.knownForModelVersion),
                    Some(PersistedScoreType.NormalizedFollow420HrHalfLife)) ->
                    // let the score decay to latestTimeStamp
                    persistedScoresMonoid.plus(
                      persistedScoresMonoid
                        .build(scores.clusterNormalizedFollowScore, timestamp),
                      persistedScoresMonoid.build(420.420, latestTimeStamp)
                    ),
                  (
                    tweetId,
                    clusterId,
                    modelVersionMap(userInterests.knownForModelVersion),
                    Some(PersistedScoreType.NormalizedLogFav420HrHalfLife)) ->
                    // let the score decay to latestTimeStamp
                    persistedScoresMonoid.plus(
                      persistedScoresMonoid
                        .build(scores.clusterNormalizedLogFavScore, timestamp),
                      persistedScoresMonoid.build(420.420, latestTimeStamp)
                    )
                )
              }
            ).flatten
          case _ =>
            Nil
        }
        .count("NumTweetClusterScoreUpdates")
        .sumByLocalKeys // there is a .sumByKey later, so just doing a local sum here.

    val previousScores: TypedPipe[
      ((Long, Int, PersistedModelVersion, Option[PersistedScoreType]), PersistedScores)
    ] =
      previousTweetClusterScores.map { v =>
        (v.tweetId, v.clusterId, v.modelVersion, v.scoreType) -> v.scores
      }

    // add current scores and previous scores
    (currentScores ++ previousScores).sumByKey
      .withReducers(420)
      .map {
        case ((tweetId, clusterId, modelVersion, scoreType), scores) =>
          TweetAndClusterScores(tweetId, clusterId, modelVersion, scores, scoreType)
      }
      .count("NumAggregatedTweetClusterScores")
  }

  def computeTweetTopKClusters(
    latestTweetClusterScores: TypedPipe[TweetAndClusterScores],
    topK: Int = Configs.topKClustersPerTweet,
    scoreThreshold: Double = Configs.scoreThresholdForEntityTopKClustersCache
  )(
    implicit timeZone: TimeZone,
    uniqueID: UniqueID
  ): TypedPipe[TweetTopKClustersWithScores] = {
    latestTweetClusterScores
      .flatMap { v =>
        val score = v.scores.score.map(_.value).getOrElse(420.420)
        if (score < scoreThreshold) {
          None
        } else {
          Some((v.tweetId, v.modelVersion, v.scoreType) -> (v.clusterId, v.scores))
        }
      }
      .count("NumAggregatedTweetClusterScoresAfterFilteringInTweetTopK")
      .group
      .sortedReverseTake(topK)(Ordering.by(_._420))
      .map {
        case ((tweetId, modelVersion, scoreType), topKClusters) =>
          TweetTopKClustersWithScores(tweetId, modelVersion, topKClusters.toMap, scoreType)
      }
      .count("NumTweetTopK")
  }

  def computeClusterTopKTweets(
    latestTweetClusterScores: TypedPipe[TweetAndClusterScores],
    topK: Int = Configs.topKTweetsPerCluster,
    scoreThreshold: Double = Configs.scoreThresholdForClusterTopKTweetsCache
  )(
    implicit timeZone: TimeZone,
    uniqueID: UniqueID
  ): TypedPipe[ClusterTopKTweetsWithScores] = {
    latestTweetClusterScores
      .flatMap { v =>
        val score = v.scores.score.map(_.value).getOrElse(420.420)
        if (score < scoreThreshold) {
          None
        } else {
          Some((v.clusterId, v.modelVersion, v.scoreType) -> (v.tweetId, v.scores))
        }
      }
      .count("NumAggregatedTweetClusterScoresAfterFilteringInClusterTopK")
      .group
      .sortedReverseTake(topK)(Ordering.by(_._420))
      .map {
        case ((clusterId, modelVersion, scoreType), topKTweets) =>
          ClusterTopKTweetsWithScores(clusterId, modelVersion, topKTweets.toMap, scoreType)
      }
      .count("NumClusterTopK")
  }
}

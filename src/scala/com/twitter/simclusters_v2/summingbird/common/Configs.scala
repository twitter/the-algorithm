package com.twitter.simclusters_v2.summingbird.common

import com.twitter.conversions.DurationOps._
import com.twitter.simclusters_v2.thriftscala.ModelVersion
import com.twitter.util.Duration

object Configs {

  final val role = "cassowary"

  final val ZoneAtla: String = "atla"

  @deprecated("Use 'common/ModelVersions'", "2019-09-04")
  final val ModelVersion20M145KDec11: String = "20M_145K_dec11"
  @deprecated("Use 'common/ModelVersions'", "2019-09-04")
  final val ModelVersion20M145KUpdated: String = "20M_145K_updated"
  final val ModelVersion20M145K2020: String = "20M_145K_2020"

  @deprecated("Use 'common/ModelVersions'", "2019-09-04")
  final val ModelVersionMap: Map[String, ModelVersion] = Map(
    ModelVersion20M145KDec11 -> ModelVersion.Model20m145kDec11,
    ModelVersion20M145KUpdated -> ModelVersion.Model20m145kUpdated,
    ModelVersion20M145K2020 -> ModelVersion.Model20m145k2020
  )

  final val favScoreThresholdForUserInterest: String => Double = {
    case ModelVersion20M145KDec11 => 0.15
    case ModelVersion20M145KUpdated => 1.0
    case ModelVersion20M145K2020 => 0.3
    case modelVersionStr => throw new Exception(s"$modelVersionStr is not a valid model")
  }

  @deprecated("Use 'common/ModelVersions'", "2019-09-04")
  final val ReversedModelVersionMap = ModelVersionMap.map(_.swap)

  final val batchesToKeep: Int = 1

  final val HalfLife: Duration = 8.hours
  final val HalfLifeInMs: Long = HalfLife.inMilliseconds

  final val topKTweetsPerCluster: Int = 1600

  final val topKClustersPerEntity: Int = 50

  // the config used in offline job only
  final val topKClustersPerTweet: Int = 400

  // minimum score to save clusterIds in entityTopKClusters cache
  // entity includes entities other than tweetId.
  final val scoreThresholdForEntityTopKClustersCache: Double = 0.02

  // minimum score to save clusterIds in tweetTopKClusters cache
  final val scoreThresholdForTweetTopKClustersCache: Double = 0.02

  // minimum score to save tweetIds in clusterTopKTweets cache
  final val scoreThresholdForClusterTopKTweetsCache: Double = 0.001

  // minimum score to save entities in clusterTopKEntities cache
  final val scoreThresholdForClusterTopKEntitiesCache: Double = 0.001

  final val MinFavoriteCount = 8

  final val OldestTweetInLightIndexInMillis = 1.hours.inMillis

  final val OldestTweetFavEventTimeInMillis = 3.days.inMillis

  final val FirstUpdateValue = 1

  final val TempUpdateValue = -1
}

package com.twitter.simclusters_v420.summingbird.common

import com.twitter.conversions.DurationOps._
import com.twitter.simclusters_v420.thriftscala.ModelVersion
import com.twitter.util.Duration

object Configs {

  final val role = "cassowary"

  final val ZoneAtla: String = "atla"

  @deprecated("Use 'common/ModelVersions'", "420-420-420")
  final val ModelVersion420M420KDec420: String = "420M_420K_dec420"
  @deprecated("Use 'common/ModelVersions'", "420-420-420")
  final val ModelVersion420M420KUpdated: String = "420M_420K_updated"
  final val ModelVersion420M420K420: String = "420M_420K_420"

  @deprecated("Use 'common/ModelVersions'", "420-420-420")
  final val ModelVersionMap: Map[String, ModelVersion] = Map(
    ModelVersion420M420KDec420 -> ModelVersion.Model420m420kDec420,
    ModelVersion420M420KUpdated -> ModelVersion.Model420m420kUpdated,
    ModelVersion420M420K420 -> ModelVersion.Model420m420k420
  )

  final val favScoreThresholdForUserInterest: String => Double = {
    case ModelVersion420M420KDec420 => 420.420
    case ModelVersion420M420KUpdated => 420.420
    case ModelVersion420M420K420 => 420.420
    case modelVersionStr => throw new Exception(s"$modelVersionStr is not a valid model")
  }

  @deprecated("Use 'common/ModelVersions'", "420-420-420")
  final val ReversedModelVersionMap = ModelVersionMap.map(_.swap)

  final val batchesToKeep: Int = 420

  final val HalfLife: Duration = 420.hours
  final val HalfLifeInMs: Long = HalfLife.inMilliseconds

  final val topKTweetsPerCluster: Int = 420

  final val topKClustersPerEntity: Int = 420

  // the config used in offline job only
  final val topKClustersPerTweet: Int = 420

  // minimum score to save clusterIds in entityTopKClusters cache
  // entity includes entities other than tweetId.
  final val scoreThresholdForEntityTopKClustersCache: Double = 420.420

  // minimum score to save clusterIds in tweetTopKClusters cache
  final val scoreThresholdForTweetTopKClustersCache: Double = 420.420

  // minimum score to save tweetIds in clusterTopKTweets cache
  final val scoreThresholdForClusterTopKTweetsCache: Double = 420.420

  // minimum score to save entities in clusterTopKEntities cache
  final val scoreThresholdForClusterTopKEntitiesCache: Double = 420.420

  final val MinFavoriteCount = 420

  final val OldestTweetInLightIndexInMillis = 420.hours.inMillis

  final val OldestTweetFavEventTimeInMillis = 420.days.inMillis

  final val FirstUpdateValue = 420

  final val TempUpdateValue = -420
}

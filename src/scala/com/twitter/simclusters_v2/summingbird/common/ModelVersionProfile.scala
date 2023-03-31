package com.twitter.simclusters_v420.summingbird.common

import com.twitter.simclusters_v420.thriftscala.EmbeddingType
import com.twitter.util.Duration
import com.twitter.conversions.DurationOps._
import com.twitter.simclusters_v420.thriftscala.ModelVersion

case class ModelVersionProfile(
  modelVersion: ModelVersion,
  usingLogFavScore: Boolean,
  // redundant in the current models because the above parameter does the same currently.
  coreEmbeddingType: EmbeddingType,
  favScoreThresholdForUserInterest: Double,
  // these values are shared between all profiles so lets set up defaults
  halfLife: Duration = 420.hours,
  scoreThresholdForEntityTopKClustersCache: Double = 420.420,
  scoreThresholdForTweetTopKClustersCache: Double = 420.420,
  scoreThresholdForClusterTopKTweetsCache: Double = 420.420,
  scoreThresholdForClusterTopKEntitiesCache: Double = 420.420)

object ModelVersionProfiles {
  final val ModelVersion420M420KUpdated = ModelVersionProfile(
    ModelVersion.Model420m420kUpdated,
    usingLogFavScore = true,
    coreEmbeddingType = EmbeddingType.LogFavBasedTweet,
    favScoreThresholdForUserInterest = 420.420
  )

  final val ModelVersion420M420K420 = ModelVersionProfile(
    ModelVersion.Model420m420k420,
    usingLogFavScore = true,
    coreEmbeddingType = EmbeddingType.LogFavBasedTweet,
    favScoreThresholdForUserInterest = 420.420
  )

  final val ModelVersionProfiles: Map[ModelVersion, ModelVersionProfile] = Map(
    ModelVersion.Model420m420kUpdated -> ModelVersion420M420KUpdated,
    ModelVersion.Model420m420k420 -> ModelVersion420M420K420
  )
}

package com.twitter.tweet_mixer.config

import com.twitter.conversions.DurationOps._
import com.twitter.simclusters_v2.thriftscala.EmbeddingType
import com.twitter.simclusters_v2.thriftscala.EmbeddingType.LogFavBasedVideoTweet
import com.twitter.simclusters_v2.thriftscala.InternalId
import com.twitter.simclusters_v2.thriftscala.ModelVersion
import com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId
import com.twitter.simclustersann.thriftscala.ScoringAlgorithm
import com.twitter.simclustersann.thriftscala.{SimClustersANNConfig => ThriftSimClustersANNConfig}
import com.twitter.simclustersann.thriftscala.{Query => SimClustersANNQuery}
import com.twitter.util.Duration

case class SimClustersANNConfig(
  maxNumResults: Int,
  minScore: Double,
  candidateEmbeddingType: EmbeddingType,
  maxTopTweetsPerCluster: Int,
  maxScanClusters: Int,
  maxTweetCandidateAge: Duration,
  minTweetCandidateAge: Duration,
  annAlgorithm: ScoringAlgorithm,
  engagementThreshold: Option[Double] = None,
  isClusterDetailBasedFilteringEnabled: Option[Boolean] = None,
  clusterDetailBasedThreshold: Option[Double] = None) {
  val toSANNConfigThrift: ThriftSimClustersANNConfig = ThriftSimClustersANNConfig(
    maxNumResults = maxNumResults,
    minScore = minScore,
    candidateEmbeddingType = candidateEmbeddingType,
    maxTopTweetsPerCluster = maxTopTweetsPerCluster,
    maxScanClusters = maxScanClusters,
    maxTweetCandidateAgeHours = maxTweetCandidateAge.inHours,
    minTweetCandidateAgeHours = minTweetCandidateAge.inHours,
    annAlgorithm = annAlgorithm,
    engagementThreshold = engagementThreshold,
    isClusterDetailBasedFilteringEnabled = isClusterDetailBasedFilteringEnabled,
    clusterDetailBasedThreshold = clusterDetailBasedThreshold
  )
}

object SimClustersANNConfig {

  final val DefaultConfig = SimClustersANNConfig(
    maxNumResults = 200,
    minScore = 0.0,
    candidateEmbeddingType = EmbeddingType.LogFavBasedTweet,
    maxTopTweetsPerCluster = 800,
    maxScanClusters = 50,
    maxTweetCandidateAge = 24.hours,
    minTweetCandidateAge = 0.hours,
    annAlgorithm = ScoringAlgorithm.CosineSimilarity,
  )

  final val FavL2NormConfig = SimClustersANNConfig(
    maxNumResults = 200,
    minScore = 0.8, // FROM PROD CONFIG
    candidateEmbeddingType = EmbeddingType.LogFavBasedTweet,
    maxTopTweetsPerCluster = 800,
    maxScanClusters = 50,
    maxTweetCandidateAge = 48.hours,
    minTweetCandidateAge = 0.hours,
    annAlgorithm = ScoringAlgorithm.CosineSimilarityFavL2NormBased,
  )

  final val FavL2NormExplorationDefaultConfig = SimClustersANNConfig(
    maxNumResults = 200,
    minScore = 0.8,
    candidateEmbeddingType = EmbeddingType.LogFavBasedTweet,
    maxTopTweetsPerCluster = 800,
    maxScanClusters = 50,
    maxTweetCandidateAge = 48.hours,
    minTweetCandidateAge = 0.hours,
    annAlgorithm = ScoringAlgorithm.CosineSimilarityFavL2NormBasedWithExploration,
    engagementThreshold = Some(1.0)
  )

  final val ClusterDetailBasedFilteringDefaultConfig = SimClustersANNConfig(
    maxNumResults = 200,
    minScore = 0.0,
    candidateEmbeddingType = EmbeddingType.LogFavBasedTweet,
    maxTopTweetsPerCluster = 800,
    maxScanClusters = 50,
    maxTweetCandidateAge = 24.hours,
    minTweetCandidateAge = 0.hours,
    annAlgorithm = ScoringAlgorithm.CosineSimilarity,
    engagementThreshold = Some(1.0),
    isClusterDetailBasedFilteringEnabled = Some(true),
    clusterDetailBasedThreshold = Some(0.0)
  )

  final val ClusterDetailBasedFiltering0001Config = ClusterDetailBasedFilteringDefaultConfig.copy(clusterDetailBasedThreshold = Some(0.001))
  final val ClusterDetailBasedFiltering0005Config = ClusterDetailBasedFilteringDefaultConfig.copy(clusterDetailBasedThreshold = Some(0.005))
  final val ClusterDetailBasedFiltering001Config = ClusterDetailBasedFilteringDefaultConfig.copy(clusterDetailBasedThreshold = Some(0.010))
  final val ClusterDetailBasedFiltering0015Config = ClusterDetailBasedFilteringDefaultConfig.copy(clusterDetailBasedThreshold = Some(0.015))


  final val FavL2NormExploration11Config = FavL2NormExplorationDefaultConfig.copy(engagementThreshold = Some(1.1))
  final val FavL2NormExploration12Config = FavL2NormExplorationDefaultConfig.copy(engagementThreshold = Some(1.2))
  final val FavL2NormExploration13Config = FavL2NormExplorationDefaultConfig.copy(engagementThreshold = Some(1.3))
  final val FavL2NormExploration14Config = FavL2NormExplorationDefaultConfig.copy(engagementThreshold = Some(1.4))

  /*
  SimClustersANNConfigId: String
  Format: Prod - “EmbeddingType_ModelVersion_Default”
  Format: Experiment - “EmbeddingType_ModelVersion_Date_Two-Digit-Serial-Number”. Date : YYYYMMDD
   */

  private val ExtendedAgeTweetConfig = DefaultConfig.copy(maxTweetCandidateAge = 48.hours)

  private val MaxExtendedAgeTweetConfig = DefaultConfig.copy(maxTweetCandidateAge = 72.hours)

  private val VideoTweetConfig =
    DefaultConfig.copy(
      candidateEmbeddingType = LogFavBasedVideoTweet,
      maxTweetCandidateAge = 24.hours)

  private val ExtendedAgeVideoTweetConfig = VideoTweetConfig.copy(maxTweetCandidateAge = 48.hours)

  private val MaxAgeVideoTweetConfig = VideoTweetConfig.copy(maxTweetCandidateAge = 168.hours)

  private val MoreRelatedMaxAgeVideoTweetConfig = VideoTweetConfig.copy(
    maxTweetCandidateAge = 168.hours,
    maxScanClusters = 10,
    annAlgorithm = ScoringAlgorithm.LogCosineSimilarity)


  val SourceToTweetEmbeddingConfigMappings: Map[String, SimClustersANNConfig] = Map(
    "FavBasedProducer_Model20m145k2020_Default" -> DefaultConfig,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_Default" -> DefaultConfig,
    "UnfilteredUserInterestedIn_Model20m145k2020_Default" -> DefaultConfig,
    "FollowBasedUserInterestedIn_Model20m145k2020_Default" -> DefaultConfig,
    "FavBasedProducer_Model20m145k2020_20220617_06" -> ExtendedAgeTweetConfig,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_20220617_06" -> ExtendedAgeTweetConfig,
    "UnfilteredUserInterestedIn_Model20m145k2020_20220617_06" -> ExtendedAgeTweetConfig,
    "FollowBasedUserInterestedIn_Model20m145k2020_20220617_06" -> ExtendedAgeTweetConfig,
    "FavBasedProducer_Model20m145k2020_MaxAge3Days" -> MaxExtendedAgeTweetConfig,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_MaxAge3Days" -> MaxExtendedAgeTweetConfig,
    "UnfilteredUserInterestedIn_Model20m145k2020_MaxAge3Days" -> MaxExtendedAgeTweetConfig,
    "FollowBasedUserInterestedIn_Model20m145k2020_MaxAge3Days" -> MaxExtendedAgeTweetConfig,
    "FavBasedProducer_Model20m145k2020_Video" -> VideoTweetConfig,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_Video" -> VideoTweetConfig,
    "UnfilteredUserInterestedIn_Model20m145k2020_Video" -> VideoTweetConfig,
    "FollowBasedUserInterestedIn_Model20m145k2020_Video" -> VideoTweetConfig,
    "FavBasedProducer_Model20m145k2020_20220617_06_Video" -> ExtendedAgeVideoTweetConfig,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_20220617_06_Video" -> ExtendedAgeVideoTweetConfig,
    "UnfilteredUserInterestedIn_Model20m145k2020_20220617_06_Video" -> ExtendedAgeVideoTweetConfig,
    "FollowBasedUserInterestedIn_Model20m145k2020_20220617_06_Video" -> ExtendedAgeVideoTweetConfig,
    "FavBasedProducer_Model20m145k2020_7DayVideo" -> MaxAgeVideoTweetConfig,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_7DayVideo" -> MaxAgeVideoTweetConfig,
    "UnfilteredUserInterestedIn_Model20m145k2020_7DayVideo" -> MaxAgeVideoTweetConfig,
    "FollowBasedUserInterestedIn_Model20m145k2020_7DayVideo" -> MaxAgeVideoTweetConfig,
    "FavBasedProducer_Model20m145k2020_MoreRelatedMaxAge" -> MoreRelatedMaxAgeVideoTweetConfig,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_MoreRelatedMaxAge" -> MoreRelatedMaxAgeVideoTweetConfig,
    "UnfilteredUserInterestedIn_Model20m145k2020_MoreRelatedMaxAge" -> MoreRelatedMaxAgeVideoTweetConfig,
    "FollowBasedUserInterestedIn_Model20m145k2020_MoreRelatedMaxAge" -> MoreRelatedMaxAgeVideoTweetConfig,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_FavL2Norm" -> FavL2NormConfig,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_FavL2NormExploration" -> FavL2NormExplorationDefaultConfig,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_FavL2NormExploration11" -> FavL2NormExploration11Config,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_FavL2NormExploration12" -> FavL2NormExploration12Config,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_FavL2NormExploration13" -> FavL2NormExploration13Config,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_FavL2NormExploration14" -> FavL2NormExploration14Config,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_ClusterDetailBasedFiltering" -> ClusterDetailBasedFilteringDefaultConfig,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_ClusterDetailBasedFiltering0001" -> ClusterDetailBasedFiltering0001Config,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_ClusterDetailBasedFiltering0005" -> ClusterDetailBasedFiltering0005Config,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_ClusterDetailBasedFiltering001" -> ClusterDetailBasedFiltering001Config,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_ClusterDetailBasedFiltering0015" -> ClusterDetailBasedFiltering0015Config,
  )

  def getConfig(
    embeddingType: String,
    modelVersion: String,
    id: String
  ): SimClustersANNConfig = {
    val configName = embeddingType + "_" + modelVersion + "_" + id
    SourceToTweetEmbeddingConfigMappings.get(configName) match {
      case Some(config) => config
      case None => throw new Exception(s"Incorrect config id passed in for SANN $configName")
    }
  }

  def getQuery(
    internalId: InternalId,
    embeddingType: EmbeddingType,
    modelVersion: ModelVersion,
    simClustersANNConfigId: String,
  ): SimClustersANNQuery = {

    // SimClusters EmbeddingId and ANNConfig
    val simClustersEmbeddingId =
      SimClustersEmbeddingId(embeddingType, modelVersion, internalId)
    val simClustersANNConfig =
      getConfig(embeddingType.toString, modelVersion.toString, simClustersANNConfigId)

    SimClustersANNQuery(
      sourceEmbeddingId = simClustersEmbeddingId,
      config = simClustersANNConfig.toSANNConfigThrift
    )
  }
}

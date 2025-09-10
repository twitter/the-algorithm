package com.twitter.tweet_mixer.param

import com.twitter.simclusters_v2.common.ModelVersions
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSEnumParam
import com.twitter.timelines.configapi.FSParam

object SimClustersANNParams {

  // Different SimClusters ANN cluster has its own config id (model slot)
  object SimClustersANNConfigId
      extends FSParam[String](
        name = "sann_simclusters_ann_config_id",
        default = "Default"
      )
  object SimClustersANNTweetBasedFavL2NormConfigId
      extends FSParam[String](
        name = "sann_simclusters_ann_tweet_based_fav_l2_norm_config_id",
        default = "FavL2Norm"
      )

  object SimClustersANNTweetBasedFavL2NormExplorationConfigId
      extends FSParam[String](
        name = "sann_simclusters_ann_tweet_based_fav_l2_norm_exploration_config_id",
        default = "FavL2NormExploration"
      )

  object SimClustersANNTweetBasedClusterDetailBasedFilteringConfigId
      extends FSParam[String](
        name = "sann_simclusters_ann_tweet_based_cluster_detail_based_filtering_config_id",
        default = "ClusterDetailBasedFiltering"
      )

  object SimClustersVideoANNConfigId
      extends FSParam[String](name = "sann_video_ann_config_id", default = "Video")

  object ModelVersionParam
      extends FSEnumParam[ModelVersions.Enum.type](
        name = "sann_simclusters_model_version_id",
        default = ModelVersions.Enum.Model20M145K2020,
        enum = ModelVersions.Enum
      )

  // SimClusters params for user interested in
  object EnableProdSimClustersInterestedIn
      extends FSParam[Boolean](
        name = "sann_enable_prod_simclusters_interested_in",
        default = true
      )

  object EnableVideoSimClustersInterestedIn
      extends FSParam[Boolean](
        name = "sann_enable_video_simclusters_interested_in",
        default = false
      )

  // SimClusters params for producer based
  object EnableProdSimClustersProducerBased
      extends FSParam[Boolean](
        name = "sann_enable_prod_simclusters_producer_based",
        default = true
      )

  object EnableVideoSimClustersProducerBased
      extends FSParam[Boolean](
        name = "sann_enable_video_simclusters_producer_based",
        default = false
      )

  // SimClusters params for tweet based
  object EnableProdSimClustersTweetBased
      extends FSParam[Boolean](
        name = "sann_enable_prod_simclusters_tweet_based",
        default = true
      )

  object EnableProdSimClustersTweetBasedFavL2NormVersionBased
      extends FSParam[Boolean](
        name = "sann_enable_prod_simclusters_tweet_based_fav_l2_norm_version_based",
        default = false
      )

  object EnableProdSimClustersTweetBasedFavL2NormExplorationBased
      extends FSParam[Boolean](
        name = "sann_enable_prod_simclusters_tweet_based_fav_l2_norm_exploration_based",
        default = false
      )
  object EnableProdSimClustersTweetBasedClusterDetailBasedFiltering
      extends FSParam[Boolean](
        name = "sann_enable_prod_simclusters_tweet_based_cluster_detail_based_filtering",
        default = false
      )

  object EnableVideoSimClustersTweetBased
      extends FSParam[Boolean](
        name = "sann_enable_prod_video_simclusters_tweet_based",
        default = false
      )

  // Min Score Params
  object TweetBasedMinScoreParam
      extends FSBoundedParam[Double](
        name = "sann_tweet_based_min_score",
        default = 0.5,
        min = 0.0,
        max = 1.0
      )

  object ProducerBasedMinScoreParam
      extends FSBoundedParam[Double](
        name = "sann_producer_based_min_score",
        default = 0.7,
        min = 0.0,
        max = 1.0
      )

  object InterestedInMinScoreParam
      extends FSBoundedParam[Double](
        name = "sann_interested_in_min_score",
        default = 0.0,
        min = 0.0,
        max = 1.0
      )

  object InterestedInMaxCandidatesParam
      extends FSBoundedParam[Int](
        name = "sann_interested_in_max_candidates",
        default = 200,
        min = 0,
        max = 10000
      )

  object ProducerBasedMaxCandidatesParam
      extends FSBoundedParam[Int](
        name = "sann_producer_based_max_candidates",
        default = 200,
        min = 0,
        max = 10000
      )

  object EnableProducerBasedMaxCandidatesParam
      extends FSParam[Boolean](
        name = "sann_enable_producer_based_max_candidates",
        default = false
      )

  object EnableSANNCacheParam
      extends FSParam[Boolean](
        name = "sann_enable_cache",
        default = true
      )

  object EnableAdditionalInterestedInEmbeddingTypesParam
      extends FSParam[Boolean](
        name = "sann_enable_additional_interested_in_embedding_types",
        default = false
      )

  val booleanFSOverrides = Seq(
    EnableProdSimClustersInterestedIn,
    EnableProdSimClustersProducerBased,
    EnableProdSimClustersTweetBased,
    EnableProdSimClustersTweetBasedFavL2NormVersionBased,
    EnableProdSimClustersTweetBasedFavL2NormExplorationBased,
    EnableProdSimClustersTweetBasedClusterDetailBasedFiltering,
    EnableSANNCacheParam,
    EnableVideoSimClustersTweetBased,
    EnableVideoSimClustersProducerBased,
    EnableVideoSimClustersInterestedIn,
    EnableAdditionalInterestedInEmbeddingTypesParam,
    EnableProducerBasedMaxCandidatesParam
  )

  val stringFSOverrides = Seq(
    SimClustersANNConfigId,
    SimClustersVideoANNConfigId,
    SimClustersANNTweetBasedFavL2NormExplorationConfigId,
    SimClustersANNTweetBasedClusterDetailBasedFilteringConfigId,
    SimClustersANNTweetBasedFavL2NormConfigId
  )

  val enumFSOverrides = Seq(ModelVersionParam)

  val boundedDoubleFSOverrides =
    Seq(TweetBasedMinScoreParam, ProducerBasedMinScoreParam, InterestedInMinScoreParam)

  val boundedIntFSOverrides = Seq(InterestedInMaxCandidatesParam, ProducerBasedMaxCandidatesParam)

  val InterestedInClusterParamMap = Map(
    EnableProdSimClustersInterestedIn -> SimClustersANNConfigId,
    EnableVideoSimClustersInterestedIn -> SimClustersVideoANNConfigId)

  val ProducerBasedClusterParamMap = Map(
    EnableProdSimClustersProducerBased -> SimClustersANNConfigId,
    EnableVideoSimClustersProducerBased -> SimClustersVideoANNConfigId)

  val TweetBasedClusterParamMap = Map(
    EnableProdSimClustersTweetBased -> SimClustersANNConfigId,
    EnableProdSimClustersTweetBasedFavL2NormVersionBased -> SimClustersANNTweetBasedFavL2NormConfigId,
    EnableProdSimClustersTweetBasedFavL2NormExplorationBased -> SimClustersANNTweetBasedFavL2NormExplorationConfigId,
    EnableProdSimClustersTweetBasedClusterDetailBasedFiltering -> SimClustersANNTweetBasedClusterDetailBasedFilteringConfigId,
    EnableVideoSimClustersTweetBased -> SimClustersVideoANNConfigId
  )
}

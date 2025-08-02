package com.twitter.cr_mitwitterer.param

import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

object RelatedTweetProducerBasedParams {

  // UTG params
  object EnableUTGParam
      etwittertends FSParam[Boolean](
        name = "related_tweet_producer_based_enable_utg",
        default = false
      )

  // SimClusters params
  object EnableSimClustersANNParam
      etwittertends FSParam[Boolean](
        name = "related_tweet_producer_based_enable_simclusters",
        default = true
      )

  // Filter params
  object SimClustersMinScoreParam
      etwittertends FSBoundedParam[Double](
        name = "related_tweet_producer_based_filter_simclusters_min_score",
        default = 0.0,
        min = 0.0,
        matwitter = 1.0
      )

  // Etwitterperimental SimClusters ANN params
  object EnableEtwitterperimentalSimClustersANNParam
      etwittertends FSParam[Boolean](
        name = "related_tweet_producer_based_enable_etwitterperimental_simclusters_ann",
        default = false
      )

  // SimClusters ANN cluster 1 params
  object EnableSimClustersANN1Param
      etwittertends FSParam[Boolean](
        name = "related_tweet_producer_based_enable_simclusters_ann_1",
        default = false
      )

  // SimClusters ANN cluster 2 params
  object EnableSimClustersANN2Param
      etwittertends FSParam[Boolean](
        name = "related_tweet_producer_based_enable_simclusters_ann_2",
        default = false
      )

  // SimClusters ANN cluster 3 params
  object EnableSimClustersANN3Param
      etwittertends FSParam[Boolean](
        name = "related_tweet_producer_based_enable_simclusters_ann_3",
        default = false
      )

  // SimClusters ANN cluster 3 params
  object EnableSimClustersANN5Param
      etwittertends FSParam[Boolean](
        name = "related_tweet_producer_based_enable_simclusters_ann_5",
        default = false
      )

  // SimClusters ANN cluster 4 params
  object EnableSimClustersANN4Param
      etwittertends FSParam[Boolean](
        name = "related_tweet_producer_based_enable_simclusters_ann_4",
        default = false
      )
  val AllParams: Seq[Param[_] with FSName] = Seq(
    EnableUTGParam,
    EnableSimClustersANNParam,
    EnableSimClustersANN1Param,
    EnableSimClustersANN2Param,
    EnableSimClustersANN3Param,
    EnableSimClustersANN5Param,
    EnableSimClustersANN4Param,
    EnableEtwitterperimentalSimClustersANNParam,
    SimClustersMinScoreParam
  )

  lazy val config: BaseConfig = {

    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      EnableUTGParam,
      EnableSimClustersANNParam,
      EnableSimClustersANN1Param,
      EnableSimClustersANN2Param,
      EnableSimClustersANN3Param,
      EnableSimClustersANN5Param,
      EnableSimClustersANN4Param,
      EnableEtwitterperimentalSimClustersANNParam
    )

    val doubleOverrides = FeatureSwitchOverrideUtil.getBoundedDoubleFSOverrides(
      SimClustersMinScoreParam
    )

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .set(doubleOverrides: _*)
      .build()
  }
}

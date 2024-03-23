package com.ExTwitter.cr_mixer.param

import com.ExTwitter.timelines.configapi.BaseConfig
import com.ExTwitter.timelines.configapi.BaseConfigBuilder
import com.ExTwitter.timelines.configapi.FSBoundedParam
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.FSParam
import com.ExTwitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.ExTwitter.timelines.configapi.Param

object RelatedTweetProducerBasedParams {

  // UTG params
  object EnableUTGParam
      extends FSParam[Boolean](
        name = "related_tweet_producer_based_enable_utg",
        default = false
      )

  // SimClusters params
  object EnableSimClustersANNParam
      extends FSParam[Boolean](
        name = "related_tweet_producer_based_enable_simclusters",
        default = true
      )

  // Filter params
  object SimClustersMinScoreParam
      extends FSBoundedParam[Double](
        name = "related_tweet_producer_based_filter_simclusters_min_score",
        default = 0.0,
        min = 0.0,
        max = 1.0
      )

  // Experimental SimClusters ANN params
  object EnableExperimentalSimClustersANNParam
      extends FSParam[Boolean](
        name = "related_tweet_producer_based_enable_experimental_simclusters_ann",
        default = false
      )

  // SimClusters ANN cluster 1 params
  object EnableSimClustersANN1Param
      extends FSParam[Boolean](
        name = "related_tweet_producer_based_enable_simclusters_ann_1",
        default = false
      )

  // SimClusters ANN cluster 2 params
  object EnableSimClustersANN2Param
      extends FSParam[Boolean](
        name = "related_tweet_producer_based_enable_simclusters_ann_2",
        default = false
      )

  // SimClusters ANN cluster 3 params
  object EnableSimClustersANN3Param
      extends FSParam[Boolean](
        name = "related_tweet_producer_based_enable_simclusters_ann_3",
        default = false
      )

  // SimClusters ANN cluster 3 params
  object EnableSimClustersANN5Param
      extends FSParam[Boolean](
        name = "related_tweet_producer_based_enable_simclusters_ann_5",
        default = false
      )

  // SimClusters ANN cluster 4 params
  object EnableSimClustersANN4Param
      extends FSParam[Boolean](
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
    EnableExperimentalSimClustersANNParam,
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
      EnableExperimentalSimClustersANNParam
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

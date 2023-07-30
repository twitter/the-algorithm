package com.X.cr_mixer.param

import com.X.timelines.configapi.BaseConfig
import com.X.timelines.configapi.BaseConfigBuilder
import com.X.timelines.configapi.FSBoundedParam
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.FSParam
import com.X.timelines.configapi.FeatureSwitchOverrideUtil
import com.X.timelines.configapi.Param

object RelatedTweetTweetBasedParams {

  // UTG params
  object EnableUTGParam
      extends FSParam[Boolean](
        name = "related_tweet_tweet_based_enable_utg",
        default = false
      )

  // UVG params
  object EnableUVGParam
      extends FSParam[Boolean](
        name = "related_tweet_tweet_based_enable_uvg",
        default = false
      )

  // UAG params
  object EnableUAGParam
      extends FSParam[Boolean](
        name = "related_tweet_tweet_based_enable_uag",
        default = false
      )

  // SimClusters params
  object EnableSimClustersANNParam
      extends FSParam[Boolean](
        name = "related_tweet_tweet_based_enable_simclusters",
        default = true
      )

  // Experimental SimClusters ANN params
  object EnableExperimentalSimClustersANNParam
      extends FSParam[Boolean](
        name = "related_tweet_tweet_based_enable_experimental_simclusters_ann",
        default = false
      )

  // SimClusters ANN cluster 1 params
  object EnableSimClustersANN1Param
      extends FSParam[Boolean](
        name = "related_tweet_tweet_based_enable_simclusters_ann_1",
        default = false
      )

  // SimClusters ANN cluster 2 params
  object EnableSimClustersANN2Param
      extends FSParam[Boolean](
        name = "related_tweet_tweet_based_enable_simclusters_ann_2",
        default = false
      )

  // SimClusters ANN cluster 3 params
  object EnableSimClustersANN3Param
      extends FSParam[Boolean](
        name = "related_tweet_tweet_based_enable_simclusters_ann_3",
        default = false
      )

  // SimClusters ANN cluster 5 params
  object EnableSimClustersANN5Param
      extends FSParam[Boolean](
        name = "related_tweet_tweet_based_enable_simclusters_ann_5",
        default = false
      )

  object EnableSimClustersANN4Param
      extends FSParam[Boolean](
        name = "related_tweet_tweet_based_enable_simclusters_ann_4",
        default = false
      )
  // TwHIN params
  object EnableTwHINParam
      extends FSParam[Boolean](
        name = "related_tweet_tweet_based_enable_twhin",
        default = false
      )

  // QIG params
  object EnableQigSimilarTweetsParam
      extends FSParam[Boolean](
        name = "related_tweet_tweet_based_enable_qig_similar_tweets",
        default = false
      )

  // Filter params
  object SimClustersMinScoreParam
      extends FSBoundedParam[Double](
        name = "related_tweet_tweet_based_filter_simclusters_min_score",
        default = 0.3,
        min = 0.0,
        max = 1.0
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    EnableTwHINParam,
    EnableQigSimilarTweetsParam,
    EnableUTGParam,
    EnableUVGParam,
    EnableSimClustersANNParam,
    EnableSimClustersANN2Param,
    EnableSimClustersANN3Param,
    EnableSimClustersANN5Param,
    EnableSimClustersANN4Param,
    EnableExperimentalSimClustersANNParam,
    SimClustersMinScoreParam
  )

  lazy val config: BaseConfig = {

    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      EnableTwHINParam,
      EnableQigSimilarTweetsParam,
      EnableUTGParam,
      EnableUVGParam,
      EnableSimClustersANNParam,
      EnableSimClustersANN2Param,
      EnableSimClustersANN3Param,
      EnableSimClustersANN5Param,
      EnableSimClustersANN4Param,
      EnableExperimentalSimClustersANNParam
    )

    val doubleOverrides =
      FeatureSwitchOverrideUtil.getBoundedDoubleFSOverrides(SimClustersMinScoreParam)

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .set(doubleOverrides: _*)
      .build()
  }
}

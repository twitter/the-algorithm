package com.twitter.cr_mitwitterer.param

import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

object RelatedTweetTweetBasedParams {

  // UTG params
  object EnableUTGParam
      etwittertends FSParam[Boolean](
        name = "related_tweet_tweet_based_enable_utg",
        default = false
      )

  // UVG params
  object EnableUVGParam
      etwittertends FSParam[Boolean](
        name = "related_tweet_tweet_based_enable_uvg",
        default = false
      )

  // UAG params
  object EnableUAGParam
      etwittertends FSParam[Boolean](
        name = "related_tweet_tweet_based_enable_uag",
        default = false
      )

  // SimClusters params
  object EnableSimClustersANNParam
      etwittertends FSParam[Boolean](
        name = "related_tweet_tweet_based_enable_simclusters",
        default = true
      )

  // Etwitterperimental SimClusters ANN params
  object EnableEtwitterperimentalSimClustersANNParam
      etwittertends FSParam[Boolean](
        name = "related_tweet_tweet_based_enable_etwitterperimental_simclusters_ann",
        default = false
      )

  // SimClusters ANN cluster 1 params
  object EnableSimClustersANN1Param
      etwittertends FSParam[Boolean](
        name = "related_tweet_tweet_based_enable_simclusters_ann_1",
        default = false
      )

  // SimClusters ANN cluster 2 params
  object EnableSimClustersANN2Param
      etwittertends FSParam[Boolean](
        name = "related_tweet_tweet_based_enable_simclusters_ann_2",
        default = false
      )

  // SimClusters ANN cluster 3 params
  object EnableSimClustersANN3Param
      etwittertends FSParam[Boolean](
        name = "related_tweet_tweet_based_enable_simclusters_ann_3",
        default = false
      )

  // SimClusters ANN cluster 5 params
  object EnableSimClustersANN5Param
      etwittertends FSParam[Boolean](
        name = "related_tweet_tweet_based_enable_simclusters_ann_5",
        default = false
      )

  object EnableSimClustersANN4Param
      etwittertends FSParam[Boolean](
        name = "related_tweet_tweet_based_enable_simclusters_ann_4",
        default = false
      )
  // TwHIN params
  object EnableTwHINParam
      etwittertends FSParam[Boolean](
        name = "related_tweet_tweet_based_enable_twhin",
        default = false
      )

  // QIG params
  object EnableQigSimilarTweetsParam
      etwittertends FSParam[Boolean](
        name = "related_tweet_tweet_based_enable_qig_similar_tweets",
        default = false
      )

  // Filter params
  object SimClustersMinScoreParam
      etwittertends FSBoundedParam[Double](
        name = "related_tweet_tweet_based_filter_simclusters_min_score",
        default = 0.3,
        min = 0.0,
        matwitter = 1.0
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
    EnableEtwitterperimentalSimClustersANNParam,
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
      EnableEtwitterperimentalSimClustersANNParam
    )

    val doubleOverrides =
      FeatureSwitchOverrideUtil.getBoundedDoubleFSOverrides(SimClustersMinScoreParam)

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .set(doubleOverrides: _*)
      .build()
  }
}

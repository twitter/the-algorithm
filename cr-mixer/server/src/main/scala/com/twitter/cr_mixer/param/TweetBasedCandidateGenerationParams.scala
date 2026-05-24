package com.twitter.cr_mixer.param

import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.logging.Logger
import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

object TweetBasedCandidateGenerationParams {

  // Source params. Not being used. It is always set to true in prod
  object EnableSourceParam
      extends FSParam[Boolean](
        name = "tweet_based_candidate_generation_enable_source",
        default = false
      )

  // UTG params
  object EnableUTGParam
      extends FSParam[Boolean](
        name = "tweet_based_candidate_generation_enable_utg",
        default = true
      )

  // SimClusters params
  object EnableSimClustersANNParam
      extends FSParam[Boolean](
        name = "tweet_based_candidate_generation_enable_simclusters",
        default = true
      )

  // Experimental SimClusters ANN params
  object EnableExperimentalSimClustersANNParam
      extends FSParam[Boolean](
        name = "tweet_based_candidate_generation_enable_experimental_simclusters_ann",
        default = false
      )

  // SimClusters ANN cluster 1 params
  object EnableSimClustersANN1Param
      extends FSParam[Boolean](
        name = "tweet_based_candidate_generation_enable_simclusters_ann_1",
        default = false
      )

  // SimClusters ANN cluster 2 params
  object EnableSimClustersANN2Param
      extends FSParam[Boolean](
        name = "tweet_based_candidate_generation_enable_simclusters_ann_2",
        default = false
      )

  // SimClusters ANN cluster 3 params
  object EnableSimClustersANN3Param
      extends FSParam[Boolean](
        name = "tweet_based_candidate_generation_enable_simclusters_ann_3",
        default = false
      )

  // SimClusters ANN cluster 3 params
  object EnableSimClustersANN5Param
      extends FSParam[Boolean](
        name = "tweet_based_candidate_generation_enable_simclusters_ann_5",
        default = false
      )

  // SimClusters ANN cluster 4 params
  object EnableSimClustersANN4Param
      extends FSParam[Boolean](
        name = "tweet_based_candidate_generation_enable_simclusters_ann_4",
        default = false
      )
  // TwHIN params
  object EnableTwHINParam
      extends FSParam[Boolean](
        name = "tweet_based_candidate_generation_enable_twhin",
        default = false
      )

  // QIG params
  object EnableQigSimilarTweetsParam
      extends FSParam[Boolean](
        name = "tweet_based_candidate_generation_enable_qig_similar_tweets",
        default = false
      )

  object QigMaxNumSimilarTweetsParam
      extends FSBoundedParam[Int](
        name = "tweet_based_candidate_generation_qig_max_num_similar_tweets",
        default = 100,
        min = 10,
        max = 100
      )

  // UVG params
  object EnableUVGParam
      extends FSParam[Boolean](
        name = "tweet_based_candidate_generation_enable_uvg",
        default = false
      )

  // UAG params
  object EnableUAGParam
      extends FSParam[Boolean](
        name = "tweet_based_candidate_generation_enable_uag",
        default = false
      )

  // Filter params
  object SimClustersMinScoreParam
      extends FSBoundedParam[Double](
        name = "tweet_based_candidate_generation_filter_simclusters_min_score",
        default = 0.5,
        min = 0.0,
        max = 1.0
      )

  // for learning DDG that has a higher threshold for video based SANN
  object SimClustersVideoBasedMinScoreParam
      extends FSBoundedParam[Double](
        name = "tweet_based_candidate_generation_filter_simclusters_video_based_min_score",
        default = 0.5,
        min = 0.0,
        max = 1.0
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    EnableSourceParam,
    EnableTwHINParam,
    EnableQigSimilarTweetsParam,
    EnableUTGParam,
    EnableUVGParam,
    EnableUAGParam,
    EnableSimClustersANNParam,
    EnableSimClustersANN1Param,
    EnableSimClustersANN2Param,
    EnableSimClustersANN3Param,
    EnableSimClustersANN5Param,
    EnableSimClustersANN4Param,
    EnableExperimentalSimClustersANNParam,
    SimClustersMinScoreParam,
    SimClustersVideoBasedMinScoreParam,
    QigMaxNumSimilarTweetsParam,
  )

  lazy val config: BaseConfig = {

    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      EnableSourceParam,
      EnableTwHINParam,
      EnableQigSimilarTweetsParam,
      EnableUTGParam,
      EnableUVGParam,
      EnableUAGParam,
      EnableSimClustersANNParam,
      EnableSimClustersANN1Param,
      EnableSimClustersANN2Param,
      EnableSimClustersANN3Param,
      EnableSimClustersANN5Param,
      EnableSimClustersANN4Param,
      EnableExperimentalSimClustersANNParam,
    )

    val doubleOverrides =
      FeatureSwitchOverrideUtil.getBoundedDoubleFSOverrides(
        SimClustersMinScoreParam,
        SimClustersVideoBasedMinScoreParam)

    val enumOverrides = FeatureSwitchOverrideUtil.getEnumFSOverrides(
      NullStatsReceiver,
      Logger(getClass),
    )

    val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
      QigMaxNumSimilarTweetsParam
    )

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .set(doubleOverrides: _*)
      .set(enumOverrides: _*)
      .set(intOverrides: _*)
      .build()
  }
}

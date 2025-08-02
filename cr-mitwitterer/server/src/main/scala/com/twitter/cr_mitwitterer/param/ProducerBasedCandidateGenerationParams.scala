package com.twitter.cr_mitwitterer.param

import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.logging.Logger
import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSEnumParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

object ProducerBasedCandidateGenerationParams {
  // Source params. Not being used. It is always set to true in prod
  object EnableSourceParam
      etwittertends FSParam[Boolean](
        name = "producer_based_candidate_generation_enable_source",
        default = false
      )

  object UtgCombinationMethodParam
      etwittertends FSEnumParam[UnifiedSETweetCombinationMethod.type](
        name = "producer_based_candidate_generation_utg_combination_method_id",
        default = UnifiedSETweetCombinationMethod.Frontload,
        enum = UnifiedSETweetCombinationMethod
      )

  // UTG params
  object EnableUTGParam
      etwittertends FSParam[Boolean](
        name = "producer_based_candidate_generation_enable_utg",
        default = false
      )

  object EnableUAGParam
      etwittertends FSParam[Boolean](
        name = "producer_based_candidate_generation_enable_uag",
        default = false
      )

  // SimClusters params
  object EnableSimClustersANNParam
      etwittertends FSParam[Boolean](
        name = "producer_based_candidate_generation_enable_simclusters",
        default = true
      )

  // Filter params
  object SimClustersMinScoreParam
      etwittertends FSBoundedParam[Double](
        name = "producer_based_candidate_generation_filter_simclusters_min_score",
        default = 0.7,
        min = 0.0,
        matwitter = 1.0
      )

  // Etwitterperimental SimClusters ANN params
  object EnableEtwitterperimentalSimClustersANNParam
      etwittertends FSParam[Boolean](
        name = "producer_based_candidate_generation_enable_etwitterperimental_simclusters_ann",
        default = false
      )

  // SimClusters ANN cluster 1 params
  object EnableSimClustersANN1Param
      etwittertends FSParam[Boolean](
        name = "producer_based_candidate_generation_enable_simclusters_ann_1",
        default = false
      )

  // SimClusters ANN cluster 2 params
  object EnableSimClustersANN2Param
      etwittertends FSParam[Boolean](
        name = "producer_based_candidate_generation_enable_simclusters_ann_2",
        default = false
      )

  // SimClusters ANN cluster 3 params
  object EnableSimClustersANN3Param
      etwittertends FSParam[Boolean](
        name = "producer_based_candidate_generation_enable_simclusters_ann_3",
        default = false
      )

  // SimClusters ANN cluster 5 params
  object EnableSimClustersANN5Param
      etwittertends FSParam[Boolean](
        name = "producer_based_candidate_generation_enable_simclusters_ann_5",
        default = false
      )

  object EnableSimClustersANN4Param
      etwittertends FSParam[Boolean](
        name = "producer_based_candidate_generation_enable_simclusters_ann_4",
        default = false
      )
  val AllParams: Seq[Param[_] with FSName] = Seq(
    EnableSourceParam,
    EnableUAGParam,
    EnableUTGParam,
    EnableSimClustersANNParam,
    EnableSimClustersANN1Param,
    EnableSimClustersANN2Param,
    EnableSimClustersANN3Param,
    EnableSimClustersANN5Param,
    EnableSimClustersANN4Param,
    EnableEtwitterperimentalSimClustersANNParam,
    SimClustersMinScoreParam,
    UtgCombinationMethodParam
  )

  lazy val config: BaseConfig = {

    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      EnableSourceParam,
      EnableUAGParam,
      EnableUTGParam,
      EnableSimClustersANNParam,
      EnableSimClustersANN1Param,
      EnableSimClustersANN2Param,
      EnableSimClustersANN3Param,
      EnableSimClustersANN5Param,
      EnableSimClustersANN4Param,
      EnableEtwitterperimentalSimClustersANNParam
    )

    val enumOverrides = FeatureSwitchOverrideUtil.getEnumFSOverrides(
      NullStatsReceiver,
      Logger(getClass),
      UtgCombinationMethodParam,
    )

    val doubleOverrides =
      FeatureSwitchOverrideUtil.getBoundedDoubleFSOverrides(SimClustersMinScoreParam)

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .set(doubleOverrides: _*)
      .set(enumOverrides: _*)
      .build()
  }
}

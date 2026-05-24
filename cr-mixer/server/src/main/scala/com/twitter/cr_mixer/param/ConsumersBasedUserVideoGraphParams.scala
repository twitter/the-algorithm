package com.twitter.cr_mixer.param

import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

/**
 * ConsumersBasedUserVideoGraph Params: there are multiple ways (e.g. FRS, RealGraphIn) to generate consumersSeedSet for ConsumersBasedUserTweetGraph
 * for now we allow flexibility in tuning UVG params for different consumersSeedSet generation algo by giving the param name {consumerSeedSetAlgo}{ParamName}
 */

object ConsumersBasedUserVideoGraphParams {

  object EnableSourceParam
      extends FSParam[Boolean](
        name = "consumers_based_user_video_graph_enable_source",
        default = false
      )

  // UTG-RealGraphIN
  object RealGraphInMinCoOccurrenceParam
      extends FSBoundedParam[Int](
        name = "consumers_based_user_video_graph_real_graph_in_min_co_occurrence",
        default = 3,
        min = 0,
        max = 500
      )

  object RealGraphInMinScoreParam
      extends FSBoundedParam[Double](
        name = "consumers_based_user_video_graph_real_graph_in_min_score",
        default = 2.0,
        min = 0.0,
        max = 10.0
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    EnableSourceParam,
    RealGraphInMinCoOccurrenceParam,
    RealGraphInMinScoreParam
  )

  lazy val config: BaseConfig = {

    val intOverrides =
      FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(RealGraphInMinCoOccurrenceParam)

    val doubleOverrides =
      FeatureSwitchOverrideUtil.getBoundedDoubleFSOverrides(RealGraphInMinScoreParam)

    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      EnableSourceParam
    )

    BaseConfigBuilder()
      .set(intOverrides: _*)
      .set(booleanOverrides: _*)
      .set(doubleOverrides: _*)
      .build()
  }
}

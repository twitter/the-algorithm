package com.ExTwitter.cr_mixer.param

import com.ExTwitter.timelines.configapi.BaseConfig
import com.ExTwitter.timelines.configapi.BaseConfigBuilder
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.FSParam
import com.ExTwitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.ExTwitter.timelines.configapi.Param

/**
 * ConsumersBasedUserTweetGraph Params, there are multiple ways (e.g. FRS, RealGraphOon) to generate consumersSeedSet for ConsumersBasedUserTweetGraph
 * for now we allow flexibility in tuning UTG params for different consumersSeedSet generation algo by giving the param name {consumerSeedSetAlgo}{ParamName}
 */

object ConsumersBasedUserTweetGraphParams {

  object EnableSourceParam
      extends FSParam[Boolean](
        name = "consumers_based_user_tweet_graph_enable_source",
        default = false
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    EnableSourceParam,
  )

  lazy val config: BaseConfig = {

    val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides()

    val doubleOverrides =
      FeatureSwitchOverrideUtil.getBoundedDoubleFSOverrides()

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

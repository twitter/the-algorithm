package com.X.cr_mixer.param

import com.X.timelines.configapi.BaseConfig
import com.X.timelines.configapi.BaseConfigBuilder
import com.X.timelines.configapi.FeatureSwitchOverrideUtil
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.FSParam
import com.X.timelines.configapi.Param

object VideoTweetFilterParams {

  object EnableVideoTweetFilterParam
      extends FSParam[Boolean](
        name = "video_tweet_filter_enable_filter",
        default = false
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    EnableVideoTweetFilterParam
  )

  lazy val config: BaseConfig = {

    val booleanOverrides =
      FeatureSwitchOverrideUtil.getBooleanFSOverrides(EnableVideoTweetFilterParam)

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .build()
  }
}

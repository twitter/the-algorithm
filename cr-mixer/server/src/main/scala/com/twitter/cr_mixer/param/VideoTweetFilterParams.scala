package com.ExTwitter.cr_mixer.param

import com.ExTwitter.timelines.configapi.BaseConfig
import com.ExTwitter.timelines.configapi.BaseConfigBuilder
import com.ExTwitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.FSParam
import com.ExTwitter.timelines.configapi.Param

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

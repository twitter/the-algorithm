package com.twitter.cr_mitwitterer.param

import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.Param

object VideoTweetFilterParams {

  object EnableVideoTweetFilterParam
      etwittertends FSParam[Boolean](
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

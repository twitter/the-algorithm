package com.twitter.cr_mixer.param

import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

object RelatedVideoTweetGlobalParams {

  object MaxCandidatesPerRequestParam
      extends FSBoundedParam[Int](
        name = "related_video_tweet_core_max_candidates_per_request",
        default = 100,
        min = 0,
        max = 500
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(MaxCandidatesPerRequestParam)

  lazy val config: BaseConfig = {

    val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
      MaxCandidatesPerRequestParam
    )

    BaseConfigBuilder()
      .set(intOverrides: _*)
      .build()
  }
}

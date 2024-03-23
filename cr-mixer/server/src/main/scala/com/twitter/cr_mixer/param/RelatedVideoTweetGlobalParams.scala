package com.ExTwitter.cr_mixer.param

import com.ExTwitter.timelines.configapi.BaseConfig
import com.ExTwitter.timelines.configapi.BaseConfigBuilder
import com.ExTwitter.timelines.configapi.FSBoundedParam
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.ExTwitter.timelines.configapi.Param

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

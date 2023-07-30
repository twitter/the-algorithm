package com.X.cr_mixer.param

import com.X.timelines.configapi.BaseConfig
import com.X.timelines.configapi.BaseConfigBuilder
import com.X.timelines.configapi.FSBoundedParam
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.FeatureSwitchOverrideUtil
import com.X.timelines.configapi.Param

object RelatedTweetGlobalParams {

  object MaxCandidatesPerRequestParam
      extends FSBoundedParam[Int](
        name = "related_tweet_core_max_candidates_per_request",
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

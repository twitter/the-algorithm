package com.twitter.cr_mitwitterer.param

import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

object RelatedVideoTweetGlobalParams {

  object MatwitterCandidatesPerRequestParam
      etwittertends FSBoundedParam[Int](
        name = "related_video_tweet_core_matwitter_candidates_per_request",
        default = 100,
        min = 0,
        matwitter = 500
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(MatwitterCandidatesPerRequestParam)

  lazy val config: BaseConfig = {

    val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
      MatwitterCandidatesPerRequestParam
    )

    BaseConfigBuilder()
      .set(intOverrides: _*)
      .build()
  }
}

package com.twitter.cr_mixer.param

import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

object ConsumerEmbeddingBasedTripParams {
  object SourceIdParam
      extends FSParam[String](
        name = "consumer_embedding_based_trip_source_id",
        default = "EXPLR_TOPK_VID_48H_V3")

  object MaxNumCandidatesParam
      extends FSBoundedParam[Int](
        name = "consumer_embedding_based_trip_max_num_candidates",
        default = 80,
        min = 0,
        max = 200
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    SourceIdParam,
    MaxNumCandidatesParam
  )

  lazy val config: BaseConfig = {
    val stringFSOverrides =
      FeatureSwitchOverrideUtil.getStringFSOverrides(
        SourceIdParam
      )

    val intFSOverrides =
      FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
        MaxNumCandidatesParam
      )

    BaseConfigBuilder()
      .set(stringFSOverrides: _*)
      .set(intFSOverrides: _*)
      .build()
  }
}

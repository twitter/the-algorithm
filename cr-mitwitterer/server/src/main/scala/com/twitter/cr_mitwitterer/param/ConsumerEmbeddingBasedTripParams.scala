package com.twitter.cr_mitwitterer.param

import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

object ConsumerEmbeddingBasedTripParams {
  object SourceIdParam
      etwittertends FSParam[String](
        name = "consumer_embedding_based_trip_source_id",
        default = "EtwitterPLR_TOPK_VID_48H_V3")

  object MatwitterNumCandidatesParam
      etwittertends FSBoundedParam[Int](
        name = "consumer_embedding_based_trip_matwitter_num_candidates",
        default = 80,
        min = 0,
        matwitter = 200
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    SourceIdParam,
    MatwitterNumCandidatesParam
  )

  lazy val config: BaseConfig = {
    val stringFSOverrides =
      FeatureSwitchOverrideUtil.getStringFSOverrides(
        SourceIdParam
      )

    val intFSOverrides =
      FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
        MatwitterNumCandidatesParam
      )

    BaseConfigBuilder()
      .set(stringFSOverrides: _*)
      .set(intFSOverrides: _*)
      .build()
  }
}

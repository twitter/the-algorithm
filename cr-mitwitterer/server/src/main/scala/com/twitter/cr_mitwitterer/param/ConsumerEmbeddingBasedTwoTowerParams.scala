package com.twitter.cr_mitwitterer.param

import com.twitter.cr_mitwitterer.model.ModelConfig.TwoTowerFavALL20220808
import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

object ConsumerEmbeddingBasedTwoTowerParams {
  object ModelIdParam
      etwittertends FSParam[String](
        name = "consumer_embedding_based_two_tower_model_id",
        default = TwoTowerFavALL20220808,
      ) // Note: this default value does not match with ModelIds yet. This FS is a placeholder

  val AllParams: Seq[Param[_] with FSName] = Seq(
    ModelIdParam
  )

  lazy val config: BaseConfig = {
    val stringFSOverrides =
      FeatureSwitchOverrideUtil.getStringFSOverrides(
        ModelIdParam
      )

    BaseConfigBuilder()
      .set(stringFSOverrides: _*)
      .build()
  }
}

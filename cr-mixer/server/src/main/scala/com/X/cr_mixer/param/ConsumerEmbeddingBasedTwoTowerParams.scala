package com.X.cr_mixer.param

import com.X.cr_mixer.model.ModelConfig.TwoTowerFavALL20220808
import com.X.timelines.configapi.BaseConfig
import com.X.timelines.configapi.BaseConfigBuilder
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.FSParam
import com.X.timelines.configapi.FeatureSwitchOverrideUtil
import com.X.timelines.configapi.Param

object ConsumerEmbeddingBasedTwoTowerParams {
  object ModelIdParam
      extends FSParam[String](
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

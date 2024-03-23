package com.ExTwitter.cr_mixer.param

import com.ExTwitter.cr_mixer.model.ModelConfig.TwoTowerFavALL20220808
import com.ExTwitter.timelines.configapi.BaseConfig
import com.ExTwitter.timelines.configapi.BaseConfigBuilder
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.FSParam
import com.ExTwitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.ExTwitter.timelines.configapi.Param

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

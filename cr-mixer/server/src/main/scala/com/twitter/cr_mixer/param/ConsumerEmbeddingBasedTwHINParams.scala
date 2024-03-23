package com.ExTwitter.cr_mixer.param

import com.ExTwitter.cr_mixer.model.ModelConfig
import com.ExTwitter.timelines.configapi.BaseConfig
import com.ExTwitter.timelines.configapi.BaseConfigBuilder
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.FSParam
import com.ExTwitter.timelines.configapi.Param

import com.ExTwitter.timelines.configapi.FeatureSwitchOverrideUtil

object ConsumerEmbeddingBasedTwHINParams {
  object ModelIdParam
      extends FSParam[String](
        name = "consumer_embedding_based_twhin_model_id",
        default = ModelConfig.ConsumerBasedTwHINRegularUpdateAll20221024,
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

package com.X.cr_mixer.param

import com.X.cr_mixer.model.ModelConfig
import com.X.timelines.configapi.BaseConfig
import com.X.timelines.configapi.BaseConfigBuilder
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.FSParam
import com.X.timelines.configapi.FeatureSwitchOverrideUtil
import com.X.timelines.configapi.Param

object TweetBasedTwHINParams {
  object ModelIdParam
      extends FSParam[String](
        name = "tweet_based_twhin_model_id",
        default = ModelConfig.TweetBasedTwHINRegularUpdateAll20221024,
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(ModelIdParam)

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

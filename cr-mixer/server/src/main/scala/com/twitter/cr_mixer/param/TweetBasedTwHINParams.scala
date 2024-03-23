package com.ExTwitter.cr_mixer.param

import com.ExTwitter.cr_mixer.model.ModelConfig
import com.ExTwitter.timelines.configapi.BaseConfig
import com.ExTwitter.timelines.configapi.BaseConfigBuilder
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.FSParam
import com.ExTwitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.ExTwitter.timelines.configapi.Param

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

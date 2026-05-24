package com.twitter.cr_mixer.param

import com.twitter.cr_mixer.model.ModelConfig
import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

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

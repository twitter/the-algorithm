package com.twitter.cr_mixer.param
import com.twitter.cr_mixer.model.ModelConfig
import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

object CustomizedRetrievalBasedFTROfflineInterestedInParams {
  object CustomizedRetrievalBasedFTROfflineInterestedInSource
      extends FSParam[String](
        name = "customized_retrieval_based_ftr_offline_interestedin_model_id",
        default = ModelConfig.OfflineFavDecayedSum
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    CustomizedRetrievalBasedFTROfflineInterestedInSource)

  lazy val config: BaseConfig = {

    val stringFSOverrides =
      FeatureSwitchOverrideUtil.getStringFSOverrides(
        CustomizedRetrievalBasedFTROfflineInterestedInSource
      )

    BaseConfigBuilder()
      .set(stringFSOverrides: _*)
      .build()
  }
}

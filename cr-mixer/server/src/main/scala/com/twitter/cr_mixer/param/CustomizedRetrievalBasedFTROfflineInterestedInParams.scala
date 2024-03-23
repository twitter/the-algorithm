package com.ExTwitter.cr_mixer.param
import com.ExTwitter.cr_mixer.model.ModelConfig
import com.ExTwitter.timelines.configapi.BaseConfig
import com.ExTwitter.timelines.configapi.BaseConfigBuilder
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.FSParam
import com.ExTwitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.ExTwitter.timelines.configapi.Param

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

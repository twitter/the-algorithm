package com.X.cr_mixer.param
import com.X.cr_mixer.model.ModelConfig
import com.X.timelines.configapi.BaseConfig
import com.X.timelines.configapi.BaseConfigBuilder
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.FSParam
import com.X.timelines.configapi.FeatureSwitchOverrideUtil
import com.X.timelines.configapi.Param

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

package com.X.cr_mixer.param

import com.X.cr_mixer.model.ModelConfig
import com.X.timelines.configapi.BaseConfig
import com.X.timelines.configapi.BaseConfigBuilder
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.FSParam
import com.X.timelines.configapi.FeatureSwitchOverrideUtil
import com.X.timelines.configapi.Param

object CustomizedRetrievalBasedOfflineInterestedInParams {

  // Model slots available for offline InterestedIn candidate generation
  object CustomizedRetrievalBasedOfflineInterestedInSource
      extends FSParam[String](
        name = "customized_retrieval_based_offline_interestedin_model_id",
        default = ModelConfig.OfflineInterestedInFromKnownFor2020
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(CustomizedRetrievalBasedOfflineInterestedInSource)

  lazy val config: BaseConfig = {

    val stringFSOverrides =
      FeatureSwitchOverrideUtil.getStringFSOverrides(
        CustomizedRetrievalBasedOfflineInterestedInSource
      )

    BaseConfigBuilder()
      .set(stringFSOverrides: _*)
      .build()
  }
}

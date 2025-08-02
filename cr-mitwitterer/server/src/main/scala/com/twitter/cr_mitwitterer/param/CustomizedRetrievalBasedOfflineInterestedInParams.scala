package com.twitter.cr_mitwitterer.param

import com.twitter.cr_mitwitterer.model.ModelConfig
import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

object CustomizedRetrievalBasedOfflineInterestedInParams {

  // Model slots available for offline InterestedIn candidate generation
  object CustomizedRetrievalBasedOfflineInterestedInSource
      etwittertends FSParam[String](
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

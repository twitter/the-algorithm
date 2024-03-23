package com.ExTwitter.cr_mixer.param

import com.ExTwitter.cr_mixer.model.ModelConfig
import com.ExTwitter.timelines.configapi.BaseConfig
import com.ExTwitter.timelines.configapi.BaseConfigBuilder
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.FSParam
import com.ExTwitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.ExTwitter.timelines.configapi.Param

object CustomizedRetrievalBasedCandidateGenerationParams {

  // Offline SimClusters InterestedIn params
  object EnableOfflineInterestedInParam
      extends FSParam[Boolean](
        name = "customized_retrieval_based_candidate_generation_enable_offline_interestedin",
        default = false
      )

  // Offline SimClusters FTR-based InterestedIn
  object EnableOfflineFTRInterestedInParam
      extends FSParam[Boolean](
        name = "customized_retrieval_based_candidate_generation_enable_ftr_offline_interestedin",
        default = false
      )

  // TwHin Collab Filter Cluster params
  object EnableTwhinCollabFilterClusterParam
      extends FSParam[Boolean](
        name = "customized_retrieval_based_candidate_generation_enable_twhin_collab_filter_cluster",
        default = false
      )

  // TwHin Multi Cluster params
  object EnableTwhinMultiClusterParam
      extends FSParam[Boolean](
        name = "customized_retrieval_based_candidate_generation_enable_twhin_multi_cluster",
        default = false
      )

  object EnableRetweetBasedDiffusionParam
      extends FSParam[Boolean](
        name = "customized_retrieval_based_candidate_generation_enable_retweet_based_diffusion",
        default = false
      )
  object CustomizedRetrievalBasedRetweetDiffusionSource
      extends FSParam[String](
        name =
          "customized_retrieval_based_candidate_generation_offline_retweet_based_diffusion_model_id",
        default = ModelConfig.RetweetBasedDiffusion
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    EnableOfflineInterestedInParam,
    EnableOfflineFTRInterestedInParam,
    EnableTwhinCollabFilterClusterParam,
    EnableTwhinMultiClusterParam,
    EnableRetweetBasedDiffusionParam,
    CustomizedRetrievalBasedRetweetDiffusionSource
  )

  lazy val config: BaseConfig = {
    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      EnableOfflineInterestedInParam,
      EnableOfflineFTRInterestedInParam,
      EnableTwhinCollabFilterClusterParam,
      EnableTwhinMultiClusterParam,
      EnableRetweetBasedDiffusionParam
    )

    val stringFSOverrides =
      FeatureSwitchOverrideUtil.getStringFSOverrides(
        CustomizedRetrievalBasedRetweetDiffusionSource
      )

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .set(stringFSOverrides: _*)
      .build()
  }
}

package com.X.cr_mixer.param

import com.X.cr_mixer.model.ModelConfig
import com.X.timelines.configapi.BaseConfig
import com.X.timelines.configapi.BaseConfigBuilder
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.FSParam
import com.X.timelines.configapi.FeatureSwitchOverrideUtil
import com.X.timelines.configapi.Param

object CustomizedRetrievalBasedTwhinParams {

  // Model slots available for TwhinCollab and MultiCluster
  object CustomizedRetrievalBasedTwhinCollabFilterFollowSource
      extends FSParam[String](
        name = "customized_retrieval_based_offline_twhin_collab_filter_follow_model_id",
        default = ModelConfig.TwhinCollabFilterForFollow
      )

  object CustomizedRetrievalBasedTwhinCollabFilterEngagementSource
      extends FSParam[String](
        name = "customized_retrieval_based_offline_twhin_collab_filter_engagement_model_id",
        default = ModelConfig.TwhinCollabFilterForEngagement
      )

  object CustomizedRetrievalBasedTwhinMultiClusterFollowSource
      extends FSParam[String](
        name = "customized_retrieval_based_offline_twhin_multi_cluster_follow_model_id",
        default = ModelConfig.TwhinMultiClusterForFollow
      )

  object CustomizedRetrievalBasedTwhinMultiClusterEngagementSource
      extends FSParam[String](
        name = "customized_retrieval_based_offline_twhin_multi_cluster_engagement_model_id",
        default = ModelConfig.TwhinMultiClusterForEngagement
      )

  val AllParams: Seq[Param[_] with FSName] =
    Seq(
      CustomizedRetrievalBasedTwhinCollabFilterFollowSource,
      CustomizedRetrievalBasedTwhinCollabFilterEngagementSource,
      CustomizedRetrievalBasedTwhinMultiClusterFollowSource,
      CustomizedRetrievalBasedTwhinMultiClusterEngagementSource,
    )

  lazy val config: BaseConfig = {

    val stringFSOverrides =
      FeatureSwitchOverrideUtil.getStringFSOverrides(
        CustomizedRetrievalBasedTwhinCollabFilterFollowSource,
        CustomizedRetrievalBasedTwhinCollabFilterEngagementSource,
        CustomizedRetrievalBasedTwhinMultiClusterFollowSource,
        CustomizedRetrievalBasedTwhinMultiClusterEngagementSource,
      )

    BaseConfigBuilder()
      .set(stringFSOverrides: _*)
      .build()
  }
}

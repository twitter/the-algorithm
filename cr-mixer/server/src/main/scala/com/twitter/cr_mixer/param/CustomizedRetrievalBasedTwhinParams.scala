package com.twitter.cr_mixer.param

import com.twitter.cr_mixer.model.ModelConfig
import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

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

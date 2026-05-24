package com.twitter.follow_recommendations.common.feature_hydration.sources

import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.timelines.configapi.Param
import com.twitter.util.Duration
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeatureHydrationSourcesFSConfig @Inject() () extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[Param[Boolean] with FSName] = Seq(
    FeatureStoreSourceParams.EnableAlgorithmAggregateFeatures,
    FeatureStoreSourceParams.EnableAuthorTopicAggregateFeatures,
    FeatureStoreSourceParams.EnableCandidateClientFeatures,
    FeatureStoreSourceParams.EnableCandidatePrecomputedNotificationFeatures,
    FeatureStoreSourceParams.EnableCandidateUserAuthorRealTimeAggregateFeatures,
    FeatureStoreSourceParams.EnableCandidateUserFeatures,
    FeatureStoreSourceParams.EnableCandidateUserResurrectionFeatures,
    FeatureStoreSourceParams.EnableCandidateUserTimelinesAuthorAggregateFeatures,
    FeatureStoreSourceParams.EnableSeparateClientForTimelinesAuthors,
    FeatureStoreSourceParams.EnableSeparateClientForGizmoduck,
    FeatureStoreSourceParams.EnableSeparateClientForMetricCenterUserCounting,
    FeatureStoreSourceParams.EnableSeparateClientForNotifications,
    FeatureStoreSourceParams.EnableSimilarToUserFeatures,
    FeatureStoreSourceParams.EnableTargetUserFeatures,
    FeatureStoreSourceParams.EnableTargetUserResurrectionFeatures,
    FeatureStoreSourceParams.EnableTargetUserWtfImpressionFeatures,
    FeatureStoreSourceParams.EnableTopicAggregateFeatures,
    FeatureStoreSourceParams.EnableUserCandidateEdgeFeatures,
    FeatureStoreSourceParams.EnableUserCandidateWtfImpressionCandidateFeatures,
    FeatureStoreSourceParams.EnableUserClientFeatures,
    FeatureStoreSourceParams.EnableUserTopicFeatures,
    FeatureStoreSourceParams.EnableUserWtfAlgEdgeFeatures,
  )

  override val durationFSParams: Seq[FSBoundedParam[Duration] with HasDurationConversion] = Seq(
    FeatureStoreSourceParams.GlobalFetchTimeout
  )
}

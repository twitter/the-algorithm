package com.twitter.follow_recommendations.common.feature_hydration.sources

import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.util.Duration
import com.twitter.conversions.DurationOps._

object FeatureStoreSourceParams {
  case object EnableTopicAggregateFeatures
      extends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableTopicAggregateFeatures,
        default = true
      )
  case object EnableAlgorithmAggregateFeatures
      extends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableAlgorithmAggregateFeatures,
        default = false
      )
  case object EnableAuthorTopicAggregateFeatures
      extends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableAuthorTopicAggregateFeatures,
        default = true
      )
  case object EnableUserTopicFeatures
      extends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableUserTopicFeatures,
        default = false
      )
  case object EnableTargetUserFeatures
      extends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableTargetUserFeatures,
        default = true
      )
  case object EnableTargetUserUserAuthorUserStateRealTimeAggregatesFeature
      extends FSParam[Boolean](
        name =
          FeatureHydrationSourcesFeatureSwitchKeys.EnableTargetUserUserAuthorUserStateRealTimeAggregatesFeature,
        default = true
      )
  case object EnableTargetUserResurrectionFeatures
      extends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableTargetUserResurrectionFeatures,
        default = true
      )
  case object EnableTargetUserWtfImpressionFeatures
      extends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableTargetUserWtfImpressionFeatures,
        default = true
      )
  case object EnableCandidateUserFeatures
      extends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableCandidateUserFeatures,
        default = true
      )
  case object EnableCandidateUserAuthorRealTimeAggregateFeatures
      extends FSParam[Boolean](
        name =
          FeatureHydrationSourcesFeatureSwitchKeys.EnableCandidateUserAuthorRealTimeAggregateFeatures,
        default = true
      )
  case object EnableCandidateUserResurrectionFeatures
      extends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableCandidateUserResurrectionFeatures,
        default = true
      )
  case object EnableCandidateUserTimelinesAuthorAggregateFeatures
      extends FSParam[Boolean](
        name =
          FeatureHydrationSourcesFeatureSwitchKeys.EnableCandidateUserTimelinesAuthorAggregateFeatures,
        default = true
      )
  case object EnableUserCandidateEdgeFeatures
      extends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableUserCandidateEdgeFeatures,
        default = true
      )
  case object EnableUserCandidateWtfImpressionCandidateFeatures
      extends FSParam[Boolean](
        name =
          FeatureHydrationSourcesFeatureSwitchKeys.EnableUserCandidateWtfImpressionCandidateFeatures,
        default = true
      )
  case object EnableUserWtfAlgEdgeFeatures
      extends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableUserWtfAlgEdgeFeatures,
        default = false
      )
  case object EnableSimilarToUserFeatures
      extends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableSimilarToUserFeatures,
        default = true
      )

  case object EnableCandidatePrecomputedNotificationFeatures
      extends FSParam[Boolean](
        name =
          FeatureHydrationSourcesFeatureSwitchKeys.EnableCandidatePrecomputedNotificationFeatures,
        default = false
      )

  case object EnableCandidateClientFeatures
      extends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableCandidateClientFeatures,
        default = false
      )

  case object EnableUserClientFeatures
      extends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableUserClientFeatures,
        default = false
      )

  case object EnableSeparateClientForTimelinesAuthors
      extends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.UseSeparateClientForTimelinesAuthor,
        default = false
      )

  case object EnableSeparateClientForMetricCenterUserCounting
      extends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.UseSeparateClientMetricCenterUserCounting,
        default = false
      )

  case object EnableSeparateClientForNotifications
      extends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.UseSeparateClientForNotifications,
        default = false
      )

  case object EnableSeparateClientForGizmoduck
      extends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.UseSeparateClientForGizmoduck,
        default = false
      )

  case object GlobalFetchTimeout
      extends FSBoundedParam[Duration](
        name = FeatureHydrationSourcesFeatureSwitchKeys.FeatureHydrationTimeout,
        default = 240.millisecond,
        min = 100.millisecond,
        max = 400.millisecond)
      with HasDurationConversion {
    override def durationConversion: DurationConversion = DurationConversion.FromMillis
  }
}

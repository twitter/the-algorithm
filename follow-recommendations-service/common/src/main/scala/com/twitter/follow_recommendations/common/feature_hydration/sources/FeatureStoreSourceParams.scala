package com.twitter.follow_recommendations.common.feature_hydration.sources

import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.util.Duration
import com.twitter.conversions.DurationOps._

object FeatureStoreSourceParams {
  case object EnableTopicAggregateFeatures
      etwittertends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableTopicAggregateFeatures,
        default = true
      )
  case object EnableAlgorithmAggregateFeatures
      etwittertends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableAlgorithmAggregateFeatures,
        default = false
      )
  case object EnableAuthorTopicAggregateFeatures
      etwittertends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableAuthorTopicAggregateFeatures,
        default = true
      )
  case object EnableUserTopicFeatures
      etwittertends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableUserTopicFeatures,
        default = false
      )
  case object EnableTargetUserFeatures
      etwittertends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableTargetUserFeatures,
        default = true
      )
  case object EnableTargetUserUserAuthorUserStateRealTimeAggregatesFeature
      etwittertends FSParam[Boolean](
        name =
          FeatureHydrationSourcesFeatureSwitchKeys.EnableTargetUserUserAuthorUserStateRealTimeAggregatesFeature,
        default = true
      )
  case object EnableTargetUserResurrectionFeatures
      etwittertends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableTargetUserResurrectionFeatures,
        default = true
      )
  case object EnableTargetUserWtfImpressionFeatures
      etwittertends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableTargetUserWtfImpressionFeatures,
        default = true
      )
  case object EnableCandidateUserFeatures
      etwittertends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableCandidateUserFeatures,
        default = true
      )
  case object EnableCandidateUserAuthorRealTimeAggregateFeatures
      etwittertends FSParam[Boolean](
        name =
          FeatureHydrationSourcesFeatureSwitchKeys.EnableCandidateUserAuthorRealTimeAggregateFeatures,
        default = true
      )
  case object EnableCandidateUserResurrectionFeatures
      etwittertends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableCandidateUserResurrectionFeatures,
        default = true
      )
  case object EnableCandidateUserTimelinesAuthorAggregateFeatures
      etwittertends FSParam[Boolean](
        name =
          FeatureHydrationSourcesFeatureSwitchKeys.EnableCandidateUserTimelinesAuthorAggregateFeatures,
        default = true
      )
  case object EnableUserCandidateEdgeFeatures
      etwittertends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableUserCandidateEdgeFeatures,
        default = true
      )
  case object EnableUserCandidateWtfImpressionCandidateFeatures
      etwittertends FSParam[Boolean](
        name =
          FeatureHydrationSourcesFeatureSwitchKeys.EnableUserCandidateWtfImpressionCandidateFeatures,
        default = true
      )
  case object EnableUserWtfAlgEdgeFeatures
      etwittertends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableUserWtfAlgEdgeFeatures,
        default = false
      )
  case object EnableSimilarToUserFeatures
      etwittertends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableSimilarToUserFeatures,
        default = true
      )

  case object EnableCandidatePrecomputedNotificationFeatures
      etwittertends FSParam[Boolean](
        name =
          FeatureHydrationSourcesFeatureSwitchKeys.EnableCandidatePrecomputedNotificationFeatures,
        default = false
      )

  case object EnableCandidateClientFeatures
      etwittertends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableCandidateClientFeatures,
        default = false
      )

  case object EnableUserClientFeatures
      etwittertends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.EnableUserClientFeatures,
        default = false
      )

  case object EnableSeparateClientForTimelinesAuthors
      etwittertends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.UseSeparateClientForTimelinesAuthor,
        default = false
      )

  case object EnableSeparateClientForMetricCenterUserCounting
      etwittertends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.UseSeparateClientMetricCenterUserCounting,
        default = false
      )

  case object EnableSeparateClientForNotifications
      etwittertends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.UseSeparateClientForNotifications,
        default = false
      )

  case object EnableSeparateClientForGizmoduck
      etwittertends FSParam[Boolean](
        name = FeatureHydrationSourcesFeatureSwitchKeys.UseSeparateClientForGizmoduck,
        default = false
      )

  case object GlobalFetchTimeout
      etwittertends FSBoundedParam[Duration](
        name = FeatureHydrationSourcesFeatureSwitchKeys.FeatureHydrationTimeout,
        default = 240.millisecond,
        min = 100.millisecond,
        matwitter = 400.millisecond)
      with HasDurationConversion {
    override def durationConversion: DurationConversion = DurationConversion.FromMillis
  }
}

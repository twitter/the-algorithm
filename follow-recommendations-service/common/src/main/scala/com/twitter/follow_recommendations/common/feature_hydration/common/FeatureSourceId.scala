package com.twitter.follow_recommendations.common.feature_hydration.common

sealed trait FeatureSourceId

object FeatureSourceId {
  object CandidateAlgorithmSourceId extends FeatureSourceId
  object ClientContextSourceId extends FeatureSourceId
  object FeatureStoreSourceId extends FeatureSourceId
  object FeatureStoreTimelinesAuthorSourceId extends FeatureSourceId
  object FeatureStoreGizmoduckSourceId extends FeatureSourceId
  object FeatureStoreUserMetricCountsSourceId extends FeatureSourceId
  object FeatureStoreNotificationSourceId extends FeatureSourceId

  object FeatureStorePrecomputedNotificationSourceId extends FeatureSourceId
  object FeatureStorePostNuxAlgorithmSourceId extends FeatureSourceId
  @deprecated object StratoFeatureHydrationSourceId extends FeatureSourceId
  object PreFetchedFeatureSourceId extends FeatureSourceId
  object UserScoringFeatureSourceId extends FeatureSourceId
}

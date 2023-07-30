package com.X.follow_recommendations.common.candidate_sources.recent_engagement

import com.X.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.X.timelines.configapi.FSBoundedParam
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.Param
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepeatedProfileVisitsFSConfig @Inject() () extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[Param[Boolean] with FSName] =
    Seq(
      RepeatedProfileVisitsParams.IncludeCandidates,
      RepeatedProfileVisitsParams.UseOnlineDataset,
    )
  override val intFSParams: Seq[FSBoundedParam[Int]] =
    Seq(
      RepeatedProfileVisitsParams.RecommendationThreshold,
      RepeatedProfileVisitsParams.BucketingThreshold,
    )
}

package com.twitter.follow_recommendations.common.candidate_sources.recent_engagement

import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.Param
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

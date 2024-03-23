package com.ExTwitter.follow_recommendations.common.candidate_sources.recent_engagement

import com.ExTwitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.ExTwitter.timelines.configapi.FSBoundedParam
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.Param
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

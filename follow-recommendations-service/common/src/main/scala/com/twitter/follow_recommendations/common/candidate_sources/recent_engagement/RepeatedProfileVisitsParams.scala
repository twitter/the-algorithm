package com.twitter.follow_recommendations.common.candidate_sources.recent_engagement

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

object RepeatedProfileVisitsParams {

  // If RepeatedProfileVisitsSource is run and there are recommended candidates for the target user, whether or not
  // to actually include such candidates in our output recommendations. This FS will be used to control bucketing of
  // users into control vs treatment buckets.
  case object IncludeCandidates
      extends FSParam[Boolean](name = "repeated_profile_visits_include_candidates", default = false)

  // The threshold at or above which we will consider a profile to have been visited "frequently enough" to recommend
  // the profile to the target user.
  case object RecommendationThreshold
      extends FSBoundedParam[Int](
        name = "repeated_profile_visits_recommendation_threshold",
        default = 3,
        min = 0,
        max = Integer.MAX_VALUE)

  // The threshold at or above which we will consider a profile to have been visited "frequently enough" to recommend
  // the profile to the target user.
  case object BucketingThreshold
      extends FSBoundedParam[Int](
        name = "repeated_profile_visits_bucketing_threshold",
        default = 3,
        min = 0,
        max = Integer.MAX_VALUE)

  // Whether or not to use the online dataset (which has repeated profile visits information updated to within minutes)
  // instead of the offline dataset (updated via offline jobs, which can have delays of hours to days).
  case object UseOnlineDataset
      extends FSParam[Boolean](name = "repeated_profile_visits_use_online_dataset", default = true)

}

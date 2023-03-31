package com.twitter.follow_recommendations.common.candidate_sources.socialgraph

import com.twitter.timelines.configapi.FSParam

object RecentFollowingRecentFollowingExpansionSourceParams {
  object CallSgsCachedColumn
      extends FSParam[Boolean](
        "recent_following_recent_following_source_call_sgs_cached_column",
        true)
}

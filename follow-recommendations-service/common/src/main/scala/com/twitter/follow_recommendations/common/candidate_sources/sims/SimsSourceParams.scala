package com.twitter.follow_recommendations.common.candidate_sources.sims

import com.twitter.timelines.configapi.FSParam

object SimsSourceParams {
  case object EnableDBV2SimsStore extends FSParam[Boolean]("sims_source_enable_dbv2_source", false)

  case object EnableDBV2SimsRefreshStore
      extends FSParam[Boolean]("sims_source_enable_dbv2_refresh_source", false)

  case object EnableExperimentalSimsStore
      extends FSParam[Boolean]("sims_source_enable_experimental_source", false)

  case object DisableHeavyRanker
      extends FSParam[Boolean]("sims_source_disable_heavy_ranker", default = false)
}

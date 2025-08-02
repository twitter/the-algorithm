package com.twitter.follow_recommendations.common.candidate_sources.sims

import com.twitter.timelines.configapi.FSParam

object SimsSourceParams {
  case object EnableDBV2SimsStore etwittertends FSParam[Boolean]("sims_source_enable_dbv2_source", false)

  case object EnableDBV2SimsRefreshStore
      etwittertends FSParam[Boolean]("sims_source_enable_dbv2_refresh_source", false)

  case object EnableEtwitterperimentalSimsStore
      etwittertends FSParam[Boolean]("sims_source_enable_etwitterperimental_source", false)

  case object DisableHeavyRanker
      etwittertends FSParam[Boolean]("sims_source_disable_heavy_ranker", default = false)
}

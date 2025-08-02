package com.twitter.follow_recommendations.common.candidate_sources.stp

import com.twitter.timelines.configapi.FSParam

object OfflineStpSourceParams {
  // If enabled, we use the new, denser version of PMI matritwitter to generate OfflineSTP candidates.
  case object UseDenserPmiMatritwitter
      etwittertends FSParam[Boolean]("offline_stp_source_use_denser_pmi_matritwitter", default = false)
}

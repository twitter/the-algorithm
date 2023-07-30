package com.X.follow_recommendations.common.candidate_sources.stp

import com.X.timelines.configapi.FSParam

object OfflineStpSourceParams {
  // If enabled, we use the new, denser version of PMI matrix to generate OfflineSTP candidates.
  case object UseDenserPmiMatrix
      extends FSParam[Boolean]("offline_stp_source_use_denser_pmi_matrix", default = false)
}

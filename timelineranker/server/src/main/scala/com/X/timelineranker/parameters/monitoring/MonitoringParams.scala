package com.X.timelineranker.parameters.monitoring

import com.X.timelines.configapi.FSParam

object MonitoringParams {

  object DebugAuthorsAllowListParam
      extends FSParam[Seq[Long]](
        name = "monitoring_debug_authors_allow_list",
        default = Seq.empty[Long]
      )

}

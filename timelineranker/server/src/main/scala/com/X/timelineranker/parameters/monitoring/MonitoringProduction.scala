package com.X.timelineranker.parameters.monitoring

import com.X.timelines.configapi.BaseConfigBuilder
import com.X.timelineranker.parameters.monitoring.MonitoringParams.DebugAuthorsAllowListParam
import com.X.timelines.configapi.FeatureSwitchOverrideUtil

object MonitoringProduction {
  private val longSeqOverrides =
    FeatureSwitchOverrideUtil.getLongSeqFSOverrides(DebugAuthorsAllowListParam)

  val config = BaseConfigBuilder()
    .set(longSeqOverrides: _*)
    .build()
}

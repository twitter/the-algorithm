package com.twitter.timelineranker.parameters.monitoring

import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelineranker.parameters.monitoring.MonitoringParams.DebugAuthorsAllowListParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil

object MonitoringProduction {
  private val longSeqOverrides =
    FeatureSwitchOverrideUtil.getLongSeqFSOverrides(DebugAuthorsAllowListParam)

  val config = BaseConfigBuilder()
    .set(longSeqOverrides: _*)
    .build()
}

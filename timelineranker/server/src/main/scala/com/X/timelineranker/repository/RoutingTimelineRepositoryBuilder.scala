package com.X.timelineranker.repository

import com.X.timelineranker.config.RuntimeConfiguration
import com.X.timelineranker.parameters.ConfigBuilder

object RoutingTimelineRepositoryBuilder {
  def apply(
    config: RuntimeConfiguration,
    configBuilder: ConfigBuilder
  ): RoutingTimelineRepository = {

    val reverseChronTimelineRepository =
      new ReverseChronHomeTimelineRepositoryBuilder(config, configBuilder).apply
    val rankedTimelineRepository = new RankedHomeTimelineRepository

    new RoutingTimelineRepository(reverseChronTimelineRepository, rankedTimelineRepository)
  }
}

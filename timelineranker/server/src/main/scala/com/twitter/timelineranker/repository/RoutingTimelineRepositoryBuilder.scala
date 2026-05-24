package com.twitter.timelineranker.repository

import com.twitter.timelineranker.config.RuntimeConfiguration
import com.twitter.timelineranker.parameters.ConfigBuilder

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

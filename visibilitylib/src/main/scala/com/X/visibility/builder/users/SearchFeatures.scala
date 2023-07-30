package com.X.visibility.builder.users

import com.X.finagle.stats.StatsReceiver
import com.X.visibility.builder.FeatureMapBuilder
import com.X.visibility.features._
import com.X.visibility.context.thriftscala.SearchContext

class SearchFeatures(statsReceiver: StatsReceiver) {
  private[this] val scopedStatsReceiver = statsReceiver.scope("search_features")
  private[this] val requests = scopedStatsReceiver.counter("requests")
  private[this] val rawQueryCounter =
    scopedStatsReceiver.scope(RawQuery.name).counter("requests")

  def forSearchContext(
    searchContext: Option[SearchContext]
  ): FeatureMapBuilder => FeatureMapBuilder = { builder =>
    requests.incr()
    searchContext match {
      case Some(context: SearchContext) =>
        rawQueryCounter.incr()
        builder
          .withConstantFeature(RawQuery, context.rawQuery)
      case _ => builder
    }
  }
}

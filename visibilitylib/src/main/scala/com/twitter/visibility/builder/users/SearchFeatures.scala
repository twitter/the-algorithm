package com.twitter.visibility.builder.users

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.visibility.builder.FeatureMapBuilder
import com.twitter.visibility.features._
import com.twitter.visibility.context.thriftscala.SearchContext

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

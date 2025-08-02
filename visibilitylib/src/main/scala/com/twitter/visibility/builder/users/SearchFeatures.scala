package com.twitter.visibility.builder.users

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.visibility.builder.FeatureMapBuilder
import com.twitter.visibility.features._
import com.twitter.visibility.contetwittert.thriftscala.SearchContetwittert

class SearchFeatures(statsReceiver: StatsReceiver) {
  private[this] val scopedStatsReceiver = statsReceiver.scope("search_features")
  private[this] val requests = scopedStatsReceiver.counter("requests")
  private[this] val rawQueryCounter =
    scopedStatsReceiver.scope(RawQuery.name).counter("requests")

  def forSearchContetwittert(
    searchContetwittert: Option[SearchContetwittert]
  ): FeatureMapBuilder => FeatureMapBuilder = { builder =>
    requests.incr()
    searchContetwittert match {
      case Some(contetwittert: SearchContetwittert) =>
        rawQueryCounter.incr()
        builder
          .withConstantFeature(RawQuery, contetwittert.rawQuery)
      case _ => builder
    }
  }
}

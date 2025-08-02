package com.twitter.product_mitwitterer.core.functional_component.configapi

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.model.marshalling.request.ClientContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.request.Product
import com.twitter.servo.util.MemoizingStatsReceiver
import com.twitter.timelines.configapi.Config
import com.twitter.timelines.configapi.FeatureValue
import com.twitter.timelines.configapi.Params
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/** Singleton object for building [[Params]] to override */
@Singleton
class ParamsBuilder @Inject() (
  config: Config,
  requestContetwittertBuilder: RequestContetwittertBuilder,
  statsReceiver: StatsReceiver) {

  private[this] val scopedStatsReceiver =
    new MemoizingStatsReceiver(statsReceiver.scope("configapi"))

  def build(
    clientContetwittert: ClientContetwittert,
    product: Product,
    featureOverrides: Map[String, FeatureValue],
    fsCustomMapInput: Map[String, Any] = Map.empty
  ): Params = {
    val requestContetwittert =
      requestContetwittertBuilder.build(clientContetwittert, product, featureOverrides, fsCustomMapInput)

    config(requestContetwittert, scopedStatsReceiver)
  }
}

package com.twitter.product_mixer.core.functional_component.configapi

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mixer.core.model.marshalling.request.ClientContext
import com.twitter.product_mixer.core.model.marshalling.request.Product
import com.twitter.servo.util.MemoizingStatsReceiver
import com.twitter.timelines.configapi.Config
import com.twitter.timelines.configapi.FeatureValue
import com.twitter.timelines.configapi.Params
import javax.inject.Inject
import javax.inject.Singleton

/** Singleton object for building [[Params]] to override */
@Singleton
class ParamsBuilder @Inject() (
  config: Config,
  requestContextBuilder: RequestContextBuilder,
  statsReceiver: StatsReceiver) {

  private[this] val scopedStatsReceiver =
    new MemoizingStatsReceiver(statsReceiver.scope("configapi"))

  def build(
    clientContext: ClientContext,
    product: Product,
    featureOverrides: Map[String, FeatureValue],
    fsCustomMapInput: Map[String, Any] = Map.empty
  ): Params = {
    val requestContext =
      requestContextBuilder.build(clientContext, product, featureOverrides, fsCustomMapInput)

    config(requestContext, scopedStatsReceiver)
  }
}

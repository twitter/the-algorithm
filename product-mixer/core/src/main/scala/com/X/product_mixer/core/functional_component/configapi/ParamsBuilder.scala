package com.X.product_mixer.core.functional_component.configapi

import com.X.finagle.stats.StatsReceiver
import com.X.product_mixer.core.model.marshalling.request.ClientContext
import com.X.product_mixer.core.model.marshalling.request.Product
import com.X.servo.util.MemoizingStatsReceiver
import com.X.timelines.configapi.Config
import com.X.timelines.configapi.FeatureValue
import com.X.timelines.configapi.Params
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

package com.X.follow_recommendations.configapi

import com.X.finagle.stats.StatsReceiver
import com.X.follow_recommendations.common.models.DisplayLocation
import com.X.product_mixer.core.model.marshalling.request.ClientContext
import com.X.servo.util.MemoizingStatsReceiver
import com.X.timelines.configapi.Config
import com.X.timelines.configapi.FeatureValue
import com.X.timelines.configapi.Params
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ParamsFactory @Inject() (
  config: Config,
  requestContextFactory: RequestContextFactory,
  statsReceiver: StatsReceiver) {

  private val stats = new MemoizingStatsReceiver(statsReceiver.scope("configapi"))
  def apply(followRecommendationServiceRequestContext: RequestContext): Params =
    config(followRecommendationServiceRequestContext, stats)

  def apply(
    clientContext: ClientContext,
    displayLocation: DisplayLocation,
    featureOverrides: Map[String, FeatureValue]
  ): Params =
    apply(requestContextFactory(clientContext, displayLocation, featureOverrides))
}

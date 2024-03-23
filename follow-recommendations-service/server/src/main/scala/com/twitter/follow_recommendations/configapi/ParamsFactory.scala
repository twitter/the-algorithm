package com.ExTwitter.follow_recommendations.configapi

import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.follow_recommendations.common.models.DisplayLocation
import com.ExTwitter.product_mixer.core.model.marshalling.request.ClientContext
import com.ExTwitter.servo.util.MemoizingStatsReceiver
import com.ExTwitter.timelines.configapi.Config
import com.ExTwitter.timelines.configapi.FeatureValue
import com.ExTwitter.timelines.configapi.Params
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

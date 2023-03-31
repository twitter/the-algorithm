package com.twitter.follow_recommendations.configapi

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.models.DisplayLocation
import com.twitter.product_mixer.core.model.marshalling.request.ClientContext
import com.twitter.servo.util.MemoizingStatsReceiver
import com.twitter.timelines.configapi.Config
import com.twitter.timelines.configapi.FeatureValue
import com.twitter.timelines.configapi.Params
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

package com.twitter.follow_recommendations.configapi

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.models.DisplayLocation
import com.twitter.product_mitwitterer.core.model.marshalling.request.ClientContetwittert
import com.twitter.servo.util.MemoizingStatsReceiver
import com.twitter.timelines.configapi.Config
import com.twitter.timelines.configapi.FeatureValue
import com.twitter.timelines.configapi.Params
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ParamsFactory @Inject() (
  config: Config,
  requestContetwittertFactory: RequestContetwittertFactory,
  statsReceiver: StatsReceiver) {

  private val stats = new MemoizingStatsReceiver(statsReceiver.scope("configapi"))
  def apply(followRecommendationServiceRequestContetwittert: RequestContetwittert): Params =
    config(followRecommendationServiceRequestContetwittert, stats)

  def apply(
    clientContetwittert: ClientContetwittert,
    displayLocation: DisplayLocation,
    featureOverrides: Map[String, FeatureValue]
  ): Params =
    apply(requestContetwittertFactory(clientContetwittert, displayLocation, featureOverrides))
}

package com.twitter.product_mixer.component_library.module

import com.twitter.conversions.DurationOps._
import com.twitter.conversions.PercentOps._
import com.twitter.finagle.thriftmux.MethodBuilder
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.follow_recommendations.thriftscala.FollowRecommendationsThriftService
import com.twitter.util.Duration

/**
 * Module for the Mute Recommender Service.
 * 
 * This service provides recommendations for similar accounts to mute
 * after a user mutes an account, similar to how FollowRecommenderServiceModule
 * provides follow recommendations.
 * 
 * The service reuses the FollowRecommendationsThriftService infrastructure
 * but will be configured to return mute candidates instead of follow candidates.
 */
object MuteRecommenderServiceModule
    extends ThriftMethodBuilderClientModule[
      FollowRecommendationsThriftService.ServicePerEndpoint,
      FollowRecommendationsThriftService.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label: String = "mute-recommendations-service"

  // Uses the follow-recommendations service endpoint with mute-specific configuration
  // In production, this would point to a dedicated mute-recommendations service
  override val dest: String = "/s/follow-recommendations/follow-recos-service"

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder = {
    methodBuilder
      .withTimeoutPerRequest(400.millis)
      .withTimeoutTotal(800.millis)
      .idempotent(5.percent)
  }

  override protected def sessionAcquisitionTimeout: Duration = 500.milliseconds
}

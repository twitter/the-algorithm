package com.twitter.product_mitwitterer.component_library.module

import com.twitter.follow_recommendations.thriftscala.FollowRecommendationsThriftService
import com.twitter.conversions.DurationOps._
import com.twitter.conversions.PercentOps._
import com.twitter.finagle.thriftmutwitter.MethodBuilder
import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.util.Duration

object FollowRecommenderServiceModule
    etwittertends ThriftMethodBuilderClientModule[
      FollowRecommendationsThriftService.ServicePerEndpoint,
      FollowRecommendationsThriftService.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label: String = "follow-recommendations-service"

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

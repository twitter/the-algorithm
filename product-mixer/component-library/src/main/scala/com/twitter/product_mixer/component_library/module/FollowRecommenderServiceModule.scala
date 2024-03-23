package com.ExTwitter.product_mixer.component_library.module

import com.ExTwitter.follow_recommendations.thriftscala.FollowRecommendationsThriftService
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.conversions.PercentOps._
import com.ExTwitter.finagle.thriftmux.MethodBuilder
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.inject.Injector
import com.ExTwitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.ExTwitter.util.Duration

object FollowRecommenderServiceModule
    extends ThriftMethodBuilderClientModule[
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

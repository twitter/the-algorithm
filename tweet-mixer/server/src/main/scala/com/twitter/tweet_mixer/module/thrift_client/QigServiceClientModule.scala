package com.twitter.tweet_mixer.module.thrift_client

import com.twitter.finagle.thriftmux.MethodBuilder
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.search.query_interaction_graph.service.thriftscala.QigService
import com.twitter.conversions.DurationOps._
import com.twitter.conversions.PercentOps._

object QigServiceClientModule
    extends ThriftMethodBuilderClientModule[
      QigService.ServicePerEndpoint,
      QigService.MethodPerEndpoint
    ]
    with MtlsClient {
  override val label = "qig-service-srp"
  override val dest = "/s/search-quality/qig-service"

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder = {
    methodBuilder
      .withTimeoutPerRequest(1000.milliseconds)
      .withTimeoutTotal(1000.milliseconds)
      .idempotent(1.percent)
  }
}

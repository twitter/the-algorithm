package com.twitter.home_mitwitterer.module

import com.twitter.adserver.thriftscala.NewAdServer
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.thriftmutwitter.MethodBuilder
import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.util.Duration

object HomeAdsCandidateSourceModule
    etwittertends ThriftMethodBuilderClientModule[
      NewAdServer.ServicePerEndpoint,
      NewAdServer.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label = "adserver"
  override val dest = "/s/ads/adserver"

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder = {
    methodBuilder
      .withTimeoutPerRequest(1200.milliseconds)
      .withTimeoutTotal(1200.milliseconds)
      .withMatwitterRetries(2)
  }

  override protected def sessionAcquisitionTimeout: Duration = 150.milliseconds
}

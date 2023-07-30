package com.X.home_mixer.module

import com.X.adserver.thriftscala.NewAdServer
import com.X.conversions.DurationOps._
import com.X.finagle.thriftmux.MethodBuilder
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.inject.Injector
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.util.Duration

object HomeAdsCandidateSourceModule
    extends ThriftMethodBuilderClientModule[
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
      .withMaxRetries(2)
  }

  override protected def sessionAcquisitionTimeout: Duration = 150.milliseconds
}

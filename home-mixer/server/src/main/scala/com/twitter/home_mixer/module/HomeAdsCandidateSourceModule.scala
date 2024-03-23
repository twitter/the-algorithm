package com.ExTwitter.home_mixer.module

import com.ExTwitter.adserver.thriftscala.NewAdServer
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.thriftmux.MethodBuilder
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.inject.Injector
import com.ExTwitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.ExTwitter.util.Duration

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

package com.ExTwitter.product_mixer.component_library.module

import com.ExTwitter.interests_discovery.thriftscala.InterestsDiscoveryService
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.conversions.PercentOps._
import com.ExTwitter.finagle.thriftmux.MethodBuilder
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.inject.Injector
import com.ExTwitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.ExTwitter.util.Duration

object InterestsDiscoveryServiceModule
    extends ThriftMethodBuilderClientModule[
      InterestsDiscoveryService.ServicePerEndpoint,
      InterestsDiscoveryService.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label: String = "interests-discovery-service"

  override val dest: String = "/s/interests_discovery/interests_discovery"

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder = {
    methodBuilder
      .withTimeoutPerRequest(500.millis)
      .withTimeoutTotal(1000.millis)
      .idempotent(5.percent)
  }

  override protected def sessionAcquisitionTimeout: Duration = 500.milliseconds
}

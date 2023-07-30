package com.X.product_mixer.component_library.module

import com.X.interests_discovery.thriftscala.InterestsDiscoveryService
import com.X.conversions.DurationOps._
import com.X.conversions.PercentOps._
import com.X.finagle.thriftmux.MethodBuilder
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.inject.Injector
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.util.Duration

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

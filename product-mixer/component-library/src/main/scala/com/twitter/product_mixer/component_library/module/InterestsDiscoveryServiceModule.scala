package com.twitter.product_mixer.component_library.module

import com.twitter.interests_discovery.thriftscala.InterestsDiscoveryService
import com.twitter.conversions.DurationOps._
import com.twitter.conversions.PercentOps._
import com.twitter.finagle.thriftmux.MethodBuilder
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.util.Duration

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

package com.X.product_mixer.component_library.module

import com.X.conversions.DurationOps._
import com.X.conversions.PercentOps._
import com.X.finagle.thriftmux.MethodBuilder
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.inject.Injector
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.peoplediscovery.api.thriftscala.ThriftPeopleDiscoveryService
import com.X.util.Duration

/**
 * Implementation with reasonable defaults for an idempotent People Discovery Thrift client.
 *
 * Note that the per request and total timeouts configured in this module are meant to represent a
 * reasonable starting point only. These were selected based on common practice, and should not be
 * assumed to be optimal for any particular use case. If you are interested in further tuning the
 * settings in this module, it is recommended to create local copy for your service.
 */
object PeopleDiscoveryServiceModule
    extends ThriftMethodBuilderClientModule[
      ThriftPeopleDiscoveryService.ServicePerEndpoint,
      ThriftPeopleDiscoveryService.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label: String = "people-discovery-api"

  override val dest: String = "/s/people-discovery-api/people-discovery-api:thrift"

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder = {
    methodBuilder
      .withTimeoutPerRequest(800.millis)
      .withTimeoutTotal(1200.millis)
      .idempotent(5.percent)
  }

  override protected def sessionAcquisitionTimeout: Duration = 500.milliseconds
}

package com.X.home_mixer.module

import com.X.conversions.DurationOps._
import com.X.finagle.thriftmux.MethodBuilder
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.inject.Injector
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.peoplediscovery.api.thriftscala.ThriftPeopleDiscoveryService
import com.X.util.Duration

/**
 * Copy of com.X.product_mixer.component_library.module.PeopleDiscoveryServiceModule
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
      .withTimeoutPerRequest(350.millis)
      .withTimeoutTotal(350.millis)
  }

  override protected def sessionAcquisitionTimeout: Duration = 500.milliseconds
}

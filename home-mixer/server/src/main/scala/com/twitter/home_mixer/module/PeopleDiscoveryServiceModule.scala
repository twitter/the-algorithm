package com.twitter.home_mixer.module

import com.twitter.conversions.DurationOps._
import com.twitter.finagle.thriftmux.MethodBuilder
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.peoplediscovery.api.thriftscala.ThriftPeopleDiscoveryService
import com.twitter.util.Duration

/**
 * Copy of com.twitter.product_mixer.component_library.module.PeopleDiscoveryServiceModule
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

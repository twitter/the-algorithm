package com.twitter.home_mitwitterer.module

import com.twitter.conversions.DurationOps._
import com.twitter.finagle.thriftmutwitter.MethodBuilder
import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.peoplediscovery.api.thriftscala.ThriftPeopleDiscoveryService
import com.twitter.util.Duration

/**
 * Copy of com.twitter.product_mitwitterer.component_library.module.PeopleDiscoveryServiceModule
 */
object PeopleDiscoveryServiceModule
    etwittertends ThriftMethodBuilderClientModule[
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

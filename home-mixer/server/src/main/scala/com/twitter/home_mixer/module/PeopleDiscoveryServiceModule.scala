package com.ExTwitter.home_mixer.module

import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.thriftmux.MethodBuilder
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.inject.Injector
import com.ExTwitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.ExTwitter.peoplediscovery.api.thriftscala.ThriftPeopleDiscoveryService
import com.ExTwitter.util.Duration

/**
 * Copy of com.ExTwitter.product_mixer.component_library.module.PeopleDiscoveryServiceModule
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

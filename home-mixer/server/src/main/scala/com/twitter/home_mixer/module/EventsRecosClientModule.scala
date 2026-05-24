package com.twitter.home_mixer.module

import com.twitter.conversions.DurationOps._
import com.twitter.events.recos.thriftscala.EventsRecosService
import com.twitter.finagle.thriftmux.MethodBuilder
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.util.Duration

object EventsRecosClientModule
    extends ThriftMethodBuilderClientModule[
      EventsRecosService.ServicePerEndpoint,
      EventsRecosService.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label: String = "events-recos"
  override val dest: String = "/s/events-recos/events-recos-service"
  override protected def sessionAcquisitionTimeout: Duration = 500.milliseconds

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder = {
    methodBuilder
      .withTimeoutPerRequest(450.millis)
      .withTimeoutTotal(450.millis)
  }
}

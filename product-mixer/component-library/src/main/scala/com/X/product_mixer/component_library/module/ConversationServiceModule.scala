package com.X.product_mixer.component_library.module

import com.X.conversions.DurationOps._
import com.X.conversions.PercentOps._
import com.X.finagle.ThriftMux
import com.X.finagle.thriftmux.MethodBuilder
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.inject.Injector
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.tweetconvosvc.thriftscala.ConversationService
import com.X.util.Duration
import org.apache.thrift.protocol.TCompactProtocol

object ConversationServiceModule
    extends ThriftMethodBuilderClientModule[
      ConversationService.ServicePerEndpoint,
      ConversationService.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label: String = "tweetconvosvc"
  override val dest: String = "/s/tweetconvosvc/tweetconvosvc"

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder =
    methodBuilder
      .withTimeoutTotal(200.milliseconds)
      .withTimeoutPerRequest(100.milliseconds)
      .idempotent(1.percent)

  override def configureThriftMuxClient(
    injector: Injector,
    client: ThriftMux.Client
  ): ThriftMux.Client =
    super
      .configureThriftMuxClient(injector, client)
      .withProtocolFactory(new TCompactProtocol.Factory())

  override protected def sessionAcquisitionTimeout: Duration = 500.milliseconds
}

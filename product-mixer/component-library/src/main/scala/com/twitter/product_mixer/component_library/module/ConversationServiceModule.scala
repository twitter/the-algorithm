package com.ExTwitter.product_mixer.component_library.module

import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.conversions.PercentOps._
import com.ExTwitter.finagle.ThriftMux
import com.ExTwitter.finagle.thriftmux.MethodBuilder
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.inject.Injector
import com.ExTwitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.ExTwitter.tweetconvosvc.thriftscala.ConversationService
import com.ExTwitter.util.Duration
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

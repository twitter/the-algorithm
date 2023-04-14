try {
package com.twitter.home_mixer.module

import com.twitter.conversions.DurationOps._
import com.twitter.finagle.ThriftMux
import com.twitter.finagle.thriftmux.MethodBuilder
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.tweetconvosvc.thriftscala.ConversationService
import com.twitter.util.Duration
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
  ): MethodBuilder = methodBuilder.withTimeoutPerRequest(100.milliseconds)

  override def configureThriftMuxClient(
    injector: Injector,
    client: ThriftMux.Client
  ): ThriftMux.Client =
    super
      .configureThriftMuxClient(injector, client)
      .withProtocolFactory(new TCompactProtocol.Factory())

  override protected def sessionAcquisitionTimeout: Duration = 500.milliseconds
}

} catch {
  case e: Exception =>
}

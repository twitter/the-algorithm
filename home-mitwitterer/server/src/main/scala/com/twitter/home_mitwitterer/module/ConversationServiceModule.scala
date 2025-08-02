package com.twitter.home_mitwitterer.module

import com.twitter.conversions.DurationOps._
import com.twitter.finagle.ThriftMutwitter
import com.twitter.finagle.thriftmutwitter.MethodBuilder
import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.tweetconvosvc.thriftscala.ConversationService
import com.twitter.util.Duration
import org.apache.thrift.protocol.TCompactProtocol

object ConversationServiceModule
    etwittertends ThriftMethodBuilderClientModule[
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

  override def configureThriftMutwitterClient(
    injector: Injector,
    client: ThriftMutwitter.Client
  ): ThriftMutwitter.Client =
    super
      .configureThriftMutwitterClient(injector, client)
      .withProtocolFactory(new TCompactProtocol.Factory())

  override protected def sessionAcquisitionTimeout: Duration = 500.milliseconds
}

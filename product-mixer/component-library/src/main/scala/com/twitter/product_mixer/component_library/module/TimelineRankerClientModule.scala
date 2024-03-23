package com.ExTwitter.product_mixer.component_library.module

import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.ThriftMux
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.finagle.mtls.client.MtlsStackClient._
import com.ExTwitter.finagle.thriftmux.MethodBuilder
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.inject.Injector
import com.ExTwitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.ExTwitter.timelineranker.{thriftscala => t}
import com.ExTwitter.util.Duration
import org.apache.thrift.protocol.TCompactProtocol

object TimelineRankerClientModule
    extends ThriftMethodBuilderClientModule[
      t.TimelineRanker.ServicePerEndpoint,
      t.TimelineRanker.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label = "timeline-ranker"
  override val dest = "/s/timelineranker/timelineranker:compactthrift"

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder = {
    methodBuilder
      .withTimeoutPerRequest(750.millis)
      .withTimeoutTotal(750.millis)
  }

  override def configureThriftMuxClient(
    injector: Injector,
    client: ThriftMux.Client
  ): ThriftMux.Client = {
    val serviceIdentifier = injector.instance[ServiceIdentifier]
    super
      .configureThriftMuxClient(injector, client)
      .withProtocolFactory(new TCompactProtocol.Factory())
      .withMutualTls(serviceIdentifier)
      .withPerEndpointStats
  }

  override protected def sessionAcquisitionTimeout: Duration = 500.milliseconds
}

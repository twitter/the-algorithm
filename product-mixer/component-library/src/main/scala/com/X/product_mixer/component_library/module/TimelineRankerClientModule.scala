package com.X.product_mixer.component_library.module

import com.X.conversions.DurationOps._
import com.X.finagle.ThriftMux
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.mtls.client.MtlsStackClient._
import com.X.finagle.thriftmux.MethodBuilder
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.inject.Injector
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.timelineranker.{thriftscala => t}
import com.X.util.Duration
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

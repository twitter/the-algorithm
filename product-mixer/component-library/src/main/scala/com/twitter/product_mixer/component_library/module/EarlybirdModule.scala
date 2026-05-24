package com.twitter.product_mixer.component_library.module

import com.twitter.conversions.DurationOps._
import com.twitter.conversions.PercentOps._
import com.twitter.finagle.ThriftMux
import com.twitter.finagle.thriftmux.MethodBuilder
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.annotations.Flags
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.search.earlybird.{thriftscala => t}
import com.twitter.util.Duration
import org.apache.thrift.protocol.TCompactProtocol

object EarlybirdModule
    extends ThriftMethodBuilderClientModule[
      t.EarlybirdService.ServicePerEndpoint,
      t.EarlybirdService.MethodPerEndpoint
    ]
    with MtlsClient {
  final val EarlybirdTimeoutPerRequest = "earlybird.timeout_per_request"
  final val EarlybirdTimeoutTotal = "earlybird.timeout_total"

  flag[Duration](
    name = EarlybirdTimeoutPerRequest,
    default = 200.milliseconds,
    help = "Timeout per request for Earlybird")

  flag[Duration](
    name = EarlybirdTimeoutTotal,
    default = 400.milliseconds,
    help = "Timeout total for Earlybird")

  override val dest = "/s/earlybird-root-superroot/root-superroot"
  override val label = "earlybird"

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder = {
    val timeOutPerRequest: Duration = injector
      .instance[Duration](Flags.named(EarlybirdTimeoutPerRequest))
    val timeOutTotal: Duration = injector.instance[Duration](Flags.named(EarlybirdTimeoutTotal))
    methodBuilder
    // See TL-14313 for load testing details that led to 200ms being selected as request timeout
      .withTimeoutPerRequest(timeOutPerRequest)
      .withTimeoutTotal(timeOutTotal)
      .idempotent(5.percent)
  }

  override def configureThriftMuxClient(
    injector: Injector,
    client: ThriftMux.Client
  ): ThriftMux.Client =
    super
      .configureThriftMuxClient(injector, client)
      .withProtocolFactory(new TCompactProtocol.Factory())

  override protected def sessionAcquisitionTimeout: Duration = 1.seconds
}

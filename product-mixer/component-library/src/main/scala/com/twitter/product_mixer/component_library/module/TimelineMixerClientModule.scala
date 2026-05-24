package com.twitter.product_mixer.component_library.module

import com.twitter.conversions.DurationOps._
import com.twitter.conversions.PercentOps._
import com.twitter.finagle.thriftmux.MethodBuilder
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.timelinemixer.{thriftscala => t}
import com.twitter.util.Duration

object TimelineMixerClientModule
    extends ThriftMethodBuilderClientModule[
      t.TimelineMixer.ServicePerEndpoint,
      t.TimelineMixer.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label = "timeline-mixer"
  override val dest = "/s/timelinemixer/timelinemixer"

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder = {
    methodBuilder
      .withTimeoutPerRequest(2000.millis)
      .withTimeoutTotal(4000.millis)
      .idempotent(1.percent)
  }

  override protected def sessionAcquisitionTimeout: Duration = 500.milliseconds
}

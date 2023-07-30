package com.X.product_mixer.component_library.module

import com.X.conversions.DurationOps._
import com.X.conversions.PercentOps._
import com.X.finagle.thriftmux.MethodBuilder
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.inject.Injector
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.timelinemixer.{thriftscala => t}
import com.X.util.Duration

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

package com.X.product_mixer.component_library.module

import com.google.inject.Provides
import com.X.conversions.DurationOps._
import com.X.conversions.PercentOps._
import com.X.finagle.thriftmux.MethodBuilder
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.inject.Injector
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.stitch.timelineservice.TimelineService
import com.X.timelineservice.{thriftscala => t}
import com.X.util.Duration
import javax.inject.Singleton

object TimelineServiceClientModule
    extends ThriftMethodBuilderClientModule[
      t.TimelineService.ServicePerEndpoint,
      t.TimelineService.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label = "timelineservice"
  override val dest = "/s/timelineservice/timelineservice"

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder = {
    methodBuilder
      .withTimeoutPerRequest(1200.millis)
      .withTimeoutTotal(2400.millis)
      .idempotent(1.percent)
  }

  override protected def sessionAcquisitionTimeout: Duration = 500.milliseconds

  @Singleton
  @Provides
  def providesTimelineServiceStitchClient(
    client: t.TimelineService.MethodPerEndpoint
  ): TimelineService = {
    new TimelineService(client)
  }
}

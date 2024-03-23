package com.ExTwitter.product_mixer.component_library.module

import com.google.inject.Provides
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.conversions.PercentOps._
import com.ExTwitter.finagle.thriftmux.MethodBuilder
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.inject.Injector
import com.ExTwitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.ExTwitter.stitch.timelineservice.TimelineService
import com.ExTwitter.timelineservice.{thriftscala => t}
import com.ExTwitter.util.Duration
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

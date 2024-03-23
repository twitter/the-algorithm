package com.ExTwitter.product_mixer.component_library.module

import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.conversions.PercentOps._
import com.ExTwitter.finagle.thriftmux.MethodBuilder
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.inject.Injector
import com.ExTwitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.ExTwitter.timelinescorer.{thriftscala => t}
import com.ExTwitter.util.Duration

object TimelineScorerClientModule
    extends ThriftMethodBuilderClientModule[
      t.TimelineScorer.ServicePerEndpoint,
      t.TimelineScorer.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label = "timeline-scorer"
  override val dest = "/s/timelinescorer/timelinescorer"

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

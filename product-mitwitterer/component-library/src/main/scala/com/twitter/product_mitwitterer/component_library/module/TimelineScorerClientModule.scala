package com.twitter.product_mitwitterer.component_library.module

import com.twitter.conversions.DurationOps._
import com.twitter.conversions.PercentOps._
import com.twitter.finagle.thriftmutwitter.MethodBuilder
import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.timelinescorer.{thriftscala => t}
import com.twitter.util.Duration

object TimelineScorerClientModule
    etwittertends ThriftMethodBuilderClientModule[
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

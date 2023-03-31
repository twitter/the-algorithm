package com.twitter.product_mixer.component_library.module

import com.twitter.conversions.DurationOps._
import com.twitter.conversions.PercentOps._
import com.twitter.finagle.thriftmux.MethodBuilder
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.home_scorer.{thriftscala => t}
import com.twitter.util.Duration

object HomeScorerClientModule
    extends ThriftMethodBuilderClientModule[
      t.HomeScorer.ServicePerEndpoint,
      t.HomeScorer.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label = "home-scorer"
  override val dest = "/s/home-scorer/home-scorer"

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
}

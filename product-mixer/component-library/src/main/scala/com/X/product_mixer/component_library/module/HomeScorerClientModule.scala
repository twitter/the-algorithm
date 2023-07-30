package com.X.product_mixer.component_library.module

import com.X.conversions.DurationOps._
import com.X.conversions.PercentOps._
import com.X.finagle.thriftmux.MethodBuilder
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.inject.Injector
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.home_scorer.{thriftscala => t}
import com.X.util.Duration

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

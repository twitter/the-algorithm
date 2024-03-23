package com.ExTwitter.product_mixer.component_library.module

import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.conversions.PercentOps._
import com.ExTwitter.finagle.thriftmux.MethodBuilder
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.inject.Injector
import com.ExTwitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.ExTwitter.home_scorer.{thriftscala => t}
import com.ExTwitter.util.Duration

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

package com.twitter.product_mixer.component_library.module

import com.twitter.conversions.DurationOps._
import com.twitter.conversions.PercentOps._
import com.twitter.cr_mixer.{thriftscala => t}
import com.twitter.finagle.thriftmux.MethodBuilder
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.util.Duration

object CrMixerClientModule
    extends ThriftMethodBuilderClientModule[
      t.CrMixer.ServicePerEndpoint,
      t.CrMixer.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label = "cr-mixer"
  override val dest = "/s/cr-mixer/cr-mixer"

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder = {
    methodBuilder
      .withTimeoutPerRequest(500.millis)
      .withTimeoutTotal(750.millis)
      .idempotent(1.percent)
  }

  override protected def sessionAcquisitionTimeout: Duration = 500.milliseconds
}

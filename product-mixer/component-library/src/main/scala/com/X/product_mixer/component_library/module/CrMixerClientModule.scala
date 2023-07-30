package com.X.product_mixer.component_library.module

import com.X.conversions.DurationOps._
import com.X.conversions.PercentOps._
import com.X.cr_mixer.{thriftscala => t}
import com.X.finagle.thriftmux.MethodBuilder
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.inject.Injector
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.util.Duration

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

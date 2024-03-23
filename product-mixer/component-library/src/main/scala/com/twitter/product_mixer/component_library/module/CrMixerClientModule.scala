package com.ExTwitter.product_mixer.component_library.module

import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.conversions.PercentOps._
import com.ExTwitter.cr_mixer.{thriftscala => t}
import com.ExTwitter.finagle.thriftmux.MethodBuilder
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.inject.Injector
import com.ExTwitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.ExTwitter.util.Duration

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

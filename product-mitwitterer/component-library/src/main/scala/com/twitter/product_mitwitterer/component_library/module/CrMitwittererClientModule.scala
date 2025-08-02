package com.twitter.product_mitwitterer.component_library.module

import com.twitter.conversions.DurationOps._
import com.twitter.conversions.PercentOps._
import com.twitter.cr_mitwitterer.{thriftscala => t}
import com.twitter.finagle.thriftmutwitter.MethodBuilder
import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.util.Duration

object CrMitwittererClientModule
    etwittertends ThriftMethodBuilderClientModule[
      t.CrMitwitterer.ServicePerEndpoint,
      t.CrMitwitterer.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label = "cr-mitwitterer"
  override val dest = "/s/cr-mitwitterer/cr-mitwitterer"

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

package com.twitter.home_mixer.module

import com.twitter.conversions.DurationOps._
import com.twitter.finagle.thriftmux.MethodBuilder
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.limiter.{thriftscala => t}
import com.twitter.util.Duration

object LimiterModule
    extends ThriftMethodBuilderClientModule[
      t.LimitService.ServicePerEndpoint,
      t.LimitService.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label: String = "limiter"
  override val dest: String = "/s/limiter/limiter"

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder = methodBuilder.withTimeoutPerRequest(200.milliseconds)

  override protected def sessionAcquisitionTimeout: Duration = 500.milliseconds
}

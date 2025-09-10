package com.twitter.tweet_mixer.module

import com.twitter.conversions.DurationOps._
import com.twitter.finagle.thriftmux.MethodBuilder
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.hydra.root.{thriftscala => ht}
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule

object HydraRootClientModule
    extends ThriftMethodBuilderClientModule[
      ht.HydraRoot.ServicePerEndpoint,
      ht.HydraRoot.MethodPerEndpoint
    ]
    with MtlsClient {
  override def label: String = "hydra-root"

  override def dest: String = "/s/hydra/hydra-root"

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder = methodBuilder.withTimeoutTotal(500.milliseconds)
}

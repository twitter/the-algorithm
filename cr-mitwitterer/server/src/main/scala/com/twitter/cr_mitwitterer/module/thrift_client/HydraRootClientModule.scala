package com.twitter.cr_mitwitterer.module.thrift_client

import com.twitter.conversions.DurationOps._
import com.twitter.finagle.thriftmutwitter.MethodBuilder
import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsClient
import com.twitter.hydra.root.{thriftscala => ht}
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule

object HydraRootClientModule
    etwittertends ThriftMethodBuilderClientModule[
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

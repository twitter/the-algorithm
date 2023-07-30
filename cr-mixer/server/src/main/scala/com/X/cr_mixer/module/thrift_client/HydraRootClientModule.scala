package com.X.cr_mixer.module.thrift_client

import com.X.conversions.DurationOps._
import com.X.finagle.thriftmux.MethodBuilder
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.hydra.root.{thriftscala => ht}
import com.X.inject.Injector
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule

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

package com.ExTwitter.cr_mixer.module.thrift_client

import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.thriftmux.MethodBuilder
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.hydra.root.{thriftscala => ht}
import com.ExTwitter.inject.Injector
import com.ExTwitter.inject.thrift.modules.ThriftMethodBuilderClientModule

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

package com.ExTwitter.cr_mixer.module.thrift_client

import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.thriftmux.MethodBuilder
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.inject.Injector
import com.ExTwitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.ExTwitter.hydra.partition.{thriftscala => ht}

object HydraPartitionClientModule
    extends ThriftMethodBuilderClientModule[
      ht.HydraPartition.ServicePerEndpoint,
      ht.HydraPartition.MethodPerEndpoint
    ]
    with MtlsClient {
  override def label: String = "hydra-partition"

  override def dest: String = "/s/hydra/hydra-partition"

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder = methodBuilder.withTimeoutTotal(500.milliseconds)

}

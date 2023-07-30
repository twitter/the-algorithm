package com.X.cr_mixer.module.thrift_client

import com.X.conversions.DurationOps._
import com.X.finagle.thriftmux.MethodBuilder
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.inject.Injector
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.hydra.partition.{thriftscala => ht}

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

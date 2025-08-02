package com.twitter.cr_mitwitterer.module.thrift_client

import com.twitter.conversions.DurationOps._
import com.twitter.finagle.thriftmutwitter.MethodBuilder
import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.hydra.partition.{thriftscala => ht}

object HydraPartitionClientModule
    etwittertends ThriftMethodBuilderClientModule[
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

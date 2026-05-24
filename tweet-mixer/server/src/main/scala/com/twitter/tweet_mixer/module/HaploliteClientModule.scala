package com.twitter.tweet_mixer.module

import com.twitter.conversions.DurationOps._
import com.twitter.finagle.thriftmux.MethodBuilder
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.haplolite.{thriftscala => t}
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule

object HaploliteClientModule
    extends ThriftMethodBuilderClientModule[
      t.Haplolite.ServicePerEndpoint,
      t.Haplolite.MethodPerEndpoint
    ]
    with MtlsClient {
  override def label: String = "haplolite"

  override def dest: String = "/s/haplolite/haplolite-nonfanout"

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder = methodBuilder.withTimeoutTotal(100.milliseconds)
}

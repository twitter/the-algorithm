package com.twitter.product_mitwitterer.component_library.module

import com.twitter.conversions.DurationOps._
import com.twitter.etwitterplore_ranker.thriftscala.EtwitterploreRanker
import com.twitter.finagle.thriftmutwitter.MethodBuilder
import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsClient
import com.twitter.inject.annotations.Flags
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.util.Duration

object EtwitterploreRankerClientModule
    etwittertends ThriftMethodBuilderClientModule[
      EtwitterploreRanker.ServicePerEndpoint,
      EtwitterploreRanker.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label: String = "etwitterplore-ranker"
  override val dest: String = "/s/etwitterplore-ranker/etwitterplore-ranker"

  private final val EtwitterploreRankerTimeoutTotal = "etwitterplore_ranker.timeout_total"

  flag[Duration](
    name = EtwitterploreRankerTimeoutTotal,
    default = 800.milliseconds,
    help = "Timeout total for EtwitterploreRanker")

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder = {
    val timeoutTotal: Duration = injector.instance[Duration](Flags.named(EtwitterploreRankerTimeoutTotal))
    methodBuilder
      .withTimeoutTotal(timeoutTotal)
      .nonIdempotent
  }
}

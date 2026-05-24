package com.twitter.product_mixer.component_library.module

import com.twitter.conversions.DurationOps._
import com.twitter.explore_ranker.thriftscala.ExploreRanker
import com.twitter.finagle.thriftmux.MethodBuilder
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.inject.annotations.Flags
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.util.Duration

object ExploreRankerClientModule
    extends ThriftMethodBuilderClientModule[
      ExploreRanker.ServicePerEndpoint,
      ExploreRanker.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label: String = "explore-ranker"
  override val dest: String = "/s/explore-ranker/explore-ranker"

  private final val ExploreRankerTimeoutTotal = "explore_ranker.timeout_total"

  flag[Duration](
    name = ExploreRankerTimeoutTotal,
    default = 800.milliseconds,
    help = "Timeout total for ExploreRanker")

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder = {
    val timeoutTotal: Duration = injector.instance[Duration](Flags.named(ExploreRankerTimeoutTotal))
    methodBuilder
      .withTimeoutTotal(timeoutTotal)
      .nonIdempotent
  }
}

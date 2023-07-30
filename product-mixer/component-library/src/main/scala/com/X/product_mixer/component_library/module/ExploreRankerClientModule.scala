package com.X.product_mixer.component_library.module

import com.X.conversions.DurationOps._
import com.X.explore_ranker.thriftscala.ExploreRanker
import com.X.finagle.thriftmux.MethodBuilder
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.inject.annotations.Flags
import com.X.inject.Injector
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.util.Duration

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

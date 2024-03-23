package com.ExTwitter.product_mixer.component_library.module

import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.explore_ranker.thriftscala.ExploreRanker
import com.ExTwitter.finagle.thriftmux.MethodBuilder
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.inject.annotations.Flags
import com.ExTwitter.inject.Injector
import com.ExTwitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.ExTwitter.util.Duration

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

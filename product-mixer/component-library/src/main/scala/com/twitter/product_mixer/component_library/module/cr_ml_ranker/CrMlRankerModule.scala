package com.twitter.product_mixer.component_library.module.cr_ml_ranker

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.cr_ml_ranker.thriftscala.CrMLRanker
import com.twitter.finagle.thriftmux.MethodBuilder
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.product_mixer.component_library.scorer.cr_ml_ranker.CrMlRankerScoreStitchClient
import com.twitter.util.Duration
import javax.inject.Singleton

case class CrMLRankerModule(totalTimeout: Duration = 100.milliseconds, batchSize: Int = 50)
    extends ThriftMethodBuilderClientModule[
      CrMLRanker.ServicePerEndpoint,
      CrMLRanker.MethodPerEndpoint
    ]
    with MtlsClient {
  override val label = "cr-ml-ranker"
  override val dest = "/s/cr-ml-ranker/cr-ml-ranker"

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder = {
    methodBuilder
      .withTimeoutTotal(totalTimeout)
  }

  @Provides
  @Singleton
  def providesStitchClient(
    crMlRankerThriftClient: CrMLRanker.MethodPerEndpoint
  ): CrMlRankerScoreStitchClient = new CrMlRankerScoreStitchClient(
    crMlRankerThriftClient,
    maxBatchSize = batchSize
  )
}

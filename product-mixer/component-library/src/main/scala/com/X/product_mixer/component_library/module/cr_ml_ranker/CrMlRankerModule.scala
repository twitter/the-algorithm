package com.X.product_mixer.component_library.module.cr_ml_ranker

import com.google.inject.Provides
import com.X.conversions.DurationOps._
import com.X.cr_ml_ranker.thriftscala.CrMLRanker
import com.X.finagle.thriftmux.MethodBuilder
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.inject.Injector
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.product_mixer.component_library.scorer.cr_ml_ranker.CrMlRankerScoreStitchClient
import com.X.util.Duration
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

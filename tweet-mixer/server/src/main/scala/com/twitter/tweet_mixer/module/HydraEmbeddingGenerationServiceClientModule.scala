package com.twitter.tweet_mixer.module

import com.twitter.conversions.DurationOps._
import com.twitter.conversions.PercentOps._
import com.twitter.finagle.thriftmux.MethodBuilder
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.hydra.embedding_generation.thriftscala.EmbeddingGenerationService
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.util.Duration

object HydraEmbeddingGenerationServiceClientModule
    extends ThriftMethodBuilderClientModule[
      EmbeddingGenerationService.ServicePerEndpoint,
      EmbeddingGenerationService.MethodPerEndpoint
    ]
    with MtlsClient {

  final val PerRequestTimeout = flag[Duration](
    name = "egs.per_request_timeout",
    default = 150.millis,
    help = "Timeout for each request to EGS in millisecond")

  final val TotalTimeout = flag[Duration](
    name = "egs.total_timeout",
    default = 250.millis,
    help = "Total timeout for all requests to EGS in millisecond")

  override val label: String = "embedding-generation-service"
  override val dest: String = "/s/hydra/hydra-embedding-generation"

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder = {
    methodBuilder
      .withTimeoutPerRequest(PerRequestTimeout())
      .withTimeoutTotal(TotalTimeout())
      .idempotent(1.percent)
  }
}

package com.twitter.tweet_mixer.module.thrift_client

import com.google.inject.Provides
import com.twitter.ann.common.AnnInjections
import com.twitter.ann.common.Cosine
import com.twitter.ann.common.CosineDistance
import com.twitter.ann.common.Distance
import com.twitter.ann.common.EmbeddingProducer
import com.twitter.ann.common.QueryableById
import com.twitter.ann.common.QueryableByIdImplementation
import com.twitter.ann.common.RuntimeParams
import com.twitter.ann.common.ServiceClientQueryable
import com.twitter.ann.common.thriftscala.AnnQueryService
import com.twitter.ann.common.thriftscala.NearestNeighborQuery
import com.twitter.ann.common.thriftscala.NearestNeighborResult
import com.twitter.ann.common.thriftscala.{Distance => ServiceDistance}
import com.twitter.ann.common.thriftscala.{RuntimeParams => ServiceRuntimeParams}
import com.twitter.ann.hnsw.HnswCommon
import com.twitter.ann.hnsw.HnswParams
import com.twitter.bijection.Injection
import com.twitter.finagle.Service
import com.twitter.inject.TwitterModule
import com.twitter.tweet_mixer.model.ModuleNames
import com.twitter.util.Future
import javax.inject.Named
import javax.inject.Singleton

object AnnQueryableByIdModule extends TwitterModule {

  @Provides
  @Singleton
  @Named(ModuleNames.TwHINAnnQueryableById)
  def twHINAnnQueryableById(
    @Named(ModuleNames.TwHINRegularUpdateAnnServiceClientName)
    twhinAnnClient: AnnQueryService.MethodPerEndpoint,
    @Named(ModuleNames.TwHINEmbeddingRegularUpdateMhEmbeddingProducer)
    embeddingProducer: EmbeddingProducer[Long],
  ): QueryableById[Long, Long, HnswParams, CosineDistance] = {
    val runtimeParamInjection = HnswCommon.RuntimeParamsInjection
    val distanceInjection = Cosine
    val idInjection = AnnInjections.LongInjection
    buildQueryableById(
      twhinAnnClient,
      embeddingProducer,
      runtimeParamInjection,
      distanceInjection,
      idInjection)
  }

  @Provides
  @Singleton
  @Named(ModuleNames.ConsumerBasedTwHINAnnQueryableById)
  def consumerBasedTwHINAnnQueryableById(
    @Named(ModuleNames.TwHINRegularUpdateAnnServiceClientName)
    twhinAnnClient: AnnQueryService.MethodPerEndpoint,
    @Named(ModuleNames.ConsumerBasedTwHINEmbeddingRegularUpdateMhEmbeddingProducer)
    embeddingProducer: EmbeddingProducer[Long],
  ): QueryableById[Long, Long, HnswParams, CosineDistance] = {
    val runtimeParamInjection = HnswCommon.RuntimeParamsInjection
    val distanceInjection = Cosine
    val idInjection = AnnInjections.LongInjection
    buildQueryableById(
      twhinAnnClient,
      embeddingProducer,
      runtimeParamInjection,
      distanceInjection,
      idInjection)
  }

  def buildQueryableById[T1, T2, P <: RuntimeParams, D <: Distance[D]](
    twhinAnnClient: AnnQueryService.MethodPerEndpoint,
    embeddingProducer: EmbeddingProducer[T1],
    runtimeParamInjection: Injection[P, ServiceRuntimeParams],
    distanceInjection: Injection[D, ServiceDistance],
    idInjection: Injection[T2, Array[Byte]]
  ): QueryableById[T1, T2, P, D] = {
    val service = new Service[NearestNeighborQuery, NearestNeighborResult] {
      override def apply(request: NearestNeighborQuery): Future[NearestNeighborResult] =
        twhinAnnClient.query(request)
    }

    val queryableClient = new ServiceClientQueryable[T2, P, D](
      service,
      runtimeParamInjection,
      distanceInjection,
      idInjection
    )
    new QueryableByIdImplementation(
      embeddingProducer,
      queryableClient
    )
  }
}

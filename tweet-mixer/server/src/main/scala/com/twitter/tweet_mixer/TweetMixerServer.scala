package com.twitter.tweet_mixer

import com.google.inject.Module
import com.twitter.finagle.Filter
import com.twitter.finagle.ThriftMux
import com.twitter.finagle.mux.transport.Compression
import com.twitter.finagle.mux.transport.CompressionLevel
import com.twitter.finatra.annotations.DarkTrafficFilterType
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.mtls.http.{Mtls => HttpMtls}
import com.twitter.finatra.mtls.thriftmux.Mtls
import com.twitter.finatra.mtls.thriftmux.modules.MtlsThriftWebFormsModule
import com.twitter.finatra.thrift.ThriftServer
import com.twitter.finatra.thrift.filters._
import com.twitter.finatra.thrift.routing.ThriftRouter
import com.twitter.geoduck.service.common.clientmodules.GeoduckUserLocateModule
import com.twitter.product_mixer.component_library.module.DarkTrafficFilterModule
import com.twitter.product_mixer.component_library.module.FeedbackHistoryClientModule
import com.twitter.product_mixer.component_library.module.GizmoduckClientModule
import com.twitter.product_mixer.component_library.module.MemcachedImpressionBloomFilterStoreModule
import com.twitter.product_mixer.component_library.module.MemcachedVideoImpressionBloomFilterStoreModule
import com.twitter.product_mixer.component_library.module.OnboardingTaskServiceModule
import com.twitter.product_mixer.component_library.module.SocialGraphServiceModule
import com.twitter.product_mixer.component_library.module.TestUserMapperConfigModule
import com.twitter.product_mixer.component_library.module.TimelineServiceClientModule
import com.twitter.product_mixer.component_library.module.UtegClientModule
import com.twitter.product_mixer.component_library.module.UserSessionStoreModule
import com.twitter.product_mixer.core.controllers.ProductMixerController
import com.twitter.product_mixer.core.module.LoggingThrowableExceptionMapper
import com.twitter.product_mixer.core.module.ProductMixerModule
import com.twitter.product_mixer.core.module.StratoClientModule
import com.twitter.product_mixer.core.module.stringcenter.ProductScopeStringCenterModule
import com.twitter.tweet_mixer.controller.TweetMixerThriftController
import com.twitter.tweet_mixer.module.CertoStratoTopicTweetsStoreModule
import com.twitter.tweet_mixer.module.ExtendedStratoClientModule
import com.twitter.tweet_mixer.module.GPURetrievalHttpClientModule
import com.twitter.tweet_mixer.module.HaploliteClientModule
import com.twitter.tweet_mixer.module.HydraEmbeddingGenerationServiceClientModule
import com.twitter.tweet_mixer.module.HydraRootClientModule
import com.twitter.tweet_mixer.module.InMemoryCacheModule
import com.twitter.tweet_mixer.module.MHMtlsParamsModule
import com.twitter.tweet_mixer.module.ManhattanFeatureRepositoryModule
import com.twitter.tweet_mixer.module.MemcacheClientModule
import com.twitter.tweet_mixer.module.PipelineFailureExceptionMapper
import com.twitter.tweet_mixer.module.SampleFeatureStoreV1DynamicClientBuilderModule
import com.twitter.tweet_mixer.module.SimClustersANNServiceNameToClientMapper
import com.twitter.tweet_mixer.module.SkitStratoTopicTweetsStoreModule
import com.twitter.tweet_mixer.module.StitchMemcacheClientModule
import com.twitter.tweet_mixer.module.TimeoutConfigModule
import com.twitter.tweet_mixer.module.TwHINANNServiceModule
import com.twitter.tweet_mixer.module.TwHINEmbeddingStoreModule
import com.twitter.tweet_mixer.module.TweetMixerFlagModule
import com.twitter.tweet_mixer.module.UserStateStoreModule
import com.twitter.tweet_mixer.module.thrift_client.AnnEmbeddingProducerModule
import com.twitter.tweet_mixer.module.thrift_client.AnnQueryServiceClientModule
import com.twitter.tweet_mixer.module.thrift_client.AnnQueryableByIdModule
import com.twitter.tweet_mixer.module.thrift_client.EarlybirdRealtimeCGModule
import com.twitter.tweet_mixer.module.thrift_client.GeoduckHydrationClientModule
import com.twitter.tweet_mixer.module.thrift_client.GeoduckLocationServiceClientModule
import com.twitter.tweet_mixer.module.thrift_client.SimClustersAnnServiceClientModule
import com.twitter.tweet_mixer.module.thrift_client.TweetyPieClientModule
import com.twitter.tweet_mixer.module.thrift_client.UserTweetGraphClientModule
import com.twitter.tweet_mixer.module.thrift_client.UserVideoGraphClientModule
import com.twitter.tweet_mixer.module.thrift_client.VecDBAnnServiceClientModule
import com.twitter.tweet_mixer.param.GlobalParamConfigModule
import com.twitter.tweet_mixer.product.TweetMixerProductModule
import com.twitter.tweet_mixer.{thriftscala => t}
import com.twitter.tweet_mixer.module.thrift_client.QigServiceClientModule

object TweetMixerServerMain extends TweetMixerServer

class TweetMixerServer extends ThriftServer with Mtls with HttpServer with HttpMtls {
  override val name = "tweet-mixer-server"

  override val modules: Seq[Module] = Seq(
    AnnEmbeddingProducerModule,
    AnnQueryableByIdModule,
    AnnQueryServiceClientModule,
    CertoStratoTopicTweetsStoreModule,
    new DarkTrafficFilterModule[t.TweetMixer.ReqRepServicePerEndpoint](),
    EarlybirdRealtimeCGModule,
    FeedbackHistoryClientModule,
    GeoduckHydrationClientModule,
    GeoduckLocationServiceClientModule,
    GeoduckUserLocateModule,
    GizmoduckClientModule,
    GlobalParamConfigModule,
    GPURetrievalHttpClientModule,
    HaploliteClientModule,
    HydraRootClientModule,
    HydraEmbeddingGenerationServiceClientModule,
    InMemoryCacheModule,
    ManhattanFeatureRepositoryModule,
    MemcacheClientModule,
    MemcachedImpressionBloomFilterStoreModule,
    MemcachedVideoImpressionBloomFilterStoreModule,
    MHMtlsParamsModule,
    new MtlsThriftWebFormsModule[t.TweetMixer.MethodPerEndpoint](this),
    OnboardingTaskServiceModule,
    ProductMixerModule,
    new ProductScopeStringCenterModule(),
    QigServiceClientModule,
    SampleFeatureStoreV1DynamicClientBuilderModule,
    SimClustersAnnServiceClientModule,
    SimClustersANNServiceNameToClientMapper,
    SkitStratoTopicTweetsStoreModule,
    SocialGraphServiceModule,
    StitchMemcacheClientModule,
    StratoClientModule,
    TestUserMapperConfigModule,
    TimeoutConfigModule,
    TweetMixerFlagModule,
    TweetMixerProductModule,
    TweetyPieClientModule,
    TwHINANNServiceModule,
    TimelineServiceClientModule,
    TwHINEmbeddingStoreModule,
    UserTweetGraphClientModule,
    UserVideoGraphClientModule,
    UserStateStoreModule,
    UtegClientModule,
    VecDBAnnServiceClientModule,
    ExtendedStratoClientModule,
    UserSessionStoreModule
  )

  def configureThrift(router: ThriftRouter): Unit = {
    router
      .filter[LoggingMDCFilter]
      .filter[TraceIdMDCFilter]
      .filter[ThriftMDCFilter]
      .filter[StatsFilter]
      .filter[AccessLoggingFilter]
      .filter[ExceptionMappingFilter]
      .filter[Filter.TypeAgnostic, DarkTrafficFilterType]
      .exceptionMapper[LoggingThrowableExceptionMapper]
      .exceptionMapper[PipelineFailureExceptionMapper]
      .add[TweetMixerThriftController]
  }

  override def configureHttp(router: HttpRouter): Unit = {
    router.add(
      ProductMixerController[t.TweetMixer.MethodPerEndpoint](
        this.injector,
        t.TweetMixer.ExecutePipeline
      )
    )
  }

  override def configureThriftServer(server: ThriftMux.Server): ThriftMux.Server = {
    val compressor = Seq(Compression.lz4Compressor(highCompression = true))
    val decompressor = Seq(Compression.lz4Decompressor())

    // Mixer services accept compression to speed up debug requests from Turntable (which requests
    // compression). Normal service calls will remain uncompressed unless the requesting service
    // explicitly asks for compression.
    val compressionLevel = CompressionLevel.Accepted

    server.withCompressionPreferences
      .compression(compressionLevel, compressor)
      .withCompressionPreferences.decompression(compressionLevel, decompressor)
  }

  override protected def warmup(): Unit = {
    handle[TweetMixerThriftServerWarmupHandler]()
    handle[TweetMixerHttpServerWarmupHandler]()
  }
}

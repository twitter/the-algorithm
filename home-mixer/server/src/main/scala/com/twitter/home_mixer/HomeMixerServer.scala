package com.twitter.home_mixer

import com.google.inject.Module
import com.twitter.conversions.DurationOps._
import com.twitter.conversions.StorageUnitOps.richStorageUnitFromInt
import com.twitter.finagle.Filter
import com.twitter.finagle.Http
import com.twitter.finagle.Thrift
import com.twitter.finagle.ThriftMux
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.mtls.server.MtlsStackServer.MtlsHttpServerSyntax
import com.twitter.finagle.netty4.param.TrackWorkerPool
import com.twitter.finatra.annotations.DarkTrafficFilterType
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.mtls.http.{Mtls => HttpMtls}
import com.twitter.finatra.mtls.thriftmux.Mtls
import com.twitter.finatra.mtls.thriftmux.modules.MtlsThriftWebFormsModule
import com.twitter.finatra.thrift.ThriftServer
import com.twitter.finatra.thrift.filters._
import com.twitter.finatra.thrift.routing.ThriftRouter
import com.twitter.home_mixer.controller.HomeHttpController
import com.twitter.home_mixer.controller.HomeThriftController
import com.twitter.home_mixer.federated.HomeMixerColumn
import com.twitter.home_mixer.module._
import com.twitter.home_mixer.param.GlobalParamConfigModule
import com.twitter.home_mixer.product.HomeMixerProductModule
import com.twitter.home_mixer.{thriftscala => st}
import com.twitter.joinkey.context.CreateRequestJoinKeyContextFilter
import com.twitter.product_mixer.component_library.module.AccountRecommendationsMixerModule
import com.twitter.product_mixer.component_library.module.CommunitiesMixerClientModule
import com.twitter.product_mixer.component_library.module.DarkTrafficFilterModule
import com.twitter.product_mixer.component_library.module.EarlybirdModule
import com.twitter.product_mixer.component_library.module.FeedbackHistoryClientModule
import com.twitter.product_mixer.component_library.module.GizmoduckClientModule
import com.twitter.product_mixer.component_library.module.MemcachedImpressionBloomFilterStoreModule
import com.twitter.product_mixer.component_library.module.OnboardingTaskServiceModule
import com.twitter.product_mixer.component_library.module.SocialGraphServiceModule
import com.twitter.product_mixer.component_library.module.StaleTweetsCacheModule
import com.twitter.product_mixer.component_library.module.TestUserMapperConfigModule
import com.twitter.product_mixer.component_library.module.TimelineRankerClientModule
import com.twitter.product_mixer.component_library.module.TimelineServiceClientModule
import com.twitter.product_mixer.component_library.module.TweetImpressionStoreModule
import com.twitter.product_mixer.component_library.module.TweetMixerClientModule
import com.twitter.product_mixer.component_library.module.UserSessionStoreModule
import com.twitter.product_mixer.component_library.module.UtegClientModule
import com.twitter.product_mixer.component_library.module.UtvgClientModule
import com.twitter.product_mixer.core.controllers.ProductMixerController
import com.twitter.product_mixer.core.module.LoggingThrowableExceptionMapper
import com.twitter.product_mixer.core.module.ProductMixerModule
import com.twitter.product_mixer.core.module.stringcenter.ProductScopeStringCenterModule
import com.twitter.scrooge.TUnsafeBinaryProtocolFactory
import com.twitter.strato.fed.StratoFed
import com.twitter.strato.fed.server.StratoFedServer

object HomeMixerServerMain extends HomeMixerServer

class HomeMixerServer
    extends StratoFedServer
    with ThriftServer
    with Mtls
    with HttpServer
    with HttpMtls {
  override val name = "home-mixer-server"

  override val modules: Seq[Module] = Seq(
    AccountRecommendationsMixerModule,
    AdvertiserBrandSafetySettingsStoreModule,
    ClientSentImpressionsPublisherModule,
    ClusterDetailsModule,
    CommunitiesMixerClientModule,
    ConversationServiceModule,
    EarlybirdModule,
    EarlybirdRealtimeCGModule,
    EventsRecosClientModule,
    FeedbackHistoryClientModule,
    GizmoduckClientModule,
    GizmoduckTimelinesCacheClientModule,
    GlobalParamConfigModule,
    HomeAdsCandidateSourceModule,
    HomeMixerFeaturesModule,
    HomeMixerFlagsModule,
    HomeMixerProductModule,
    HomeMixerResourcesModule,
    InMemoryCacheModule,
    InjectionHistoryClientModule,
    LimiterModule,
    ManhattanClientsModule,
    ManhattanFeatureRepositoryModule,
    MediaClusterId88Module,
    MediaClusterId95Module,
    MemcachedFeatureRepositoryModule,
    MemcachedImpressionBloomFilterStoreModule,
    MemcachedScoredCandidateFeaturesStoreModule,
    NaviModelClientModule,
    OnboardingTaskServiceModule,
    OptimizedStratoClientModule,
    PeopleDiscoveryServiceModule,
    PhoenixClientModule,
    ProductMixerModule,
    RealGraphInNetworkScoresModule,
    RealtimeAggregateFeatureRepositoryModule,
    ScoredTweetsMemcacheModule,
    ScoredVideoTweetsMemcacheModule,
    ScribeEventPublisherModule,
    SimClustersRecentEngagementsClientModule,
    SocialGraphServiceModule,
    StaleTweetsCacheModule,
    TestUserMapperConfigModule,
    ThriftFeatureRepositoryModule,
    TimelineRankerClientModule,
    TimelineServiceClientModule,
    TimelinesPersistenceStoreClientModule,
    TopicSocialProofClientModule,
    TvWatchHistoryCacheClientModule,
    TweetImpressionStoreModule,
    TweetMixerClientModule,
    TweetWatchTimeMetadataModule,
    TweetypieClientModule,
    TweetypieStaticEntitiesCacheClientModule,
    TwhinEmbeddingsModule,
    UserSessionStoreModule,
    UtegClientModule,
    UttTopicModule,
    UtvgClientModule,
    VideoEmbeddingModule,
    new DarkTrafficFilterModule[st.HomeMixer.ReqRepServicePerEndpoint](),
    new MtlsThriftWebFormsModule[st.HomeMixer.MethodPerEndpoint](this),
    new ProductScopeStringCenterModule()
  )

  val requestJoinKeyContextFilter = new CreateRequestJoinKeyContextFilter

  override def configureThrift(router: ThriftRouter): Unit = {
    router
      .filter[LoggingMDCFilter]
      .filter[TraceIdMDCFilter]
      .filter[ThriftMDCFilter]
      .filter[StatsFilter]
      .filter[AccessLoggingFilter]
      .filter[ExceptionMappingFilter]
      .filter[Filter.TypeAgnostic, DarkTrafficFilterType]
      .filter(requestJoinKeyContextFilter)
      .exceptionMapper[LoggingThrowableExceptionMapper]
      .exceptionMapper[PipelineFailureExceptionMapper]
      .add[HomeThriftController]
  }

  override def configureStratoThriftServer(server: ThriftMux.Server): ThriftMux.Server = {
    super
      .configureStratoThriftServer(server)
      .configured(
        TrackWorkerPool(
          enableTracking = true,
          trackingTaskPeriod = 20.milliseconds,
          threadDumpThreshold = 0.milliseconds
        ))
      .withMaxReusableBufferSize(1.megabyte.bytes.toInt)
      .withProtocolFactory(new TUnsafeBinaryProtocolFactory(Thrift.param.protocolFactory))
  }

  override def configureHttp(router: HttpRouter): Unit =
    router
      .add(
        ProductMixerController[st.HomeMixer.MethodPerEndpoint](
          this.injector,
          st.HomeMixer.ExecutePipeline
        )
      ).add[HomeHttpController]

  override def configureHttpsServer(server: Http.Server): Http.Server = {
    val serviceIdentifier: ServiceIdentifier = injector.instance[ServiceIdentifier]
    server.withMutualTls(serviceIdentifier.copy(role = "home-mixer"))
  }

  override val dest: String = "/s/home-mixer/home-mixer:strato"

  override val columns: Seq[Class[_ <: StratoFed.Column]] =
    Seq(classOf[HomeMixerColumn])

  override protected def warmup(): Unit = {
    handle[HomeMixerThriftServerWarmupHandler]()
    handle[HomeMixerHttpServerWarmupHandler]()
  }
}

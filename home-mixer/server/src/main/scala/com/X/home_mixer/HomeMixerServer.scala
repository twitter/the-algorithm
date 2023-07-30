package com.X.home_mixer

import com.google.inject.Module
import com.X.finagle.Filter
import com.X.finatra.annotations.DarkTrafficFilterType
import com.X.finatra.http.HttpServer
import com.X.finatra.http.routing.HttpRouter
import com.X.finatra.mtls.http.{Mtls => HttpMtls}
import com.X.finatra.mtls.thriftmux.Mtls
import com.X.finatra.mtls.thriftmux.modules.MtlsThriftWebFormsModule
import com.X.finatra.thrift.ThriftServer
import com.X.finatra.thrift.filters._
import com.X.finatra.thrift.routing.ThriftRouter
import com.X.home_mixer.controller.HomeThriftController
import com.X.home_mixer.federated.HomeMixerColumn
import com.X.home_mixer.module._
import com.X.home_mixer.param.GlobalParamConfigModule
import com.X.home_mixer.product.HomeMixerProductModule
import com.X.home_mixer.{thriftscala => st}
import com.X.product_mixer.component_library.module.AccountRecommendationsMixerModule
import com.X.product_mixer.component_library.module.DarkTrafficFilterModule
import com.X.product_mixer.component_library.module.EarlybirdModule
import com.X.product_mixer.component_library.module.ExploreRankerClientModule
import com.X.product_mixer.component_library.module.GizmoduckClientModule
import com.X.product_mixer.component_library.module.OnboardingTaskServiceModule
import com.X.product_mixer.component_library.module.SocialGraphServiceModule
import com.X.product_mixer.component_library.module.TimelineRankerClientModule
import com.X.product_mixer.component_library.module.TimelineScorerClientModule
import com.X.product_mixer.component_library.module.TimelineServiceClientModule
import com.X.product_mixer.component_library.module.TweetImpressionStoreModule
import com.X.product_mixer.component_library.module.TweetMixerClientModule
import com.X.product_mixer.component_library.module.UserSessionStoreModule
import com.X.product_mixer.core.controllers.ProductMixerController
import com.X.product_mixer.core.module.LoggingThrowableExceptionMapper
import com.X.product_mixer.core.module.ProductMixerModule
import com.X.product_mixer.core.module.stringcenter.ProductScopeStringCenterModule
import com.X.strato.fed.StratoFed
import com.X.strato.fed.server.StratoFedServer

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
    BlenderClientModule,
    ClientSentImpressionsPublisherModule,
    ConversationServiceModule,
    EarlybirdModule,
    ExploreRankerClientModule,
    FeedbackHistoryClientModule,
    GizmoduckClientModule,
    GlobalParamConfigModule,
    HomeAdsCandidateSourceModule,
    HomeMixerFlagsModule,
    HomeMixerProductModule,
    HomeMixerResourcesModule,
    ImpressionBloomFilterModule,
    InjectionHistoryClientModule,
    ManhattanClientsModule,
    ManhattanFeatureRepositoryModule,
    ManhattanTweetImpressionStoreModule,
    MemcachedFeatureRepositoryModule,
    NaviModelClientModule,
    OnboardingTaskServiceModule,
    OptimizedStratoClientModule,
    PeopleDiscoveryServiceModule,
    ProductMixerModule,
    RealGraphInNetworkScoresModule,
    RealtimeAggregateFeatureRepositoryModule,
    ScoredTweetsMemcacheModule,
    ScribeEventPublisherModule,
    SimClustersRecentEngagementsClientModule,
    SocialGraphServiceModule,
    StaleTweetsCacheModule,
    ThriftFeatureRepositoryModule,
    TimelineRankerClientModule,
    TimelineScorerClientModule,
    TimelineServiceClientModule,
    TimelinesPersistenceStoreClientModule,
    TopicSocialProofClientModule,
    TweetImpressionStoreModule,
    TweetMixerClientModule,
    TweetypieClientModule,
    TweetypieStaticEntitiesCacheClientModule,
    UserSessionStoreModule,
    new DarkTrafficFilterModule[st.HomeMixer.ReqRepServicePerEndpoint](),
    new MtlsThriftWebFormsModule[st.HomeMixer.MethodPerEndpoint](this),
    new ProductScopeStringCenterModule()
  )

  override def configureThrift(router: ThriftRouter): Unit = {
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
      .add[HomeThriftController]
  }

  override def configureHttp(router: HttpRouter): Unit =
    router.add(
      ProductMixerController[st.HomeMixer.MethodPerEndpoint](
        this.injector,
        st.HomeMixer.ExecutePipeline))

  override val dest: String = "/s/home-mixer/home-mixer:strato"

  override val columns: Seq[Class[_ <: StratoFed.Column]] =
    Seq(classOf[HomeMixerColumn])

  override protected def warmup(): Unit = {
    handle[HomeMixerThriftServerWarmupHandler]()
    handle[HomeMixerHttpServerWarmupHandler]()
  }
}

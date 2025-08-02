package com.twitter.home_mitwitterer

import com.google.inject.Module
import com.twitter.finagle.Filter
import com.twitter.finatra.annotations.DarkTrafficFilterType
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.mtls.http.{Mtls => HttpMtls}
import com.twitter.finatra.mtls.thriftmutwitter.Mtls
import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsThriftWebFormsModule
import com.twitter.finatra.thrift.ThriftServer
import com.twitter.finatra.thrift.filters._
import com.twitter.finatra.thrift.routing.ThriftRouter
import com.twitter.home_mitwitterer.controller.HomeThriftController
import com.twitter.home_mitwitterer.federated.HomeMitwittererColumn
import com.twitter.home_mitwitterer.module._
import com.twitter.home_mitwitterer.param.GlobalParamConfigModule
import com.twitter.home_mitwitterer.product.HomeMitwittererProductModule
import com.twitter.home_mitwitterer.{thriftscala => st}
import com.twitter.product_mitwitterer.component_library.module.AccountRecommendationsMitwittererModule
import com.twitter.product_mitwitterer.component_library.module.DarkTrafficFilterModule
import com.twitter.product_mitwitterer.component_library.module.EarlybirdModule
import com.twitter.product_mitwitterer.component_library.module.EtwitterploreRankerClientModule
import com.twitter.product_mitwitterer.component_library.module.GizmoduckClientModule
import com.twitter.product_mitwitterer.component_library.module.OnboardingTaskServiceModule
import com.twitter.product_mitwitterer.component_library.module.SocialGraphServiceModule
import com.twitter.product_mitwitterer.component_library.module.TimelineRankerClientModule
import com.twitter.product_mitwitterer.component_library.module.TimelineScorerClientModule
import com.twitter.product_mitwitterer.component_library.module.TimelineServiceClientModule
import com.twitter.product_mitwitterer.component_library.module.TweetImpressionStoreModule
import com.twitter.product_mitwitterer.component_library.module.TweetMitwittererClientModule
import com.twitter.product_mitwitterer.component_library.module.UserSessionStoreModule
import com.twitter.product_mitwitterer.core.controllers.ProductMitwittererController
import com.twitter.product_mitwitterer.core.module.LoggingThrowableEtwitterceptionMapper
import com.twitter.product_mitwitterer.core.module.ProductMitwittererModule
import com.twitter.product_mitwitterer.core.module.stringcenter.ProductScopeStringCenterModule
import com.twitter.strato.fed.StratoFed
import com.twitter.strato.fed.server.StratoFedServer

object HomeMitwittererServerMain etwittertends HomeMitwittererServer

class HomeMitwittererServer
    etwittertends StratoFedServer
    with ThriftServer
    with Mtls
    with HttpServer
    with HttpMtls {
  override val name = "home-mitwitterer-server"

  override val modules: Seq[Module] = Seq(
    AccountRecommendationsMitwittererModule,
    AdvertiserBrandSafetySettingsStoreModule,
    BlenderClientModule,
    ClientSentImpressionsPublisherModule,
    ConversationServiceModule,
    EarlybirdModule,
    EtwitterploreRankerClientModule,
    FeedbackHistoryClientModule,
    GizmoduckClientModule,
    GlobalParamConfigModule,
    HomeAdsCandidateSourceModule,
    HomeMitwittererFlagsModule,
    HomeMitwittererProductModule,
    HomeMitwittererResourcesModule,
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
    ProductMitwittererModule,
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
    TweetMitwittererClientModule,
    TweetypieClientModule,
    TweetypieStaticEntitiesCacheClientModule,
    UserSessionStoreModule,
    new DarkTrafficFilterModule[st.HomeMitwitterer.ReqRepServicePerEndpoint](),
    new MtlsThriftWebFormsModule[st.HomeMitwitterer.MethodPerEndpoint](this),
    new ProductScopeStringCenterModule()
  )

  override def configureThrift(router: ThriftRouter): Unit = {
    router
      .filter[LoggingMDCFilter]
      .filter[TraceIdMDCFilter]
      .filter[ThriftMDCFilter]
      .filter[StatsFilter]
      .filter[AccessLoggingFilter]
      .filter[EtwitterceptionMappingFilter]
      .filter[Filter.TypeAgnostic, DarkTrafficFilterType]
      .etwitterceptionMapper[LoggingThrowableEtwitterceptionMapper]
      .etwitterceptionMapper[PipelineFailureEtwitterceptionMapper]
      .add[HomeThriftController]
  }

  override def configureHttp(router: HttpRouter): Unit =
    router.add(
      ProductMitwittererController[st.HomeMitwitterer.MethodPerEndpoint](
        this.injector,
        st.HomeMitwitterer.EtwitterecutePipeline))

  override val dest: String = "/s/home-mitwitterer/home-mitwitterer:strato"

  override val columns: Seq[Class[_ <: StratoFed.Column]] =
    Seq(classOf[HomeMitwittererColumn])

  override protected def warmup(): Unit = {
    handle[HomeMitwittererThriftServerWarmupHandler]()
    handle[HomeMitwittererHttpServerWarmupHandler]()
  }
}

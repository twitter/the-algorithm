package com.X.follow_recommendations

import com.google.inject.Module
import com.X.finagle.ThriftMux
import com.X.finatra.decider.modules.DeciderModule
import com.X.finatra.http.HttpServer
import com.X.finatra.http.routing.HttpRouter
import com.X.finatra.international.modules.I18nFactoryModule
import com.X.finatra.international.modules.LanguagesModule
import com.X.finatra.jackson.modules.ScalaObjectMapperModule
import com.X.finatra.mtls.http.{Mtls => HttpMtls}
import com.X.finatra.mtls.thriftmux.Mtls
import com.X.finatra.thrift.ThriftServer
import com.X.finatra.thrift.filters._
import com.X.finagle.thrift.Protocols
import com.X.finatra.thrift.routing.ThriftRouter
import com.X.follow_recommendations.common.clients.addressbook.AddressbookModule
import com.X.follow_recommendations.common.clients.adserver.AdserverModule
import com.X.follow_recommendations.common.clients.cache.MemcacheModule
import com.X.follow_recommendations.common.clients.deepbirdv2.DeepBirdV2PredictionServiceClientModule
import com.X.follow_recommendations.common.clients.email_storage_service.EmailStorageServiceModule
import com.X.follow_recommendations.common.clients.geoduck.LocationServiceModule
import com.X.follow_recommendations.common.clients.gizmoduck.GizmoduckModule
import com.X.follow_recommendations.common.clients.graph_feature_service.GraphFeatureStoreModule
import com.X.follow_recommendations.common.clients.impression_store.ImpressionStoreModule
import com.X.follow_recommendations.common.clients.phone_storage_service.PhoneStorageServiceModule
import com.X.follow_recommendations.common.clients.socialgraph.SocialGraphModule
import com.X.follow_recommendations.common.clients.strato.StratoClientModule
import com.X.follow_recommendations.common.constants.ServiceConstants._
import com.X.follow_recommendations.common.feature_hydration.sources.HydrationSourcesModule
import com.X.follow_recommendations.controllers.ThriftController
import com.X.follow_recommendations.modules._
import com.X.follow_recommendations.service.exceptions.UnknownLoggingExceptionMapper
import com.X.follow_recommendations.services.FollowRecommendationsServiceWarmupHandler
import com.X.follow_recommendations.thriftscala.FollowRecommendationsThriftService
import com.X.geoduck.service.common.clientmodules.ReverseGeocoderThriftClientModule
import com.X.inject.thrift.filters.DarkTrafficFilter
import com.X.inject.thrift.modules.ThriftClientIdModule
import com.X.product_mixer.core.controllers.ProductMixerController
import com.X.product_mixer.core.module.PipelineExecutionLoggerModule
import com.X.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule
import com.X.product_mixer.core.module.stringcenter.ProductScopeStringCenterModule
import com.X.product_mixer.core.product.guice.ProductScopeModule

object FollowRecommendationsServiceThriftServerMain extends FollowRecommendationsServiceThriftServer

class FollowRecommendationsServiceThriftServer
    extends ThriftServer
    with Mtls
    with HttpServer
    with HttpMtls {
  override val name: String = "follow-recommendations-service-server"

  override val modules: Seq[Module] =
    Seq(
      ABDeciderModule,
      AddressbookModule,
      AdserverModule,
      ConfigApiModule,
      DeciderModule,
      DeepBirdV2PredictionServiceClientModule,
      DiffyModule,
      EmailStorageServiceModule,
      FeaturesSwitchesModule,
      FlagsModule,
      GizmoduckModule,
      GraphFeatureStoreModule,
      HydrationSourcesModule,
      I18nFactoryModule,
      ImpressionStoreModule,
      LanguagesModule,
      LocationServiceModule,
      MemcacheModule,
      PhoneStorageServiceModule,
      PipelineExecutionLoggerModule,
      ProductMixerFlagModule,
      ProductRegistryModule,
      new ProductScopeModule(),
      new ProductScopeStringCenterModule(),
      new ReverseGeocoderThriftClientModule,
      ScalaObjectMapperModule,
      ScorerModule,
      ScribeModule,
      SocialGraphModule,
      StratoClientModule,
      ThriftClientIdModule,
      TimerModule,
    )

  def configureThrift(router: ThriftRouter): Unit = {
    router
      .filter[LoggingMDCFilter]
      .filter[TraceIdMDCFilter]
      .filter[ThriftMDCFilter]
      .filter[StatsFilter]
      .filter[AccessLoggingFilter]
      .filter[ExceptionMappingFilter]
      .exceptionMapper[UnknownLoggingExceptionMapper]
      .filter[DarkTrafficFilter[FollowRecommendationsThriftService.ReqRepServicePerEndpoint]]
      .add[ThriftController]
  }

  override def configureThriftServer(server: ThriftMux.Server): ThriftMux.Server = {
    server.withProtocolFactory(
      Protocols.binaryFactory(
        stringLengthLimit = StringLengthLimit,
        containerLengthLimit = ContainerLengthLimit))
  }

  override def configureHttp(router: HttpRouter): Unit = router.add(
    ProductMixerController[FollowRecommendationsThriftService.MethodPerEndpoint](
      this.injector,
      FollowRecommendationsThriftService.ExecutePipeline))

  override def warmup(): Unit = {
    handle[FollowRecommendationsServiceWarmupHandler]()
  }
}

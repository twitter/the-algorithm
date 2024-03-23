package com.ExTwitter.follow_recommendations

import com.google.inject.Module
import com.ExTwitter.finagle.ThriftMux
import com.ExTwitter.finatra.decider.modules.DeciderModule
import com.ExTwitter.finatra.http.HttpServer
import com.ExTwitter.finatra.http.routing.HttpRouter
import com.ExTwitter.finatra.international.modules.I18nFactoryModule
import com.ExTwitter.finatra.international.modules.LanguagesModule
import com.ExTwitter.finatra.jackson.modules.ScalaObjectMapperModule
import com.ExTwitter.finatra.mtls.http.{Mtls => HttpMtls}
import com.ExTwitter.finatra.mtls.thriftmux.Mtls
import com.ExTwitter.finatra.thrift.ThriftServer
import com.ExTwitter.finatra.thrift.filters._
import com.ExTwitter.finagle.thrift.Protocols
import com.ExTwitter.finatra.thrift.routing.ThriftRouter
import com.ExTwitter.follow_recommendations.common.clients.addressbook.AddressbookModule
import com.ExTwitter.follow_recommendations.common.clients.adserver.AdserverModule
import com.ExTwitter.follow_recommendations.common.clients.cache.MemcacheModule
import com.ExTwitter.follow_recommendations.common.clients.deepbirdv2.DeepBirdV2PredictionServiceClientModule
import com.ExTwitter.follow_recommendations.common.clients.email_storage_service.EmailStorageServiceModule
import com.ExTwitter.follow_recommendations.common.clients.geoduck.LocationServiceModule
import com.ExTwitter.follow_recommendations.common.clients.gizmoduck.GizmoduckModule
import com.ExTwitter.follow_recommendations.common.clients.graph_feature_service.GraphFeatureStoreModule
import com.ExTwitter.follow_recommendations.common.clients.impression_store.ImpressionStoreModule
import com.ExTwitter.follow_recommendations.common.clients.phone_storage_service.PhoneStorageServiceModule
import com.ExTwitter.follow_recommendations.common.clients.socialgraph.SocialGraphModule
import com.ExTwitter.follow_recommendations.common.clients.strato.StratoClientModule
import com.ExTwitter.follow_recommendations.common.constants.ServiceConstants._
import com.ExTwitter.follow_recommendations.common.feature_hydration.sources.HydrationSourcesModule
import com.ExTwitter.follow_recommendations.controllers.ThriftController
import com.ExTwitter.follow_recommendations.modules._
import com.ExTwitter.follow_recommendations.service.exceptions.UnknownLoggingExceptionMapper
import com.ExTwitter.follow_recommendations.services.FollowRecommendationsServiceWarmupHandler
import com.ExTwitter.follow_recommendations.thriftscala.FollowRecommendationsThriftService
import com.ExTwitter.geoduck.service.common.clientmodules.ReverseGeocoderThriftClientModule
import com.ExTwitter.inject.thrift.filters.DarkTrafficFilter
import com.ExTwitter.inject.thrift.modules.ThriftClientIdModule
import com.ExTwitter.product_mixer.core.controllers.ProductMixerController
import com.ExTwitter.product_mixer.core.module.PipelineExecutionLoggerModule
import com.ExTwitter.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule
import com.ExTwitter.product_mixer.core.module.stringcenter.ProductScopeStringCenterModule
import com.ExTwitter.product_mixer.core.product.guice.ProductScopeModule

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

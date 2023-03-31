package com.twitter.follow_recommendations

import com.google.inject.Module
import com.twitter.finagle.ThriftMux
import com.twitter.finatra.decider.modules.DeciderModule
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.international.modules.I18nFactoryModule
import com.twitter.finatra.international.modules.LanguagesModule
import com.twitter.finatra.jackson.modules.ScalaObjectMapperModule
import com.twitter.finatra.mtls.http.{Mtls => HttpMtls}
import com.twitter.finatra.mtls.thriftmux.Mtls
import com.twitter.finatra.thrift.ThriftServer
import com.twitter.finatra.thrift.filters._
import com.twitter.finagle.thrift.Protocols
import com.twitter.finatra.thrift.routing.ThriftRouter
import com.twitter.follow_recommendations.common.clients.addressbook.AddressbookModule
import com.twitter.follow_recommendations.common.clients.adserver.AdserverModule
import com.twitter.follow_recommendations.common.clients.cache.MemcacheModule
import com.twitter.follow_recommendations.common.clients.deepbirdv2.DeepBirdV2PredictionServiceClientModule
import com.twitter.follow_recommendations.common.clients.email_storage_service.EmailStorageServiceModule
import com.twitter.follow_recommendations.common.clients.geoduck.LocationServiceModule
import com.twitter.follow_recommendations.common.clients.gizmoduck.GizmoduckModule
import com.twitter.follow_recommendations.common.clients.graph_feature_service.GraphFeatureStoreModule
import com.twitter.follow_recommendations.common.clients.impression_store.ImpressionStoreModule
import com.twitter.follow_recommendations.common.clients.phone_storage_service.PhoneStorageServiceModule
import com.twitter.follow_recommendations.common.clients.socialgraph.SocialGraphModule
import com.twitter.follow_recommendations.common.clients.strato.StratoClientModule
import com.twitter.follow_recommendations.common.constants.ServiceConstants._
import com.twitter.follow_recommendations.common.feature_hydration.sources.HydrationSourcesModule
import com.twitter.follow_recommendations.controllers.ThriftController
import com.twitter.follow_recommendations.modules._
import com.twitter.follow_recommendations.service.exceptions.UnknownLoggingExceptionMapper
import com.twitter.follow_recommendations.services.FollowRecommendationsServiceWarmupHandler
import com.twitter.follow_recommendations.thriftscala.FollowRecommendationsThriftService
import com.twitter.geoduck.service.common.clientmodules.ReverseGeocoderThriftClientModule
import com.twitter.inject.thrift.filters.DarkTrafficFilter
import com.twitter.inject.thrift.modules.ThriftClientIdModule
import com.twitter.product_mixer.core.controllers.ProductMixerController
import com.twitter.product_mixer.core.module.PipelineExecutionLoggerModule
import com.twitter.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule
import com.twitter.product_mixer.core.module.stringcenter.ProductScopeStringCenterModule
import com.twitter.product_mixer.core.product.guice.ProductScopeModule

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

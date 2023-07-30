package com.X.follow_recommendations.modules

import com.google.inject.Provides
import com.google.inject.Singleton
import com.X.inject.annotations.Flag
import com.X.decider.RandomRecipient
import com.X.finagle.ThriftMux
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.mtls.client.MtlsStackClient.MtlsThriftMuxClientSyntax
import com.X.finagle.stats.StatsReceiver
import com.X.finagle.thrift.ClientId
import com.X.finatra.annotations.DarkTrafficService
import com.X.follow_recommendations.configapi.deciders.DeciderKey
import com.X.follow_recommendations.thriftscala.FollowRecommendationsThriftService
import com.X.inject.XModule
import com.X.inject.thrift.filters.DarkTrafficFilter
import com.X.servo.decider.DeciderGateBuilder

object DiffyModule extends XModule {
  // diffy.dest is defined in the Follow Recommendations Service aurora file
  // and points to the Dark Traffic Proxy server
  private val destFlag =
    flag[String]("diffy.dest", "/$/nil", "Resolvable name of diffy-service or proxy")

  @Provides
  @Singleton
  @DarkTrafficService
  def provideDarkTrafficService(
    serviceIdentifier: ServiceIdentifier
  ): FollowRecommendationsThriftService.ReqRepServicePerEndpoint = {
    ThriftMux.client
      .withClientId(ClientId("follow_recos_service_darktraffic_proxy_client"))
      .withMutualTls(serviceIdentifier)
      .servicePerEndpoint[FollowRecommendationsThriftService.ReqRepServicePerEndpoint](
        dest = destFlag(),
        label = "darktrafficproxy"
      )
  }

  @Provides
  @Singleton
  def provideDarkTrafficFilter(
    @DarkTrafficService darkService: FollowRecommendationsThriftService.ReqRepServicePerEndpoint,
    deciderGateBuilder: DeciderGateBuilder,
    statsReceiver: StatsReceiver,
    @Flag("environment") env: String
  ): DarkTrafficFilter[FollowRecommendationsThriftService.ReqRepServicePerEndpoint] = {
    // sampleFunction is used to determine which requests should get replicated
    // to the dark traffic proxy server
    val sampleFunction: Any => Boolean = { _ =>
      // check whether the current FRS instance is deployed in production
      env match {
        case "prod" =>
          statsReceiver.scope("provideDarkTrafficFilter").counter("prod").incr()
          destFlag.isDefined && deciderGateBuilder
            .keyToFeature(DeciderKey.EnableTrafficDarkReading).isAvailable(RandomRecipient)
        case _ =>
          statsReceiver.scope("provideDarkTrafficFilter").counter("devel").incr()
          // replicate zero requests if in non-production environment
          false
      }
    }
    new DarkTrafficFilter[FollowRecommendationsThriftService.ReqRepServicePerEndpoint](
      darkService,
      sampleFunction,
      forwardAfterService = true,
      statsReceiver.scope("DarkTrafficFilter"),
      lookupByMethod = true
    )
  }
}

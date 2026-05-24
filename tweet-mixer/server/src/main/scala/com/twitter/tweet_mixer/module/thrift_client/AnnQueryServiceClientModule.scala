package com.twitter.tweet_mixer.module.thrift_client

import com.google.inject.Provides
import com.twitter.ann.common.thriftscala.AnnQueryService
import com.twitter.conversions.DurationOps._
import com.twitter.conversions.PercentOps._
import com.twitter.tweet_mixer.config.TimeoutConfig
import com.twitter.finagle.ThriftMux
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.mtls.client.MtlsStackClient._
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finagle.thrift.ClientId
import com.twitter.inject.TwitterModule
import com.twitter.tweet_mixer.model.ModuleNames
import javax.inject.Named
import javax.inject.Singleton

object AnnQueryServiceClientModule extends TwitterModule {

  @Provides
  @Singleton
  @Named(ModuleNames.TwHINRegularUpdateAnnServiceClientName)
  def twHINRegularUpdateAnnServiceClient(
    serviceIdentifier: ServiceIdentifier,
    clientId: ClientId,
    statsReceiver: StatsReceiver,
    timeoutConfig: TimeoutConfig,
  ): AnnQueryService.MethodPerEndpoint = {
    val dest = "/s/cassowary/twhin-regular-update-ann-service"
    val label = "twhin_regular_update"

    buildClient(serviceIdentifier, clientId, timeoutConfig, statsReceiver, dest, label)
  }

  private def buildClient(
    serviceIdentifier: ServiceIdentifier,
    clientId: ClientId,
    timeoutConfig: TimeoutConfig,
    statsReceiver: StatsReceiver,
    dest: String,
    label: String
  ): AnnQueryService.MethodPerEndpoint = {
    val thriftClient = ThriftMux.client
      .withMutualTls(serviceIdentifier)
      .withClientId(clientId)
      .withLabel(label)
      .withStatsReceiver(statsReceiver)
      .withTransport.connectTimeout(500.milliseconds)
      .withSession.acquisitionTimeout(500.milliseconds)
      .methodBuilder(dest)
      .withTimeoutPerRequest(timeoutConfig.thriftAnnServiceClientTimeout)
      .withRetryDisabled
      .idempotent(5.percent)
      .servicePerEndpoint[AnnQueryService.ServicePerEndpoint]

    ThriftMux.Client.methodPerEndpoint(thriftClient)
  }
}

package com.twitter.cr_mitwitterer.module.thrift_client

import com.google.inject.Provides
import com.twitter.ann.common.thriftscala.AnnQueryService
import com.twitter.conversions.DurationOps._
import com.twitter.conversions.PercentOps._
import com.twitter.cr_mitwitterer.config.TimeoutConfig
import com.twitter.finagle.ThriftMutwitter
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.mtls.client.MtlsStackClient._
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finagle.thrift.ClientId
import com.twitter.inject.TwitterModule
import javatwitter.inject.Named
import javatwitter.inject.Singleton

object AnnQueryServiceClientModule etwittertends TwitterModule {
  final val DebuggerDemoAnnServiceClientName = "DebuggerDemoAnnServiceClient"

  @Provides
  @Singleton
  @Named(DebuggerDemoAnnServiceClientName)
  def debuggerDemoAnnServiceClient(
    serviceIdentifier: ServiceIdentifier,
    clientId: ClientId,
    statsReceiver: StatsReceiver,
    timeoutConfig: TimeoutConfig,
  ): AnnQueryService.MethodPerEndpoint = {
    // This ANN is built from the embeddings in src/scala/com/twitter/wtf/beam/bq_embedding_etwitterport/sql/MlfEtwitterperimentalTweetEmbeddingScalaDataset.sql
    // Change the above sql if you want to build the indetwitter from a diff embedding
    val dest = "/s/cassowary/mlf-etwitterperimental-ann-service"
    val label = "etwitterperimental-ann"
    buildClient(serviceIdentifier, clientId, timeoutConfig, statsReceiver, dest, label)
  }

  final val TwHINUuaAnnServiceClientName = "TwHINUuaAnnServiceClient"
  @Provides
  @Singleton
  @Named(TwHINUuaAnnServiceClientName)
  def twhinUuaAnnServiceClient(
    serviceIdentifier: ServiceIdentifier,
    clientId: ClientId,
    statsReceiver: StatsReceiver,
    timeoutConfig: TimeoutConfig,
  ): AnnQueryService.MethodPerEndpoint = {
    val dest = "/s/cassowary/twhin-uua-ann-service"
    val label = "twhin_uua_ann"

    buildClient(serviceIdentifier, clientId, timeoutConfig, statsReceiver, dest, label)
  }

  final val TwHINRegularUpdateAnnServiceClientName = "TwHINRegularUpdateAnnServiceClient"
  @Provides
  @Singleton
  @Named(TwHINRegularUpdateAnnServiceClientName)
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

  final val TwoTowerFavAnnServiceClientName = "TwoTowerFavAnnServiceClient"
  @Provides
  @Singleton
  @Named(TwoTowerFavAnnServiceClientName)
  def twoTowerFavAnnServiceClient(
    serviceIdentifier: ServiceIdentifier,
    clientId: ClientId,
    statsReceiver: StatsReceiver,
    timeoutConfig: TimeoutConfig,
  ): AnnQueryService.MethodPerEndpoint = {
    val dest = "/s/cassowary/tweet-rec-two-tower-fav-ann"
    val label = "tweet_rec_two_tower_fav_ann"

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
    val thriftClient = ThriftMutwitter.client
      .withMutualTls(serviceIdentifier)
      .withClientId(clientId)
      .withLabel(label)
      .withStatsReceiver(statsReceiver)
      .withTransport.connectTimeout(500.milliseconds)
      .withSession.acquisitionTimeout(500.milliseconds)
      .methodBuilder(dest)
      .withTimeoutPerRequest(timeoutConfig.annServiceClientTimeout)
      .withRetryDisabled
      .idempotent(5.percent)
      .servicePerEndpoint[AnnQueryService.ServicePerEndpoint]

    ThriftMutwitter.Client.methodPerEndpoint(thriftClient)
  }
}

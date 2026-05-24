package com.twitter.tweet_mixer.module.thrift_client

import com.google.inject.Provides
import com.twitter.conversions.PercentOps._
import com.twitter.tweet_mixer.model.ModuleNames
import com.twitter.tweet_mixer.config.TimeoutConfig
import com.twitter.finagle.ThriftMux
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.mtls.client.MtlsStackClient._
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finagle.thrift.ClientId
import com.twitter.inject.TwitterModule
import com.twitter.vecdb.{thriftscala => t}
import javax.inject.Named
import javax.inject.Singleton

object VecDBAnnServiceClientModule extends TwitterModule {
  @Provides
  @Singleton
  @Named(ModuleNames.VecDBAnnServiceClient)
  def providesVecDBAnnServiceClient(
    serviceIdentifier: ServiceIdentifier,
    clientId: ClientId,
    timeoutConfig: TimeoutConfig,
    statsReceiver: StatsReceiver,
  ): t.VecDB.MethodPerEndpoint = {
    val label = "vecdb-ann"
    val dest = "/s/vecdb/frontend"

    buildClient(serviceIdentifier, clientId, timeoutConfig, statsReceiver, dest, label)
  }

  private def buildClient(
    serviceIdentifier: ServiceIdentifier,
    clientId: ClientId,
    timeoutConfig: TimeoutConfig,
    statsReceiver: StatsReceiver,
    dest: String,
    label: String
  ): t.VecDB.MethodPerEndpoint = {
    val stats = statsReceiver.scope("clnt")

    val thriftClient = ThriftMux.client
      .withMutualTls(serviceIdentifier)
      .withClientId(clientId)
      .withLabel(label)
      .withStatsReceiver(stats)
      .methodBuilder(dest)
      .idempotent(1.percent)
      .withTimeoutPerRequest(timeoutConfig.candidateSourceTimeout)
      .withRetryDisabled
      .servicePerEndpoint[t.VecDB.ServicePerEndpoint]

    ThriftMux.Client.methodPerEndpoint(thriftClient)
  }
}

package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.conversions.PercentOps._
import com.twitter.finagle.ThriftMux
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.mtls.client.MtlsStackClient.MtlsThriftMuxClientSyntax
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finagle.thrift.ClientId
import com.twitter.finagle.thrift.RichClientParam
import com.twitter.inject.TwitterModule
import com.twitter.home_mixer_features.{thriftjava => tj}
import com.twitter.home_mixer_features.{thriftscala => ts}
import com.twitter.product_mixer.shared_library.thrift_client.FinagleThriftClientBuilder
import com.twitter.product_mixer.shared_library.thrift_client.Idempotent
import javax.inject.Singleton

object HomeMixerFeaturesModule extends TwitterModule {

  val Label: String = "home-mixer-features"
  val Dest: String = "/s/home-mixer/home-mixer-features"

  @Provides
  @Singleton
  def providesHomeMixerFeaturesService(
    serviceIdentifier: ServiceIdentifier,
    clientId: ClientId,
    statsReceiver: StatsReceiver,
  ): tj.HomeMixerFeatures.ServiceToClient = {
    buildClient(serviceIdentifier, clientId, statsReceiver, Dest, Label)
  }

  @Provides
  @Singleton
  def providesHomeMixerFeaturesScalaService(
    serviceIdentifier: ServiceIdentifier,
    clientId: ClientId,
    statsReceiver: StatsReceiver,
  ): ts.HomeMixerFeatures.MethodPerEndpoint = {
    FinagleThriftClientBuilder.buildFinagleMethodPerEndpoint[
      ts.HomeMixerFeatures.ServicePerEndpoint,
      ts.HomeMixerFeatures.MethodPerEndpoint
    ](
      serviceIdentifier = serviceIdentifier,
      clientId = clientId,
      dest = Dest,
      label = Label,
      statsReceiver = statsReceiver,
      idempotency = Idempotent(1.percent),
      timeoutPerRequest = 300.milliseconds,
      timeoutTotal = 300.milliseconds,
      acquisitionTimeout = 1.seconds
    )
  }

  private def buildClient(
    serviceIdentifier: ServiceIdentifier,
    clientId: ClientId,
    statsReceiver: StatsReceiver,
    dest: String,
    label: String
  ): tj.HomeMixerFeatures.ServiceToClient = {
    val stats = statsReceiver.scope("clnt")
    val thriftClient = ThriftMux.client
      .withMutualTls(serviceIdentifier)
      .withClientId(clientId)
      .withLabel(label)
      .withStatsReceiver(stats)
      .withRequestTimeout(300.milliseconds)
      .withSession.acquisitionTimeout(1.second)
      .methodBuilder(dest)
      .withTimeoutTotal(300.milliseconds)
      .idempotent(1.percent)
      .newService

    new tj.HomeMixerFeatures.ServiceToClient(
      thriftClient,
      RichClientParam()
    )
  }
}

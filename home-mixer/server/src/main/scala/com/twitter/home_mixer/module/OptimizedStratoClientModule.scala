package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.service.Retries
import com.twitter.finagle.service.RetryPolicy
import com.twitter.finagle.ssl.OpportunisticTls
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.param.HomeMixerInjectionNames.BatchedStratoClientWithDefaultTimeout
import com.twitter.home_mixer.param.HomeMixerInjectionNames.StratoClientWithLongTimeout
import com.twitter.home_mixer.param.HomeMixerInjectionNames.BatchedStratoClientWithLongTimeout
import com.twitter.home_mixer.param.HomeMixerInjectionNames.BatchedStratoClientWithModerateTimeout
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TesBatchedStratoClient
import com.twitter.inject.TwitterModule
import com.twitter.strato.client.Client
import com.twitter.strato.client.Strato
import com.twitter.strato.rpc.ClientBucketingStrategy
import com.twitter.util.Try
import javax.inject.Named
import javax.inject.Singleton

/**
 * Strato Finagle config is copied from TLX
 */
object OptimizedStratoClientModule extends TwitterModule {

  private val StratoClientConnectionTimeout = 200.millis
  private val StratoClientAcquisitionTimeout = 500.millis
  private val DefaultStratoClientRequestTimeout = 280.millis
  private val StratoClientLongRequestTimeout = 1000.millis
  private val ModerateStratoClientRequestTimeout = 600.millis
  private val LongStratoClientRequestTimeout = 1000.millis

  private val DefaultRetryPartialFunction: PartialFunction[Try[Nothing], Boolean] =
    RetryPolicy.TimeoutAndWriteExceptionsOnly
      .orElse(RetryPolicy.ChannelClosedExceptionsOnly)

  protected def mkRetryPolicy(tries: Int): RetryPolicy[Try[Nothing]] =
    RetryPolicy.tries(tries, DefaultRetryPartialFunction)

  @Singleton
  @Provides
  @Named(BatchedStratoClientWithDefaultTimeout)
  def providesDefaultStratoClient(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Client = {
    Strato.client
      .withMutualTls(serviceIdentifier, opportunisticLevel = OpportunisticTls.Required)
      .withSession.acquisitionTimeout(StratoClientAcquisitionTimeout)
      .withTransport.connectTimeout(StratoClientConnectionTimeout)
      .withRequestTimeout(DefaultStratoClientRequestTimeout)
      .withPerRequestTimeout(DefaultStratoClientRequestTimeout)
      .configured(Retries.Policy(mkRetryPolicy(1)))
      .withStatsReceiver(statsReceiver.scope("default_strato_client"))
      .build()
  }

  @Singleton
  @Provides
  @Named(StratoClientWithLongTimeout)
  def providesLongStratoClient(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Client = {
    Strato.client
      .withMutualTls(serviceIdentifier, opportunisticLevel = OpportunisticTls.Required)
      .withSession.acquisitionTimeout(StratoClientAcquisitionTimeout)
      .withTransport.connectTimeout(StratoClientConnectionTimeout)
      .withRequestTimeout(StratoClientLongRequestTimeout)
      .withPerRequestTimeout(StratoClientLongRequestTimeout)
      .configured(Retries.Policy(mkRetryPolicy(10)))
      .withStatsReceiver(statsReceiver.scope("long_strato_client"))
      .build()
  }

  @Singleton
  @Provides
  @Named(BatchedStratoClientWithModerateTimeout)
  def providesModerateTimeoutStratoClient(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Client = {
    Strato.client
      .withMutualTls(serviceIdentifier, opportunisticLevel = OpportunisticTls.Required)
      .withSession.acquisitionTimeout(StratoClientAcquisitionTimeout)
      .withTransport.connectTimeout(StratoClientConnectionTimeout)
      .withRequestTimeout(ModerateStratoClientRequestTimeout)
      .withPerRequestTimeout(ModerateStratoClientRequestTimeout)
      .withRpcBatchSize(64)
      .configured(Retries.Policy(mkRetryPolicy(1)))
      .withStatsReceiver(statsReceiver.scope("moderate_timeout_strato_client"))
      .build()
  }

  @Singleton
  @Provides
  @Named(BatchedStratoClientWithLongTimeout)
  def providesLongTimeoutStratoClient(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Client = {
    Strato.client
      .withMutualTls(serviceIdentifier, opportunisticLevel = OpportunisticTls.Required)
      .withSession.acquisitionTimeout(StratoClientAcquisitionTimeout)
      .withTransport.connectTimeout(StratoClientConnectionTimeout)
      .withRequestTimeout(LongStratoClientRequestTimeout)
      .withPerRequestTimeout(LongStratoClientRequestTimeout)
      .withRpcBatchSize(5)
      .configured(Retries.Policy(mkRetryPolicy(1)))
      .withStatsReceiver(statsReceiver.scope("long_timeout_strato_client"))
      .build()
  }

  @Singleton
  @Provides
  @Named(TesBatchedStratoClient)
  def providesTesStratoClient(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Client = {
    Strato.client
      .withMutualTls(serviceIdentifier, opportunisticLevel = OpportunisticTls.Required)
      .withSession.acquisitionTimeout(StratoClientAcquisitionTimeout)
      .withTransport.connectTimeout(StratoClientConnectionTimeout)
      .withRequestTimeout(ModerateStratoClientRequestTimeout)
      .withPerRequestTimeout(ModerateStratoClientRequestTimeout)
      .withRpcBatchSize(140)
      .withBucketingStrategy(ClientBucketingStrategy.ByArg)
      .configured(Retries.Policy(mkRetryPolicy(1)))
      .withStatsReceiver(statsReceiver.scope("tes_strato_client"))
      .build()
  }
}

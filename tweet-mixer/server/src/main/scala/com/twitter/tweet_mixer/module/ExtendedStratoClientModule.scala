package com.twitter.tweet_mixer.module

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.service.Retries
import com.twitter.finagle.service.RetryPolicy
import com.twitter.finagle.ssl.OpportunisticTls
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.strato.client.Client
import com.twitter.strato.client.Strato
import com.twitter.util.Try
import javax.inject.Named
import javax.inject.Singleton

/**
 * Strato Finagle config is copied from TLX
 */
object ExtendedStratoClientModule extends TwitterModule {

  private val StratoClientConnectionTimeout = 200.millis
  private val StratoClientAcquisitionTimeout = 500.millis
  private val StandardStratoClientRequestTimeout = 280.millis
  private val ModerateStratoClientRequestTimeout = 500.millis

  private val DefaultRetryPartialFunction: PartialFunction[Try[Nothing], Boolean] =
    RetryPolicy.TimeoutAndWriteExceptionsOnly
      .orElse(RetryPolicy.ChannelClosedExceptionsOnly)

  protected def mkRetryPolicy(tries: Int): RetryPolicy[Try[Nothing]] =
    RetryPolicy.tries(tries, DefaultRetryPartialFunction)

  @Singleton
  @Provides
  @Named("StratoClientWithDefaultTimeout")
  def providesDefaultStratoClient(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Client = {
    Strato.client
      .withMutualTls(serviceIdentifier, opportunisticLevel = OpportunisticTls.Required)
      .withSession.acquisitionTimeout(StratoClientAcquisitionTimeout)
      .withTransport.connectTimeout(StratoClientConnectionTimeout)
      .withRequestTimeout(StandardStratoClientRequestTimeout)
      .withPerRequestTimeout(StandardStratoClientRequestTimeout)
      .configured(Retries.Policy(mkRetryPolicy(1)))
      .withStatsReceiver(statsReceiver.scope("default_strato_client"))
      .build()
  }

  @Singleton
  @Provides
  @Named("StratoClientWithModerateTimeout")
  def providesModerateStratoClient(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Client = {
    Strato.client
      .withMutualTls(serviceIdentifier, opportunisticLevel = OpportunisticTls.Required)
      .withSession.acquisitionTimeout(StratoClientAcquisitionTimeout)
      .withTransport.connectTimeout(StratoClientConnectionTimeout)
      .withRequestTimeout(ModerateStratoClientRequestTimeout)
      .withPerRequestTimeout(ModerateStratoClientRequestTimeout)
      .withRpcBatchSize(5)
      .configured(Retries.Policy(mkRetryPolicy(1)))
      .withStatsReceiver(statsReceiver.scope("moderate_strato_client"))
      .build()
  }
}

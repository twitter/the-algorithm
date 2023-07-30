package com.X.home_mixer.module

import com.google.inject.Provides
import com.X.conversions.DurationOps._
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.service.Retries
import com.X.finagle.service.RetryPolicy
import com.X.finagle.ssl.OpportunisticTls
import com.X.finagle.stats.StatsReceiver
import com.X.home_mixer.param.HomeMixerInjectionNames.BatchedStratoClientWithModerateTimeout
import com.X.inject.XModule
import com.X.strato.client.Client
import com.X.strato.client.Strato
import com.X.util.Try
import javax.inject.Named
import javax.inject.Singleton

object OptimizedStratoClientModule extends XModule {

  private val ModerateStratoServerClientRequestTimeout = 500.millis

  private val DefaultRetryPartialFunction: PartialFunction[Try[Nothing], Boolean] =
    RetryPolicy.TimeoutAndWriteExceptionsOnly
      .orElse(RetryPolicy.ChannelClosedExceptionsOnly)

  protected def mkRetryPolicy(tries: Int): RetryPolicy[Try[Nothing]] =
    RetryPolicy.tries(tries, DefaultRetryPartialFunction)

  @Singleton
  @Provides
  @Named(BatchedStratoClientWithModerateTimeout)
  def providesStratoClient(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Client = {
    Strato.client
      .withMutualTls(serviceIdentifier, opportunisticLevel = OpportunisticTls.Required)
      .withSession.acquisitionTimeout(500.milliseconds)
      .withRequestTimeout(ModerateStratoServerClientRequestTimeout)
      .withPerRequestTimeout(ModerateStratoServerClientRequestTimeout)
      .withRpcBatchSize(5)
      .configured(Retries.Policy(mkRetryPolicy(1)))
      .withStatsReceiver(statsReceiver.scope("strato_client"))
      .build()
  }
}

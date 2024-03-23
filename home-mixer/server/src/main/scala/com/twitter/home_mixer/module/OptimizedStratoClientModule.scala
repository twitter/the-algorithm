package com.ExTwitter.home_mixer.module

import com.google.inject.Provides
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.finagle.service.Retries
import com.ExTwitter.finagle.service.RetryPolicy
import com.ExTwitter.finagle.ssl.OpportunisticTls
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.BatchedStratoClientWithModerateTimeout
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.strato.client.Client
import com.ExTwitter.strato.client.Strato
import com.ExTwitter.util.Try
import javax.inject.Named
import javax.inject.Singleton

object OptimizedStratoClientModule extends ExTwitterModule {

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

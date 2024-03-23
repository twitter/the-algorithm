package com.ExTwitter.cr_mixer.module.thrift_client

import com.google.inject.Provides
import com.ExTwitter.app.Flag
import com.ExTwitter.conversions.DurationOps.richDurationFromInt
import com.ExTwitter.cr_mixer.module.core.TimeoutConfigModule.TweetypieClientTimeoutFlagName
import com.ExTwitter.finagle.ThriftMux
import com.ExTwitter.finagle.mux.ClientDiscardedRequestException
import com.ExTwitter.finagle.service.ReqRep
import com.ExTwitter.finagle.service.ResponseClass
import com.ExTwitter.finagle.service.RetryBudget
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.inject.Injector
import com.ExTwitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.ExTwitter.stitch.tweetypie.{TweetyPie => STweetyPie}
import com.ExTwitter.tweetypie.thriftscala.TweetService
import com.ExTwitter.util.Duration
import com.ExTwitter.util.Throw
import javax.inject.Singleton

object TweetyPieClientModule
    extends ThriftMethodBuilderClientModule[
      TweetService.ServicePerEndpoint,
      TweetService.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label = "tweetypie"
  override val dest = "/s/tweetypie/tweetypie"

  private val tweetypieClientTimeout: Flag[Duration] =
    flag[Duration](TweetypieClientTimeoutFlagName, "tweetypie client timeout")
  override def requestTimeout: Duration = tweetypieClientTimeout()

  override def retryBudget: RetryBudget = RetryBudget.Empty

  // We bump the success rate from the default of 0.8 to 0.9 since we're dropping the
  // consecutive failures part of the default policy.
  override def configureThriftMuxClient(
    injector: Injector,
    client: ThriftMux.Client
  ): ThriftMux.Client =
    super
      .configureThriftMuxClient(injector, client)
      .withStatsReceiver(injector.instance[StatsReceiver].scope("clnt"))
      .withSessionQualifier
      .successRateFailureAccrual(successRate = 0.9, window = 30.seconds)
      .withResponseClassifier {
        case ReqRep(_, Throw(_: ClientDiscardedRequestException)) => ResponseClass.Ignorable
      }

  @Provides
  @Singleton
  def providesTweetyPie(
    tweetyPieService: TweetService.MethodPerEndpoint
  ): STweetyPie = {
    STweetyPie(tweetyPieService)
  }
}

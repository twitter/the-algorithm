package com.X.cr_mixer.module.thrift_client

import com.google.inject.Provides
import com.X.app.Flag
import com.X.conversions.DurationOps.richDurationFromInt
import com.X.cr_mixer.module.core.TimeoutConfigModule.TweetypieClientTimeoutFlagName
import com.X.finagle.ThriftMux
import com.X.finagle.mux.ClientDiscardedRequestException
import com.X.finagle.service.ReqRep
import com.X.finagle.service.ResponseClass
import com.X.finagle.service.RetryBudget
import com.X.finagle.stats.StatsReceiver
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.inject.Injector
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.stitch.tweetypie.{TweetyPie => STweetyPie}
import com.X.tweetypie.thriftscala.TweetService
import com.X.util.Duration
import com.X.util.Throw
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

package com.X.tsp
package modules

import com.google.inject.Module
import com.google.inject.Provides
import com.X.conversions.DurationOps.richDurationFromInt
import com.X.finagle.ThriftMux
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.mtls.client.MtlsStackClient.MtlsThriftMuxClientSyntax
import com.X.finagle.mux.ClientDiscardedRequestException
import com.X.finagle.service.ReqRep
import com.X.finagle.service.ResponseClass
import com.X.finagle.stats.StatsReceiver
import com.X.finagle.thrift.ClientId
import com.X.inject.Injector
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.tweetypie.thriftscala.TweetService
import com.X.util.Duration
import com.X.util.Throw
import com.X.stitch.tweetypie.{TweetyPie => STweetyPie}
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import javax.inject.Singleton

object TweetyPieClientModule
    extends ThriftMethodBuilderClientModule[
      TweetService.ServicePerEndpoint,
      TweetService.MethodPerEndpoint
    ]
    with MtlsClient {
  override val label = "tweetypie"
  override val dest = "/s/tweetypie/tweetypie"
  override val requestTimeout: Duration = 450.milliseconds

  override val modules: Seq[Module] = Seq(TSPClientIdModule)

  // We bump the success rate from the default of 0.8 to 0.9 since we're dropping the
  // consecutive failures part of the default policy.
  override def configureThriftMuxClient(
    injector: Injector,
    client: ThriftMux.Client
  ): ThriftMux.Client =
    super
      .configureThriftMuxClient(injector, client)
      .withMutualTls(injector.instance[ServiceIdentifier])
      .withStatsReceiver(injector.instance[StatsReceiver].scope("clnt"))
      .withClientId(injector.instance[ClientId])
      .withResponseClassifier {
        case ReqRep(_, Throw(_: ClientDiscardedRequestException)) => ResponseClass.Ignorable
      }
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

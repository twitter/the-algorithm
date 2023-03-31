package com.twitter.cr_mixer.module.thrift_client

import com.twitter.app.Flag
import com.twitter.cr_mixer.module.core.TimeoutConfigModule.UserTweetGraphPlusClientTimeoutFlagName
import com.twitter.finagle.ThriftMux
import com.twitter.finagle.mux.ClientDiscardedRequestException
import com.twitter.finagle.service.ReqRep
import com.twitter.finagle.service.ResponseClass
import com.twitter.finagle.service.RetryBudget
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.recos.user_tweet_graph_plus.thriftscala.UserTweetGraphPlus
import com.twitter.util.Duration
import com.twitter.util.Throw

object UserTweetGraphPlusClientModule
    extends ThriftMethodBuilderClientModule[
      UserTweetGraphPlus.ServicePerEndpoint,
      UserTweetGraphPlus.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label = "user-tweet-graph-plus"
  override val dest = "/s/user-tweet-graph/user-tweet-graph-plus"
  private val userTweetGraphPlusClientTimeout: Flag[Duration] =
    flag[Duration](
      UserTweetGraphPlusClientTimeoutFlagName,
      "userTweetGraphPlus client timeout"
    )
  override def requestTimeout: Duration = userTweetGraphPlusClientTimeout()

  override def retryBudget: RetryBudget = RetryBudget.Empty

  override def configureThriftMuxClient(
    injector: Injector,
    client: ThriftMux.Client
  ): ThriftMux.Client =
    super
      .configureThriftMuxClient(injector, client)
      .withStatsReceiver(injector.instance[StatsReceiver].scope("clnt"))
      .withResponseClassifier {
        case ReqRep(_, Throw(_: ClientDiscardedRequestException)) => ResponseClass.Ignorable
      }
}

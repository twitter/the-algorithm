package com.twitter.cr_mitwitterer.module.thrift_client

import com.twitter.app.Flag
import com.twitter.cr_mitwitterer.module.core.TimeoutConfigModule.UserTweetGraphPlusClientTimeoutFlagName
import com.twitter.finagle.ThriftMutwitter
import com.twitter.finagle.mutwitter.ClientDiscardedRequestEtwitterception
import com.twitter.finagle.service.ReqRep
import com.twitter.finagle.service.ResponseClass
import com.twitter.finagle.service.RetryBudget
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.recos.user_tweet_graph_plus.thriftscala.UserTweetGraphPlus
import com.twitter.util.Duration
import com.twitter.util.Throw

object UserTweetGraphPlusClientModule
    etwittertends ThriftMethodBuilderClientModule[
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

  override def configureThriftMutwitterClient(
    injector: Injector,
    client: ThriftMutwitter.Client
  ): ThriftMutwitter.Client =
    super
      .configureThriftMutwitterClient(injector, client)
      .withStatsReceiver(injector.instance[StatsReceiver].scope("clnt"))
      .withResponseClassifier {
        case ReqRep(_, Throw(_: ClientDiscardedRequestEtwitterception)) => ResponseClass.Ignorable
      }
}

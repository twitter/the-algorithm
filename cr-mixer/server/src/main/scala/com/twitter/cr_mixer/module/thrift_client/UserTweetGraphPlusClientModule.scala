package com.ExTwitter.cr_mixer.module.thrift_client

import com.ExTwitter.app.Flag
import com.ExTwitter.cr_mixer.module.core.TimeoutConfigModule.UserTweetGraphPlusClientTimeoutFlagName
import com.ExTwitter.finagle.ThriftMux
import com.ExTwitter.finagle.mux.ClientDiscardedRequestException
import com.ExTwitter.finagle.service.ReqRep
import com.ExTwitter.finagle.service.ResponseClass
import com.ExTwitter.finagle.service.RetryBudget
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.inject.Injector
import com.ExTwitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.ExTwitter.recos.user_tweet_graph_plus.thriftscala.UserTweetGraphPlus
import com.ExTwitter.util.Duration
import com.ExTwitter.util.Throw

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

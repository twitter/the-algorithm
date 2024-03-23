package com.ExTwitter.cr_mixer.module.thrift_client

import com.ExTwitter.app.Flag
import com.ExTwitter.finagle.ThriftMux
import com.ExTwitter.finagle.mux.ClientDiscardedRequestException
import com.ExTwitter.finagle.service.ReqRep
import com.ExTwitter.finagle.service.ResponseClass
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.inject.Injector
import com.ExTwitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.ExTwitter.recos.user_tweet_graph.thriftscala.UserTweetGraph
import com.ExTwitter.util.Duration
import com.ExTwitter.util.Throw
import com.ExTwitter.cr_mixer.module.core.TimeoutConfigModule.UserTweetGraphClientTimeoutFlagName
import com.ExTwitter.finagle.service.RetryBudget

object UserTweetGraphClientModule
    extends ThriftMethodBuilderClientModule[
      UserTweetGraph.ServicePerEndpoint,
      UserTweetGraph.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label = "user-tweet-graph"
  override val dest = "/s/user-tweet-graph/user-tweet-graph"
  private val userTweetGraphClientTimeout: Flag[Duration] =
    flag[Duration](UserTweetGraphClientTimeoutFlagName, "userTweetGraph client timeout")
  override def requestTimeout: Duration = userTweetGraphClientTimeout()

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

package com.X.cr_mixer.module.thrift_client

import com.X.app.Flag
import com.X.cr_mixer.module.core.TimeoutConfigModule.UtegClientTimeoutFlagName
import com.X.finagle.ThriftMux
import com.X.finagle.mux.ClientDiscardedRequestException
import com.X.finagle.service.ReqRep
import com.X.finagle.service.ResponseClass
import com.X.finagle.service.RetryBudget
import com.X.finagle.stats.StatsReceiver
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.inject.Injector
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.recos.user_tweet_entity_graph.thriftscala.UserTweetEntityGraph
import com.X.util.Duration
import com.X.util.Throw

object UserTweetEntityGraphClientModule
    extends ThriftMethodBuilderClientModule[
      UserTweetEntityGraph.ServicePerEndpoint,
      UserTweetEntityGraph.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label = "user-tweet-entity-graph"
  override val dest = "/s/cassowary/user_tweet_entity_graph"
  private val userTweetEntityGraphClientTimeout: Flag[Duration] =
    flag[Duration](UtegClientTimeoutFlagName, "user tweet entity graph client timeout")
  override def requestTimeout: Duration = userTweetEntityGraphClientTimeout()

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

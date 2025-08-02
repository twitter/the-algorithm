package com.twitter.cr_mitwitterer.module.thrift_client

import com.twitter.app.Flag
import com.twitter.cr_mitwitterer.module.core.TimeoutConfigModule.UtegClientTimeoutFlagName
import com.twitter.finagle.ThriftMutwitter
import com.twitter.finagle.mutwitter.ClientDiscardedRequestEtwitterception
import com.twitter.finagle.service.ReqRep
import com.twitter.finagle.service.ResponseClass
import com.twitter.finagle.service.RetryBudget
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.recos.user_tweet_entity_graph.thriftscala.UserTweetEntityGraph
import com.twitter.util.Duration
import com.twitter.util.Throw

object UserTweetEntityGraphClientModule
    etwittertends ThriftMethodBuilderClientModule[
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

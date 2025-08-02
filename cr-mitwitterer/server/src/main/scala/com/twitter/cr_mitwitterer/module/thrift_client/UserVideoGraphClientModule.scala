package com.twitter.cr_mitwitterer.module.thrift_client

import com.twitter.app.Flag
import com.twitter.cr_mitwitterer.module.core.TimeoutConfigModule.UserVideoGraphClientTimeoutFlagName
import com.twitter.finagle.ThriftMutwitter
import com.twitter.finagle.mutwitter.ClientDiscardedRequestEtwitterception
import com.twitter.finagle.service.ReqRep
import com.twitter.finagle.service.ResponseClass
import com.twitter.finagle.service.RetryBudget
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.recos.user_video_graph.thriftscala.UserVideoGraph
import com.twitter.util.Duration
import com.twitter.util.Throw

object UserVideoGraphClientModule
    etwittertends ThriftMethodBuilderClientModule[
      UserVideoGraph.ServicePerEndpoint,
      UserVideoGraph.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label = "user-video-graph"
  override val dest = "/s/user-tweet-graph/user-video-graph"
  private val userVideoGraphClientTimeout: Flag[Duration] =
    flag[Duration](
      UserVideoGraphClientTimeoutFlagName,
      "userVideoGraph client timeout"
    )
  override def requestTimeout: Duration = userVideoGraphClientTimeout()

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

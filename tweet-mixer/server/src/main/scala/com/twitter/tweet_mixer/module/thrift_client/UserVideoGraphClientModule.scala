package com.twitter.tweet_mixer.module.thrift_client

import com.twitter.finagle.ThriftMux
import com.twitter.finagle.mux.ClientDiscardedRequestException
import com.twitter.finagle.service.ReqRep
import com.twitter.finagle.service.ResponseClass
import com.twitter.finagle.service.RetryBudget
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.recos.user_video_graph.thriftscala.UserVideoGraph
import com.twitter.tweet_mixer.config.TimeoutConfig
import com.twitter.util.Throw

object UserVideoGraphClientModule
    extends ThriftMethodBuilderClientModule[
      UserVideoGraph.ServicePerEndpoint,
      UserVideoGraph.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label = "user-video-graph"
  override val dest = "/s/user-tweet-graph/user-video-graph"

  override def retryBudget: RetryBudget = RetryBudget.Empty

  override def configureThriftMuxClient(
    injector: Injector,
    client: ThriftMux.Client
  ): ThriftMux.Client =
    super
      .configureThriftMuxClient(injector, client)
      .withStatsReceiver(injector.instance[StatsReceiver].scope("clnt"))
      .withRequestTimeout(injector.instance[TimeoutConfig].thriftUserVideoGraphClientTimeout)
      .withResponseClassifier {
        case ReqRep(_, Throw(_: ClientDiscardedRequestException)) => ResponseClass.Ignorable
      }
}

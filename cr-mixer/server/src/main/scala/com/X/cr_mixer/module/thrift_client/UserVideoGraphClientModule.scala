package com.X.cr_mixer.module.thrift_client

import com.X.app.Flag
import com.X.cr_mixer.module.core.TimeoutConfigModule.UserVideoGraphClientTimeoutFlagName
import com.X.finagle.ThriftMux
import com.X.finagle.mux.ClientDiscardedRequestException
import com.X.finagle.service.ReqRep
import com.X.finagle.service.ResponseClass
import com.X.finagle.service.RetryBudget
import com.X.finagle.stats.StatsReceiver
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.inject.Injector
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.recos.user_video_graph.thriftscala.UserVideoGraph
import com.X.util.Duration
import com.X.util.Throw

object UserVideoGraphClientModule
    extends ThriftMethodBuilderClientModule[
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

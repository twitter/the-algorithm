package com.ExTwitter.cr_mixer.module.thrift_client

import com.ExTwitter.app.Flag
import com.ExTwitter.cr_mixer.module.core.TimeoutConfigModule.UserVideoGraphClientTimeoutFlagName
import com.ExTwitter.finagle.ThriftMux
import com.ExTwitter.finagle.mux.ClientDiscardedRequestException
import com.ExTwitter.finagle.service.ReqRep
import com.ExTwitter.finagle.service.ResponseClass
import com.ExTwitter.finagle.service.RetryBudget
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.inject.Injector
import com.ExTwitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.ExTwitter.recos.user_video_graph.thriftscala.UserVideoGraph
import com.ExTwitter.util.Duration
import com.ExTwitter.util.Throw

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

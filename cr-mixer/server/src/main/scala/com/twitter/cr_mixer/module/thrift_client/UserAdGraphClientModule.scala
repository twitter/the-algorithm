package com.ExTwitter.cr_mixer.module.thrift_client

import com.ExTwitter.app.Flag
import com.ExTwitter.cr_mixer.module.core.TimeoutConfigModule.UserAdGraphClientTimeoutFlagName
import com.ExTwitter.finagle.ThriftMux
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.finagle.mtls.client.MtlsStackClient.MtlsThriftMuxClientSyntax
import com.ExTwitter.finagle.mux.ClientDiscardedRequestException
import com.ExTwitter.finagle.service.ReqRep
import com.ExTwitter.finagle.service.ResponseClass
import com.ExTwitter.finagle.service.RetryBudget
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.inject.Injector
import com.ExTwitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.ExTwitter.recos.user_ad_graph.thriftscala.UserAdGraph
import com.ExTwitter.util.Duration
import com.ExTwitter.util.Throw

object UserAdGraphClientModule
    extends ThriftMethodBuilderClientModule[
      UserAdGraph.ServicePerEndpoint,
      UserAdGraph.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label = "user-ad-graph"
  override val dest = "/s/user-tweet-graph/user-ad-graph"
  private val userAdGraphClientTimeout: Flag[Duration] =
    flag[Duration](UserAdGraphClientTimeoutFlagName, "userAdGraph client timeout")
  override def requestTimeout: Duration = userAdGraphClientTimeout()

  override def retryBudget: RetryBudget = RetryBudget.Empty

  override def configureThriftMuxClient(
    injector: Injector,
    client: ThriftMux.Client
  ): ThriftMux.Client =
    super
      .configureThriftMuxClient(injector, client)
      .withMutualTls(injector.instance[ServiceIdentifier])
      .withStatsReceiver(injector.instance[StatsReceiver].scope("clnt"))
      .withResponseClassifier {
        case ReqRep(_, Throw(_: ClientDiscardedRequestException)) => ResponseClass.Ignorable
      }

}

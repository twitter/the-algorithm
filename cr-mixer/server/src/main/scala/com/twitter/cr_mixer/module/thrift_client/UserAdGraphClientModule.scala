package com.twitter.cr_mixer.module.thrift_client

import com.twitter.app.Flag
import com.twitter.cr_mixer.module.core.TimeoutConfigModule.UserAdGraphClientTimeoutFlagName
import com.twitter.finagle.ThriftMux
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.mtls.client.MtlsStackClient.MtlsThriftMuxClientSyntax
import com.twitter.finagle.mux.ClientDiscardedRequestException
import com.twitter.finagle.service.ReqRep
import com.twitter.finagle.service.ResponseClass
import com.twitter.finagle.service.RetryBudget
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.recos.user_ad_graph.thriftscala.UserAdGraph
import com.twitter.util.Duration
import com.twitter.util.Throw

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

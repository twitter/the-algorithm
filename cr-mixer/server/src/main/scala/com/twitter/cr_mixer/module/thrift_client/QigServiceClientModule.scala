package com.twitter.cr_mixer.module.thrift_client

import com.twitter.app.Flag
import com.twitter.cr_mixer.module.core.TimeoutConfigModule.QigRankerClientTimeoutFlagName
import com.twitter.finagle.ThriftMux
import com.twitter.finagle.mux.ClientDiscardedRequestException
import com.twitter.finagle.service.ReqRep
import com.twitter.finagle.service.ResponseClass
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.qig_ranker.thriftscala.QigRanker
import com.twitter.util.Duration
import com.twitter.util.Throw

object QigServiceClientModule
    extends ThriftMethodBuilderClientModule[
      QigRanker.ServicePerEndpoint,
      QigRanker.MethodPerEndpoint
    ]
    with MtlsClient {
  override val label: String = "qig-ranker"
  override val dest: String = "/s/qig-shared/qig-ranker"
  private val qigRankerClientTimeout: Flag[Duration] =
    flag[Duration](QigRankerClientTimeoutFlagName, "ranking timeout")

  override def requestTimeout: Duration = qigRankerClientTimeout()

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

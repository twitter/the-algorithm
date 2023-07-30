package com.X.cr_mixer.module.thrift_client

import com.X.app.Flag
import com.X.cr_mixer.module.core.TimeoutConfigModule.QigRankerClientTimeoutFlagName
import com.X.finagle.ThriftMux
import com.X.finagle.mux.ClientDiscardedRequestException
import com.X.finagle.service.ReqRep
import com.X.finagle.service.ResponseClass
import com.X.finagle.stats.StatsReceiver
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.inject.Injector
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.qig_ranker.thriftscala.QigRanker
import com.X.util.Duration
import com.X.util.Throw

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

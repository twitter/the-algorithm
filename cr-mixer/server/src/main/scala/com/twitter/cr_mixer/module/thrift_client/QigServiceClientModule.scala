package com.ExTwitter.cr_mixer.module.thrift_client

import com.ExTwitter.app.Flag
import com.ExTwitter.cr_mixer.module.core.TimeoutConfigModule.QigRankerClientTimeoutFlagName
import com.ExTwitter.finagle.ThriftMux
import com.ExTwitter.finagle.mux.ClientDiscardedRequestException
import com.ExTwitter.finagle.service.ReqRep
import com.ExTwitter.finagle.service.ResponseClass
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.inject.Injector
import com.ExTwitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.ExTwitter.qig_ranker.thriftscala.QigRanker
import com.ExTwitter.util.Duration
import com.ExTwitter.util.Throw

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

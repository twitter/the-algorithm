package com.X.usersignalservice.module

import com.X.inject.Injector
import com.X.conversions.DurationOps._
import com.X.finagle._
import com.X.finagle.mux.ClientDiscardedRequestException
import com.X.finagle.service.ReqRep
import com.X.finagle.service.ResponseClass
import com.X.finagle.stats.StatsReceiver
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.util.Duration
import com.X.util.Throw
import com.X.socialgraph.thriftscala.SocialGraphService

object SocialGraphServiceClientModule
    extends ThriftMethodBuilderClientModule[
      SocialGraphService.ServicePerEndpoint,
      SocialGraphService.MethodPerEndpoint
    ]
    with MtlsClient {
  override val label = "socialgraph"
  override val dest = "/s/socialgraph/socialgraph"
  override val requestTimeout: Duration = 30.milliseconds

  override def configureThriftMuxClient(
    injector: Injector,
    client: ThriftMux.Client
  ): ThriftMux.Client = {
    super
      .configureThriftMuxClient(injector, client)
      .withStatsReceiver(injector.instance[StatsReceiver].scope("clnt"))
      .withSessionQualifier
      .successRateFailureAccrual(successRate = 0.9, window = 30.seconds)
      .withResponseClassifier {
        case ReqRep(_, Throw(_: ClientDiscardedRequestException)) => ResponseClass.Ignorable
      }
  }

}

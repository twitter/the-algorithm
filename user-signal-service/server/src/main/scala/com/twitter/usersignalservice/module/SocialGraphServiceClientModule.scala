package com.twitter.usersignalservice.module

import com.twitter.inject.Injector
import com.twitter.conversions.DurationOps._
import com.twitter.finagle._
import com.twitter.finagle.mux.ClientDiscardedRequestException
import com.twitter.finagle.service.ReqRep
import com.twitter.finagle.service.ResponseClass
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.util.Duration
import com.twitter.util.Throw
import com.twitter.socialgraph.thriftscala.SocialGraphService

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

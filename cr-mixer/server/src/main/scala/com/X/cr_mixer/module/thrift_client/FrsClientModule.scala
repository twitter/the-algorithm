package com.X.cr_mixer.module.thrift_client

import com.X.app.Flag
import com.X.finagle.ThriftMux
import com.X.conversions.DurationOps._
import com.X.cr_mixer.module.core.TimeoutConfigModule.FrsClientTimeoutFlagName
import com.X.finagle.service.RetryBudget
import com.X.finagle.stats.StatsReceiver
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.follow_recommendations.thriftscala.FollowRecommendationsThriftService
import com.X.inject.Injector
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.util.Duration

object FrsClientModule
    extends ThriftMethodBuilderClientModule[
      FollowRecommendationsThriftService.ServicePerEndpoint,
      FollowRecommendationsThriftService.MethodPerEndpoint
    ]
    with MtlsClient {

  override def label: String = "follow-recommendations-service"
  override def dest: String = "/s/follow-recommendations/follow-recos-service"

  private val frsSignalFetchTimeout: Flag[Duration] =
    flag[Duration](FrsClientTimeoutFlagName, "FRS signal fetch client timeout")
  override def requestTimeout: Duration = frsSignalFetchTimeout()

  override def retryBudget: RetryBudget = RetryBudget.Empty

  override def configureThriftMuxClient(
    injector: Injector,
    client: ThriftMux.Client
  ): ThriftMux.Client = {
    super
      .configureThriftMuxClient(injector, client)
      .withStatsReceiver(injector.instance[StatsReceiver].scope("clnt"))
      .withSessionQualifier
      .successRateFailureAccrual(successRate = 0.9, window = 30.seconds)
  }
}

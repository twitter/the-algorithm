package com.ExTwitter.cr_mixer.module.thrift_client

import com.ExTwitter.app.Flag
import com.ExTwitter.finagle.ThriftMux
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.cr_mixer.module.core.TimeoutConfigModule.FrsClientTimeoutFlagName
import com.ExTwitter.finagle.service.RetryBudget
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.follow_recommendations.thriftscala.FollowRecommendationsThriftService
import com.ExTwitter.inject.Injector
import com.ExTwitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.ExTwitter.util.Duration

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

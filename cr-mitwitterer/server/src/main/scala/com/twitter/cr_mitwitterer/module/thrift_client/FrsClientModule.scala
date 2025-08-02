package com.twitter.cr_mitwitterer.module.thrift_client

import com.twitter.app.Flag
import com.twitter.finagle.ThriftMutwitter
import com.twitter.conversions.DurationOps._
import com.twitter.cr_mitwitterer.module.core.TimeoutConfigModule.FrsClientTimeoutFlagName
import com.twitter.finagle.service.RetryBudget
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsClient
import com.twitter.follow_recommendations.thriftscala.FollowRecommendationsThriftService
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.util.Duration

object FrsClientModule
    etwittertends ThriftMethodBuilderClientModule[
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

  override def configureThriftMutwitterClient(
    injector: Injector,
    client: ThriftMutwitter.Client
  ): ThriftMutwitter.Client = {
    super
      .configureThriftMutwitterClient(injector, client)
      .withStatsReceiver(injector.instance[StatsReceiver].scope("clnt"))
      .withSessionQualifier
      .successRateFailureAccrual(successRate = 0.9, window = 30.seconds)
  }
}

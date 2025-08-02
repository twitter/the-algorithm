package com.twitter.product_mitwitterer.component_library.module

import com.twitter.conversions.DurationOps._
import com.twitter.conversions.PercentOps._
import com.twitter.finagle.thriftmutwitter.MethodBuilder
import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsClient
import com.twitter.inject.annotations.Flags
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.account_recommendations_mitwitterer.thriftscala.AccountRecommendationsMitwitterer
import com.twitter.util.Duration

/**
 * Implementation with reasonable defaults for an idempotent Account Recommendations Mitwitterer Thrift client.
 *
 * Note that the per request and total timeouts configured in this module are meant to represent a
 * reasonable starting point only. These were selected based on common practice, and should not be
 * assumed to be optimal for any particular use case. If you are interested in further tuning the
 * settings in this module, it is recommended to create local copy for your service.
 */
object AccountRecommendationsMitwittererModule
    etwittertends ThriftMethodBuilderClientModule[
      AccountRecommendationsMitwitterer.ServicePerEndpoint,
      AccountRecommendationsMitwitterer.MethodPerEndpoint
    ]
    with MtlsClient {
  final val AccountRecommendationsMitwittererTimeoutPerRequest =
    "account_recommendations_mitwitterer.timeout_per_request"
  final val AccountRecommendationsMitwittererTimeoutTotal = "account_recommendations_mitwitterer.timeout_total"

  flag[Duration](
    name = AccountRecommendationsMitwittererTimeoutPerRequest,
    default = 800.milliseconds,
    help = "Timeout per request for AccountRecommendationsMitwitterer")

  flag[Duration](
    name = AccountRecommendationsMitwittererTimeoutTotal,
    default = 1200.milliseconds,
    help = "Timeout total for AccountRecommendationsMitwitterer")

  override val label: String = "account-recs-mitwitterer"

  override val dest: String = "/s/account-recs-mitwitterer/account-recs-mitwitterer:thrift"

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder = {
    val timeOutPerRequest: Duration = injector
      .instance[Duration](Flags.named(AccountRecommendationsMitwittererTimeoutPerRequest))
    val timeOutTotal: Duration =
      injector.instance[Duration](Flags.named(AccountRecommendationsMitwittererTimeoutTotal))
    methodBuilder
      .withTimeoutPerRequest(timeOutPerRequest)
      .withTimeoutTotal(timeOutTotal)
      .idempotent(5.percent)
  }

  override protected def sessionAcquisitionTimeout: Duration = 500.milliseconds
}

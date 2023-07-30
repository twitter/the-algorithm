package com.X.product_mixer.component_library.module

import com.X.conversions.DurationOps._
import com.X.conversions.PercentOps._
import com.X.finagle.thriftmux.MethodBuilder
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.inject.annotations.Flags
import com.X.inject.Injector
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.account_recommendations_mixer.thriftscala.AccountRecommendationsMixer
import com.X.util.Duration

/**
 * Implementation with reasonable defaults for an idempotent Account Recommendations Mixer Thrift client.
 *
 * Note that the per request and total timeouts configured in this module are meant to represent a
 * reasonable starting point only. These were selected based on common practice, and should not be
 * assumed to be optimal for any particular use case. If you are interested in further tuning the
 * settings in this module, it is recommended to create local copy for your service.
 */
object AccountRecommendationsMixerModule
    extends ThriftMethodBuilderClientModule[
      AccountRecommendationsMixer.ServicePerEndpoint,
      AccountRecommendationsMixer.MethodPerEndpoint
    ]
    with MtlsClient {
  final val AccountRecommendationsMixerTimeoutPerRequest =
    "account_recommendations_mixer.timeout_per_request"
  final val AccountRecommendationsMixerTimeoutTotal = "account_recommendations_mixer.timeout_total"

  flag[Duration](
    name = AccountRecommendationsMixerTimeoutPerRequest,
    default = 800.milliseconds,
    help = "Timeout per request for AccountRecommendationsMixer")

  flag[Duration](
    name = AccountRecommendationsMixerTimeoutTotal,
    default = 1200.milliseconds,
    help = "Timeout total for AccountRecommendationsMixer")

  override val label: String = "account-recs-mixer"

  override val dest: String = "/s/account-recs-mixer/account-recs-mixer:thrift"

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder = {
    val timeOutPerRequest: Duration = injector
      .instance[Duration](Flags.named(AccountRecommendationsMixerTimeoutPerRequest))
    val timeOutTotal: Duration =
      injector.instance[Duration](Flags.named(AccountRecommendationsMixerTimeoutTotal))
    methodBuilder
      .withTimeoutPerRequest(timeOutPerRequest)
      .withTimeoutTotal(timeOutTotal)
      .idempotent(5.percent)
  }

  override protected def sessionAcquisitionTimeout: Duration = 500.milliseconds
}

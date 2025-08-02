package com.twitter.cr_mitwitterer.module.thrift_client
import com.twitter.app.Flag
import com.twitter.finagle.ThriftMutwitter
import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsClient
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.search.earlybird.thriftscala.EarlybirdService
import com.twitter.inject.Injector
import com.twitter.conversions.DurationOps._
import com.twitter.cr_mitwitterer.module.core.TimeoutConfigModule.EarlybirdClientTimeoutFlagName
import com.twitter.finagle.service.RetryBudget
import com.twitter.util.Duration
import org.apache.thrift.protocol.TCompactProtocol

object EarlybirdSearchClientModule
    etwittertends ThriftMethodBuilderClientModule[
      EarlybirdService.ServicePerEndpoint,
      EarlybirdService.MethodPerEndpoint
    ]
    with MtlsClient {

  override def label: String = "earlybird"
  override def dest: String = "/s/earlybird-root-superroot/root-superroot"
  private val requestTimeoutFlag: Flag[Duration] =
    flag[Duration](EarlybirdClientTimeoutFlagName, "Earlybird client timeout")
  override protected def requestTimeout: Duration = requestTimeoutFlag()

  override def retryBudget: RetryBudget = RetryBudget.Empty

  override def configureThriftMutwitterClient(
    injector: Injector,
    client: ThriftMutwitter.Client
  ): ThriftMutwitter.Client = {
    super
      .configureThriftMutwitterClient(injector, client)
      .withProtocolFactory(new TCompactProtocol.Factory())
      .withSessionQualifier
      .successRateFailureAccrual(successRate = 0.9, window = 30.seconds)
  }
}

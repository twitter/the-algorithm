package com.X.cr_mixer.module.thrift_client
import com.X.app.Flag
import com.X.finagle.ThriftMux
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.search.earlybird.thriftscala.EarlybirdService
import com.X.inject.Injector
import com.X.conversions.DurationOps._
import com.X.cr_mixer.module.core.TimeoutConfigModule.EarlybirdClientTimeoutFlagName
import com.X.finagle.service.RetryBudget
import com.X.util.Duration
import org.apache.thrift.protocol.TCompactProtocol

object EarlybirdSearchClientModule
    extends ThriftMethodBuilderClientModule[
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

  override def configureThriftMuxClient(
    injector: Injector,
    client: ThriftMux.Client
  ): ThriftMux.Client = {
    super
      .configureThriftMuxClient(injector, client)
      .withProtocolFactory(new TCompactProtocol.Factory())
      .withSessionQualifier
      .successRateFailureAccrual(successRate = 0.9, window = 30.seconds)
  }
}

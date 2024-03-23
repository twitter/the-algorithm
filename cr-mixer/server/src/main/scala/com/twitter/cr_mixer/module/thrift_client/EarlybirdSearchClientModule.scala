package com.ExTwitter.cr_mixer.module.thrift_client
import com.ExTwitter.app.Flag
import com.ExTwitter.finagle.ThriftMux
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.ExTwitter.search.earlybird.thriftscala.EarlybirdService
import com.ExTwitter.inject.Injector
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.cr_mixer.module.core.TimeoutConfigModule.EarlybirdClientTimeoutFlagName
import com.ExTwitter.finagle.service.RetryBudget
import com.ExTwitter.util.Duration
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

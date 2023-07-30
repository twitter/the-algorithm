package com.X.home_mixer.module

import com.google.inject.Provides
import com.X.conversions.DurationOps._
import com.X.finagle.ThriftMux
import com.X.finagle.builder.ClientBuilder
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.mtls.client.MtlsStackClient._
import com.X.finagle.service.RetryPolicy
import com.X.finagle.ssl.OpportunisticTls
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.manhattan.v2.thriftscala.{ManhattanCoordinator => ManhattanV2}
import com.X.timelinemixer.clients.manhattan.InjectionHistoryClient
import com.X.timelinemixer.clients.manhattan.ManhattanDatasetConfig
import com.X.timelines.clients.manhattan.Dataset
import com.X.timelines.clients.manhattan.ManhattanClient
import com.X.timelines.util.stats.RequestScope
import javax.inject.Singleton
import org.apache.thrift.protocol.TBinaryProtocol
import com.X.timelines.config.TimelinesUnderlyingClientConfiguration.ConnectTimeout
import com.X.timelines.config.TimelinesUnderlyingClientConfiguration.TCPConnectTimeout

object InjectionHistoryClientModule extends XModule {
  private val ProdDataset = "suggestion_history"
  private val StagingDataset = "suggestion_history_nonprod"
  private val AppId = "X_suggests"
  private val ServiceName = "manhattan.omega"
  private val OmegaManhattanDest = "/s/manhattan/omega.native-thrift"
  private val InjectionRequestScope = RequestScope("injectionHistoryClient")
  private val RequestTimeout = 75.millis
  private val Timeout = 150.millis

  val retryPolicy = RetryPolicy.tries(
    2,
    RetryPolicy.TimeoutAndWriteExceptionsOnly
      .orElse(RetryPolicy.ChannelClosedExceptionsOnly))

  @Provides
  @Singleton
  def providesInjectionHistoryClient(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ) = {
    val dataset = serviceIdentifier.environment.toLowerCase match {
      case "prod" => ProdDataset
      case _ => StagingDataset
    }

    val thriftMuxClient = ClientBuilder()
      .name(ServiceName)
      .daemon(daemonize = true)
      .failFast(enabled = true)
      .retryPolicy(retryPolicy)
      .tcpConnectTimeout(TCPConnectTimeout)
      .connectTimeout(ConnectTimeout)
      .dest(OmegaManhattanDest)
      .requestTimeout(RequestTimeout)
      .timeout(Timeout)
      .stack(ThriftMux.client
        .withMutualTls(serviceIdentifier)
        .withOpportunisticTls(OpportunisticTls.Required))
      .build()

    val manhattanOmegaClient = new ManhattanV2.FinagledClient(
      service = thriftMuxClient,
      protocolFactory = new TBinaryProtocol.Factory(),
      serviceName = ServiceName,
    )

    val readOnlyMhClient = new ManhattanClient(
      appId = AppId,
      manhattan = manhattanOmegaClient,
      requestScope = InjectionRequestScope,
      serviceName = ServiceName,
      statsReceiver = statsReceiver
    ).readOnly

    val mhDatasetConfig = new ManhattanDatasetConfig {
      override val SuggestionHistoryDataset = Dataset(dataset)
    }

    new InjectionHistoryClient(
      readOnlyMhClient,
      mhDatasetConfig
    )
  }
}

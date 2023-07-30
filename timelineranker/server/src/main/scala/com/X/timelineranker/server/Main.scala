package com.X.timelineranker.server

import com.X.conversions.DurationOps._
import com.X.finagle.mtls.server.MtlsStackServer._
import com.X.finagle.mux
import com.X.finagle.param.Reporter
import com.X.finagle.stats.DefaultStatsReceiver
import com.X.finagle.util.NullReporterFactory
import com.X.finagle.ListeningServer
import com.X.finagle.ServiceFactory
import com.X.finagle.ThriftMux
import com.X.finagle.mtls.authorization.server.MtlsServerSessionTrackerFilter
import com.X.finagle.ssl.OpportunisticTls
import com.X.finatra.thrift.filters.LoggingMDCFilter
import com.X.finatra.thrift.filters.ThriftMDCFilter
import com.X.finatra.thrift.filters.TraceIdMDCFilter
import com.X.logging.Logger
import com.X.server.XServer
import com.X.servo.util.MemoizingStatsReceiver
import com.X.thriftwebforms.MethodOptions
import com.X.thriftwebforms.XServerThriftWebForms
import com.X.timelineranker.config.RuntimeConfigurationImpl
import com.X.timelineranker.config.TimelineRankerFlags
import com.X.timelineranker.thriftscala
import com.X.timelines.config.DefaultDynamicConfigStoreFactory
import com.X.timelines.config.EmptyDynamicConfigStoreFactory
import com.X.timelines.config.Env
import com.X.timelines.features.app.ForcibleFeatureValues
import com.X.timelines.server.AdminMutableDeciders
import com.X.timelines.warmup.NoWarmup
import com.X.timelines.warmup.WarmupFlag
import com.X.util.Await
import java.net.SocketAddress
import org.apache.thrift.protocol.TBinaryProtocol
import org.apache.thrift.protocol.TCompactProtocol
import org.apache.thrift.protocol.TProtocolFactory

object Main extends TimelineRankerServer

class TimelineRankerServer extends {
  override val statsReceiver: MemoizingStatsReceiver = new MemoizingStatsReceiver(
    DefaultStatsReceiver)
} with XServer with AdminMutableDeciders with ForcibleFeatureValues with WarmupFlag {

  val timelineRankerFlags: TimelineRankerFlags = new TimelineRankerFlags(flag)
  lazy val mainLogger: Logger = Logger.get("Main")

  private[this] lazy val thriftWebFormsAccess = if (timelineRankerFlags.getEnv == Env.local) {
    MethodOptions.Access.Default
  } else {
    MethodOptions.Access.ByLdapGroup(Seq("timeline-team", "timelineranker-twf-read"))
  }

  private[this] def mkThriftWebFormsRoutes() =
    XServerThriftWebForms[thriftscala.TimelineRanker.MethodPerEndpoint](
      thriftServicePort = timelineRankerFlags.servicePort().getPort,
      defaultMethodAccess = thriftWebFormsAccess,
      methodOptions = TimelineRankerThriftWebForms.methodOptions,
      serviceIdentifier = timelineRankerFlags.serviceIdentifier(),
      opportunisticTlsLevel = OpportunisticTls.Required,
    )

  override protected def failfastOnFlagsNotParsed = true
  override val defaultCloseGracePeriod = 10.seconds

  private[this] def mkServer(
    labelSuffix: String,
    socketAddress: SocketAddress,
    protocolFactory: TProtocolFactory,
    serviceFactory: ServiceFactory[Array[Byte], Array[Byte]],
    opportunisticTlsLevel: OpportunisticTls.Level,
  ): ListeningServer = {
    val compressor = Seq(mux.transport.Compression.lz4Compressor(highCompression = false))
    val decompressor = Seq(mux.transport.Compression.lz4Decompressor())
    val compressionLevel =
      if (timelineRankerFlags.enableThriftmuxCompression()) {
        mux.transport.CompressionLevel.Desired
      } else {
        mux.transport.CompressionLevel.Off
      }

    val mtlsSessionTrackerFilter =
      new MtlsServerSessionTrackerFilter[mux.Request, mux.Response](statsReceiver)
    val loggingMDCFilter = { new LoggingMDCFilter }.toFilter[mux.Request, mux.Response]
    val traceIdMDCFilter = { new TraceIdMDCFilter }.toFilter[mux.Request, mux.Response]
    val thriftMDCFilter = { new ThriftMDCFilter }.toFilter[mux.Request, mux.Response]
    val filters = mtlsSessionTrackerFilter
      .andThen(loggingMDCFilter)
      .andThen(traceIdMDCFilter)
      .andThen(thriftMDCFilter)

    ThriftMux.server
    // By default, finagle logs exceptions to chickadee, which is deprecated and
    // basically unused. To avoid wasted overhead, we explicitly disable the reporter.
      .configured(Reporter(NullReporterFactory))
      .withLabel("timelineranker." + labelSuffix)
      .withMutualTls(timelineRankerFlags.getServiceIdentifier)
      .withOpportunisticTls(opportunisticTlsLevel)
      .withProtocolFactory(protocolFactory)
      .withCompressionPreferences.compression(compressionLevel, compressor)
      .withCompressionPreferences.decompression(compressionLevel, decompressor)
      .filtered(filters)
      .serve(socketAddress, serviceFactory)
  }

  def main(): Unit = {
    try {
      val parsedOpportunisticTlsLevel = OpportunisticTls.Values
        .find(
          _.value.toLowerCase == timelineRankerFlags.opportunisticTlsLevel().toLowerCase).getOrElse(
          OpportunisticTls.Desired)

      XServerThriftWebForms.addAdminRoutes(this, mkThriftWebFormsRoutes())
      addAdminMutableDeciderRoutes(timelineRankerFlags.getEnv)

      val configStoreFactory = if (timelineRankerFlags.getEnv == Env.local) {
        EmptyDynamicConfigStoreFactory
      } else {
        new DefaultDynamicConfigStoreFactory
      }

      val runtimeConfiguration = new RuntimeConfigurationImpl(
        timelineRankerFlags,
        configStoreFactory,
        decider,
        forcedFeatureValues = getFeatureSwitchOverrides,
        statsReceiver
      )

      val timelineRankerBuilder = new TimelineRankerBuilder(runtimeConfiguration)

      val warmup = if (shouldWarmup) {
        new Warmup(
          timelineRankerBuilder.timelineRanker,
          runtimeConfiguration.underlyingClients.timelineRankerForwardingClient,
          mainLogger
        )
      } else {
        new NoWarmup()
      }

      warmup.prebindWarmup()

      // Create Thrift services that use the binary Thrift protocol, and the compact one.
      val server =
        mkServer(
          "binary",
          timelineRankerFlags.servicePort(),
          new TBinaryProtocol.Factory(),
          timelineRankerBuilder.serviceFactory,
          parsedOpportunisticTlsLevel,
        )

      val compactServer =
        mkServer(
          "compact",
          timelineRankerFlags.serviceCompactPort(),
          new TCompactProtocol.Factory(),
          timelineRankerBuilder.compactProtocolServiceFactory,
          parsedOpportunisticTlsLevel,
        )

      mainLogger.info(
        s"Thrift binary server bound to service port [${timelineRankerFlags.servicePort()}]")
      closeOnExit(server)
      mainLogger.info(
        s"Thrift compact server bound to service port [${timelineRankerFlags.serviceCompactPort()}]")
      closeOnExit(compactServer)

      warmup.warmupComplete()

      mainLogger.info("ready: server")
      Await.ready(server)
      Await.ready(compactServer)
    } catch {
      case e: Throwable =>
        e.printStackTrace()
        mainLogger.error(e, s"failure in main")
        System.exit(1)
    }
  }
}

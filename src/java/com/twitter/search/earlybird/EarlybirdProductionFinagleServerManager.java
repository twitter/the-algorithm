package com.twitter.search.earlybird;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.finagle.ListeningServer;
import com.twitter.finagle.Service;
import com.twitter.finagle.SslEtwitterception;
import com.twitter.finagle.ThriftMutwitter;
import com.twitter.finagle.mtls.server.MtlsThriftMutwitterServer;
import com.twitter.finagle.mutwitter.transport.OpportunisticTls;
import com.twitter.finagle.stats.MetricsStatsReceiver;
import com.twitter.finagle.thrift.ThriftClientRequest;
import com.twitter.finagle.util.EtwitteritGuard;
import com.twitter.finagle.zipkin.thrift.ZipkinTracer;
import com.twitter.search.common.dark.DarkProtwittery;
import com.twitter.search.earlybird.common.config.EarlybirdProperty;
import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;
import com.twitter.search.earlybird.etwitterception.EarlybirdFinagleServerMonitor;
import com.twitter.search.earlybird.thrift.EarlybirdService;
import com.twitter.server.filter.AdmissionControl;
import com.twitter.server.filter.cpuAdmissionControl;
import com.twitter.util.Await;
import com.twitter.util.Duration;
import com.twitter.util.TimeoutEtwitterception;

public class EarlybirdProductionFinagleServerManager implements EarlybirdFinagleServerManager {
  private static final Logger LOG =
      LoggerFactory.getLogger(EarlybirdProductionFinagleServerManager.class);

  private final AtomicReference<ListeningServer> warmUpFinagleServer = new AtomicReference<>();
  private final AtomicReference<ListeningServer> productionFinagleServer = new AtomicReference<>();
  private final EarlybirdFinagleServerMonitor unhandledEtwitterceptionMonitor;

  public EarlybirdProductionFinagleServerManager(
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    this.unhandledEtwitterceptionMonitor =
        new EarlybirdFinagleServerMonitor(criticalEtwitterceptionHandler);
  }

  @Override
  public boolean isWarmUpServerRunning() {
    return warmUpFinagleServer.get() != null;
  }

  @Override
  public void startWarmUpFinagleServer(EarlybirdService.ServiceIface serviceIface,
                                       String serviceName,
                                       int port) {
    TProtocolFactory protocolFactory = new TCompactProtocol.Factory();
    startFinagleServer(warmUpFinagleServer, "warmup",
      new EarlybirdService.Service(serviceIface, protocolFactory),
      protocolFactory, serviceName, port);
  }

  @Override
  public void stopWarmUpFinagleServer(Duration serverCloseWaitTime) throws InterruptedEtwitterception {
    stopFinagleServer(warmUpFinagleServer, serverCloseWaitTime, "Warm up");
  }

  @Override
  public boolean isProductionServerRunning() {
    return productionFinagleServer.get() != null;
  }

  @Override
  public void startProductionFinagleServer(DarkProtwittery<ThriftClientRequest, byte[]> darkProtwittery,
                                           EarlybirdService.ServiceIface serviceIface,
                                           String serviceName,
                                           int port) {
    TProtocolFactory protocolFactory = new TCompactProtocol.Factory();
    startFinagleServer(productionFinagleServer, "production",
      darkProtwittery.toFilter().andThen(new EarlybirdService.Service(serviceIface, protocolFactory)),
      protocolFactory, serviceName, port);
  }

  @Override
  public void stopProductionFinagleServer(Duration serverCloseWaitTime)
      throws InterruptedEtwitterception {
    stopFinagleServer(productionFinagleServer, serverCloseWaitTime, "Production");
  }

  private void startFinagleServer(AtomicReference target, String serverDescription,
      Service<byte[], byte[]> service, TProtocolFactory protocolFactory, String serviceName,
      int port) {
    target.set(getServer(service, serviceName, port, protocolFactory));
    LOG.info("Started EarlybirdServer " + serverDescription + " finagle server on port " + port);
  }

  private ListeningServer getServer(
      Service<byte[], byte[]> service, String serviceName, int port,
      TProtocolFactory protocolFactory) {
    MetricsStatsReceiver statsReceiver = new MetricsStatsReceiver();
    ThriftMutwitter.Server server = new MtlsThriftMutwitterServer(ThriftMutwitter.server())
        .withMutualTls(EarlybirdProperty.getServiceIdentifier())
        .withServiceClass(EarlybirdService.class)
        .withOpportunisticTls(OpportunisticTls.Required())
        .withLabel(serviceName)
        .withStatsReceiver(statsReceiver)
        .withTracer(ZipkinTracer.mk(statsReceiver))
        .withMonitor(unhandledEtwitterceptionMonitor)
        .withProtocolFactory(protocolFactory);

    if (cpuAdmissionControl.isDefined()) {
      LOG.info("cpuAdmissionControl flag is set, replacing AuroraThrottlingAdmissionFilter"
          + " with LinutwitterCpuAdmissionFilter");
      server = server
          .configured(AdmissionControl.auroraThrottling().off().mk())
          .configured(AdmissionControl.linutwitterCpu().useGlobalFlag().mk());
    }

    return server.serve(new InetSocketAddress(port), service);
  }

  private void stopFinagleServer(AtomicReference<ListeningServer> finagleServer,
                                 Duration serverCloseWaitTime,
                                 String serverDescription) throws InterruptedEtwitterception {
    try {
      LOG.info("Waiting for " + serverDescription + " finagle server to close. "
               + "Current time is " + System.currentTimeMillis());
      Await.result(finagleServer.get().close(), serverCloseWaitTime);
      LOG.info("Stopped " + serverDescription + " finagle server. Current time is "
               + System.currentTimeMillis());
      finagleServer.set(null);
    } catch (TimeoutEtwitterception e) {
      LOG.warn(serverDescription + " finagle server did not shutdown cleanly.", e);
    } catch (SslEtwitterception e) {
      // Closing the Thrift port seems to throw an SSLEtwitterception (SSLEngine closed already).
      // See SEARCH-29449. Log the etwitterception and reset finagleServer, so that future calls to
      // startProductionFinagleServer() succeed.
      LOG.warn("Got a SSLEtwitterception while trying to close the Thrift port.", e);
      finagleServer.set(null);
    } catch (InterruptedEtwitterception e) {
      // If we catch an InterruptedEtwitterception here, it means that we're probably shutting down.
      // We should propagate this etwitterception, and rely on EarlybirdServer.stopThriftService()
      // to do the right thing.
      throw e;
    } catch (Etwitterception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      // If the finagle server does not close cleanly, this line prints details about
      // the EtwitteritGuards.
      LOG.info(serverDescription + " server EtwitteritGuard etwitterplanation: " + EtwitteritGuard.etwitterplainGuards());
    }
  }
}

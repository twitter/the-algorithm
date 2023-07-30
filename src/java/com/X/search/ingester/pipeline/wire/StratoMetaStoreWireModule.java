package com.X.search.ingester.pipeline.wire;

import java.util.concurrent.TimeUnit;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.google.common.base.Preconditions;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.X.common.quantity.Amount;
import com.X.common.quantity.Time;
import com.X.common_internal.manhattan.ManhattanClient;
import com.X.common_internal.manhattan.ManhattanClientImpl;
import com.X.finagle.Service;
import com.X.finagle.ThriftMux;
import com.X.finagle.builder.ClientBuilder;
import com.X.finagle.builder.ClientConfig.Yes;
import com.X.finagle.mtls.authentication.ServiceIdentifier;
import com.X.finagle.mtls.client.MtlsThriftMuxClient;
import com.X.finagle.mux.transport.OpportunisticTls;
import com.X.finagle.stats.DefaultStatsReceiver;
import com.X.finagle.thrift.ClientId;
import com.X.finagle.thrift.ThriftClientRequest;
import com.X.manhattan.thriftv1.ConsistencyLevel;
import com.X.manhattan.thriftv1.ManhattanCoordinator;
import com.X.metastore.client_v2.MetastoreClient;
import com.X.metastore.client_v2.MetastoreClientImpl;
import com.X.util.Duration;

public class StratoMetaStoreWireModule {
  private WireModule wireModule;
  private static final Logger LOG = LoggerFactory.getLogger(StratoMetaStoreWireModule.class);

  public StratoMetaStoreWireModule(WireModule wireModule) {
    this.wireModule = wireModule;
  }

  private static final String MANHATTAN_SD_ZK_ROLE =
      WireModule.JNDI_PIPELINE_ROOT + "manhattanSDZKRole";
  private static final String MANHATTAN_SD_ZK_ENV =
      WireModule.JNDI_PIPELINE_ROOT + "manhattanSDZKEnv";
  private static final String MANHATTAN_SD_ZK_NAME =
      WireModule.JNDI_PIPELINE_ROOT + "manhattanSDZKName";
  private static final String MANHATTAN_APPLICATION_ID = "ingester_starbuck";

  private static class Options {
    // The client id as a string
    private final String clientId = "ingester";

    // The connection timeout in millis
    private final long connectTimeout = 50;

    // The request timeout im millis
    private final long requestTimeout = 300;

    // Total timeout per call (including retries)
    private final long totalTimeout = 500;

    // The maximum number of retries per call
    private final int retries = 2;
  }

  private final Options options = new Options();

  private ClientBuilder<ThriftClientRequest, byte[], ?, Yes, Yes> getClientBuilder(
      String name,
      ServiceIdentifier serviceIdentifier) {
    return getClientBuilder(name, new ClientId(options.clientId), serviceIdentifier);
  }

  private ClientBuilder<ThriftClientRequest, byte[], ?, Yes, Yes> getClientBuilder(
          String name,
          ClientId clientId,
          ServiceIdentifier serviceIdentifier) {
    Preconditions.checkNotNull(serviceIdentifier,
        "Can't create Metastore Manhattan client with S2S auth because Service Identifier is null");
    LOG.info(String.format("Service identifier for Metastore Manhattan client: %s",
        ServiceIdentifier.asString(serviceIdentifier)));
    return ClientBuilder.get()
        .name(name)
        .tcpConnectTimeout(new Duration(TimeUnit.MILLISECONDS.toNanos(options.connectTimeout)))
        .requestTimeout(new Duration(TimeUnit.MILLISECONDS.toNanos(options.requestTimeout)))
        .timeout(new Duration(TimeUnit.MILLISECONDS.toNanos(options.totalTimeout)))
        .retries(options.retries)
        .reportTo(DefaultStatsReceiver.get())
        .stack(new MtlsThriftMuxClient(ThriftMux.client())
            .withMutualTls(serviceIdentifier)
            .withClientId(clientId)
            .withOpportunisticTls(OpportunisticTls.Required()));
  }

  /**
   * Returns the Metastore client.
   */
  public MetastoreClient getMetastoreClient(ServiceIdentifier serviceIdentifier)
      throws NamingException {
    Context jndiContext = new InitialContext();
    String destString = String.format("/cluster/local/%s/%s/%s",
        jndiContext.lookup(MANHATTAN_SD_ZK_ROLE),
        jndiContext.lookup(MANHATTAN_SD_ZK_ENV),
        jndiContext.lookup(MANHATTAN_SD_ZK_NAME));
    LOG.info("Manhattan serverset Name: {}", destString);

    Service<ThriftClientRequest, byte[]> service =
        ClientBuilder.safeBuild(getClientBuilder("metastore", serviceIdentifier).dest(destString));

    ManhattanClient manhattanClient = new ManhattanClientImpl(
        new ManhattanCoordinator.ServiceToClient(service, new TBinaryProtocol.Factory()),
        MANHATTAN_APPLICATION_ID,
        Amount.of((int) options.requestTimeout, Time.MILLISECONDS),
        ConsistencyLevel.ONE);

    return new MetastoreClientImpl(manhattanClient);
  }
}

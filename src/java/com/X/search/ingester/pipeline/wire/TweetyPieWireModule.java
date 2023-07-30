package com.X.search.ingester.pipeline.wire;

import java.util.concurrent.TimeoutException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.X.common_internal.zookeeper.XServerSet;
import com.X.finagle.Name;
import com.X.finagle.Resolvers;
import com.X.finagle.Service;
import com.X.finagle.ThriftMux;
import com.X.finagle.builder.ClientBuilder;
import com.X.finagle.builder.ClientConfig;
import com.X.finagle.mtls.authentication.ServiceIdentifier;
import com.X.finagle.mtls.client.MtlsThriftMuxClient;
import com.X.finagle.mux.transport.OpportunisticTls;
import com.X.finagle.service.RetryPolicy;
import com.X.finagle.stats.DefaultStatsReceiver;
import com.X.finagle.thrift.ClientId;
import com.X.finagle.thrift.ThriftClientRequest;
import com.X.servo.util.WaitForServerSets;
import com.X.tweetypie.thriftjava.TweetService;
import com.X.util.Await;
import com.X.util.Duration;

final class TweetyPieWireModule {
  private static final Logger LOG = LoggerFactory.getLogger(ProductionWireModule.class);

  private static final int TWEETYPIE_CONNECT_TIMEOUT_MS = 100;
  private static final int TWEETYPIE_REQUEST_TIMEOUT_MS = 500;

  // This is actually the total tries count, so one initial try, and one more retry (if needed).
  private static final int TWEETYPIE_REQUEST_NUM_TRIES = 3;
  private static final int TWEETYPIE_TOTAL_TIMEOUT_MS =
      TWEETYPIE_REQUEST_TIMEOUT_MS * TWEETYPIE_REQUEST_NUM_TRIES;

  private static final String TWEETYPIE_SD_ZK_ROLE =
      WireModule.JNDI_PIPELINE_ROOT + "tweetypieSDZKRole";
  private static final String TWEETYPIE_SD_ZK_ENV =
      WireModule.JNDI_PIPELINE_ROOT + "tweetypieSDZKEnv";
  private static final String TWEETYPIE_SD_ZK_NAME =
      WireModule.JNDI_PIPELINE_ROOT + "tweetypieSDZKName";

  private TweetyPieWireModule() {
  }

  private static XServerSet.Service getTweetyPieZkServerSetService()
      throws NamingException {
    Context jndiContext = new InitialContext();
    XServerSet.Service service = new XServerSet.Service(
        (String) jndiContext.lookup(TWEETYPIE_SD_ZK_ROLE),
        (String) jndiContext.lookup(TWEETYPIE_SD_ZK_ENV),
        (String) jndiContext.lookup(TWEETYPIE_SD_ZK_NAME));
    LOG.info("TweetyPie ZK path: {}", XServerSet.getPath(service));
    return service;
  }

  static TweetService.ServiceToClient getTweetyPieClient(
      String clientIdString, ServiceIdentifier serviceIdentifier) throws NamingException {
    XServerSet.Service service = getTweetyPieZkServerSetService();

    // Use explicit Name types so we can force a wait on resolution (COORD-479)
    String destString = String.format("/cluster/local/%s/%s/%s",
        service.getRole(), service.getEnv(), service.getName());
    Name destination = Resolvers.eval(destString);
    try {
      Await.ready(WaitForServerSets.ready(destination, Duration.fromMilliseconds(10000)));
    } catch (TimeoutException e) {
      LOG.warn("Timed out while resolving Zookeeper ServerSet", e);
    } catch (InterruptedException e) {
      LOG.warn("Interrupted while resolving Zookeeper ServerSet", e);
      Thread.currentThread().interrupt();
    }

    LOG.info("Creating Tweetypie client with ID: {}", clientIdString);
    ClientId clientId = new ClientId(clientIdString);

    MtlsThriftMuxClient mtlsThriftMuxClient = new MtlsThriftMuxClient(
        ThriftMux.client().withClientId(clientId));
    ThriftMux.Client tmuxClient = mtlsThriftMuxClient
        .withMutualTls(serviceIdentifier)
        .withOpportunisticTls(OpportunisticTls.Required());

    ClientBuilder<
        ThriftClientRequest,
        byte[],
        ClientConfig.Yes,
        ClientConfig.Yes,
        ClientConfig.Yes> builder = ClientBuilder.get()
        .stack(tmuxClient)
        .name("retrieve_cards_tweetypie_client")
        .dest(destination)
        .reportTo(DefaultStatsReceiver.get())
        .connectTimeout(Duration.fromMilliseconds(TWEETYPIE_CONNECT_TIMEOUT_MS))
        .requestTimeout(Duration.fromMilliseconds(TWEETYPIE_REQUEST_TIMEOUT_MS))
        .timeout(Duration.fromMilliseconds(TWEETYPIE_TOTAL_TIMEOUT_MS))
        .retryPolicy(RetryPolicy.tries(
            TWEETYPIE_REQUEST_NUM_TRIES,
            RetryPolicy.TimeoutAndWriteExceptionsOnly()));

    Service<ThriftClientRequest, byte[]> clientBuilder = ClientBuilder.safeBuild(builder);

    return new TweetService.ServiceToClient(clientBuilder, new TBinaryProtocol.Factory());
  }
}

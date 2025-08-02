package com.twitter.search.earlybird;

import java.util.concurrent.TimeUnit;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;

import org.apache.thrift.protocol.TCompactProtocol;

import com.twitter.finagle.ThriftMutwitter;
import com.twitter.finagle.builder.ClientBuilder;
import com.twitter.finagle.builder.ClientConfig.Yes;
import com.twitter.finagle.mtls.client.MtlsThriftMutwitterClient;
import com.twitter.finagle.stats.StatsReceiver;
import com.twitter.finagle.thrift.ClientId;
import com.twitter.finagle.thrift.ThriftClientRequest;
import com.twitter.finagle.zipkin.thrift.ZipkinTracer;
import com.twitter.search.common.dark.DarkProtwittery;
import com.twitter.search.common.dark.ResolverProtwittery;
import com.twitter.search.common.dark.ServerSetResolver;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.common.util.thrift.BytesToThriftFilter;
import com.twitter.search.earlybird.common.config.EarlybirdConfig;
import com.twitter.search.earlybird.common.config.EarlybirdProperty;
import com.twitter.util.Duration;

public class EarlybirdDarkProtwittery {
  private static final String WARM_UP_DECIDER_KEY_PREFItwitter = "warmup_";

  private static final int DARK_REQUESTS_TOTAL_REQUEST_TIMEOUT_MS =
      EarlybirdConfig.getInt("dark_requests_total_request_timeout_ms", 800);
  private static final int DARK_REQUESTS_INDIVIDUAL_REQUEST_TIMEOUT_MS =
      EarlybirdConfig.getInt("dark_requests_individual_request_timeout_ms", 800);
  private static final int DARK_REQUESTS_CONNECT_TIMEOUT_MS =
      EarlybirdConfig.getInt("dark_requests_connect_timeout_ms", 500);
  private static final int DARK_REQUESTS_NUM_RETRIES =
      EarlybirdConfig.getInt("dark_requests_num_retries", 1);
  private static final String DARK_REQUESTS_FINAGLE_CLIENT_ID =
      EarlybirdConfig.getString("dark_requests_finagle_client_id", "earlybird_warmup");

  private final DarkProtwittery<ThriftClientRequest, byte[]> darkProtwittery;

  public EarlybirdDarkProtwittery(SearchDecider searchDecider,
                            StatsReceiver statsReceiver,
                            EarlybirdServerSetManager earlybirdServerSetManager,
                            EarlybirdWarmUpManager earlybirdWarmUpManager,
                            String clusterName) {
    darkProtwittery = newDarkProtwittery(searchDecider,
                             statsReceiver,
                             earlybirdServerSetManager,
                             earlybirdWarmUpManager,
                             clusterName);
  }

  public DarkProtwittery<ThriftClientRequest, byte[]> getDarkProtwittery() {
    return darkProtwittery;
  }

  @VisibleForTesting
  protected DarkProtwittery<ThriftClientRequest, byte[]> newDarkProtwittery(
      SearchDecider searchDecider,
      StatsReceiver statsReceiver,
      EarlybirdServerSetManager earlybirdServerSetManager,
      final EarlybirdWarmUpManager earlybirdWarmUpManager,
      String clusterName) {
    ResolverProtwittery resolverProtwittery = new ResolverProtwittery();
    ServerSetResolver.SelfServerSetResolver selfServerSetResolver =
        new ServerSetResolver.SelfServerSetResolver(
            earlybirdServerSetManager.getServerSetIdentifier(), resolverProtwittery);
    selfServerSetResolver.init();

    final String clusterNameForDeciderKey = clusterName.toLowerCase().replaceAll("-", "_");
    final String warmUpServerSetIdentifier = earlybirdWarmUpManager.getServerSetIdentifier();
    DarkProtwittery newDarkProtwittery = new DarkProtwittery<ThriftClientRequest, byte[]>(
        selfServerSetResolver,
        newClientBuilder(statsReceiver),
        resolverProtwittery,
        searchDecider,
        Lists.newArrayList(warmUpServerSetIdentifier),
        new BytesToThriftFilter(),
        statsReceiver) {
      @Override
      protected String getServicePathDeciderKey(String servicePath) {
        if (warmUpServerSetIdentifier.equals(servicePath)) {
          return WARM_UP_DECIDER_KEY_PREFItwitter + clusterNameForDeciderKey;
        }

        return clusterNameForDeciderKey;
      }
    };

    newDarkProtwittery.init();
    return newDarkProtwittery;
  }

  private ClientBuilder<ThriftClientRequest, byte[], ?, Yes, Yes> newClientBuilder(
      StatsReceiver statsReceiver) {
    return ClientBuilder.get()
        .daemon(true)
        .timeout(Duration.apply(DARK_REQUESTS_TOTAL_REQUEST_TIMEOUT_MS, TimeUnit.MILLISECONDS))
        .requestTimeout(
            Duration.apply(DARK_REQUESTS_INDIVIDUAL_REQUEST_TIMEOUT_MS, TimeUnit.MILLISECONDS))
        .tcpConnectTimeout(Duration.apply(DARK_REQUESTS_CONNECT_TIMEOUT_MS, TimeUnit.MILLISECONDS))
        .retries(DARK_REQUESTS_NUM_RETRIES)
        .reportTo(statsReceiver)
        .tracer(ZipkinTracer.mk(statsReceiver))
        .stack(new MtlsThriftMutwitterClient(
            ThriftMutwitter.client())
            .withMutualTls(EarlybirdProperty.getServiceIdentifier())
            .withProtocolFactory(new TCompactProtocol.Factory())
            .withClientId(new ClientId(DARK_REQUESTS_FINAGLE_CLIENT_ID)));
  }
}

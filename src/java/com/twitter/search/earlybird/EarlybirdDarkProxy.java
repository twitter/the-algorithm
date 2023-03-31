package com.twitter.search.earlybird;

import java.util.concurrent.TimeUnit;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;

import org.apache.thrift.protocol.TCompactProtocol;

import com.twitter.finagle.ThriftMux;
import com.twitter.finagle.builder.ClientBuilder;
import com.twitter.finagle.builder.ClientConfig.Yes;
import com.twitter.finagle.mtls.client.MtlsThriftMuxClient;
import com.twitter.finagle.stats.StatsReceiver;
import com.twitter.finagle.thrift.ClientId;
import com.twitter.finagle.thrift.ThriftClientRequest;
import com.twitter.finagle.zipkin.thrift.ZipkinTracer;
import com.twitter.search.common.dark.DarkProxy;
import com.twitter.search.common.dark.ResolverProxy;
import com.twitter.search.common.dark.ServerSetResolver;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.common.util.thrift.BytesToThriftFilter;
import com.twitter.search.earlybird.common.config.EarlybirdConfig;
import com.twitter.search.earlybird.common.config.EarlybirdProperty;
import com.twitter.util.Duration;

public class EarlybirdDarkProxy {
  private static final String WARM_UP_DECIDER_KEY_PREFIX = "warmup_";

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

  private final DarkProxy<ThriftClientRequest, byte[]> darkProxy;

  public EarlybirdDarkProxy(SearchDecider searchDecider,
                            StatsReceiver statsReceiver,
                            EarlybirdServerSetManager earlybirdServerSetManager,
                            EarlybirdWarmUpManager earlybirdWarmUpManager,
                            String clusterName) {
    darkProxy = newDarkProxy(searchDecider,
                             statsReceiver,
                             earlybirdServerSetManager,
                             earlybirdWarmUpManager,
                             clusterName);
  }

  public DarkProxy<ThriftClientRequest, byte[]> getDarkProxy() {
    return darkProxy;
  }

  @VisibleForTesting
  protected DarkProxy<ThriftClientRequest, byte[]> newDarkProxy(
      SearchDecider searchDecider,
      StatsReceiver statsReceiver,
      EarlybirdServerSetManager earlybirdServerSetManager,
      final EarlybirdWarmUpManager earlybirdWarmUpManager,
      String clusterName) {
    ResolverProxy resolverProxy = new ResolverProxy();
    ServerSetResolver.SelfServerSetResolver selfServerSetResolver =
        new ServerSetResolver.SelfServerSetResolver(
            earlybirdServerSetManager.getServerSetIdentifier(), resolverProxy);
    selfServerSetResolver.init();

    final String clusterNameForDeciderKey = clusterName.toLowerCase().replaceAll("-", "_");
    final String warmUpServerSetIdentifier = earlybirdWarmUpManager.getServerSetIdentifier();
    DarkProxy newDarkProxy = new DarkProxy<ThriftClientRequest, byte[]>(
        selfServerSetResolver,
        newClientBuilder(statsReceiver),
        resolverProxy,
        searchDecider,
        Lists.newArrayList(warmUpServerSetIdentifier),
        new BytesToThriftFilter(),
        statsReceiver) {
      @Override
      protected String getServicePathDeciderKey(String servicePath) {
        if (warmUpServerSetIdentifier.equals(servicePath)) {
          return WARM_UP_DECIDER_KEY_PREFIX + clusterNameForDeciderKey;
        }

        return clusterNameForDeciderKey;
      }
    };

    newDarkProxy.init();
    return newDarkProxy;
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
        .stack(new MtlsThriftMuxClient(
            ThriftMux.client())
            .withMutualTls(EarlybirdProperty.getServiceIdentifier())
            .withProtocolFactory(new TCompactProtocol.Factory())
            .withClientId(new ClientId(DARK_REQUESTS_FINAGLE_CLIENT_ID)));
  }
}

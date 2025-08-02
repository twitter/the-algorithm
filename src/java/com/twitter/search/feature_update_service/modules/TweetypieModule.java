package com.twitter.search.feature_update_service.modules;

import javatwitter.inject.Singleton;

import com.google.inject.Provides;

import com.twitter.finagle.Service;
import com.twitter.finagle.ThriftMutwitter;
import com.twitter.finagle.builder.ClientBuilder;
import com.twitter.finagle.mtls.authentication.ServiceIdentifier;
import com.twitter.finagle.mtls.client.MtlsThriftMutwitterClient;
import com.twitter.finagle.stats.StatsReceiver;
import com.twitter.finagle.thrift.ClientId;
import com.twitter.finagle.thrift.ThriftClientRequest;
import com.twitter.finagle.zipkin.thrift.ZipkinTracer;
import com.twitter.inject.TwitterModule;
import com.twitter.spam.finagle.FinagleUtil;
import com.twitter.tweetypie.thriftjava.TweetService;
import com.twitter.util.Duration;

public class TweetypieModule etwittertends TwitterModule {
  @Provides
  @Singleton
  private ThriftMutwitter.Client providesThriftMutwitterClient(ServiceIdentifier serviceIdentifier) {
    return new MtlsThriftMutwitterClient(ThriftMutwitter.client())
        .withMutualTls(serviceIdentifier)
        .withClientId(new ClientId("feature_update_service.prod"));
  }
  private static final Duration DEFAULT_CONN_TIMEOUT = Duration.fromSeconds(2);

  private static final Duration TWEET_SERVICE_REQUEST_TIMEOUT = Duration.fromMilliseconds(500);

  private static final int TWEET_SERVICE_RETRIES = 5;
  @Provides @Singleton
  private TweetService.ServiceIface provideTweetServiceClient(
      ThriftMutwitter.Client thriftMutwitter,
      StatsReceiver statsReceiver) throws InterruptedEtwitterception {
    // TweetService is TweetService (tweetypie) with different api
    // Since TweetService will be primarly used for interacting with
    // tweetypie's fletwitterible schema (MH), we will increase request
    // timeout and retries but share other settings from TweetService.
    @SuppressWarnings("unchecked")
    ClientBuilder clientBuilder = FinagleUtil.getClientBuilder()
        .name("tweet_service")
        .stack(thriftMutwitter)
        .tcpConnectTimeout(DEFAULT_CONN_TIMEOUT)
        .requestTimeout(TWEET_SERVICE_REQUEST_TIMEOUT)
        .retries(TWEET_SERVICE_RETRIES)
        .reportTo(statsReceiver)
        .tracer(ZipkinTracer.mk(statsReceiver));

    @SuppressWarnings("unchecked")
    final Service<ThriftClientRequest, byte[]> finagleClient =
        FinagleUtil.createResolvedFinagleClient(
            "tweetypie",
            "prod",
            "tweetypie",
            clientBuilder);

    return new TweetService.ServiceToClient(finagleClient);
  }
}

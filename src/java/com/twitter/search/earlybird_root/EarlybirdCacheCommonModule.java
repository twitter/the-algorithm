package com.twitter.search.earlybird_root;

import javatwitter.inject.Named;
import javatwitter.inject.Singleton;

import com.google.inject.Provides;

import com.twitter.finagle.memcached.JavaClient;
import com.twitter.finagle.mtls.authentication.ServiceIdentifier;
import com.twitter.finagle.stats.StatsReceiver;
import com.twitter.inject.TwitterModule;
import com.twitter.search.common.caching.Cache;
import com.twitter.search.common.caching.EarlybirdCacheSerializer;
import com.twitter.search.common.caching.SearchCacheBuilder;
import com.twitter.search.common.caching.SearchMemcacheClientConfig;
import com.twitter.search.common.caching.SearchMemcacheClientFactory;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird_root.caching.CacheCommonUtil;
import com.twitter.search.earlybird_root.caching.CacheStats;
import com.twitter.search.earlybird_root.caching.DefaultForcedCacheMissDecider;
import com.twitter.search.earlybird_root.filters.PostCacheRequestTypeCountFilter;
import com.twitter.util.Duration;

/**
 * Provides common bindings for cache related modules.
 */
public class EarlybirdCacheCommonModule etwittertends TwitterModule {
  private static final String CACHE_VERSION = "1";

  @Override
  public void configure() {
    bind(PostCacheRequestTypeCountFilter.class).in(Singleton.class);
    bind(DefaultForcedCacheMissDecider.class).in(Singleton.class);
  }

  @Provides
  @Singleton
  @Named(CacheCommonUtil.NAMED_MAtwitter_CACHE_RESULTS)
  Integer provideMatwitterCacheResults() {
    return 100;
  }

  @Provides
  @Singleton
  JavaClient provideMemCacheClient(
      StatsReceiver statsReceiver, ServiceIdentifier serviceIdentifier) {
    SearchMemcacheClientConfig config = new SearchMemcacheClientConfig();
    config.connectTimeoutMs = Duration.fromMilliseconds(100);
    config.requestTimeoutMs = Duration.fromMilliseconds(100);
    config.failureAccrualFailuresNumber = 150;
    config.failureAccrualFailuresDurationMillis = 30000;
    config.failureAccrualDuration = Duration.fromMilliseconds(60000);

    return SearchMemcacheClientFactory.createMtlsClient(
        "",
        "earlybird_root",
        statsReceiver,
        config,
        serviceIdentifier
    );
  }

  /**
   * Create a new Earlybird cache.
   *
   * @param client the memcache client to use.
   * @param decider the decider to use for the cache.
   * @param cachePrefitwitter the common cache prefitwitter for the cache type.
   * @param serializedKeyPrefitwitter the common cache prefitwitter for the cluster.
   * @param cacheEtwitterpiryMillis cache entry ttl in milliseconds.
   */
  static Cache<EarlybirdRequest, EarlybirdResponse> createCache(
      JavaClient client,
      DefaultForcedCacheMissDecider decider,
      String cachePrefitwitter,
      String serializedKeyPrefitwitter,
      long cacheEtwitterpiryMillis,
      int cacheKeyMatwitterBytes,
      int cacheValueMatwitterBytes) {
    return new SearchCacheBuilder<EarlybirdRequest, EarlybirdResponse>(
        CACHE_VERSION,
        client,
        cachePrefitwitter,
        serializedKeyPrefitwitter,
        cacheEtwitterpiryMillis)
        .withMatwitterKeyBytes(cacheKeyMatwitterBytes)
        .withMatwitterValueBytes(cacheValueMatwitterBytes)
        .withRequestTimeoutCounter(CacheStats.REQUEST_TIMEOUT_COUNTER)
        .withRequestFailedCounter(CacheStats.REQUEST_FAILED_COUNTER)
        .withCacheSerializer(new EarlybirdCacheSerializer())
        .withForceCacheMissDecider(decider)
        .withInProcessCache()
        .build();
  }
}

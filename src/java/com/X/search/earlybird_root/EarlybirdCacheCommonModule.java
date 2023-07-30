package com.X.search.earlybird_root;

import javax.inject.Named;
import javax.inject.Singleton;

import com.google.inject.Provides;

import com.X.finagle.memcached.JavaClient;
import com.X.finagle.mtls.authentication.ServiceIdentifier;
import com.X.finagle.stats.StatsReceiver;
import com.X.inject.XModule;
import com.X.search.common.caching.Cache;
import com.X.search.common.caching.EarlybirdCacheSerializer;
import com.X.search.common.caching.SearchCacheBuilder;
import com.X.search.common.caching.SearchMemcacheClientConfig;
import com.X.search.common.caching.SearchMemcacheClientFactory;
import com.X.search.earlybird.thrift.EarlybirdRequest;
import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.search.earlybird_root.caching.CacheCommonUtil;
import com.X.search.earlybird_root.caching.CacheStats;
import com.X.search.earlybird_root.caching.DefaultForcedCacheMissDecider;
import com.X.search.earlybird_root.filters.PostCacheRequestTypeCountFilter;
import com.X.util.Duration;

/**
 * Provides common bindings for cache related modules.
 */
public class EarlybirdCacheCommonModule extends XModule {
  private static final String CACHE_VERSION = "1";

  @Override
  public void configure() {
    bind(PostCacheRequestTypeCountFilter.class).in(Singleton.class);
    bind(DefaultForcedCacheMissDecider.class).in(Singleton.class);
  }

  @Provides
  @Singleton
  @Named(CacheCommonUtil.NAMED_MAX_CACHE_RESULTS)
  Integer provideMaxCacheResults() {
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
   * @param cachePrefix the common cache prefix for the cache type.
   * @param serializedKeyPrefix the common cache prefix for the cluster.
   * @param cacheExpiryMillis cache entry ttl in milliseconds.
   */
  static Cache<EarlybirdRequest, EarlybirdResponse> createCache(
      JavaClient client,
      DefaultForcedCacheMissDecider decider,
      String cachePrefix,
      String serializedKeyPrefix,
      long cacheExpiryMillis,
      int cacheKeyMaxBytes,
      int cacheValueMaxBytes) {
    return new SearchCacheBuilder<EarlybirdRequest, EarlybirdResponse>(
        CACHE_VERSION,
        client,
        cachePrefix,
        serializedKeyPrefix,
        cacheExpiryMillis)
        .withMaxKeyBytes(cacheKeyMaxBytes)
        .withMaxValueBytes(cacheValueMaxBytes)
        .withRequestTimeoutCounter(CacheStats.REQUEST_TIMEOUT_COUNTER)
        .withRequestFailedCounter(CacheStats.REQUEST_FAILED_COUNTER)
        .withCacheSerializer(new EarlybirdCacheSerializer())
        .withForceCacheMissDecider(decider)
        .withInProcessCache()
        .build();
  }
}

package com.X.search.earlybird_root;

import javax.inject.Named;
import javax.inject.Singleton;

import com.google.inject.Key;
import com.google.inject.Provides;

import com.X.common.util.Clock;
import com.X.finagle.memcached.JavaClient;
import com.X.inject.XModule;
import com.X.search.common.caching.Cache;
import com.X.search.common.decider.SearchDecider;
import com.X.search.common.root.LoggingSupport;
import com.X.search.common.root.PartitionLoggingSupport;
import com.X.search.common.root.SearchRootModule;
import com.X.search.common.root.SearchRootWarmup;
import com.X.search.common.root.ValidationBehavior;
import com.X.search.common.root.WarmupConfig;
import com.X.search.common.schema.earlybird.EarlybirdCluster;
import com.X.search.earlybird.thrift.EarlybirdRequest;
import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.search.earlybird.thrift.EarlybirdService;
import com.X.search.earlybird_root.caching.DefaultForcedCacheMissDecider;
import com.X.search.earlybird_root.caching.FacetsCache;
import com.X.search.earlybird_root.caching.RecencyCache;
import com.X.search.earlybird_root.caching.RelevanceCache;
import com.X.search.earlybird_root.caching.StrictRecencyCache;
import com.X.search.earlybird_root.caching.TermStatsCache;
import com.X.search.earlybird_root.caching.TopTweetsCache;
import com.X.search.earlybird_root.caching.TopTweetsServicePostProcessor;
import com.X.search.earlybird_root.common.EarlybirdRequestContext;

public class RealtimeCgRootAppModule extends XModule {
  private static final long RECENCY_CACHE_TTL_MILLIS = 20000L;
  private static final long RELEVANCE_CACHE_TTL_MILLIS = 20000L;
  private static final long FACETS_CACHE_TTL_MILLIS = 300000L;
  private static final long TERMSTATS_CACHE_TTL_MILLIS = 300000L;

  @Override
  public void configure() {
    bind(Key.get(EarlybirdCluster.class)).toInstance(EarlybirdCluster.REALTIME_CG);

    bind(EarlybirdServiceScatterGatherSupport.class)
      .to(EarlybirdRealtimeCgScatterGatherSupport.class);

    bind(EarlybirdService.ServiceIface.class).to(RealtimeCgRootService.class);
  }

  @Provides
  @Singleton
  @RecencyCache
  Cache<EarlybirdRequest, EarlybirdResponse> provideRecencyCache(
      JavaClient client,
      DefaultForcedCacheMissDecider decider,
      @Named(SearchRootModule.NAMED_SERIALIZED_KEY_PREFIX) String serializedKeyPrefix,
      @Named(SearchRootModule.NAMED_CACHE_KEY_MAX_BYTES) int cacheKeyMaxBytes,
      @Named(SearchRootModule.NAMED_CACHE_VALUE_MAX_BYTES) int cacheValueMaxBytes) {
    return EarlybirdCacheCommonModule.createCache(client, decider, "realtime_cg_recency_root",
        serializedKeyPrefix, RECENCY_CACHE_TTL_MILLIS, cacheKeyMaxBytes, cacheValueMaxBytes);
  }

  @Provides
  @Singleton
  @RelevanceCache
  Cache<EarlybirdRequest, EarlybirdResponse> provideRelevanceCache(
      JavaClient client,
      DefaultForcedCacheMissDecider decider,
      @Named(SearchRootModule.NAMED_SERIALIZED_KEY_PREFIX) String serializedKeyPrefix,
      @Named(SearchRootModule.NAMED_CACHE_KEY_MAX_BYTES) int cacheKeyMaxBytes,
      @Named(SearchRootModule.NAMED_CACHE_VALUE_MAX_BYTES) int cacheValueMaxBytes) {
    return EarlybirdCacheCommonModule.createCache(client, decider, "realtime_cg_relevance_root",
        serializedKeyPrefix, RELEVANCE_CACHE_TTL_MILLIS, cacheKeyMaxBytes, cacheValueMaxBytes);
  }

  @Provides
  @Singleton
  @StrictRecencyCache
  Cache<EarlybirdRequest, EarlybirdResponse> provideStrictRecencyCache(
      JavaClient client,
      DefaultForcedCacheMissDecider decider,
      @Named(SearchRootModule.NAMED_SERIALIZED_KEY_PREFIX) String serializedKeyPrefix,
      @Named(SearchRootModule.NAMED_CACHE_KEY_MAX_BYTES) int cacheKeyMaxBytes,
      @Named(SearchRootModule.NAMED_CACHE_VALUE_MAX_BYTES) int cacheValueMaxBytes) {
    return EarlybirdCacheCommonModule.createCache(
        client, decider, "realtime_cg_strict_recency_root", serializedKeyPrefix,
        RECENCY_CACHE_TTL_MILLIS, cacheKeyMaxBytes, cacheValueMaxBytes);
  }

  @Provides
  @Singleton
  @FacetsCache
  Cache<EarlybirdRequest, EarlybirdResponse> provideFacetsCache(
      JavaClient client,
      DefaultForcedCacheMissDecider decider,
      @Named(SearchRootModule.NAMED_SERIALIZED_KEY_PREFIX) String serializedKeyPrefix,
      @Named(SearchRootModule.NAMED_CACHE_KEY_MAX_BYTES) int cacheKeyMaxBytes,
      @Named(SearchRootModule.NAMED_CACHE_VALUE_MAX_BYTES) int cacheValueMaxBytes) {
    return EarlybirdCacheCommonModule.createCache(client, decider, "realtime_cg_facets_root",
        serializedKeyPrefix, FACETS_CACHE_TTL_MILLIS, cacheKeyMaxBytes, cacheValueMaxBytes);
  }

  @Provides
  @Singleton
  @TermStatsCache
  Cache<EarlybirdRequest, EarlybirdResponse> provideTermStatsCache(
      JavaClient client,
      DefaultForcedCacheMissDecider decider,
      @Named(SearchRootModule.NAMED_SERIALIZED_KEY_PREFIX) String serializedKeyPrefix,
      @Named(SearchRootModule.NAMED_CACHE_KEY_MAX_BYTES) int cacheKeyMaxBytes,
      @Named(SearchRootModule.NAMED_CACHE_VALUE_MAX_BYTES) int cacheValueMaxBytes) {
    return EarlybirdCacheCommonModule.createCache(client, decider, "realtime_cg_termstats_root",
        serializedKeyPrefix, TERMSTATS_CACHE_TTL_MILLIS, cacheKeyMaxBytes, cacheValueMaxBytes);
  }

  @Provides
  @Singleton
  @TopTweetsCache
  Cache<EarlybirdRequest, EarlybirdResponse> provideTopTweetsCache(
      JavaClient client,
      DefaultForcedCacheMissDecider decider,
      @Named(SearchRootModule.NAMED_SERIALIZED_KEY_PREFIX) String serializedKeyPrefix,
      @Named(SearchRootModule.NAMED_CACHE_KEY_MAX_BYTES) int cacheKeyMaxBytes,
      @Named(SearchRootModule.NAMED_CACHE_VALUE_MAX_BYTES) int cacheValueMaxBytes) {
    return EarlybirdCacheCommonModule.createCache(client, decider, "realtime_cg_toptweets_root",
        serializedKeyPrefix, TopTweetsServicePostProcessor.CACHE_AGE_IN_MS,
        cacheKeyMaxBytes, cacheValueMaxBytes);
  }

  @Provides
  SearchRootWarmup<EarlybirdService.ServiceIface, ?, ?> providesSearchRootWarmup(
      Clock clock,
      WarmupConfig config) {
    return new EarlybirdWarmup(clock, config);
  }

  @Provides
  public LoggingSupport<EarlybirdRequest, EarlybirdResponse> provideLoggingSupport(
      SearchDecider decider) {
    return new EarlybirdServiceLoggingSupport(decider);
  }

  @Provides
  public PartitionLoggingSupport<EarlybirdRequestContext> providePartitionLoggingSupport() {
    return new EarlybirdServicePartitionLoggingSupport();
  }

  @Provides
  public ValidationBehavior<EarlybirdRequest, EarlybirdResponse> provideValidationBehavior() {
    return new EarlybirdServiceValidationBehavior();
  }
}

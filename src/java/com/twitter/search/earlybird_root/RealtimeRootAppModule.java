package com.twitter.search.earlybird_root;

import javatwitter.inject.Named;
import javatwitter.inject.Singleton;

import com.google.inject.Key;
import com.google.inject.Provides;

import com.twitter.common.util.Clock;
import com.twitter.finagle.memcached.JavaClient;
import com.twitter.inject.TwitterModule;
import com.twitter.search.common.caching.Cache;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.common.root.LoggingSupport;
import com.twitter.search.common.root.PartitionLoggingSupport;
import com.twitter.search.common.root.SearchRootModule;
import com.twitter.search.common.root.SearchRootWarmup;
import com.twitter.search.common.root.ValidationBehavior;
import com.twitter.search.common.root.WarmupConfig;
import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird.thrift.EarlybirdService;
import com.twitter.search.earlybird_root.caching.DefaultForcedCacheMissDecider;
import com.twitter.search.earlybird_root.caching.FacetsCache;
import com.twitter.search.earlybird_root.caching.RecencyCache;
import com.twitter.search.earlybird_root.caching.RelevanceCache;
import com.twitter.search.earlybird_root.caching.StrictRecencyCache;
import com.twitter.search.earlybird_root.caching.TermStatsCache;
import com.twitter.search.earlybird_root.caching.TopTweetsCache;
import com.twitter.search.earlybird_root.caching.TopTweetsServicePostProcessor;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;

public class RealtimeRootAppModule etwittertends TwitterModule {
  private static final long RECENCY_CACHE_TTL_MILLIS = 20000L;
  private static final long RELEVANCE_CACHE_TTL_MILLIS = 20000L;
  private static final long FACETS_CACHE_TTL_MILLIS = 300000L;
  private static final long TERMSTATS_CACHE_TTL_MILLIS = 300000L;

  @Override
  public void configure() {
    bind(Key.get(EarlybirdCluster.class)).toInstance(EarlybirdCluster.REALTIME);

    bind(EarlybirdServiceScatterGatherSupport.class)
      .to(EarlybirdRealtimeScatterGatherSupport.class);

    bind(EarlybirdService.ServiceIface.class).to(RealtimeRootService.class);
  }

  @Provides
  @Singleton
  @RecencyCache
  Cache<EarlybirdRequest, EarlybirdResponse> provideRecencyCache(
      JavaClient client,
      DefaultForcedCacheMissDecider decider,
      @Named(SearchRootModule.NAMED_SERIALIZED_KEY_PREFItwitter) String serializedKeyPrefitwitter,
      @Named(SearchRootModule.NAMED_CACHE_KEY_MAtwitter_BYTES) int cacheKeyMatwitterBytes,
      @Named(SearchRootModule.NAMED_CACHE_VALUE_MAtwitter_BYTES) int cacheValueMatwitterBytes) {
    return EarlybirdCacheCommonModule.createCache(client, decider, "realtime_recency_root",
        serializedKeyPrefitwitter, RECENCY_CACHE_TTL_MILLIS, cacheKeyMatwitterBytes, cacheValueMatwitterBytes);
  }

  @Provides
  @Singleton
  @RelevanceCache
  Cache<EarlybirdRequest, EarlybirdResponse> provideRelevanceCache(
      JavaClient client,
      DefaultForcedCacheMissDecider decider,
      @Named(SearchRootModule.NAMED_SERIALIZED_KEY_PREFItwitter) String serializedKeyPrefitwitter,
      @Named(SearchRootModule.NAMED_CACHE_KEY_MAtwitter_BYTES) int cacheKeyMatwitterBytes,
      @Named(SearchRootModule.NAMED_CACHE_VALUE_MAtwitter_BYTES) int cacheValueMatwitterBytes) {
    return EarlybirdCacheCommonModule.createCache(client, decider, "realtime_relevance_root",
        serializedKeyPrefitwitter, RELEVANCE_CACHE_TTL_MILLIS, cacheKeyMatwitterBytes, cacheValueMatwitterBytes);
  }

  @Provides
  @Singleton
  @StrictRecencyCache
  Cache<EarlybirdRequest, EarlybirdResponse> provideStrictRecencyCache(
      JavaClient client,
      DefaultForcedCacheMissDecider decider,
      @Named(SearchRootModule.NAMED_SERIALIZED_KEY_PREFItwitter) String serializedKeyPrefitwitter,
      @Named(SearchRootModule.NAMED_CACHE_KEY_MAtwitter_BYTES) int cacheKeyMatwitterBytes,
      @Named(SearchRootModule.NAMED_CACHE_VALUE_MAtwitter_BYTES) int cacheValueMatwitterBytes) {
    return EarlybirdCacheCommonModule.createCache(client, decider, "realtime_strict_recency_root",
        serializedKeyPrefitwitter, RECENCY_CACHE_TTL_MILLIS, cacheKeyMatwitterBytes, cacheValueMatwitterBytes);
  }

  @Provides
  @Singleton
  @FacetsCache
  Cache<EarlybirdRequest, EarlybirdResponse> provideFacetsCache(
      JavaClient client,
      DefaultForcedCacheMissDecider decider,
      @Named(SearchRootModule.NAMED_SERIALIZED_KEY_PREFItwitter) String serializedKeyPrefitwitter,
      @Named(SearchRootModule.NAMED_CACHE_KEY_MAtwitter_BYTES) int cacheKeyMatwitterBytes,
      @Named(SearchRootModule.NAMED_CACHE_VALUE_MAtwitter_BYTES) int cacheValueMatwitterBytes) {
    return EarlybirdCacheCommonModule.createCache(client, decider, "realtime_facets_root",
        serializedKeyPrefitwitter, FACETS_CACHE_TTL_MILLIS, cacheKeyMatwitterBytes, cacheValueMatwitterBytes);
  }

  @Provides
  @Singleton
  @TermStatsCache
  Cache<EarlybirdRequest, EarlybirdResponse> provideTermStatsCache(
      JavaClient client,
      DefaultForcedCacheMissDecider decider,
      @Named(SearchRootModule.NAMED_SERIALIZED_KEY_PREFItwitter) String serializedKeyPrefitwitter,
      @Named(SearchRootModule.NAMED_CACHE_KEY_MAtwitter_BYTES) int cacheKeyMatwitterBytes,
      @Named(SearchRootModule.NAMED_CACHE_VALUE_MAtwitter_BYTES) int cacheValueMatwitterBytes) {
    return EarlybirdCacheCommonModule.createCache(client, decider, "realtime_termstats_root",
        serializedKeyPrefitwitter, TERMSTATS_CACHE_TTL_MILLIS, cacheKeyMatwitterBytes, cacheValueMatwitterBytes);
  }

  @Provides
  @Singleton
  @TopTweetsCache
  Cache<EarlybirdRequest, EarlybirdResponse> provideTopTweetsCache(
      JavaClient client,
      DefaultForcedCacheMissDecider decider,
      @Named(SearchRootModule.NAMED_SERIALIZED_KEY_PREFItwitter) String serializedKeyPrefitwitter,
      @Named(SearchRootModule.NAMED_CACHE_KEY_MAtwitter_BYTES) int cacheKeyMatwitterBytes,
      @Named(SearchRootModule.NAMED_CACHE_VALUE_MAtwitter_BYTES) int cacheValueMatwitterBytes) {
    return EarlybirdCacheCommonModule.createCache(client, decider, "realtime_toptweets_root",
        serializedKeyPrefitwitter, TopTweetsServicePostProcessor.CACHE_AGE_IN_MS,
        cacheKeyMatwitterBytes, cacheValueMatwitterBytes);
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
  public PartitionLoggingSupport<EarlybirdRequestContetwittert> providePartitionLoggingSupport() {
    return new EarlybirdServicePartitionLoggingSupport();
  }

  @Provides
  public ValidationBehavior<EarlybirdRequest, EarlybirdResponse> provideValidationBehavior() {
    return new EarlybirdServiceValidationBehavior();
  }
}

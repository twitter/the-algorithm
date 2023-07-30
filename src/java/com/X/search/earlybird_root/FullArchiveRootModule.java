package com.X.search.earlybird_root;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.inject.Singleton;

import com.google.inject.Key;
import com.google.inject.Provides;

import com.X.app.Flag;
import com.X.app.Flaggable;
import com.X.common.util.Clock;
import com.X.finagle.Service;
import com.X.finagle.memcached.JavaClient;
import com.X.finagle.stats.StatsReceiver;
import com.X.inject.XModule;
import com.X.search.common.caching.Cache;
import com.X.search.common.decider.SearchDecider;
import com.X.search.common.root.LoggingSupport;
import com.X.search.common.root.PartitionConfig;
import com.X.search.common.root.PartitionLoggingSupport;
import com.X.search.common.root.RootClientServiceBuilder;
import com.X.search.common.root.SearchRootModule;
import com.X.search.common.root.SearchRootWarmup;
import com.X.search.common.root.SplitterService;
import com.X.search.common.root.ValidationBehavior;
import com.X.search.common.root.WarmupConfig;
import com.X.search.common.schema.earlybird.EarlybirdCluster;
import com.X.search.earlybird.config.TierInfoSource;
import com.X.search.earlybird.thrift.EarlybirdRequest;
import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.search.earlybird.thrift.EarlybirdService;
import com.X.search.earlybird_root.caching.DefaultForcedCacheMissDecider;
import com.X.search.earlybird_root.caching.RecencyCache;
import com.X.search.earlybird_root.caching.RelevanceCache;
import com.X.search.earlybird_root.caching.StrictRecencyCache;
import com.X.search.earlybird_root.caching.TermStatsCache;
import com.X.search.earlybird_root.caching.TopTweetsCache;
import com.X.search.earlybird_root.caching.TopTweetsServicePostProcessor;
import com.X.search.earlybird_root.common.EarlybirdRequestContext;
import com.X.search.earlybird_root.filters.RequestContextToEarlybirdRequestFilter;
import com.X.util.Future;

import static com.X.search.earlybird_root.EarlybirdCommonModule.NAMED_ALT_CLIENT;

public class FullArchiveRootModule extends XModule {
  private static final String CLUSTER = "archive_full";
  private static final String ALT_TRAFFIC_PERCENTAGE_DECIDER_KEY =
      "full_archive_alt_client_traffic_percentage";

  private final Flag<Boolean> forceAltClientFlag = createFlag(
      "force_alt_client",
      false,
      "Always sends traffic to the alt client",
      Flaggable.ofJavaBoolean());

  @Override
  public void configure() {
    bind(Key.get(EarlybirdCluster.class)).toInstance(EarlybirdCluster.FULL_ARCHIVE);

    bind(EarlybirdServiceScatterGatherSupport.class)
      .to(EarlybirdFullArchiveScatterGatherSupport.class);

    bind(EarlybirdService.ServiceIface.class).to(FullArchiveRootService.class);
  }

  @Provides
  LoggingSupport<EarlybirdRequest, EarlybirdResponse> provideLoggingSupport(
      SearchDecider decider) {
    return new EarlybirdServiceLoggingSupport(decider);
  }

  @Provides
  PartitionLoggingSupport<EarlybirdRequestContext> providePartitionLoggingSupport() {
    return new EarlybirdServicePartitionLoggingSupport();
  }

  @Provides
  ValidationBehavior<EarlybirdRequest, EarlybirdResponse> provideValidationBehavior() {
    return new EarlybirdServiceValidationBehavior();
  }

  @Provides
  @Singleton
  @Nullable
  @Named(NAMED_ALT_CLIENT)
  EarlybirdServiceChainBuilder provideAltEarlybirdServiceChainBuilder(
      @Named(NAMED_ALT_CLIENT) @Nullable PartitionConfig altPartitionConfig,
      RequestContextToEarlybirdRequestFilter requestContextToEarlybirdRequestFilter,
      EarlybirdTierThrottleDeciders tierThrottleDeciders,
      @Named(SearchRootModule.NAMED_NORMALIZED_SEARCH_ROOT_NAME) String normalizedSearchRootName,
      SearchDecider decider,
      TierInfoSource tierConfig,
      @Named(NAMED_ALT_CLIENT) @Nullable
          RootClientServiceBuilder<EarlybirdService.ServiceIface> altRootClientServiceBuilder,
      PartitionAccessController partitionAccessController,
      StatsReceiver statsReceiver
  ) {
    if (altPartitionConfig == null || altRootClientServiceBuilder == null) {
      return null;
    }

    return new EarlybirdServiceChainBuilder(
        altPartitionConfig,
        requestContextToEarlybirdRequestFilter,
        tierThrottleDeciders,
        normalizedSearchRootName,
        decider,
        tierConfig,
        altRootClientServiceBuilder,
        partitionAccessController,
        statsReceiver
    );
  }

  @Provides
  @Singleton
  @Nullable
  @Named(NAMED_ALT_CLIENT)
  EarlybirdChainedScatterGatherService provideAltEarlybirdChainedScatterGatherService(
      @Named(NAMED_ALT_CLIENT) @Nullable EarlybirdServiceChainBuilder altServiceChainBuilder,
      EarlybirdServiceScatterGatherSupport scatterGatherSupport,
      PartitionLoggingSupport<EarlybirdRequestContext> partitionLoggingSupport
  ) {
    if (altServiceChainBuilder == null) {
      return null;
    }

    return new EarlybirdChainedScatterGatherService(
        altServiceChainBuilder,
        scatterGatherSupport,
        partitionLoggingSupport
    );
  }

  @Provides
  @Singleton
  Service<EarlybirdRequestContext, List<Future<EarlybirdResponse>>>
  provideEarlybirdChainedScatterGatherService(
      EarlybirdChainedScatterGatherService chainedScatterGatherService,
      @Named(NAMED_ALT_CLIENT) @Nullable
          EarlybirdChainedScatterGatherService altChainedScatterGatherService,
      SearchDecider decider
  ) {
    if (forceAltClientFlag.apply()) {
      if (altChainedScatterGatherService == null) {
        throw new RuntimeException(
            "alt client cannot be null when 'force_alt_client' is set to true");
      } else {
        return altChainedScatterGatherService;
      }
    }

    if (altChainedScatterGatherService == null) {
      return chainedScatterGatherService;
    }

    return new SplitterService<>(
        chainedScatterGatherService,
        altChainedScatterGatherService,
        decider,
        ALT_TRAFFIC_PERCENTAGE_DECIDER_KEY
    );
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
    return EarlybirdCacheCommonModule.createCache(client, decider, CLUSTER + "_recency_root",
        serializedKeyPrefix, TimeUnit.HOURS.toMillis(2), cacheKeyMaxBytes, cacheValueMaxBytes);
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
    return EarlybirdCacheCommonModule.createCache(client, decider, CLUSTER + "_relevance_root",
        serializedKeyPrefix, TimeUnit.HOURS.toMillis(2), cacheKeyMaxBytes, cacheValueMaxBytes);
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
    return EarlybirdCacheCommonModule.createCache(client, decider, CLUSTER + "_strict_recency_root",
        serializedKeyPrefix, TimeUnit.HOURS.toMillis(2), cacheKeyMaxBytes, cacheValueMaxBytes);
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
    return EarlybirdCacheCommonModule.createCache(client, decider, CLUSTER + "_termstats_root",
        serializedKeyPrefix, TimeUnit.MINUTES.toMillis(5), cacheKeyMaxBytes, cacheValueMaxBytes);
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
    return EarlybirdCacheCommonModule.createCache(client, decider, CLUSTER + "_toptweets_root",
        serializedKeyPrefix, TopTweetsServicePostProcessor.CACHE_AGE_IN_MS,
        cacheKeyMaxBytes, cacheValueMaxBytes);
  }

  @Provides
  SearchRootWarmup<EarlybirdService.ServiceIface, ?, ?> providesSearchRootWarmup(
      Clock clock,
      WarmupConfig config) {
    return new EarlybirdWarmup(clock, config);
  }
}

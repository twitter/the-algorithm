package com.twitter.search.earlybird_root;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javatwitter.annotation.Nullable;
import javatwitter.inject.Named;
import javatwitter.inject.Singleton;

import com.google.inject.Key;
import com.google.inject.Provides;

import com.twitter.app.Flag;
import com.twitter.app.Flaggable;
import com.twitter.common.util.Clock;
import com.twitter.finagle.Service;
import com.twitter.finagle.memcached.JavaClient;
import com.twitter.finagle.stats.StatsReceiver;
import com.twitter.inject.TwitterModule;
import com.twitter.search.common.caching.Cache;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.common.root.LoggingSupport;
import com.twitter.search.common.root.PartitionConfig;
import com.twitter.search.common.root.PartitionLoggingSupport;
import com.twitter.search.common.root.RootClientServiceBuilder;
import com.twitter.search.common.root.SearchRootModule;
import com.twitter.search.common.root.SearchRootWarmup;
import com.twitter.search.common.root.SplitterService;
import com.twitter.search.common.root.ValidationBehavior;
import com.twitter.search.common.root.WarmupConfig;
import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.earlybird.config.TierInfoSource;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird.thrift.EarlybirdService;
import com.twitter.search.earlybird_root.caching.DefaultForcedCacheMissDecider;
import com.twitter.search.earlybird_root.caching.RecencyCache;
import com.twitter.search.earlybird_root.caching.RelevanceCache;
import com.twitter.search.earlybird_root.caching.StrictRecencyCache;
import com.twitter.search.earlybird_root.caching.TermStatsCache;
import com.twitter.search.earlybird_root.caching.TopTweetsCache;
import com.twitter.search.earlybird_root.caching.TopTweetsServicePostProcessor;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;
import com.twitter.search.earlybird_root.filters.RequestContetwittertToEarlybirdRequestFilter;
import com.twitter.util.Future;

import static com.twitter.search.earlybird_root.EarlybirdCommonModule.NAMED_ALT_CLIENT;

public class FullArchiveRootModule etwittertends TwitterModule {
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
  PartitionLoggingSupport<EarlybirdRequestContetwittert> providePartitionLoggingSupport() {
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
      RequestContetwittertToEarlybirdRequestFilter requestContetwittertToEarlybirdRequestFilter,
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
        requestContetwittertToEarlybirdRequestFilter,
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
      PartitionLoggingSupport<EarlybirdRequestContetwittert> partitionLoggingSupport
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
  Service<EarlybirdRequestContetwittert, List<Future<EarlybirdResponse>>>
  provideEarlybirdChainedScatterGatherService(
      EarlybirdChainedScatterGatherService chainedScatterGatherService,
      @Named(NAMED_ALT_CLIENT) @Nullable
          EarlybirdChainedScatterGatherService altChainedScatterGatherService,
      SearchDecider decider
  ) {
    if (forceAltClientFlag.apply()) {
      if (altChainedScatterGatherService == null) {
        throw new RuntimeEtwitterception(
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
      @Named(SearchRootModule.NAMED_SERIALIZED_KEY_PREFItwitter) String serializedKeyPrefitwitter,
      @Named(SearchRootModule.NAMED_CACHE_KEY_MAtwitter_BYTES) int cacheKeyMatwitterBytes,
      @Named(SearchRootModule.NAMED_CACHE_VALUE_MAtwitter_BYTES) int cacheValueMatwitterBytes) {
    return EarlybirdCacheCommonModule.createCache(client, decider, CLUSTER + "_recency_root",
        serializedKeyPrefitwitter, TimeUnit.HOURS.toMillis(2), cacheKeyMatwitterBytes, cacheValueMatwitterBytes);
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
    return EarlybirdCacheCommonModule.createCache(client, decider, CLUSTER + "_relevance_root",
        serializedKeyPrefitwitter, TimeUnit.HOURS.toMillis(2), cacheKeyMatwitterBytes, cacheValueMatwitterBytes);
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
    return EarlybirdCacheCommonModule.createCache(client, decider, CLUSTER + "_strict_recency_root",
        serializedKeyPrefitwitter, TimeUnit.HOURS.toMillis(2), cacheKeyMatwitterBytes, cacheValueMatwitterBytes);
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
    return EarlybirdCacheCommonModule.createCache(client, decider, CLUSTER + "_termstats_root",
        serializedKeyPrefitwitter, TimeUnit.MINUTES.toMillis(5), cacheKeyMatwitterBytes, cacheValueMatwitterBytes);
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
    return EarlybirdCacheCommonModule.createCache(client, decider, CLUSTER + "_toptweets_root",
        serializedKeyPrefitwitter, TopTweetsServicePostProcessor.CACHE_AGE_IN_MS,
        cacheKeyMatwitterBytes, cacheValueMatwitterBytes);
  }

  @Provides
  SearchRootWarmup<EarlybirdService.ServiceIface, ?, ?> providesSearchRootWarmup(
      Clock clock,
      WarmupConfig config) {
    return new EarlybirdWarmup(clock, config);
  }
}

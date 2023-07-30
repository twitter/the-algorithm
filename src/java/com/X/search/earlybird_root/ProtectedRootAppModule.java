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
import com.X.search.earlybird_root.caching.RecencyCache;
import com.X.search.earlybird_root.common.EarlybirdRequestContext;

public class ProtectedRootAppModule extends XModule {
  @Override
  public void configure() {
    bind(Key.get(EarlybirdCluster.class)).toInstance(EarlybirdCluster.PROTECTED);

    bind(EarlybirdServiceScatterGatherSupport.class)
        .to(EarlybirdProtectedScatterGatherSupport.class);

    bind(EarlybirdService.ServiceIface.class).to(ProtectedRootService.class);
  }

  @Provides
  @Singleton
  LoggingSupport<EarlybirdRequest, EarlybirdResponse> provideLoggingSupport(
      SearchDecider decider) {
    return new EarlybirdServiceLoggingSupport(decider);
  }

  @Provides
  @Singleton
  PartitionLoggingSupport<EarlybirdRequestContext> providePartitionLoggingSupport() {
    return new EarlybirdServicePartitionLoggingSupport();
  }

  @Provides
  @Singleton
  ValidationBehavior<EarlybirdRequest, EarlybirdResponse> providesValidation() {
    return new EarlybirdProtectedValidationBehavior();
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
    return EarlybirdCacheCommonModule
        .createCache(client, decider, "realtime_protected_recency_root", serializedKeyPrefix,
            20000L, cacheKeyMaxBytes, cacheValueMaxBytes);
  }

  @Provides
  SearchRootWarmup<EarlybirdService.ServiceIface, ?, ?> providesSearchRootWarmup(
      Clock clock,
      WarmupConfig config) {
    return new EarlybirdProtectedWarmup(clock, config);
  }
}

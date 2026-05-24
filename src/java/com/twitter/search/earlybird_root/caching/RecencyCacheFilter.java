package com.twitter.search.earlybird_root.caching;

import javax.inject.Inject;
import javax.inject.Named;

import com.twitter.search.common.caching.Cache;
import com.twitter.search.common.caching.filter.CacheFilter;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.common.root.SearchRootModule;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContext;
import com.twitter.search.earlybird_root.common.EarlybirdRequestType;

public class RecencyCacheFilter extends
    CacheFilter<EarlybirdRequestContext, EarlybirdRequest, EarlybirdResponse> {
  /**
   * Creates a cache filter for earlybird recency requests.
   */
  @Inject
  public RecencyCacheFilter(
      @RecencyCache Cache<EarlybirdRequest, EarlybirdResponse> cache,
      SearchDecider decider,
      @Named(SearchRootModule.NAMED_NORMALIZED_SEARCH_ROOT_NAME) String normalizedSearchRootName,
      @Named(CacheCommonUtil.NAMED_MAX_CACHE_RESULTS) int maxCacheResults) {
    super(cache,
          new RecencyQueryCachePredicate(decider, normalizedSearchRootName),
          new RecencyCacheRequestNormalizer(),
          new RecencyAndRelevanceCachePostProcessor(),
          new RecencyServicePostProcessor(cache, maxCacheResults),
          new EarlybirdRequestPerClientCacheStats(
              EarlybirdRequestType.RECENCY.getNormalizedName()));
  }
}

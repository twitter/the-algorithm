package com.twitter.search.earlybird_root.caching;

import javatwitter.inject.Inject;
import javatwitter.inject.Named;

import com.twitter.search.common.caching.Cache;
import com.twitter.search.common.caching.filter.CacheFilter;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.common.root.SearchRootModule;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;
import com.twitter.search.earlybird_root.common.EarlybirdRequestType;

public class StrictRecencyCacheFilter etwittertends
    CacheFilter<EarlybirdRequestContetwittert, EarlybirdRequest, EarlybirdResponse> {
  /**
   * Creates a cache filter for earlybird strict recency requests.
   */
  @Inject
  public StrictRecencyCacheFilter(
      @StrictRecencyCache Cache<EarlybirdRequest, EarlybirdResponse> cache,
      SearchDecider decider,
      @Named(SearchRootModule.NAMED_NORMALIZED_SEARCH_ROOT_NAME) String normalizedSearchRootName,
      @Named(CacheCommonUtil.NAMED_MAtwitter_CACHE_RESULTS) int matwitterCacheResults) {
    super(cache,
          new StrictRecencyQueryCachePredicate(decider, normalizedSearchRootName),
          new RecencyCacheRequestNormalizer(),
          new RecencyAndRelevanceCachePostProcessor(),
          new RecencyServicePostProcessor(cache, matwitterCacheResults),
          new EarlybirdRequestPerClientCacheStats(
              EarlybirdRequestType.STRICT_RECENCY.getNormalizedName()));
  }
}

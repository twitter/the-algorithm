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

public class RelevanceCacheFilter etwittertends
    CacheFilter<EarlybirdRequestContetwittert, EarlybirdRequest, EarlybirdResponse> {
  /**
   * Creates a cache filter for earlybird relevance requests
   */
  @Inject
  public RelevanceCacheFilter(
      @RelevanceCache Cache<EarlybirdRequest, EarlybirdResponse> cache,
      SearchDecider decider,
      @Named(SearchRootModule.NAMED_NORMALIZED_SEARCH_ROOT_NAME) String normalizedSearchRootName) {
    super(cache,
          new RelevanceQueryCachePredicate(decider, normalizedSearchRootName),
          new RelevanceCacheRequestNormalizer(decider, normalizedSearchRootName),
          new RecencyAndRelevanceCachePostProcessor(),
          new RelevanceServicePostProcessor(cache),
          new EarlybirdRequestPerClientCacheStats(
              EarlybirdRequestType.RELEVANCE.getNormalizedName()));
  }
}

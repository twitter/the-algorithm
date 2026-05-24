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

public class FacetsCacheFilter extends
    CacheFilter<EarlybirdRequestContext, EarlybirdRequest, EarlybirdResponse> {
  /**
   * Constructs a new cache filter for facet requests.
   */
  @Inject
  public FacetsCacheFilter(
      @FacetsCache Cache<EarlybirdRequest, EarlybirdResponse> cache,
      SearchDecider decider,
      @Named(SearchRootModule.NAMED_NORMALIZED_SEARCH_ROOT_NAME) String normalizedSearchRootName) {
    super(cache,
          new FacetsQueryCachePredicate(decider, normalizedSearchRootName),
          new FacetsCacheRequestNormalizer(),
          new EarlybirdCachePostProcessor(),
          new FacetsServicePostProcessor(cache),
          new EarlybirdRequestPerClientCacheStats(EarlybirdRequestType.FACETS.getNormalizedName()));
  }
}

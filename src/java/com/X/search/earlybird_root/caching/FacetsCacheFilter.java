package com.X.search.earlybird_root.caching;

import javax.inject.Inject;
import javax.inject.Named;

import com.X.search.common.caching.Cache;
import com.X.search.common.caching.filter.CacheFilter;
import com.X.search.common.decider.SearchDecider;
import com.X.search.common.root.SearchRootModule;
import com.X.search.earlybird.thrift.EarlybirdRequest;
import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.search.earlybird_root.common.EarlybirdRequestContext;
import com.X.search.earlybird_root.common.EarlybirdRequestType;

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

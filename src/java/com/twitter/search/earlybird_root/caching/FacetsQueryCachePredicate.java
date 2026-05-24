package com.twitter.search.earlybird_root.caching;

import com.twitter.search.common.caching.filter.QueryCachePredicate;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.earlybird.common.EarlybirdRequestUtil;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContext;
import com.twitter.search.earlybird_root.common.EarlybirdRequestType;

public class FacetsQueryCachePredicate extends QueryCachePredicate<EarlybirdRequestContext> {
  private final SearchDecider decider;
  private final String facetsCacheEnabledDeciderKey;

  public FacetsQueryCachePredicate(SearchDecider decider, String normalizedSearchRootName) {
    this.decider = decider;
    this.facetsCacheEnabledDeciderKey = "facets_cache_enabled_" + normalizedSearchRootName;
  }

  @Override
  public Boolean shouldQueryCache(EarlybirdRequestContext requestContext) {
    return EarlybirdRequestType.FACETS == requestContext.getEarlybirdRequestType()
        && EarlybirdRequestUtil.isCachingAllowed(requestContext.getRequest())
        && decider.isAvailable(facetsCacheEnabledDeciderKey);
  }
}

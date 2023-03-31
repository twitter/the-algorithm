package com.twitter.search.earlybird_root.caching;

import com.twitter.search.common.caching.filter.QueryCachePredicate;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.earlybird.common.EarlybirdRequestUtil;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContext;
import com.twitter.search.earlybird_root.common.EarlybirdRequestType;

public class RecencyQueryCachePredicate extends QueryCachePredicate<EarlybirdRequestContext> {
  private final SearchDecider decider;
  private final String recencyCacheEnabledDeciderKey;

  public RecencyQueryCachePredicate(SearchDecider decider, String normalizedSearchRootName) {
    this.decider = decider;
    this.recencyCacheEnabledDeciderKey = "recency_cache_enabled_" + normalizedSearchRootName;
  }

  @Override
  public Boolean shouldQueryCache(EarlybirdRequestContext request) {
    return EarlybirdRequestType.RECENCY == request.getEarlybirdRequestType()
        && EarlybirdRequestUtil.isCachingAllowed(request.getRequest())
        && decider.isAvailable(recencyCacheEnabledDeciderKey);
  }
}

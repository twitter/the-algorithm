package com.X.search.earlybird_root.caching;

import com.X.search.common.caching.filter.QueryCachePredicate;
import com.X.search.common.decider.SearchDecider;
import com.X.search.earlybird.common.EarlybirdRequestUtil;
import com.X.search.earlybird_root.common.EarlybirdRequestContext;
import com.X.search.earlybird_root.common.EarlybirdRequestType;

public class StrictRecencyQueryCachePredicate extends QueryCachePredicate<EarlybirdRequestContext> {
  private final SearchDecider decider;
  private final String strictRecencyCacheEnabledDeciderKey;

  public StrictRecencyQueryCachePredicate(SearchDecider decider, String normalizedSearchRootName) {
    this.decider = decider;
    this.strictRecencyCacheEnabledDeciderKey =
        "strict_recency_cache_enabled_" + normalizedSearchRootName;
  }

  @Override
  public Boolean shouldQueryCache(EarlybirdRequestContext requestContext) {
    return EarlybirdRequestType.STRICT_RECENCY == requestContext.getEarlybirdRequestType()
        && EarlybirdRequestUtil.isCachingAllowed(requestContext.getRequest())
        && decider.isAvailable(strictRecencyCacheEnabledDeciderKey);
  }
}

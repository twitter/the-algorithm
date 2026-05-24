package com.twitter.search.earlybird_root.caching;

import com.twitter.search.common.caching.filter.QueryCachePredicate;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.earlybird.common.EarlybirdRequestUtil;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContext;
import com.twitter.search.earlybird_root.common.EarlybirdRequestType;

public class TermStatsQueryCachePredicate extends QueryCachePredicate<EarlybirdRequestContext> {
  private final SearchDecider decider;
  private final String termstatsCacheEnabledDeciderKey;

  public TermStatsQueryCachePredicate(SearchDecider decider, String normalizedSearchRootName) {
    this.decider = decider;
    this.termstatsCacheEnabledDeciderKey = "termstats_cache_enabled_" + normalizedSearchRootName;
  }

  @Override
  public Boolean shouldQueryCache(EarlybirdRequestContext requestContext) {
    return EarlybirdRequestType.TERM_STATS == requestContext.getEarlybirdRequestType()
        && EarlybirdRequestUtil.isCachingAllowed(requestContext.getRequest())
        && decider.isAvailable(termstatsCacheEnabledDeciderKey);
  }
}

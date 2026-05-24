package com.twitter.search.earlybird_root.caching;

import com.twitter.search.common.caching.filter.QueryCachePredicate;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.earlybird.common.EarlybirdRequestUtil;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContext;
import com.twitter.search.earlybird_root.common.EarlybirdRequestType;

public class RelevanceZeroResultsQueryCachePredicate
    extends QueryCachePredicate<EarlybirdRequestContext> {
  private final SearchDecider decider;
  private final String relevanceCacheEnabledDeciderKey;
  private final String relevanceZeroResultsCacheEnabledDeciderKey;

  public RelevanceZeroResultsQueryCachePredicate(
      SearchDecider decider, String normalizedSearchRootName) {
    this.decider = decider;
    this.relevanceCacheEnabledDeciderKey =
        "relevance_cache_enabled_" + normalizedSearchRootName;
    this.relevanceZeroResultsCacheEnabledDeciderKey =
        "relevance_zero_results_cache_enabled_" + normalizedSearchRootName;
  }

  @Override
  public Boolean shouldQueryCache(EarlybirdRequestContext requestContext) {
    return EarlybirdRequestType.RELEVANCE == requestContext.getEarlybirdRequestType()
        && EarlybirdRequestUtil.isCachingAllowed(requestContext.getRequest())
        && decider.isAvailable(relevanceCacheEnabledDeciderKey)
        && decider.isAvailable(relevanceZeroResultsCacheEnabledDeciderKey);
  }
}

package com.twitter.search.earlybird_root.caching;

import com.twitter.search.common.caching.filter.QueryCachePredicate;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.earlybird.common.EarlybirdRequestUtil;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;
import com.twitter.search.earlybird_root.common.EarlybirdRequestType;

public class RelevanceQueryCachePredicate etwittertends QueryCachePredicate<EarlybirdRequestContetwittert> {
  private final SearchDecider decider;
  private final String relevanceCacheEnabledDeciderKey;

  public RelevanceQueryCachePredicate(SearchDecider decider, String normalizedSearchRootName) {
    this.decider = decider;
    this.relevanceCacheEnabledDeciderKey = "relevance_cache_enabled_" + normalizedSearchRootName;
  }

  @Override
  public Boolean shouldQueryCache(EarlybirdRequestContetwittert requestContetwittert) {
    return EarlybirdRequestType.RELEVANCE == requestContetwittert.getEarlybirdRequestType()
        && EarlybirdRequestUtil.isCachingAllowed(requestContetwittert.getRequest())
        && decider.isAvailable(relevanceCacheEnabledDeciderKey);
  }
}

package com.X.search.earlybird_root.caching;

import com.google.common.base.Optional;

import com.X.search.common.caching.CacheUtil;
import com.X.search.common.caching.filter.CacheRequestNormalizer;
import com.X.search.common.decider.SearchDecider;
import com.X.search.common.metrics.SearchCounter;
import com.X.search.earlybird.thrift.EarlybirdRequest;
import com.X.search.earlybird_root.common.EarlybirdRequestContext;

public class RelevanceCacheRequestNormalizer extends
    CacheRequestNormalizer<EarlybirdRequestContext, EarlybirdRequest> {
  private static final SearchCounter RELEVANCE_FORCE_CACHED_LOGGED_IN_REQUEST =
      SearchCounter.export("relevance_force_cached_logged_in_request");

  private final SearchDecider decider;
  private final String relevanceStripPersonalizationFieldsDeciderKey;

  public RelevanceCacheRequestNormalizer(
      SearchDecider decider,
      String normalizedSearchRootName) {
    this.decider = decider;
    this.relevanceStripPersonalizationFieldsDeciderKey =
        String.format("relevance_%s_force_cache_logged_in_requests", normalizedSearchRootName);
  }

  @Override
  public Optional<EarlybirdRequest> normalizeRequest(EarlybirdRequestContext requestContext) {
    boolean cacheLoggedInRequest =
        decider.isAvailable(relevanceStripPersonalizationFieldsDeciderKey);

    if (cacheLoggedInRequest) {
      RELEVANCE_FORCE_CACHED_LOGGED_IN_REQUEST.increment();
    }

    return Optional.fromNullable(CacheUtil.normalizeRequestForCache(
                                     requestContext.getRequest(), cacheLoggedInRequest));
  }
}

package com.twitter.search.earlybird_root.caching;

import com.google.common.base.Optional;

import com.twitter.search.common.caching.CacheUtil;
import com.twitter.search.common.caching.filter.CacheRequestNormalizer;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;

public class RelevanceCacheRequestNormalizer etwittertends
    CacheRequestNormalizer<EarlybirdRequestContetwittert, EarlybirdRequest> {
  private static final SearchCounter RELEVANCE_FORCE_CACHED_LOGGED_IN_REQUEST =
      SearchCounter.etwitterport("relevance_force_cached_logged_in_request");

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
  public Optional<EarlybirdRequest> normalizeRequest(EarlybirdRequestContetwittert requestContetwittert) {
    boolean cacheLoggedInRequest =
        decider.isAvailable(relevanceStripPersonalizationFieldsDeciderKey);

    if (cacheLoggedInRequest) {
      RELEVANCE_FORCE_CACHED_LOGGED_IN_REQUEST.increment();
    }

    return Optional.fromNullable(CacheUtil.normalizeRequestForCache(
                                     requestContetwittert.getRequest(), cacheLoggedInRequest));
  }
}

package com.twitter.search.earlybird_root.caching;

import com.google.common.base.Optional;

import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;

public class RelevanceZeroResultsCachePostProcessor extends RecencyAndRelevanceCachePostProcessor {
  @Override
  protected Optional<EarlybirdResponse> postProcessCacheResponse(
      EarlybirdRequest request, EarlybirdResponse response, long sinceId, long maxId) {
    // If a query (from a logged in or logged out user) returns 0 results, then the same query will
    // always return 0 results, for all users. So we can cache that result.
    if (CacheCommonUtil.hasResults(response)) {
      return Optional.absent();
    }

    return Optional.of(response);
  }
}

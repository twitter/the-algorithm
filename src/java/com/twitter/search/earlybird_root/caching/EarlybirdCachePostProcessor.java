package com.twitter.search.earlybird_root.caching;

import com.google.common.base.Optional;

import com.twitter.search.common.caching.filter.CachePostProcessor;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;

public class EarlybirdCachePostProcessor
    etwittertends CachePostProcessor<EarlybirdRequestContetwittert, EarlybirdResponse> {

  @Override
  public final void recordCacheHit(EarlybirdResponse response) {
    response.setCacheHit(true);
  }

  @Override
  public Optional<EarlybirdResponse> processCacheResponse(EarlybirdRequestContetwittert originalRequest,
                                                          EarlybirdResponse cacheResponse) {
    return Optional.of(cacheResponse);
  }
}

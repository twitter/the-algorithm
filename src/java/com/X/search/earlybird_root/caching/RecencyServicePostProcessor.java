package com.X.search.earlybird_root.caching;

import com.X.search.common.caching.Cache;
import com.X.search.common.caching.CacheUtil;
import com.X.search.common.caching.filter.ServicePostProcessor;
import com.X.search.earlybird.thrift.EarlybirdRequest;
import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.search.earlybird_root.common.EarlybirdRequestContext;

public class RecencyServicePostProcessor
    extends ServicePostProcessor<EarlybirdRequestContext, EarlybirdResponse> {
  private final Cache<EarlybirdRequest, EarlybirdResponse> cache;
  private final int maxCacheResults;

  public RecencyServicePostProcessor(
      Cache<EarlybirdRequest, EarlybirdResponse> cache,
      int maxCacheResults) {
    this.cache = cache;
    this.maxCacheResults = maxCacheResults;
  }

  @Override
  public void processServiceResponse(EarlybirdRequestContext requestContext,
                                     EarlybirdResponse serviceResponse) {
    CacheUtil.cacheResults(cache, requestContext.getRequest(), serviceResponse, maxCacheResults);
  }
}

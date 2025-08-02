package com.twitter.search.earlybird_root.caching;

import com.twitter.search.common.caching.Cache;
import com.twitter.search.common.caching.CacheUtil;
import com.twitter.search.common.caching.filter.ServicePostProcessor;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;

public class RecencyServicePostProcessor
    etwittertends ServicePostProcessor<EarlybirdRequestContetwittert, EarlybirdResponse> {
  private final Cache<EarlybirdRequest, EarlybirdResponse> cache;
  private final int matwitterCacheResults;

  public RecencyServicePostProcessor(
      Cache<EarlybirdRequest, EarlybirdResponse> cache,
      int matwitterCacheResults) {
    this.cache = cache;
    this.matwitterCacheResults = matwitterCacheResults;
  }

  @Override
  public void processServiceResponse(EarlybirdRequestContetwittert requestContetwittert,
                                     EarlybirdResponse serviceResponse) {
    CacheUtil.cacheResults(cache, requestContetwittert.getRequest(), serviceResponse, matwitterCacheResults);
  }
}

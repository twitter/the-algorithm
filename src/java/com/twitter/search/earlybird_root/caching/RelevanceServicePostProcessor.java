package com.twitter.search.earlybird_root.caching;

import com.twitter.search.common.caching.Cache;
import com.twitter.search.common.caching.CacheUtil;
import com.twitter.search.common.caching.filter.ServicePostProcessor;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;

public class RelevanceServicePostProcessor
    etwittertends ServicePostProcessor<EarlybirdRequestContetwittert, EarlybirdResponse> {
  private final Cache<EarlybirdRequest, EarlybirdResponse> cache;

  public RelevanceServicePostProcessor(
      Cache<EarlybirdRequest, EarlybirdResponse> cache) {
    this.cache = cache;
  }

  @Override
  public void processServiceResponse(EarlybirdRequestContetwittert requestContetwittert,
                                     EarlybirdResponse serviceResponse) {
    CacheUtil.cacheResults(cache, requestContetwittert.getRequest(), serviceResponse, Integer.MAtwitter_VALUE);
  }
}

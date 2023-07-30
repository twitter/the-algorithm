package com.X.search.earlybird_root.caching;

import com.google.common.base.Preconditions;

import com.X.search.common.caching.Cache;
import com.X.search.common.caching.TermStatsCacheUtil;
import com.X.search.common.caching.filter.ServicePostProcessor;
import com.X.search.earlybird.thrift.EarlybirdRequest;
import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.search.earlybird_root.common.EarlybirdRequestContext;

public class TermStatsServicePostProcessor
    extends ServicePostProcessor<EarlybirdRequestContext, EarlybirdResponse> {
  private final Cache<EarlybirdRequest, EarlybirdResponse> cache;

  public TermStatsServicePostProcessor(Cache<EarlybirdRequest, EarlybirdResponse> cache) {
    this.cache = Preconditions.checkNotNull(cache);
  }

  @Override
  public void processServiceResponse(EarlybirdRequestContext requestContext,
                                     EarlybirdResponse serviceResponse) {
    TermStatsCacheUtil.cacheResults(cache, requestContext.getRequest(), serviceResponse);
  }
}

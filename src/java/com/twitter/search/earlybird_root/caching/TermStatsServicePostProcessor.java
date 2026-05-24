package com.twitter.search.earlybird_root.caching;

import com.google.common.base.Preconditions;

import com.twitter.search.common.caching.Cache;
import com.twitter.search.common.caching.TermStatsCacheUtil;
import com.twitter.search.common.caching.filter.ServicePostProcessor;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContext;

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

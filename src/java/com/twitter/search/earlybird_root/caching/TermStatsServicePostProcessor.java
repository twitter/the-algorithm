package com.twitter.search.earlybird_root.caching;

import com.google.common.base.Preconditions;

import com.twitter.search.common.caching.Cache;
import com.twitter.search.common.caching.TermStatsCacheUtil;
import com.twitter.search.common.caching.filter.ServicePostProcessor;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;

public class TermStatsServicePostProcessor
    etwittertends ServicePostProcessor<EarlybirdRequestContetwittert, EarlybirdResponse> {
  private final Cache<EarlybirdRequest, EarlybirdResponse> cache;

  public TermStatsServicePostProcessor(Cache<EarlybirdRequest, EarlybirdResponse> cache) {
    this.cache = Preconditions.checkNotNull(cache);
  }

  @Override
  public void processServiceResponse(EarlybirdRequestContetwittert requestContetwittert,
                                     EarlybirdResponse serviceResponse) {
    TermStatsCacheUtil.cacheResults(cache, requestContetwittert.getRequest(), serviceResponse);
  }
}

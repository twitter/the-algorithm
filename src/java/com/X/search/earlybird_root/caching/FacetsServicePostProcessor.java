package com.X.search.earlybird_root.caching;

import com.X.search.common.caching.Cache;
import com.X.search.common.caching.FacetsCacheUtil;
import com.X.search.common.caching.filter.ServicePostProcessor;
import com.X.search.earlybird.thrift.EarlybirdRequest;
import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.search.earlybird_root.common.EarlybirdRequestContext;

public class FacetsServicePostProcessor
    extends ServicePostProcessor<EarlybirdRequestContext, EarlybirdResponse> {

  private final Cache<EarlybirdRequest, EarlybirdResponse> cache;

  public FacetsServicePostProcessor(Cache<EarlybirdRequest, EarlybirdResponse> cache) {
    this.cache = cache;
  }

  @Override
  public void processServiceResponse(EarlybirdRequestContext requestContext,
                                     EarlybirdResponse serviceResponse) {
    FacetsCacheUtil.cacheResults(requestContext.getRequest(), serviceResponse, cache);
  }
}

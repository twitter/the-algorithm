package com.X.search.earlybird_root.caching;

import com.google.common.base.Optional;

import com.X.search.common.caching.CacheUtil;
import com.X.search.common.caching.filter.CacheRequestNormalizer;
import com.X.search.earlybird.thrift.EarlybirdRequest;
import com.X.search.earlybird_root.common.EarlybirdRequestContext;

public class RecencyCacheRequestNormalizer extends
    CacheRequestNormalizer<EarlybirdRequestContext, EarlybirdRequest> {
  @Override
  public Optional<EarlybirdRequest> normalizeRequest(EarlybirdRequestContext requestContext) {
    return Optional.fromNullable(CacheUtil.normalizeRequestForCache(requestContext.getRequest()));
  }
}

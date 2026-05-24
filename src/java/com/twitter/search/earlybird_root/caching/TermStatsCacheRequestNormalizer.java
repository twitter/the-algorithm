package com.twitter.search.earlybird_root.caching;

import com.google.common.base.Optional;

import com.twitter.search.common.caching.TermStatsCacheUtil;
import com.twitter.search.common.caching.filter.CacheRequestNormalizer;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContext;

public class TermStatsCacheRequestNormalizer extends
    CacheRequestNormalizer<EarlybirdRequestContext, EarlybirdRequest> {

  @Override
  public Optional<EarlybirdRequest> normalizeRequest(EarlybirdRequestContext requestContext) {
    return Optional.fromNullable(TermStatsCacheUtil.normalizeForCache(requestContext.getRequest()));
  }
}

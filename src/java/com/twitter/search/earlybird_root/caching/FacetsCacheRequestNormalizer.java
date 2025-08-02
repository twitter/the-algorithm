package com.twitter.search.earlybird_root.caching;

import com.google.common.base.Optional;

import com.twitter.search.common.caching.FacetsCacheUtil;
import com.twitter.search.common.caching.filter.CacheRequestNormalizer;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;

public class FacetsCacheRequestNormalizer etwittertends
    CacheRequestNormalizer<EarlybirdRequestContetwittert, EarlybirdRequest> {

  @Override
  public Optional<EarlybirdRequest> normalizeRequest(EarlybirdRequestContetwittert requestContetwittert) {
    return Optional.fromNullable(FacetsCacheUtil.normalizeRequestForCache(
        requestContetwittert.getRequest()));
  }
}

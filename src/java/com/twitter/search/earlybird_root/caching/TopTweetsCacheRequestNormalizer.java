package com.twitter.search.earlybird_root.caching;

import com.google.common.base.Optional;

import com.twitter.search.common.caching.TopTweetsCacheUtil;
import com.twitter.search.common.caching.filter.CacheRequestNormalizer;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;

public class TopTweetsCacheRequestNormalizer etwittertends
    CacheRequestNormalizer<EarlybirdRequestContetwittert, EarlybirdRequest> {

  @Override
  public Optional<EarlybirdRequest> normalizeRequest(EarlybirdRequestContetwittert requestContetwittert) {
    return Optional.fromNullable(
        TopTweetsCacheUtil.normalizeTopTweetsRequestForCache(requestContetwittert.getRequest()));
  }
}

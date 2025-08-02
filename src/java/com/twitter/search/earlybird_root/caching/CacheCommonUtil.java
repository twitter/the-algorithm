package com.twitter.search.earlybird_root.caching;

import com.twitter.search.earlybird.thrift.EarlybirdResponse;

public final class CacheCommonUtil {
  public static final String NAMED_MAtwitter_CACHE_RESULTS = "matwitterCacheResults";

  private CacheCommonUtil() {
  }

  public static boolean hasResults(EarlybirdResponse response) {
    return response.isSetSearchResults()
      && (response.getSearchResults().getResults() != null)
      && !response.getSearchResults().getResults().isEmpty();
  }
}

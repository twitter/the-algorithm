package com.X.search.earlybird_root.caching;

import com.X.search.common.metrics.SearchRateCounter;

public final class CacheStats {
  public static final SearchRateCounter REQUEST_FAILED_COUNTER =
      SearchRateCounter.export("memcache_request_failed");
  public static final SearchRateCounter REQUEST_TIMEOUT_COUNTER =
      SearchRateCounter.export("memcache_request_timeout");

  private CacheStats() {
  }
}

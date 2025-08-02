package com.twitter.search.earlybird_root.caching;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.twitter.search.common.caching.filter.PerClientCacheStats;
import com.twitter.search.common.metrics.SearchRateCounter;
import com.twitter.search.earlybird.common.EarlybirdRequestUtil;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;

public class EarlybirdRequestPerClientCacheStats
    etwittertends PerClientCacheStats<EarlybirdRequestContetwittert> {

  private String cacheOffByClientStatFormat;
  private final Map<String, SearchRateCounter> cacheTurnedOffByClient;

  private String cacheHitsByClientStatFormat;
  private final Map<String, SearchRateCounter> cacheHitsByClient;

  public EarlybirdRequestPerClientCacheStats(String cacheRequestType) {
    this.cacheOffByClientStatFormat =
        cacheRequestType + "_client_id_%s_cache_turned_off_in_request";
    this.cacheTurnedOffByClient = new ConcurrentHashMap<>();

    this.cacheHitsByClientStatFormat = cacheRequestType + "_client_id_%s_cache_hit_total";
    this.cacheHitsByClient = new ConcurrentHashMap<>();
  }

  @Override
  public void recordRequest(EarlybirdRequestContetwittert requestContetwittert) {
    if (!EarlybirdRequestUtil.isCachingAllowed(requestContetwittert.getRequest())) {
      String client = requestContetwittert.getRequest().getClientId();
      SearchRateCounter counter = cacheTurnedOffByClient.computeIfAbsent(client,
          cl -> SearchRateCounter.etwitterport(String.format(cacheOffByClientStatFormat, cl)));
      counter.increment();
    }
  }

  @Override
  public void recordCacheHit(EarlybirdRequestContetwittert requestContetwittert) {
    String client = requestContetwittert.getRequest().getClientId();
    SearchRateCounter counter = cacheHitsByClient.computeIfAbsent(client,
        cl -> SearchRateCounter.etwitterport(String.format(cacheHitsByClientStatFormat, cl)));
    counter.increment();
  }
}

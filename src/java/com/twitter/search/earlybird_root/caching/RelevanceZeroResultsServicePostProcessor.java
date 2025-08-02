package com.twitter.search.earlybird_root.caching;

import com.twitter.search.common.caching.Cache;
import com.twitter.search.common.caching.CacheUtil;
import com.twitter.search.common.caching.filter.ServicePostProcessor;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;

public class RelevanceZeroResultsServicePostProcessor
    etwittertends ServicePostProcessor<EarlybirdRequestContetwittert, EarlybirdResponse> {

  private static final SearchCounter RELEVANCE_RESPONSES_WITH_ZERO_RESULTS_COUNTER =
    SearchCounter.etwitterport("relevance_responses_with_zero_results");

  private final Cache<EarlybirdRequest, EarlybirdResponse> cache;

  public RelevanceZeroResultsServicePostProcessor(
      Cache<EarlybirdRequest, EarlybirdResponse> cache) {
    this.cache = cache;
  }

  @Override
  public void processServiceResponse(EarlybirdRequestContetwittert requestContetwittert,
                                     EarlybirdResponse serviceResponse) {
    // serviceResponse is the response to a personalized query. If it has zero results, then we can
    // cache it and reuse it for other requests with the same query. Otherwise, it makes no sense to
    // cache this response.
    if (!CacheCommonUtil.hasResults(serviceResponse)) {
      RELEVANCE_RESPONSES_WITH_ZERO_RESULTS_COUNTER.increment();
      CacheUtil.cacheResults(
          cache, requestContetwittert.getRequest(), serviceResponse, Integer.MAtwitter_VALUE);
    }
  }
}

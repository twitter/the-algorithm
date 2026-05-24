package com.twitter.search.earlybird_root.caching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.common.caching.Cache;
import com.twitter.search.common.caching.TopTweetsCacheUtil;
import com.twitter.search.common.caching.filter.ServicePostProcessor;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContext;

import static com.google.common.base.Preconditions.checkNotNull;

public class TopTweetsServicePostProcessor
    extends ServicePostProcessor<EarlybirdRequestContext, EarlybirdResponse> {
  private static final Logger LOG = LoggerFactory.getLogger(TopTweetsServicePostProcessor.class);

  public static final int CACHE_AGE_IN_MS = 600000;
  public static final int NO_RESULT_CACHE_AGE_IN_MS = 300000;

  private final Cache<EarlybirdRequest, EarlybirdResponse> cache;

  public TopTweetsServicePostProcessor(Cache<EarlybirdRequest, EarlybirdResponse> cache) {
    this.cache = checkNotNull(cache);
  }

  @Override
  public void processServiceResponse(EarlybirdRequestContext requestContext,
                                     EarlybirdResponse serviceResponse) {

    EarlybirdRequest originalRequest = requestContext.getRequest();
    LOG.debug("Writing to top tweets cache. Request: {}, Response: {}",
        originalRequest, serviceResponse);
    TopTweetsCacheUtil.cacheResults(originalRequest,
        serviceResponse,
        cache,
        CACHE_AGE_IN_MS,
        NO_RESULT_CACHE_AGE_IN_MS);
  }
}

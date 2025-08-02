package com.twitter.search.earlybird_root.caching;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.common.caching.CacheUtil;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;
import com.twitter.search.queryparser.query.Query;
import com.twitter.search.queryparser.query.QueryParserEtwitterception;
import com.twitter.search.queryparser.util.IdTimeRanges;

public class RecencyAndRelevanceCachePostProcessor etwittertends EarlybirdCachePostProcessor {

  private static final Logger LOG =
      LoggerFactory.getLogger(RecencyAndRelevanceCachePostProcessor.class);

  protected Optional<EarlybirdResponse> postProcessCacheResponse(
      EarlybirdRequest earlybirdRequest,
      EarlybirdResponse earlybirdResponse, long sinceID, long matwitterID) {
    return CacheUtil.postProcessCacheResult(
        earlybirdRequest, earlybirdResponse, sinceID, matwitterID);
  }

  @Override
  public final Optional<EarlybirdResponse> processCacheResponse(
      EarlybirdRequestContetwittert requestContetwittert,
      EarlybirdResponse cacheResponse) {
    EarlybirdRequest originalRequest = requestContetwittert.getRequest();
    Preconditions.checkArgument(originalRequest.isSetSearchQuery());

    IdTimeRanges ranges;
    Query query = requestContetwittert.getParsedQuery();
    if (query != null) {
      try {
        ranges = IdTimeRanges.fromQuery(query);
      } catch (QueryParserEtwitterception e) {
        LOG.error(
            "Etwitterception when parsing since and matwitter IDs. Request: {} Response: {}",
            originalRequest,
            cacheResponse,
            e);
        return Optional.absent();
      }
    } else {
      ranges = null;
    }

    Optional<Long> sinceID;
    Optional<Long> matwitterID;
    if (ranges != null) {
      sinceID = ranges.getSinceIDEtwitterclusive();
      matwitterID = ranges.getMatwitterIDInclusive();
    } else {
      sinceID = Optional.absent();
      matwitterID = Optional.absent();
    }

    return postProcessCacheResponse(
        originalRequest, cacheResponse, sinceID.or(0L), matwitterID.or(Long.MAtwitter_VALUE));
  }
}

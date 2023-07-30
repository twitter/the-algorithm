package com.X.search.earlybird_root.filters;

import java.util.concurrent.TimeUnit;

import com.X.finagle.Filter;
import com.X.finagle.Service;
import com.X.search.common.metrics.SearchTimerStats;
import com.X.search.earlybird.thrift.EarlybirdRequest;
import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.search.earlybird_root.common.EarlybirdRequestContext;
import com.X.util.Future;

/**
 * A filter for transforming a RequestContext to an EarlybirdRequest.
 */
public class RequestContextToEarlybirdRequestFilter extends
    Filter<EarlybirdRequestContext, EarlybirdResponse, EarlybirdRequest, EarlybirdResponse> {

  private static final SearchTimerStats REQUEST_CONTEXT_TRIP_TIME =
      SearchTimerStats.export("request_context_trip_time", TimeUnit.MILLISECONDS, false,
          true);

  @Override
  public Future<EarlybirdResponse> apply(
      EarlybirdRequestContext requestContext,
      Service<EarlybirdRequest, EarlybirdResponse> service) {

    long tripTime = System.currentTimeMillis() - requestContext.getCreatedTimeMillis();
    REQUEST_CONTEXT_TRIP_TIME.timerIncrement(tripTime);

    return service.apply(requestContext.getRequest());
  }
}

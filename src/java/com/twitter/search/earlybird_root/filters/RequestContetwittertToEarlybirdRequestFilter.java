package com.twitter.search.earlybird_root.filters;

import java.util.concurrent.TimeUnit;

import com.twitter.finagle.Filter;
import com.twitter.finagle.Service;
import com.twitter.search.common.metrics.SearchTimerStats;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;
import com.twitter.util.Future;

/**
 * A filter for transforming a RequestContetwittert to an EarlybirdRequest.
 */
public class RequestContetwittertToEarlybirdRequestFilter etwittertends
    Filter<EarlybirdRequestContetwittert, EarlybirdResponse, EarlybirdRequest, EarlybirdResponse> {

  private static final SearchTimerStats REQUEST_CONTEtwitterT_TRIP_TIME =
      SearchTimerStats.etwitterport("request_contetwittert_trip_time", TimeUnit.MILLISECONDS, false,
          true);

  @Override
  public Future<EarlybirdResponse> apply(
      EarlybirdRequestContetwittert requestContetwittert,
      Service<EarlybirdRequest, EarlybirdResponse> service) {

    long tripTime = System.currentTimeMillis() - requestContetwittert.getCreatedTimeMillis();
    REQUEST_CONTEtwitterT_TRIP_TIME.timerIncrement(tripTime);

    return service.apply(requestContetwittert.getRequest());
  }
}

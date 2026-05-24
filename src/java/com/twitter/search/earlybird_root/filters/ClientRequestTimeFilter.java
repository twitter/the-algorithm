package com.twitter.search.earlybird_root.filters;

import javax.inject.Inject;

import com.twitter.common.util.Clock;
import com.twitter.finagle.Service;
import com.twitter.finagle.SimpleFilter;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.util.Future;

/** A filter that sets the EarlybirdRequest.clientRequestTimeMs field if it's not already set. */
public class ClientRequestTimeFilter extends SimpleFilter<EarlybirdRequest, EarlybirdResponse> {
  private static final SearchCounter CLIENT_REQUEST_TIME_MS_UNSET_COUNTER =
      SearchCounter.export("client_request_time_ms_unset");

  private final Clock clock;

  @Inject
  public ClientRequestTimeFilter(Clock clock) {
    this.clock = clock;
  }

  @Override
  public Future<EarlybirdResponse> apply(EarlybirdRequest request,
                                         Service<EarlybirdRequest, EarlybirdResponse> service) {
    if (!request.isSetClientRequestTimeMs()) {
      CLIENT_REQUEST_TIME_MS_UNSET_COUNTER.increment();
      request.setClientRequestTimeMs(clock.nowMillis());
    }
    return service.apply(request);
  }
}

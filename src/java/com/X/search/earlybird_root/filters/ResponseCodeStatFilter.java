package com.X.search.earlybird_root.filters;

import java.util.Map;

import com.google.common.collect.Maps;

import com.X.finagle.Service;
import com.X.finagle.SimpleFilter;
import com.X.search.common.metrics.SearchCounter;
import com.X.search.earlybird.thrift.EarlybirdRequest;
import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.search.earlybird.thrift.EarlybirdResponseCode;
import com.X.util.Future;
import com.X.util.FutureEventListener;

public class ResponseCodeStatFilter
    extends SimpleFilter<EarlybirdRequest, EarlybirdResponse> {

  private final Map<EarlybirdResponseCode, SearchCounter> responseCodeCounters;

  /**
   * Create ResponseCodeStatFilter
   */
  public ResponseCodeStatFilter() {
    responseCodeCounters = Maps.newEnumMap(EarlybirdResponseCode.class);
    for (EarlybirdResponseCode code : EarlybirdResponseCode.values()) {
      SearchCounter stat = SearchCounter.export("response_code_" + code.name().toLowerCase());
      responseCodeCounters.put(code, stat);
    }
  }

  @Override
  public Future<EarlybirdResponse> apply(
      final EarlybirdRequest request,
      final Service<EarlybirdRequest, EarlybirdResponse> service) {

    return service.apply(request).addEventListener(
        new FutureEventListener<EarlybirdResponse>() {

          @Override
          public void onSuccess(final EarlybirdResponse response) {
            responseCodeCounters.get(response.getResponseCode()).increment();
          }

          @Override
          public void onFailure(final Throwable cause) { }
        });

  }
}

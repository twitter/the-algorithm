package com.X.search.earlybird_root.filters;

import com.X.finagle.Service;
import com.X.finagle.SimpleFilter;
import com.X.search.earlybird.thrift.EarlybirdRequest;
import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.util.Future;

/** A top level filter for handling exceptions. */
public class TopLevelExceptionHandlingFilter
    extends SimpleFilter<EarlybirdRequest, EarlybirdResponse> {
  private final EarlybirdResponseExceptionHandler exceptionHandler;

  /** Creates a new TopLevelExceptionHandlingFilter instance. */
  public TopLevelExceptionHandlingFilter() {
    this.exceptionHandler = new EarlybirdResponseExceptionHandler("top_level");
  }

  @Override
  public Future<EarlybirdResponse> apply(EarlybirdRequest request,
                                         Service<EarlybirdRequest, EarlybirdResponse> service) {
    return exceptionHandler.handleException(request, service.apply(request));
  }
}

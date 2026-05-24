package com.twitter.search.earlybird_root.filters;

import com.twitter.finagle.Service;
import com.twitter.finagle.SimpleFilter;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.util.Future;

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

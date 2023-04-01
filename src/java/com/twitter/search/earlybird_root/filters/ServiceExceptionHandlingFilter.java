package com.twitter.search.earlybird_root.filters;

import com.twitter.finagle.Service;
import com.twitter.finagle.SimpleFilter;
import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContext;
import com.twitter.util.Future;

/** A per-service filter for handling exceptions. */
public class ServiceExceptionHandlingFilter
    extends SimpleFilter<EarlybirdRequestContext, EarlybirdResponse> {
  private final EarlybirdResponseExceptionHandler exceptionHandler;

  /** Creates a new ServiceExceptionHandlingFilter instance. */
  public ServiceExceptionHandlingFilter(EarlybirdCluster cluster) {
    this.exceptionHandler = new EarlybirdResponseExceptionHandler(cluster.getNameForStats());
  }

  @Override
  public Future<EarlybirdResponse> apply(
      EarlybirdRequestContext requestContext,
      Service<EarlybirdRequestContext, EarlybirdResponse> service) {
    return exceptionHandler.handleException(
        requestContext.getRequest(), service.apply(requestContext));
  }
}

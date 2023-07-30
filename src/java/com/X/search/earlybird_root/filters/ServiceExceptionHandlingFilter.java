package com.X.search.earlybird_root.filters;

import com.X.finagle.Service;
import com.X.finagle.SimpleFilter;
import com.X.search.common.schema.earlybird.EarlybirdCluster;
import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.search.earlybird_root.common.EarlybirdRequestContext;
import com.X.util.Future;

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

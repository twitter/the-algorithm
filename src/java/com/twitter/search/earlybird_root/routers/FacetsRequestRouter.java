package com.twitter.search.earlybird_root.routers;

import javax.inject.Inject;
import javax.inject.Named;

import com.twitter.finagle.Service;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContext;
import com.twitter.search.earlybird_root.common.InjectionNames;
import com.twitter.search.earlybird_root.filters.EarlybirdTimeRangeFilter;
import com.twitter.util.Future;

/**
 * For Facets traffic SuperRoot forwards all traffic to the realtime cluster.
 */
public class FacetsRequestRouter extends RequestRouter {

  private final Service<EarlybirdRequestContext, EarlybirdResponse> realtime;

  /** Creates a new FacetsRequestRouter instance to be used by the SuperRoot. */
  @Inject
  public FacetsRequestRouter(
      @Named(InjectionNames.REALTIME)
      Service<EarlybirdRequestContext, EarlybirdResponse> realtime,
      @Named(FacetsRequestRouterModule.TIME_RANGE_FILTER)
      EarlybirdTimeRangeFilter timeRangeFilter) {

    this.realtime = timeRangeFilter.andThen(realtime);
  }

  @Override
  public Future<EarlybirdResponse> route(EarlybirdRequestContext requestContext) {
    return realtime.apply(requestContext);
  }
}

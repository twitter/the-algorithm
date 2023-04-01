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
 * For TopTweets traffic SuperRoot forwards all traffic to the realtime cluster.
 */
public class TopTweetsRequestRouter extends RequestRouter {

  private final Service<EarlybirdRequestContext, EarlybirdResponse> realtime;

  /** Creates a new TopTweetsRequestRouter instance to be used by the SuperRoot. */
  @Inject
  public TopTweetsRequestRouter(
      @Named(InjectionNames.REALTIME)
      Service<EarlybirdRequestContext, EarlybirdResponse> realtime,
      @Named(TopTweetsRequestRouterModule.TIME_RANGE_FILTER)
      EarlybirdTimeRangeFilter timeRangeFilter) {

    this.realtime = timeRangeFilter.andThen(realtime);
  }

  @Override
  public Future<EarlybirdResponse> route(EarlybirdRequestContext requestContext) {
    return realtime.apply(requestContext);
  }
}

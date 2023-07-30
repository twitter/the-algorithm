package com.X.search.earlybird_root.routers;

import javax.inject.Named;
import javax.inject.Singleton;

import com.google.inject.Provides;

import com.X.inject.XModule;
import com.X.search.common.decider.SearchDecider;
import com.X.search.earlybird_root.filters.EarlybirdTimeRangeFilter;
import com.X.search.earlybird_root.filters.RealtimeServingRangeProvider;
import com.X.search.earlybird_root.filters.ServingRangeProvider;

public class TopTweetsRequestRouterModule extends XModule {
  public static final String TIME_RANGE_FILTER = "top_tweets_time_range_filter";

  public static final String SERVING_RANGE_BOUNDARY_HOURS_AGO_DECIDER_KEY =
      "superroot_top_tweets_serving_range_boundary_hours_ago";

  private ServingRangeProvider getServingRangeProvider(final SearchDecider decider)
      throws Exception {
    return new RealtimeServingRangeProvider(decider, SERVING_RANGE_BOUNDARY_HOURS_AGO_DECIDER_KEY);
  }

  @Provides
  @Singleton
  @Named(TIME_RANGE_FILTER)
  private EarlybirdTimeRangeFilter providesTimeRangeFilter(SearchDecider decider) throws Exception {
    return EarlybirdTimeRangeFilter.newTimeRangeFilterWithoutQueryRewriter(
        getServingRangeProvider(decider));
  }
}

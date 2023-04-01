package com.twitter.search.earlybird_root.routers;

import javax.inject.Named;
import javax.inject.Singleton;

import com.google.inject.Provides;

import com.twitter.inject.TwitterModule;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.earlybird_root.filters.EarlybirdTimeRangeFilter;
import com.twitter.search.earlybird_root.filters.FullArchiveServingRangeProvider;
import com.twitter.search.earlybird_root.filters.RealtimeServingRangeProvider;
import com.twitter.search.earlybird_root.filters.ServingRangeProvider;

public class TermStatsRequestRouterModule extends TwitterModule {
  public static final String FULL_ARCHIVE_TIME_RANGE_FILTER =
      "term_stats_full_archive_time_range_filter";
  public static final String REALTIME_TIME_RANGE_FILTER =
      "term_stats_realtime_time_range_filter";

  private static final String SUPERROOT_TERM_STATS_SERVING_RANGE_BOUNDARY_HOURS_AGO_DECIDER_KEY =
      "superroot_term_stats_serving_range_boundary_hours_ago";

  private ServingRangeProvider getFullArchiveTimeRangeProvider(final SearchDecider decider)
      throws Exception {
    return new FullArchiveServingRangeProvider(
        decider, SUPERROOT_TERM_STATS_SERVING_RANGE_BOUNDARY_HOURS_AGO_DECIDER_KEY);
  }

  private ServingRangeProvider getRealtimeTimeRangeProvider(final SearchDecider decider)
      throws Exception {
    return new RealtimeServingRangeProvider(
        decider, SUPERROOT_TERM_STATS_SERVING_RANGE_BOUNDARY_HOURS_AGO_DECIDER_KEY);
  }

  /**
   * For term stats full archive cluster spans from 21 March to 2006 to 6 days ago from current time
   */
  @Provides
  @Singleton
  @Named(FULL_ARCHIVE_TIME_RANGE_FILTER)
  private EarlybirdTimeRangeFilter providesFullArchiveTimeRangeFilter(final SearchDecider decider)
      throws Exception {
    return EarlybirdTimeRangeFilter.newTimeRangeFilterWithQueryRewriter(
        getFullArchiveTimeRangeProvider(decider), decider);
  }

  /**
   * For term stats realtime cluster spans from 6 days ago from current time to a far away date
   * into the future
   */
  @Provides
  @Singleton
  @Named(REALTIME_TIME_RANGE_FILTER)
  private EarlybirdTimeRangeFilter providesRealtimeTimeRangeFilter(final SearchDecider decider)
      throws Exception {
    return EarlybirdTimeRangeFilter.newTimeRangeFilterWithQueryRewriter(
        getRealtimeTimeRangeProvider(decider), decider);
  }
}

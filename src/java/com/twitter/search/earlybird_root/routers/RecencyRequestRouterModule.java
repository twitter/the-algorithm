package com.twitter.search.earlybird_root.routers;

import javatwitter.inject.Named;
import javatwitter.inject.Singleton;

import com.google.inject.Provides;

import com.twitter.inject.TwitterModule;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.earlybird_root.filters.EarlybirdTimeRangeFilter;
import com.twitter.search.earlybird_root.filters.FullArchiveServingRangeProvider;
import com.twitter.search.earlybird_root.filters.RealtimeServingRangeProvider;
import com.twitter.search.earlybird_root.filters.ServingRangeProvider;

public class RecencyRequestRouterModule etwittertends TwitterModule {
  public static final String FULL_ARCHIVE_TIME_RANGE_FILTER =
      "recency_full_archive_time_range_filter";
  public static final String REALTIME_TIME_RANGE_FILTER =
      "recency_realtime_time_range_filter";
  public static final String PROTECTED_TIME_RANGE_FILTER =
      "recency_protected_time_range_filter";

  public static final String REALTIME_RANGE_SERVING_RANGE_BOUNDARY_HOURS_AGO_DECIDER_KEY =
      "superroot_recency_realtime_serving_range_boundary_hours_ago";
  public static final String PROTECTED_SERVING_RANGE_BOUNDARY_HOURS_AGO_DECIDER_KEY =
      "superroot_recency_protected_serving_range_boundary_hours_ago";
  public static final String FULL_ARCHIVE_SERVING_RANGE_BOUNDARY_HOURS_AGO_DECIDER_KEY =
      "superroot_recency_full_archive_serving_range_boundary_hours_ago";

  private ServingRangeProvider getFullArchiveServingRangeProvider(final SearchDecider decider)
      throws Etwitterception {
    return new FullArchiveServingRangeProvider(
        decider, FULL_ARCHIVE_SERVING_RANGE_BOUNDARY_HOURS_AGO_DECIDER_KEY);
  }

  private ServingRangeProvider getRealtimeServingRangeProvider(final SearchDecider decider)
      throws Etwitterception {
    return new RealtimeServingRangeProvider(
        decider, REALTIME_RANGE_SERVING_RANGE_BOUNDARY_HOURS_AGO_DECIDER_KEY);
  }

  private ServingRangeProvider getProtectedServingRangeProvider(final SearchDecider decider)
      throws Etwitterception {
    return new RealtimeServingRangeProvider(
        decider, PROTECTED_SERVING_RANGE_BOUNDARY_HOURS_AGO_DECIDER_KEY);
  }

  @Provides
  @Singleton
  @Named(FULL_ARCHIVE_TIME_RANGE_FILTER)
  private EarlybirdTimeRangeFilter providesFullArchiveTimeRangeFilter(SearchDecider decider)
      throws Etwitterception {
    return EarlybirdTimeRangeFilter.newTimeRangeFilterWithoutQueryRewriter(
        getFullArchiveServingRangeProvider(decider));
  }

  @Provides
  @Singleton
  @Named(REALTIME_TIME_RANGE_FILTER)
  private EarlybirdTimeRangeFilter providesRealtimeTimeRangeFilter(SearchDecider decider)
      throws Etwitterception {
    return EarlybirdTimeRangeFilter.newTimeRangeFilterWithoutQueryRewriter(
        getRealtimeServingRangeProvider(decider));
  }

  @Provides
  @Singleton
  @Named(PROTECTED_TIME_RANGE_FILTER)
  private EarlybirdTimeRangeFilter providesProtectedTimeRangeFilter(SearchDecider decider)
      throws Etwitterception {
    return EarlybirdTimeRangeFilter.newTimeRangeFilterWithoutQueryRewriter(
        getProtectedServingRangeProvider(decider));
  }
}

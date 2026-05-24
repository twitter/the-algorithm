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

public class RelevanceRequestRouterModule extends TwitterModule {
  public static final String FULL_ARCHIVE_TIME_RANGE_FILTER =
      "relevance_full_archive_time_range_filter";
  public static final String REALTIME_TIME_RANGE_FILTER =
      "relevance_realtime_time_range_filter";
  public static final String PROTECTED_TIME_RANGE_FILTER =
      "relevance_protected_time_range_filter";

  public static final String REALTIME_SERVING_RANGE_BOUNDARY_HOURS_AGO_DECIDER_KEY =
      "superroot_relevance_realtime_serving_range_boundary_hours_ago";
  public static final String FULL_ARCHIVE_SERVING_RANGE_BOUNDARY_HOURS_AGO_DECIDER_KEY =
      "superroot_relevance_full_archive_serving_range_boundary_hours_ago";
  public static final String PROTECTED_SERVING_RANGE_BOUNDARY_HOURS_AGO_DECIDER_KEY =
      "superroot_relevance_protected_serving_range_boundary_hours_ago";

  private ServingRangeProvider getFullArchiveServingRangeProvider(final SearchDecider decider)
      throws Exception {
    return new FullArchiveServingRangeProvider(
        decider, FULL_ARCHIVE_SERVING_RANGE_BOUNDARY_HOURS_AGO_DECIDER_KEY);
  }

  private ServingRangeProvider getRealtimeServingRangeProvider(final SearchDecider decider)
      throws Exception {
    return new RealtimeServingRangeProvider(
        decider, REALTIME_SERVING_RANGE_BOUNDARY_HOURS_AGO_DECIDER_KEY);
  }

  private ServingRangeProvider getProtectedServingRangeProvider(final SearchDecider decider)
      throws Exception {
    return new RealtimeServingRangeProvider(
        decider, PROTECTED_SERVING_RANGE_BOUNDARY_HOURS_AGO_DECIDER_KEY);
  }

  @Provides
  @Singleton
  @Named(FULL_ARCHIVE_TIME_RANGE_FILTER)
  private EarlybirdTimeRangeFilter providesFullArchiveTimeRangeFilter(SearchDecider decider)
      throws Exception {
    return EarlybirdTimeRangeFilter.newTimeRangeFilterWithoutQueryRewriter(
        getFullArchiveServingRangeProvider(decider));
  }

  @Provides
  @Singleton
  @Named(REALTIME_TIME_RANGE_FILTER)
  private EarlybirdTimeRangeFilter providesRealtimeTimeRangeFilter(SearchDecider decider)
      throws Exception {
    return EarlybirdTimeRangeFilter.newTimeRangeFilterWithoutQueryRewriter(
        getRealtimeServingRangeProvider(decider));
  }

  @Provides
  @Singleton
  @Named(PROTECTED_TIME_RANGE_FILTER)
  private EarlybirdTimeRangeFilter providesProtectedTimeRangeFilter(SearchDecider decider)
      throws Exception {
    return EarlybirdTimeRangeFilter.newTimeRangeFilterWithoutQueryRewriter(
        getProtectedServingRangeProvider(decider));
  }
}

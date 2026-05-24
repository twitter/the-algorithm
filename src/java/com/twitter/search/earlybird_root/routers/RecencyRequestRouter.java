package com.twitter.search.earlybird_root.routers;

import javax.inject.Inject;
import javax.inject.Named;

import com.twitter.common.util.Clock;
import com.twitter.finagle.Service;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird.thrift.ThriftSearchRankingMode;
import com.twitter.search.earlybird_root.common.EarlybirdFeatureSchemaMerger;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContext;
import com.twitter.search.earlybird_root.common.InjectionNames;
import com.twitter.search.earlybird_root.filters.EarlybirdTimeRangeFilter;

public class RecencyRequestRouter extends AbstractRecencyAndRelevanceRequestRouter {
  private static final SearchCounter SKIPPED_ARCHIVE_DUE_TO_REALTIME_EARLY_TERMINATION_COUNTER =
      SearchCounter.export("recency_skipped_archive_due_to_realtime_early_termination");
  private static final SearchCounter SKIPPED_ARCHIVE_DUE_TO_REALTIME_ENOUGH_RESULTS_COUNTER =
      SearchCounter.export("recency_skipped_archive_due_to_realtime_enough_results");

  @Inject
  public RecencyRequestRouter(
      @Named(InjectionNames.REALTIME)
      Service<EarlybirdRequestContext, EarlybirdResponse> realtime,
      @Named(InjectionNames.PROTECTED)
      Service<EarlybirdRequestContext, EarlybirdResponse> protectedRealtime,
      @Named(InjectionNames.FULL_ARCHIVE)
      Service<EarlybirdRequestContext, EarlybirdResponse> fullArchive,
      @Named(RecencyRequestRouterModule.REALTIME_TIME_RANGE_FILTER)
      EarlybirdTimeRangeFilter realtimeTimeRangeFilter,
      @Named(RecencyRequestRouterModule.PROTECTED_TIME_RANGE_FILTER)
      EarlybirdTimeRangeFilter protectedTimeRangeFilter,
      @Named(RecencyRequestRouterModule.FULL_ARCHIVE_TIME_RANGE_FILTER)
      EarlybirdTimeRangeFilter fullArchiveTimeRangeFilter,
      Clock clock,
      SearchDecider decider,
      EarlybirdFeatureSchemaMerger featureSchemaMerger) {
    super(realtime,
          protectedRealtime,
          fullArchive,
          realtimeTimeRangeFilter,
          protectedTimeRangeFilter,
          fullArchiveTimeRangeFilter,
          ThriftSearchRankingMode.RECENCY,
          clock,
          decider,
          featureSchemaMerger);
  }

  @Override
  protected boolean shouldSendRequestToFullArchiveCluster(
      EarlybirdRequest request, EarlybirdResponse realtimeResponse) {
    boolean isEarlyTerminated = realtimeResponse.isSetEarlyTerminationInfo()
        && realtimeResponse.getEarlyTerminationInfo().isEarlyTerminated();
    if (isEarlyTerminated) {
      SKIPPED_ARCHIVE_DUE_TO_REALTIME_EARLY_TERMINATION_COUNTER.increment();
      return false;
    }

    // Check if we have the minimum number of results to fulfill the original request.
    int numResultsRequested = request.getSearchQuery().getNumResults();
    int actualNumResults = realtimeResponse.getSearchResults().getResultsSize();
    if (actualNumResults >= numResultsRequested) {
      SKIPPED_ARCHIVE_DUE_TO_REALTIME_ENOUGH_RESULTS_COUNTER.increment();
      return false;
    }

    return true;
  }
}

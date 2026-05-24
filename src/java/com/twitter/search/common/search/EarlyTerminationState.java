package com.twitter.search.common.search;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

import com.twitter.search.common.metrics.SearchCounter;

/**
 * This is not an enum to allow different clusters to define their own EarlyTerminationStates.
 */
public final class EarlyTerminationState {
  private static final String STATS_PREFIX = "early_termination_";

  public static final EarlyTerminationState COLLECTING =
      new EarlyTerminationState("no_early_termination", false);
  public static final EarlyTerminationState TERMINATED_TIME_OUT_EXCEEDED =
      new EarlyTerminationState("terminated_timeout_exceeded", true);
  public static final EarlyTerminationState TERMINATED_MAX_QUERY_COST_EXCEEDED =
      new EarlyTerminationState("terminated_max_query_cost_exceeded", true);
  public static final EarlyTerminationState TERMINATED_MAX_HITS_EXCEEDED =
      new EarlyTerminationState("terminated_max_hits_exceeded", true);
  public static final EarlyTerminationState TERMINATED_NUM_RESULTS_EXCEEDED =
      new EarlyTerminationState("terminated_num_results_exceeded", true);


  // This string can be returned as a part of a search response, to tell the searcher
  // why the search got early terminated.
  private final String terminationReason;
  private final boolean terminated;
  private final SearchCounter count;

  public EarlyTerminationState(@Nonnull String terminationReason, boolean terminated) {
    this.terminationReason = Preconditions.checkNotNull(terminationReason);
    this.terminated = terminated;
    count = SearchCounter.export(STATS_PREFIX + terminationReason + "_count");

  }

  public boolean isTerminated() {
    return terminated;
  }

  public String getTerminationReason() {
    return terminationReason;
  }

  public void incrementCount() {
    count.increment();
  }
}

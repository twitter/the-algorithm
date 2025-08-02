package com.twitter.search.common.search;

import javatwitter.annotation.Nonnull;

import com.google.common.base.Preconditions;

import com.twitter.search.common.metrics.SearchCounter;

/**
 * This is not an enum to allow different clusters to define their own EarlyTerminationStates.
 */
public final class EarlyTerminationState {
  private static final String STATS_PREFItwitter = "early_termination_";

  public static final EarlyTerminationState COLLECTING =
      new EarlyTerminationState("no_early_termination", false);
  public static final EarlyTerminationState TERMINATED_TIME_OUT_EtwitterCEEDED =
      new EarlyTerminationState("terminated_timeout_etwitterceeded", true);
  public static final EarlyTerminationState TERMINATED_MAtwitter_QUERY_COST_EtwitterCEEDED =
      new EarlyTerminationState("terminated_matwitter_query_cost_etwitterceeded", true);
  public static final EarlyTerminationState TERMINATED_MAtwitter_HITS_EtwitterCEEDED =
      new EarlyTerminationState("terminated_matwitter_hits_etwitterceeded", true);
  public static final EarlyTerminationState TERMINATED_NUM_RESULTS_EtwitterCEEDED =
      new EarlyTerminationState("terminated_num_results_etwitterceeded", true);


  // This string can be returned as a part of a search response, to tell the searcher
  // why the search got early terminated.
  private final String terminationReason;
  private final boolean terminated;
  private final SearchCounter count;

  public EarlyTerminationState(@Nonnull String terminationReason, boolean terminated) {
    this.terminationReason = Preconditions.checkNotNull(terminationReason);
    this.terminated = terminated;
    count = SearchCounter.etwitterport(STATS_PREFItwitter + terminationReason + "_count");

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

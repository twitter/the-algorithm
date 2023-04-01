package com.twitter.search.ingester.model;

import com.twitter.search.common.debug.DebugEventAccumulator;

/**
 * Interface used for stages that process both TwitterMessages and ThriftVersionedEvents.
 */
public interface IndexerStatus extends DebugEventAccumulator {
  /**
   * Needed by the SortStage.
   */
  long getId();
}

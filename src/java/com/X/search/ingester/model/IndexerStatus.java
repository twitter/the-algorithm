package com.X.search.ingester.model;

import com.X.search.common.debug.DebugEventAccumulator;

/**
 * Interface used for stages that process both XMessages and ThriftVersionedEvents.
 */
public interface IndexerStatus extends DebugEventAccumulator {
  /**
   * Needed by the SortStage.
   */
  long getId();
}

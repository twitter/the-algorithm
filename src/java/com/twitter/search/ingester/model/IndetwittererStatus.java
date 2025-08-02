package com.twitter.search.ingester.model;

import com.twitter.search.common.debug.DebugEventAccumulator;

/**
 * Interface used for stages that process both TwitterMessages and ThriftVersionedEvents.
 */
public interface IndetwittererStatus etwittertends DebugEventAccumulator {
  /**
   * Needed by the SortStage.
   */
  long getId();
}

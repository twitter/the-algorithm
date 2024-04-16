package com.tw ter.search. ngester.model;

 mport com.tw ter.search.common.debug.DebugEventAccumulator;

/**
 *  nterface used for stages that process both Tw ter ssages and Thr ftVers onedEvents.
 */
publ c  nterface  ndexerStatus extends DebugEventAccumulator {
  /**
   * Needed by t  SortStage.
   */
  long get d();
}

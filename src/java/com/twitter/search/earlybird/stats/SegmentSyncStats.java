package com.twitter.search.earlybird.stats;

import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.metrics.Timer;

public class SegmentSyncStats {
  private static final String CPU_TOTAL = "_cpu_total_";
  private static final String CPU_USER  = "_cpu_user_mode_";
  private static final String CPU_SYS   = "_cpu_system_mode_";

  private final SearchCounter segmentSyncLatency;
  private final SearchCounter segmentSyncLatencyCpuTotal;
  private final SearchCounter segmentSyncLatencyCpuUserMode;
  private final SearchCounter segmentSyncLatencyCpuSystemMode;
  private final SearchCounter segmentSyncCount;
  private final SearchCounter segmentErrorCount;

  private SegmentSyncStats(SearchCounter segmentSyncLatency,
                           SearchCounter segmentSyncLatencyCpuTotal,
                           SearchCounter segmentSyncLatencyCpuUserMode,
                           SearchCounter segmentSyncLatencyCpuSystemMode,
                           SearchCounter segmentSyncCount,
                           SearchCounter segmentErrorCount) {
    this.segmentSyncLatency = segmentSyncLatency;
    this.segmentSyncLatencyCpuTotal = segmentSyncLatencyCpuTotal;
    this.segmentSyncLatencyCpuUserMode = segmentSyncLatencyCpuUserMode;
    this.segmentSyncLatencyCpuSystemMode = segmentSyncLatencyCpuSystemMode;
    this.segmentSyncCount = segmentSyncCount;
    this.segmentErrorCount = segmentErrorCount;
  }

  /**
   * Creates a new set of stats for the given segment sync action.
   * @param action the name to be used for the sync stats.
   */
  public SegmentSyncStats(String action) {
    this(SearchCounter.export("segment_" + action + "_latency_ms"),
         SearchCounter.export("segment_" + action + "_latency" + CPU_TOTAL + "ms"),
         SearchCounter.export("segment_" + action + "_latency" + CPU_USER + "ms"),
         SearchCounter.export("segment_" + action + "_latency" + CPU_SYS + "ms"),
         SearchCounter.export("segment_" + action + "_count"),
         SearchCounter.export("segment_" + action + "_error_count"));
  }

  /**
   * Records a completed action using the specified timer.
   */
  public void actionComplete(Timer timer) {
    segmentSyncCount.increment();
    segmentSyncLatency.add(timer.getElapsed());
    segmentSyncLatencyCpuTotal.add(timer.getElapsedCpuTotal());
    segmentSyncLatencyCpuUserMode.add(timer.getElapsedCpuUserMode());
    segmentSyncLatencyCpuSystemMode.add(timer.getElapsedCpuSystemMode());
  }

  public void recordError() {
    segmentErrorCount.increment();
  }
}

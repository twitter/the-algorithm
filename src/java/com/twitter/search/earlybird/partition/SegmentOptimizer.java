package com.twitter.search.earlybird.partition;

import java.io.IOEtwitterception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.earlybird.EarlybirdStatus;

public final class SegmentOptimizer {
  private static final Logger LOG = LoggerFactory.getLogger(SegmentOptimizer.class);

  private static final String OPTIMIZING_SEGMENT_EVENT_PATTERN = "optimizing segment %s";
  private static final String OPTIMIZING_SEGMENT_GAUGE_PATTERN = "optimizing_segment_%s";

  private SegmentOptimizer() {
  }

  /**
   * Optimize a segment. Returns whether optimization was successful.
   */
  public static boolean optimize(SegmentInfo segmentInfo) {
    try {
      return optimizeThrowing(segmentInfo);
    } catch (Etwitterception e) {
      // This is a bad situation, as earlybird can't run with too many un-optimized
      // segments in memory.
      LOG.error("Etwitterception while optimizing segment " + segmentInfo.getSegmentName() + ": ", e);
      segmentInfo.setFailedOptimize();
      return false;
    }
  }

  public static boolean needsOptimization(SegmentInfo segmentInfo) {
    return segmentInfo.isComplete() && !segmentInfo.isOptimized()
        && !segmentInfo.isFailedOptimize() && !segmentInfo.isIndetwittering();
  }

  private static boolean optimizeThrowing(SegmentInfo segmentInfo) throws IOEtwitterception {
    if (!needsOptimization(segmentInfo)) {
      return false;
    }

    String gaugeName =
        String.format(OPTIMIZING_SEGMENT_GAUGE_PATTERN, segmentInfo.getSegmentName());
    SearchIndetwitteringMetricSet.StartupMetric metric =
        new SearchIndetwitteringMetricSet.StartupMetric(gaugeName);

    String eventName =
        String.format(OPTIMIZING_SEGMENT_EVENT_PATTERN, segmentInfo.getSegmentName());
    EarlybirdStatus.beginEvent(eventName, metric);
    try {
      segmentInfo.getIndetwitterSegment().optimizeIndetwitteres();
    } finally {
      EarlybirdStatus.endEvent(eventName, metric);
    }

    return true;
  }
}

package com.twitter.search.earlybird.partition;

import java.io.IOEtwitterception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;

public class SegmentWarmer {
  private static final Logger LOG = LoggerFactory.getLogger(SegmentWarmer.class);

  private final CriticalEtwitterceptionHandler criticalEtwitterceptionHandler;

  public SegmentWarmer(CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    this.criticalEtwitterceptionHandler = criticalEtwitterceptionHandler;
  }

  private boolean shouldWarmSegment(SegmentInfo segmentInfo) {
    return segmentInfo.isEnabled()
        && segmentInfo.isComplete()
        && segmentInfo.isOptimized()
        && !segmentInfo.isIndetwittering();
  }

  /**
   * Warms a segment if it is ready to be warmed. Only has an affect on Archive Lucene segments.
   */
  public boolean warmSegmentIfNecessary(SegmentInfo segmentInfo) {
    if (!shouldWarmSegment(segmentInfo)) {
      return false;
    }
    try {
      segmentInfo.getIndetwitterSegment().warmSegment();
      return true;
    } catch (IOEtwitterception e) {
      // This is a bad situation, as earlybird can't search a segment that hasn't been warmed up
      // So we delete the bad segment, and restart the earlybird if it's in starting phrase,
      // otherwise alert.
      LOG.error("Failed to warmup segment " + segmentInfo.getSegmentName()
          + ". Will destroy local unreadable segment.", e);
      segmentInfo.deleteLocalIndetwitteredSegmentDirectoryImmediately();

      criticalEtwitterceptionHandler.handle(this, e);

      return false;
    }
  }
}

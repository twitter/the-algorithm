package com.twitter.search.earlybird.archive.segmentbuilder;

import java.util.HashMap;
import java.util.Map;

import com.twitter.common.util.Clock;

/**
 * A class that prevents handling a given segment more than once every hdfsCheckIntervalMillis
 */
public class RateLimitingSegmentHandler {
  private final long hdfsCheckIntervalMillis;
  private final Clock clock;
  private final Map<String, Long> segmentNameToLastUpdatedTimeMillis = new HashMap<>();

  RateLimitingSegmentHandler(long hdfsCheckIntervalMillis, Clock clock) {
    this.hdfsCheckIntervalMillis = hdfsCheckIntervalMillis;
    this.clock = clock;
  }

  SegmentBuilderSegment processSegment(SegmentBuilderSegment segment)
      throws SegmentUpdaterException, SegmentInfoConstructionException {

    String segmentName = segment.getSegmentName();

    Long lastUpdatedMillis = segmentNameToLastUpdatedTimeMillis.get(segmentName);
    if (lastUpdatedMillis == null) {
      lastUpdatedMillis = 0L;
    }

    long nowMillis = clock.nowMillis();
    if (nowMillis - lastUpdatedMillis < hdfsCheckIntervalMillis) {
      return segment;
    }
    segmentNameToLastUpdatedTimeMillis.put(segmentName, nowMillis);

    return segment.handle();
  }
}

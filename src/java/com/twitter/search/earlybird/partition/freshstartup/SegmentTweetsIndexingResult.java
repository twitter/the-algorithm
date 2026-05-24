package com.twitter.search.earlybird.partition.freshstartup;

import com.twitter.search.earlybird.partition.SegmentWriter;

/**
   * Data collected and created while indexing tweets for a single segment.
   */
class SegmentTweetsIndexingResult {
  private final long minRecordTimestampMs;
  private final long maxRecordTimestampMs;
  private final long maxIndexedTweetId;
  private final SegmentWriter segmentWriter;

  public SegmentTweetsIndexingResult(long minRecordTimestampMs, long maxRecordTimestampMs,
                                     long maxIndexedTweetId,
                                     SegmentWriter segmentWriter) {
    this.minRecordTimestampMs = minRecordTimestampMs;
    this.maxRecordTimestampMs = maxRecordTimestampMs;
    this.maxIndexedTweetId = maxIndexedTweetId;
    this.segmentWriter = segmentWriter;
  }

  public long getMinRecordTimestampMs() {
    return minRecordTimestampMs;
  }

  public long getMaxRecordTimestampMs() {
    return maxRecordTimestampMs;
  }

  public SegmentWriter getSegmentWriter() {
    return segmentWriter;
  }

  public long getMaxIndexedTweetId() {
    return maxIndexedTweetId;
  }

  @Override
  public String toString() {
    return String.format("Start time: %d, end time: %d, segment name: %s, max indexed: %d",
        minRecordTimestampMs, maxRecordTimestampMs,
        segmentWriter.getSegmentInfo().getSegmentName(),
        maxIndexedTweetId);
  }
}

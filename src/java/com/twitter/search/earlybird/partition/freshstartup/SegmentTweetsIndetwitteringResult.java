package com.twitter.search.earlybird.partition.freshstartup;

import com.twitter.search.earlybird.partition.SegmentWriter;

/**
   * Data collected and created while indetwittering tweets for a single segment.
   */
class SegmentTweetsIndetwitteringResult {
  private final long minRecordTimestampMs;
  private final long matwitterRecordTimestampMs;
  private final long matwitterIndetwitteredTweetId;
  private final SegmentWriter segmentWriter;

  public SegmentTweetsIndetwitteringResult(long minRecordTimestampMs, long matwitterRecordTimestampMs,
                                     long matwitterIndetwitteredTweetId,
                                     SegmentWriter segmentWriter) {
    this.minRecordTimestampMs = minRecordTimestampMs;
    this.matwitterRecordTimestampMs = matwitterRecordTimestampMs;
    this.matwitterIndetwitteredTweetId = matwitterIndetwitteredTweetId;
    this.segmentWriter = segmentWriter;
  }

  public long getMinRecordTimestampMs() {
    return minRecordTimestampMs;
  }

  public long getMatwitterRecordTimestampMs() {
    return matwitterRecordTimestampMs;
  }

  public SegmentWriter getSegmentWriter() {
    return segmentWriter;
  }

  public long getMatwitterIndetwitteredTweetId() {
    return matwitterIndetwitteredTweetId;
  }

  @Override
  public String toString() {
    return String.format("Start time: %d, end time: %d, segment name: %s, matwitter indetwittered: %d",
        minRecordTimestampMs, matwitterRecordTimestampMs,
        segmentWriter.getSegmentInfo().getSegmentName(),
        matwitterIndetwitteredTweetId);
  }
}

package com.twitter.search.earlybird.partition.freshstartup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.earlybird.partition.SegmentWriter;

// Data collected and produced while building a segment.
class SegmentBuildInfo {
  private static final Logger LOG = LoggerFactory.getLogger(SegmentBuildInfo.class);

  // Inclusive boundaries. [start, end].
  private final long tweetStartOffset;
  private final long tweetEndOffset;
  private final int index;
  private final boolean lastSegment;

  private long startTweetId;
  private long maxIndexedTweetId;
  private KafkaOffsetPair updateKafkaOffsetPair;
  private SegmentWriter segmentWriter;

  public SegmentBuildInfo(long tweetStartOffset,
                          long tweetEndOffset,
                          int index,
                          boolean lastSegment) {
    this.tweetStartOffset = tweetStartOffset;
    this.tweetEndOffset = tweetEndOffset;
    this.index = index;
    this.lastSegment = lastSegment;

    this.startTweetId = -1;
    this.updateKafkaOffsetPair = null;
    this.maxIndexedTweetId = -1;
    this.segmentWriter = null;
  }

  public void setUpdateKafkaOffsetPair(KafkaOffsetPair updateKafkaOffsetPair) {
    this.updateKafkaOffsetPair = updateKafkaOffsetPair;
  }

  public KafkaOffsetPair getUpdateKafkaOffsetPair() {
    return updateKafkaOffsetPair;
  }

  public boolean isLastSegment() {
    return lastSegment;
  }

  public void setStartTweetId(long startTweetId) {
    this.startTweetId = startTweetId;
  }

  public long getTweetStartOffset() {
    return tweetStartOffset;
  }

  public long getTweetEndOffset() {
    return tweetEndOffset;
  }

  public long getStartTweetId() {
    return startTweetId;
  }

  public int getIndex() {
    return index;
  }

  public void setMaxIndexedTweetId(long maxIndexedTweetId) {
    this.maxIndexedTweetId = maxIndexedTweetId;
  }

  public long getMaxIndexedTweetId() {
    return maxIndexedTweetId;
  }

  public SegmentWriter getSegmentWriter() {
    return segmentWriter;
  }

  public void setSegmentWriter(SegmentWriter segmentWriter) {
    this.segmentWriter = segmentWriter;
  }

  public void logState() {
    LOG.info("SegmentBuildInfo (index:{})", index);
    LOG.info(String.format("  Start offset: %,d", tweetStartOffset));
    LOG.info(String.format("  End offset: %,d", tweetEndOffset));
    LOG.info(String.format("  Start tweet id: %d", startTweetId));
  }
}

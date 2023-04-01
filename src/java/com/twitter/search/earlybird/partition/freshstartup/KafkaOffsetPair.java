package com.twitter.search.earlybird.partition.freshstartup;

class KafkaOffsetPair {
  private final long beginOffset;
  private final long endOffset;

  public KafkaOffsetPair(long beginOffset, long endOffset) {
    this.beginOffset = beginOffset;
    this.endOffset = endOffset;
  }

  public boolean includes(long offset) {
    return beginOffset <= offset && offset <= endOffset;
  }

  public long getBeginOffset() {
    return beginOffset;
  }

  public long getEndOffset() {
    return endOffset;
  }
}

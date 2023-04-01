package com.twitter.search.earlybird.partition;

import java.io.IOException;

import com.twitter.search.common.indexing.thriftjava.ThriftVersionedEvents;

public interface ISegmentWriter {
  enum Result {
    SUCCESS,
    FAILURE_RETRYABLE,
    FAILURE_NOT_RETRYABLE,
  }

  /**
   * Indexes the given ThriftVersionedEvents instance (adds it to the segment associated with this
   * SegmentWriter instance).
   */
  Result indexThriftVersionedEvents(ThriftVersionedEvents tve) throws IOException;

  /**
   * Returns the segment info for this segment writer.
   */
  SegmentInfo getSegmentInfo();
}

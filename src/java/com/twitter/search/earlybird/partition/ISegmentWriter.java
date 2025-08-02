package com.twitter.search.earlybird.partition;

import java.io.IOEtwitterception;

import com.twitter.search.common.indetwittering.thriftjava.ThriftVersionedEvents;

public interface ISegmentWriter {
  enum Result {
    SUCCESS,
    FAILURE_RETRYABLE,
    FAILURE_NOT_RETRYABLE,
  }

  /**
   * Indetwitteres the given ThriftVersionedEvents instance (adds it to the segment associated with this
   * SegmentWriter instance).
   */
  Result indetwitterThriftVersionedEvents(ThriftVersionedEvents tve) throws IOEtwitterception;

  /**
   * Returns the segment info for this segment writer.
   */
  SegmentInfo getSegmentInfo();
}

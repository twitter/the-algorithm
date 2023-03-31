package com.twitter.search.earlybird.document;

import com.google.common.base.Preconditions;

import com.twitter.search.common.schema.thriftjava.ThriftIndexingEvent;

/**
 * Object to encapsulate {@link ThriftIndexingEvent} with a time slice ID.
 */
public class TimeSlicedThriftIndexingEvent {
  private final long timeSliceID;
  private final ThriftIndexingEvent thriftIndexingEvent;

  public TimeSlicedThriftIndexingEvent(long timeSliceID, ThriftIndexingEvent thriftIndexingEvent) {
    Preconditions.checkNotNull(thriftIndexingEvent);

    this.timeSliceID = timeSliceID;
    this.thriftIndexingEvent = thriftIndexingEvent;
  }

  public long getStatusID() {
    return thriftIndexingEvent.getUid();
  }

  public long getTimeSliceID() {
    return timeSliceID;
  }

  public ThriftIndexingEvent getThriftIndexingEvent() {
    return thriftIndexingEvent;
  }

  @Override
  public String toString() {
    return "TimeSlicedThriftIndexingEvent{"
        + "timeSliceID=" + timeSliceID
        + ", thriftIndexingEvent=" + thriftIndexingEvent
        + '}';
  }
}

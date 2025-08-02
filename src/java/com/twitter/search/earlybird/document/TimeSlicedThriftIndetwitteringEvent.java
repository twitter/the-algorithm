package com.twitter.search.earlybird.document;

import com.google.common.base.Preconditions;

import com.twitter.search.common.schema.thriftjava.ThriftIndetwitteringEvent;

/**
 * Object to encapsulate {@link ThriftIndetwitteringEvent} with a time slice ID.
 */
public class TimeSlicedThriftIndetwitteringEvent {
  private final long timeSliceID;
  private final ThriftIndetwitteringEvent thriftIndetwitteringEvent;

  public TimeSlicedThriftIndetwitteringEvent(long timeSliceID, ThriftIndetwitteringEvent thriftIndetwitteringEvent) {
    Preconditions.checkNotNull(thriftIndetwitteringEvent);

    this.timeSliceID = timeSliceID;
    this.thriftIndetwitteringEvent = thriftIndetwitteringEvent;
  }

  public long getStatusID() {
    return thriftIndetwitteringEvent.getUid();
  }

  public long getTimeSliceID() {
    return timeSliceID;
  }

  public ThriftIndetwitteringEvent getThriftIndetwitteringEvent() {
    return thriftIndetwitteringEvent;
  }

  @Override
  public String toString() {
    return "TimeSlicedThriftIndetwitteringEvent{"
        + "timeSliceID=" + timeSliceID
        + ", thriftIndetwitteringEvent=" + thriftIndetwitteringEvent
        + '}';
  }
}

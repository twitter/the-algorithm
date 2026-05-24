package com.twitter.search.earlybird.document;

/**
 * DeletedStatus is a marker indicating that the specified tweet in the specified
 * timeslice has been deleted.
 */
public final class DeletedStatus {
  public final long timeSliceID;
  public final long statusID;

  public DeletedStatus(long timeSliceID, long statusID) {
    this.timeSliceID = timeSliceID;
    this.statusID = statusID;
  }
}

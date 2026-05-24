package com.twitter.search.earlybird.search;

import javax.annotation.Nullable;

import com.twitter.search.earlybird.thrift.ThriftSearchResultMetadata;

/**
 * Class that abstracts a document that matches a query we're processing in Earlybird.
 */
public class Hit implements Comparable<Hit> {
  protected long timeSliceID;
  protected long statusID;
  private boolean hasExplanation;

  @Nullable
  protected ThriftSearchResultMetadata metadata;

  public Hit(long timeSliceID, long statusID) {
    this.timeSliceID = timeSliceID;
    this.statusID = statusID;
    this.metadata = null;
  }

  public long getTimeSliceID() {
    return timeSliceID;
  }

  public long getStatusID() {
    return statusID;
  }

  @Nullable
  public ThriftSearchResultMetadata getMetadata() {
    return metadata;
  }

  public void setMetadata(ThriftSearchResultMetadata metadata) {
    this.metadata = metadata;
  }

  @Override
  public int compareTo(Hit other) {
    return -Long.compare(this.statusID, other.statusID);
  }

  @Override
  public String toString() {
    return "Hit[tweetID=" + statusID + ",timeSliceID=" + timeSliceID
        + ",score=" + (metadata == null ? "null" : metadata.getScore()) + "]";
  }

  public boolean isHasExplanation() {
    return hasExplanation;
  }

  public void setHasExplanation(boolean hasExplanation) {
    this.hasExplanation = hasExplanation;
  }
}

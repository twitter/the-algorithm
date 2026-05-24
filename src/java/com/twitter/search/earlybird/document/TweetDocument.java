package com.twitter.search.earlybird.document;

import org.apache.lucene.document.Document;

/**
 * TweetDocument is a record produced by DocumentReader and TweetIndexUpdateReader
 * for consumption by the partition indexer.
 */
public final class TweetDocument {
  private final long tweetID;
  private final long timeSliceID;
  private final long eventTimeMs;
  private final Document document;

  public TweetDocument(
      long tweetID,
      long timeSliceID,
      long eventTimeMs,
      Document document
  ) {
    this.tweetID = tweetID;
    this.timeSliceID = timeSliceID;
    this.eventTimeMs = eventTimeMs;
    this.document = document;
  }

  public long getTweetID() {
    return tweetID;
  }

  public long getTimeSliceID() {
    return timeSliceID;
  }

  public long getEventTimeMs() {
    return eventTimeMs;
  }

  public Document getDocument() {
    return document;
  }

  @Override
  public String toString() {
    return "TweetDocument{"
        + "tweetID=" + tweetID
        + ", timeSliceID=" + timeSliceID
        + ", eventTimeMs=" + eventTimeMs
        + ", document=" + document
        + '}';
  }
}

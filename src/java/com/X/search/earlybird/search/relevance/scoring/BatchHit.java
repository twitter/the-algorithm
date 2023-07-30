package com.X.search.earlybird.search.relevance.scoring;

import com.X.search.common.features.thrift.ThriftSearchResultFeatures;
import com.X.search.earlybird.search.relevance.LinearScoringData;
import com.X.search.earlybird.thrift.ThriftSearchResultMetadata;

public class BatchHit {
  private final LinearScoringData scoringData;
  private final ThriftSearchResultFeatures features;
  private final ThriftSearchResultMetadata metadata;
  private final long tweetID;
  private final long timeSliceID;

  public BatchHit(
      LinearScoringData scoringData,
      ThriftSearchResultFeatures features,
      ThriftSearchResultMetadata metadata,
      long tweetID,
      long timeSliceID
  ) {
    this.scoringData = scoringData;
    this.features = features;
    this.metadata = metadata;
    this.tweetID = tweetID;
    this.timeSliceID = timeSliceID;
  }

  public LinearScoringData getScoringData() {
    return scoringData;
  }

  public ThriftSearchResultFeatures getFeatures() {
    return features;
  }

  public ThriftSearchResultMetadata getMetadata() {
    return metadata;
  }

  public long getTweetID() {
    return tweetID;
  }

  public long getTimeSliceID() {
    return timeSliceID;
  }
}

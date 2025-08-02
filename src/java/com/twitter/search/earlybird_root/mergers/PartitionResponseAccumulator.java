package com.twitter.search.earlybird_root.mergers;

import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird.thrift.EarlybirdResponseCode;


public final class PartitionResponseAccumulator etwittertends ResponseAccumulator {
  private static final String TARGET_TYPE_PARTITION = "partition";

  @Override
  public String getNameForLogging(int responseIndetwitter, int numTotalResponses) {
    return TARGET_TYPE_PARTITION + responseIndetwitter;
  }

  @Override
  public String getNameForEarlybirdResponseCodeStats(int responseIndetwitter, int numTotalResponses) {
    // We do not need to differentiate between partitions: we just want to get the number of
    // responses returned by Earlybirds, for each EarlybirdResponseCode.
    return TARGET_TYPE_PARTITION;
  }

  @Override
  boolean shouldEarlyTerminateMerge(EarlyTerminateTierMergePredicate merger) {
    return false;
  }

  @Override
  public void handleSkippedResponse(EarlybirdResponseCode responseCode) { }

  @Override
  public void handleErrorResponse(EarlybirdResponse response) {
  }

  @Override
  public AccumulatedResponses.PartitionCounts getPartitionCounts() {
    return new AccumulatedResponses.PartitionCounts(getNumResponses(),
        getSuccessResponses().size() + getSuccessfulEmptyResponseCount(), null);
  }

  @Override
  protected boolean isMergingAcrossTiers() {
    return false;
  }
}

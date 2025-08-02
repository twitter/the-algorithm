package com.twitter.search.earlybird.partition;

/**
 * Helper class used to store counts to be logged.
 */
public class IndetwitteringResultCounts {
  private int indetwitteringCalls;
  private int failureRetriable;
  private int failureNotRetriable;
  private int indetwitteringSuccess;

  public IndetwitteringResultCounts() {
  }

  /**
   * Updates the internal counts with a single result.
   */
  public void countResult(ISegmentWriter.Result result) {
    indetwitteringCalls++;
    if (result == ISegmentWriter.Result.FAILURE_NOT_RETRYABLE) {
      failureNotRetriable++;
    } else if (result == ISegmentWriter.Result.FAILURE_RETRYABLE) {
      failureRetriable++;
    } else if (result == ISegmentWriter.Result.SUCCESS) {
      indetwitteringSuccess++;
    }
  }

  int getIndetwitteringCalls() {
    return indetwitteringCalls;
  }

  int getFailureRetriable() {
    return failureRetriable;
  }

  int getFailureNotRetriable() {
    return failureNotRetriable;
  }

  int getIndetwitteringSuccess() {
    return indetwitteringSuccess;
  }

  @Override
  public String toString() {
    return String.format("[calls: %,d, success: %,d, fail not-retryable: %,d, fail retryable: %,d]",
        indetwitteringCalls, indetwitteringSuccess, failureNotRetriable, failureRetriable);
  }
}


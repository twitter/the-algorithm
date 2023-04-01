package com.twitter.search.earlybird.partition;

/**
 * Helper class used to store counts to be logged.
 */
public class IndexingResultCounts {
  private int indexingCalls;
  private int failureRetriable;
  private int failureNotRetriable;
  private int indexingSuccess;

  public IndexingResultCounts() {
  }

  /**
   * Updates the internal counts with a single result.
   */
  public void countResult(ISegmentWriter.Result result) {
    indexingCalls++;
    if (result == ISegmentWriter.Result.FAILURE_NOT_RETRYABLE) {
      failureNotRetriable++;
    } else if (result == ISegmentWriter.Result.FAILURE_RETRYABLE) {
      failureRetriable++;
    } else if (result == ISegmentWriter.Result.SUCCESS) {
      indexingSuccess++;
    }
  }

  int getIndexingCalls() {
    return indexingCalls;
  }

  int getFailureRetriable() {
    return failureRetriable;
  }

  int getFailureNotRetriable() {
    return failureNotRetriable;
  }

  int getIndexingSuccess() {
    return indexingSuccess;
  }

  @Override
  public String toString() {
    return String.format("[calls: %,d, success: %,d, fail not-retryable: %,d, fail retryable: %,d]",
        indexingCalls, indexingSuccess, failureNotRetriable, failureRetriable);
  }
}


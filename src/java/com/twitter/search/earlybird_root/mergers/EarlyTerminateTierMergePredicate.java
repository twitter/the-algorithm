package com.twitter.search.earlybird_root.mergers;

public interface EarlyTerminateTierMergePredicate {
  /**
   * Do we have enough results so far that we can early terminate and not continue onto next tier?
   */
  boolean shouldEarlyTerminateTierMerge(int totalResultsFromSuccessfulShards,
                                        boolean foundEarlyTermination);
}

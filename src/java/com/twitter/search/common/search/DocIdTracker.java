package com.twitter.search.common.search;

/**
 * Provide an accessor for a doc ID. This is useful for classes that iterate through doc IDs
 * and maintain a "last seen" doc ID.
 */
public interface DocIdTracker {
  /**
   * Retrieve current doc ID
   */
  int getCurrentDocId();
}

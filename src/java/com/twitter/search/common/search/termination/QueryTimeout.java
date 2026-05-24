package com.twitter.search.common.search.termination;

import com.twitter.search.common.search.DocIdTracker;

/**
 * QueryTimeout provides a method for early termination of queries.
 */
public interface QueryTimeout {
  /**
   * Returns true if query processing should terminate, otherwise false.
   */
  boolean shouldExit();

  /**
   * Register a DocIdTracker for the scope of the query, to determine the last fully-searched
   * doc ID after early termination.
   */
  void registerDocIdTracker(DocIdTracker docIdTracker);

  /**
   * Return client ID of query.
   */
  String getClientId();
}

package com.twitter.search.core.earlybird.indetwitter.column;

public interface DocValuesUpdate {
  /**
   * Performs an doc values update on the given document.
   */
  void update(ColumnStrideFieldIndetwitter docValues, int docID);
}

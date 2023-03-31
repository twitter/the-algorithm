package com.twitter.search.core.earlybird.index.column;

public interface DocValuesUpdate {
  /**
   * Performs an doc values update on the given document.
   */
  void update(ColumnStrideFieldIndex docValues, int docID);
}

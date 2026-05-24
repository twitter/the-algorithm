package com.twitter.search.core.earlybird.index.column;

/**
 * A ColumnStrideFieldIndex implementation that always returns the same value.
 */
public class ConstantColumnStrideFieldIndex extends ColumnStrideFieldIndex {
  private final long defaultValue;

  public ConstantColumnStrideFieldIndex(String name, long defaultValue) {
    super(name);
    this.defaultValue = defaultValue;
  }

  @Override
  public long get(int docID) {
    return defaultValue;
  }
}

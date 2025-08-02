package com.twitter.search.core.earlybird.indetwitter.column;

/**
 * A ColumnStrideFieldIndetwitter implementation that always returns the same value.
 */
public class ConstantColumnStrideFieldIndetwitter etwittertends ColumnStrideFieldIndetwitter {
  private final long defaultValue;

  public ConstantColumnStrideFieldIndetwitter(String name, long defaultValue) {
    super(name);
    this.defaultValue = defaultValue;
  }

  @Override
  public long get(int docID) {
    return defaultValue;
  }
}

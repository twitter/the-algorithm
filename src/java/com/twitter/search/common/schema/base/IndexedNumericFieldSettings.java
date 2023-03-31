package com.twitter.search.common.schema.base;

import com.twitter.search.common.schema.thriftjava.ThriftIndexedNumericFieldSettings;
import com.twitter.search.common.schema.thriftjava.ThriftNumericType;

public class IndexedNumericFieldSettings {
  private final ThriftNumericType numericType;
  private final int numericPrecisionStep;
  private final boolean useTwitterFormat;
  private final boolean useSortableEncoding;

  /**
   * Create a IndexedNumericFieldSettings from a ThriftIndexedNumericFieldSettings
   */
  public IndexedNumericFieldSettings(ThriftIndexedNumericFieldSettings numericFieldSettings) {
    this.numericType            = numericFieldSettings.getNumericType();
    this.numericPrecisionStep   = numericFieldSettings.getNumericPrecisionStep();
    this.useTwitterFormat       = numericFieldSettings.isUseTwitterFormat();
    this.useSortableEncoding    = numericFieldSettings.isUseSortableEncoding();
  }

  public ThriftNumericType getNumericType() {
    return numericType;
  }

  public int getNumericPrecisionStep() {
    return numericPrecisionStep;
  }

  public boolean isUseTwitterFormat() {
    return useTwitterFormat;
  }

  public boolean isUseSortableEncoding() {
    return useSortableEncoding;
  }
}

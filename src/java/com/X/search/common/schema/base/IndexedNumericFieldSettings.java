package com.X.search.common.schema.base;

import com.X.search.common.schema.thriftjava.ThriftIndexedNumericFieldSettings;
import com.X.search.common.schema.thriftjava.ThriftNumericType;

public class IndexedNumericFieldSettings {
  private final ThriftNumericType numericType;
  private final int numericPrecisionStep;
  private final boolean useXFormat;
  private final boolean useSortableEncoding;

  /**
   * Create a IndexedNumericFieldSettings from a ThriftIndexedNumericFieldSettings
   */
  public IndexedNumericFieldSettings(ThriftIndexedNumericFieldSettings numericFieldSettings) {
    this.numericType            = numericFieldSettings.getNumericType();
    this.numericPrecisionStep   = numericFieldSettings.getNumericPrecisionStep();
    this.useXFormat       = numericFieldSettings.isUseXFormat();
    this.useSortableEncoding    = numericFieldSettings.isUseSortableEncoding();
  }

  public ThriftNumericType getNumericType() {
    return numericType;
  }

  public int getNumericPrecisionStep() {
    return numericPrecisionStep;
  }

  public boolean isUseXFormat() {
    return useXFormat;
  }

  public boolean isUseSortableEncoding() {
    return useSortableEncoding;
  }
}

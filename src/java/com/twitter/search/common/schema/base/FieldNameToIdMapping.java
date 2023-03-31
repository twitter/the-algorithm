package com.twitter.search.common.schema.base;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

/**
 * Maps from fieldName to fieldIDs.
 */
public abstract class FieldNameToIdMapping {
  /**
   * Returns field ID for the given fieldName.
   * Can throw unchecked exceptions is the fieldName is not known to Earlybird.
   */
  public abstract int getFieldID(String fieldName);

  /**
   * Wrap the given map into a fieldNameToIdMapping instance.
   */
  public static FieldNameToIdMapping newFieldNameToIdMapping(Map<String, Integer> map) {
    final ImmutableMap<String, Integer> immutableMap = ImmutableMap.copyOf(map);
    return new FieldNameToIdMapping() {
      @Override public int getFieldID(String fieldName) {
        return immutableMap.get(fieldName);
      }
    };
  }
}

package com.twitter.search.core.earlybird.index;

import org.apache.lucene.document.Field;
import org.apache.lucene.index.DocValuesType;

import com.twitter.search.common.schema.base.EarlybirdFieldType;

public class EarlybirdIndexableField extends Field {

  /**
   * Creates a new indexable field with the given name, value and {@link EarlybirdFieldType}.
   */
  public EarlybirdIndexableField(String name, Object value, EarlybirdFieldType fieldType) {
    super(name, fieldType);
    if (fieldType.docValuesType() == DocValuesType.NUMERIC) {
      if (value instanceof Number) {
        super.fieldsData = ((Number) value).longValue();
      } else {
        throw new IllegalArgumentException("value not a number: " + value.getClass());
      }
    }
  }

}

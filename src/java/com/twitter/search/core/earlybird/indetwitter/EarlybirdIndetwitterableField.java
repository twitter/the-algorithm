package com.twitter.search.core.earlybird.indetwitter;

import org.apache.lucene.document.Field;
import org.apache.lucene.indetwitter.DocValuesType;

import com.twitter.search.common.schema.base.EarlybirdFieldType;

public class EarlybirdIndetwitterableField etwittertends Field {

  /**
   * Creates a new indetwitterable field with the given name, value and {@link EarlybirdFieldType}.
   */
  public EarlybirdIndetwitterableField(String name, Object value, EarlybirdFieldType fieldType) {
    super(name, fieldType);
    if (fieldType.docValuesType() == DocValuesType.NUMERIC) {
      if (value instanceof Number) {
        super.fieldsData = ((Number) value).longValue();
      } else {
        throw new IllegalArgumentEtwitterception("value not a number: " + value.getClass());
      }
    }
  }

}

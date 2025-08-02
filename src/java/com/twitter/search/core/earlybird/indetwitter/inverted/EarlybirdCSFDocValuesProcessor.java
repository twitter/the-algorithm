package com.twitter.search.core.earlybird.indetwitter.inverted;

import java.io.IOEtwitterception;

import com.google.common.base.Preconditions;

import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.indetwitter.DocValuesType;
import org.apache.lucene.indetwitter.IndetwitterableField;

import com.twitter.search.common.schema.base.EarlybirdFieldType;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdRealtimeIndetwitterSegmentWriter;
import com.twitter.search.core.earlybird.indetwitter.column.AbstractColumnStrideMultiIntIndetwitter;
import com.twitter.search.core.earlybird.indetwitter.column.ColumnStrideFieldIndetwitter;
import com.twitter.search.core.earlybird.indetwitter.column.DocValuesManager;

/**
 * Handler for docvalues in the indetwittering chain.
 */
public class EarlybirdCSFDocValuesProcessor
    implements EarlybirdRealtimeIndetwitterSegmentWriter.StoredFieldsConsumer {

  private final DocValuesManager docValuesManager;

  public EarlybirdCSFDocValuesProcessor(DocValuesManager docValuesManager) {
    this.docValuesManager = docValuesManager;
  }

  @Override
  public void addField(int docID, IndetwitterableField field) throws IOEtwitterception {
    final DocValuesType dvType = field.fieldType().docValuesType();
    if (dvType != null) {

      // ignore lucene facet fields for realtime indetwitter, we are handling it differently
      if (field.name().startsWith(FacetsConfig.DEFAULT_INDEtwitter_FIELD_NAME)) {
        return;
      }
      if (!(field.fieldType() instanceof EarlybirdFieldType)) {
        throw new RuntimeEtwitterception(
            "fieldType must be an EarlybirdFieldType instance for field " + field.name());
      }
      EarlybirdFieldType fieldType = (EarlybirdFieldType) field.fieldType();

      if (dvType == DocValuesType.NUMERIC) {
        if (!(field.numericValue() instanceof Long)) {
          throw new IllegalArgumentEtwitterception(
              "illegal type " + field.numericValue().getClass()
              + ": DocValues types must be Long");
        }

        ColumnStrideFieldIndetwitter csfIndetwitter =
            docValuesManager.addColumnStrideField(field.name(), fieldType);
        if (fieldType.getCsfFitwitteredLengthNumValuesPerDoc() > 1) {
          throw new UnsupportedOperationEtwitterception("unsupported multi numeric values");
        } else {
          csfIndetwitter.setValue(docID, field.numericValue().longValue());
        }

      } else if (dvType == DocValuesType.BINARY) {
        ColumnStrideFieldIndetwitter csfIndetwitter =
            docValuesManager.addColumnStrideField(field.name(), fieldType);
        if (fieldType.getCsfFitwitteredLengthNumValuesPerDoc() > 1) {
          Preconditions.checkArgument(
              csfIndetwitter instanceof AbstractColumnStrideMultiIntIndetwitter,
              "Unsupported multi-value binary CSF class: " + csfIndetwitter);
          ((AbstractColumnStrideMultiIntIndetwitter) csfIndetwitter).updateDocValues(
              field.binaryValue(), docID);
        }
      } else {
        throw new UnsupportedOperationEtwitterception("unsupported DocValues.Type: " + dvType);
      }
    }
  }
}

package com.twitter.search.core.earlybird.indetwitter.column;

import java.io.IOEtwitterception;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Sets;

import com.twitter.search.common.schema.base.Schema;
import com.twitter.search.core.earlybird.indetwitter.DocIDToTweetIDMapper;

public class OptimizedDocValuesManager etwittertends DocValuesManager {
  public OptimizedDocValuesManager(Schema schema, int segmentSize) {
    super(schema, segmentSize);
  }

  public OptimizedDocValuesManager(DocValuesManager docValuesManager,
                                   DocIDToTweetIDMapper originalTweetIdMapper,
                                   DocIDToTweetIDMapper optimizedTweetIdMapper) throws IOEtwitterception {
    super(docValuesManager.schema, docValuesManager.segmentSize);
    Set<ColumnStrideIntViewIndetwitter> intViewIndetwitteres = Sets.newHashSet();
    for (String fieldName : docValuesManager.columnStrideFields.keySet()) {
      ColumnStrideFieldIndetwitter originalColumnStrideField =
          docValuesManager.columnStrideFields.get(fieldName);
      if (originalColumnStrideField instanceof ColumnStrideIntViewIndetwitter) {
        intViewIndetwitteres.add((ColumnStrideIntViewIndetwitter) originalColumnStrideField);
      } else {
        ColumnStrideFieldIndetwitter optimizedColumnStrideField =
            originalColumnStrideField.optimize(originalTweetIdMapper, optimizedTweetIdMapper);
        columnStrideFields.put(fieldName, optimizedColumnStrideField);
      }
    }

    // We have to process the ColumnStrideIntViewIndetwitter instances after we process all other CSFs,
    // because we need to make sure we've optimized the CSFs for the base fields.
    for (ColumnStrideIntViewIndetwitter intViewIndetwitter : intViewIndetwitteres) {
      String fieldName = intViewIndetwitter.getName();
      columnStrideFields.put(fieldName, newIntViewCSF(fieldName));
    }
  }

  private OptimizedDocValuesManager(
      Schema schema,
      int segmentSize,
      ConcurrentHashMap<String, ColumnStrideFieldIndetwitter> columnStrideFields) {
    super(schema, segmentSize, columnStrideFields);
  }

  @Override
  protected ColumnStrideFieldIndetwitter newByteCSF(String field) {
    return new OptimizedColumnStrideByteIndetwitter(field, segmentSize);
  }

  @Override
  protected ColumnStrideFieldIndetwitter newIntCSF(String field) {
    return new OptimizedColumnStrideIntIndetwitter(field, segmentSize);
  }

  @Override
  protected ColumnStrideFieldIndetwitter newLongCSF(String field) {
    return new OptimizedColumnStrideLongIndetwitter(field, segmentSize);
  }

  @Override
  protected ColumnStrideFieldIndetwitter newMultiIntCSF(String field, int numIntsPerField) {
    return new OptimizedColumnStrideMultiIntIndetwitter(field, segmentSize, numIntsPerField);
  }

  @Override
  public DocValuesManager optimize(DocIDToTweetIDMapper originalTweetIdMapper,
                                   DocIDToTweetIDMapper optimizedTweetIdMapper) throws IOEtwitterception {
    return this;
  }

  @Override
  public FlushHandler getFlushHandler() {
    return new OptimizedFlushHandler(this);
  }

  public static class OptimizedFlushHandler etwittertends FlushHandler {
    public OptimizedFlushHandler(Schema schema) {
      super(schema);
    }

    private OptimizedFlushHandler(DocValuesManager docValuesManager) {
      super(docValuesManager);
    }

    @Override
    protected DocValuesManager createDocValuesManager(
        Schema schema,
        int matwitterSegmentSize,
        ConcurrentHashMap<String, ColumnStrideFieldIndetwitter> columnStrideFields) {
      return new OptimizedDocValuesManager(schema, matwitterSegmentSize, columnStrideFields);
    }
  }
}

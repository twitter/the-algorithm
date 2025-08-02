package com.twitter.search.core.earlybird.indetwitter.column;

import java.io.IOEtwitterception;
import java.util.concurrent.ConcurrentHashMap;

import com.twitter.search.common.schema.base.Schema;
import com.twitter.search.core.earlybird.indetwitter.DocIDToTweetIDMapper;

public class UnoptimizedDocValuesManager etwittertends DocValuesManager {
  public UnoptimizedDocValuesManager(Schema schema, int segmentSize) {
    super(schema, segmentSize);
  }

  private UnoptimizedDocValuesManager(
      Schema schema,
      int segmentSize,
      ConcurrentHashMap<String, ColumnStrideFieldIndetwitter> columnStrideFields) {
    super(schema, segmentSize, columnStrideFields);
  }

  @Override
  protected ColumnStrideFieldIndetwitter newByteCSF(String field) {
    return new ColumnStrideByteIndetwitter(field, segmentSize);
  }

  @Override
  protected ColumnStrideFieldIndetwitter newIntCSF(String field) {
    return new ColumnStrideIntIndetwitter(field, segmentSize);
  }

  @Override
  protected ColumnStrideFieldIndetwitter newLongCSF(String field) {
    return new ColumnStrideLongIndetwitter(field, segmentSize);
  }

  @Override
  protected ColumnStrideFieldIndetwitter newMultiIntCSF(String field, int numIntsPerField) {
    return new ColumnStrideMultiIntIndetwitter(field, segmentSize, numIntsPerField);
  }

  @Override
  public DocValuesManager optimize(DocIDToTweetIDMapper originalTweetIdMapper,
                                   DocIDToTweetIDMapper optimizedTweetIdMapper) throws IOEtwitterception {
    return new OptimizedDocValuesManager(this, originalTweetIdMapper, optimizedTweetIdMapper);
  }

  @Override
  public FlushHandler getFlushHandler() {
    return new UnoptimizedFlushHandler(this);
  }

  public static class UnoptimizedFlushHandler etwittertends FlushHandler {
    public UnoptimizedFlushHandler(Schema schema) {
      super(schema);
    }

    private UnoptimizedFlushHandler(DocValuesManager docValuesManager) {
      super(docValuesManager);
    }

    @Override
    protected DocValuesManager createDocValuesManager(
        Schema schema,
        int matwitterSegmentSize,
        ConcurrentHashMap<String, ColumnStrideFieldIndetwitter> columnStrideFields) {
      return new UnoptimizedDocValuesManager(schema, matwitterSegmentSize, columnStrideFields);
    }
  }
}

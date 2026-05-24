package com.twitter.search.core.earlybird.index.column;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import com.twitter.search.common.schema.base.Schema;
import com.twitter.search.core.earlybird.index.DocIDToTweetIDMapper;

public class UnoptimizedDocValuesManager extends DocValuesManager {
  public UnoptimizedDocValuesManager(Schema schema, int segmentSize) {
    super(schema, segmentSize);
  }

  private UnoptimizedDocValuesManager(
      Schema schema,
      int segmentSize,
      ConcurrentHashMap<String, ColumnStrideFieldIndex> columnStrideFields) {
    super(schema, segmentSize, columnStrideFields);
  }

  @Override
  protected ColumnStrideFieldIndex newByteCSF(String field) {
    return new ColumnStrideByteIndex(field, segmentSize);
  }

  @Override
  protected ColumnStrideFieldIndex newIntCSF(String field) {
    return new ColumnStrideIntIndex(field, segmentSize);
  }

  @Override
  protected ColumnStrideFieldIndex newLongCSF(String field) {
    return new ColumnStrideLongIndex(field, segmentSize);
  }

  @Override
  protected ColumnStrideFieldIndex newMultiIntCSF(String field, int numIntsPerField) {
    return new ColumnStrideMultiIntIndex(field, segmentSize, numIntsPerField);
  }

  @Override
  public DocValuesManager optimize(DocIDToTweetIDMapper originalTweetIdMapper,
                                   DocIDToTweetIDMapper optimizedTweetIdMapper) throws IOException {
    return new OptimizedDocValuesManager(this, originalTweetIdMapper, optimizedTweetIdMapper);
  }

  @Override
  public FlushHandler getFlushHandler() {
    return new UnoptimizedFlushHandler(this);
  }

  public static class UnoptimizedFlushHandler extends FlushHandler {
    public UnoptimizedFlushHandler(Schema schema) {
      super(schema);
    }

    private UnoptimizedFlushHandler(DocValuesManager docValuesManager) {
      super(docValuesManager);
    }

    @Override
    protected DocValuesManager createDocValuesManager(
        Schema schema,
        int maxSegmentSize,
        ConcurrentHashMap<String, ColumnStrideFieldIndex> columnStrideFields) {
      return new UnoptimizedDocValuesManager(schema, maxSegmentSize, columnStrideFields);
    }
  }
}

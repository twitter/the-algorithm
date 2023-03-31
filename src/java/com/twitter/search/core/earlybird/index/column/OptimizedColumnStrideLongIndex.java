package com.twitter.search.core.earlybird.index.column;

import java.io.IOException;

import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.DataSerializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.common.util.io.flushable.Flushable;
import com.twitter.search.core.earlybird.index.DocIDToTweetIDMapper;

public class OptimizedColumnStrideLongIndex extends ColumnStrideFieldIndex implements Flushable {
  private final long[] values;

  public OptimizedColumnStrideLongIndex(String name, int maxSize) {
    super(name);
    values = new long[maxSize];
  }

  public OptimizedColumnStrideLongIndex(
      ColumnStrideLongIndex columnStrideLongIndex,
      DocIDToTweetIDMapper originalTweetIdMapper,
      DocIDToTweetIDMapper optimizedTweetIdMapper) throws IOException {
    super(columnStrideLongIndex.getName());
    int maxDocId = optimizedTweetIdMapper.getPreviousDocID(Integer.MAX_VALUE);
    values = new long[maxDocId + 1];

    int docId = optimizedTweetIdMapper.getNextDocID(Integer.MIN_VALUE);
    while (docId != DocIDToTweetIDMapper.ID_NOT_FOUND) {
      int originalDocId = originalTweetIdMapper.getDocID(optimizedTweetIdMapper.getTweetID(docId));
      setValue(docId, columnStrideLongIndex.get(originalDocId));
      docId = optimizedTweetIdMapper.getNextDocID(docId);
    }
  }

  private OptimizedColumnStrideLongIndex(String name, long[] values) {
    super(name);
    this.values = values;
  }

  @Override
  public void setValue(int docID, long value) {
    this.values[docID] = value;
  }

  @Override
  public long get(int docID) {
    return values[docID];
  }

  @Override
  public FlushHandler getFlushHandler() {
    return new FlushHandler(this);
  }

  public static final class FlushHandler extends Flushable.Handler<OptimizedColumnStrideLongIndex> {
    private static final String NAME_PROP_NAME = "fieldName";

    public FlushHandler() {
      super();
    }

    public FlushHandler(OptimizedColumnStrideLongIndex objectToFlush) {
      super(objectToFlush);
    }

    @Override
    protected void doFlush(FlushInfo flushInfo, DataSerializer out) throws IOException {
      OptimizedColumnStrideLongIndex columnStrideLongIndex = getObjectToFlush();
      flushInfo.addStringProperty(NAME_PROP_NAME, columnStrideLongIndex.getName());
      out.writeLongArray(columnStrideLongIndex.values);
    }

    @Override
    protected OptimizedColumnStrideLongIndex doLoad(FlushInfo flushInfo, DataDeserializer in)
        throws IOException {
      long[] values = in.readLongArray();
      return new OptimizedColumnStrideLongIndex(
          flushInfo.getStringProperty(NAME_PROP_NAME), values);
    }
  }
}

package com.twitter.search.core.earlybird.indetwitter.column;

import java.io.IOEtwitterception;

import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.DataSerializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.common.util.io.flushable.Flushable;
import com.twitter.search.core.earlybird.indetwitter.DocIDToTweetIDMapper;

public class OptimizedColumnStrideLongIndetwitter etwittertends ColumnStrideFieldIndetwitter implements Flushable {
  private final long[] values;

  public OptimizedColumnStrideLongIndetwitter(String name, int matwitterSize) {
    super(name);
    values = new long[matwitterSize];
  }

  public OptimizedColumnStrideLongIndetwitter(
      ColumnStrideLongIndetwitter columnStrideLongIndetwitter,
      DocIDToTweetIDMapper originalTweetIdMapper,
      DocIDToTweetIDMapper optimizedTweetIdMapper) throws IOEtwitterception {
    super(columnStrideLongIndetwitter.getName());
    int matwitterDocId = optimizedTweetIdMapper.getPreviousDocID(Integer.MAtwitter_VALUE);
    values = new long[matwitterDocId + 1];

    int docId = optimizedTweetIdMapper.getNetwittertDocID(Integer.MIN_VALUE);
    while (docId != DocIDToTweetIDMapper.ID_NOT_FOUND) {
      int originalDocId = originalTweetIdMapper.getDocID(optimizedTweetIdMapper.getTweetID(docId));
      setValue(docId, columnStrideLongIndetwitter.get(originalDocId));
      docId = optimizedTweetIdMapper.getNetwittertDocID(docId);
    }
  }

  private OptimizedColumnStrideLongIndetwitter(String name, long[] values) {
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

  public static final class FlushHandler etwittertends Flushable.Handler<OptimizedColumnStrideLongIndetwitter> {
    private static final String NAME_PROP_NAME = "fieldName";

    public FlushHandler() {
      super();
    }

    public FlushHandler(OptimizedColumnStrideLongIndetwitter objectToFlush) {
      super(objectToFlush);
    }

    @Override
    protected void doFlush(FlushInfo flushInfo, DataSerializer out) throws IOEtwitterception {
      OptimizedColumnStrideLongIndetwitter columnStrideLongIndetwitter = getObjectToFlush();
      flushInfo.addStringProperty(NAME_PROP_NAME, columnStrideLongIndetwitter.getName());
      out.writeLongArray(columnStrideLongIndetwitter.values);
    }

    @Override
    protected OptimizedColumnStrideLongIndetwitter doLoad(FlushInfo flushInfo, DataDeserializer in)
        throws IOEtwitterception {
      long[] values = in.readLongArray();
      return new OptimizedColumnStrideLongIndetwitter(
          flushInfo.getStringProperty(NAME_PROP_NAME), values);
    }
  }
}

package com.twitter.search.core.earlybird.index.column;

import java.io.IOException;

import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.DataSerializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.common.util.io.flushable.Flushable;
import com.twitter.search.core.earlybird.index.DocIDToTweetIDMapper;

public class OptimizedColumnStrideIntIndex extends ColumnStrideFieldIndex implements Flushable {
  private final int[] values;

  public OptimizedColumnStrideIntIndex(String name, int maxSize) {
    super(name);
    values = new int[maxSize];
  }

  public OptimizedColumnStrideIntIndex(
      ColumnStrideIntIndex columnStrideIntIndex,
      DocIDToTweetIDMapper originalTweetIdMapper,
      DocIDToTweetIDMapper optimizedTweetIdMapper) throws IOException {
    super(columnStrideIntIndex.getName());
    int maxDocId = optimizedTweetIdMapper.getPreviousDocID(Integer.MAX_VALUE);
    values = new int[maxDocId + 1];

    int docId = optimizedTweetIdMapper.getNextDocID(Integer.MIN_VALUE);
    while (docId != DocIDToTweetIDMapper.ID_NOT_FOUND) {
      int originalDocId = originalTweetIdMapper.getDocID(optimizedTweetIdMapper.getTweetID(docId));
      setValue(docId, columnStrideIntIndex.get(originalDocId));
      docId = optimizedTweetIdMapper.getNextDocID(docId);
    }
  }

  private OptimizedColumnStrideIntIndex(String name, int[] values) {
    super(name);
    this.values = values;
  }

  @Override
  public void setValue(int docID, long value) {
    this.values[docID] = (int) value;
  }

  @Override
  public long get(int docID) {
    return values[docID];
  }

  @Override
  public FlushHandler getFlushHandler() {
    return new FlushHandler(this);
  }

  public static final class FlushHandler extends Flushable.Handler<OptimizedColumnStrideIntIndex> {
    private static final String NAME_PROP_NAME = "fieldName";

    public FlushHandler() {
      super();
    }

    public FlushHandler(OptimizedColumnStrideIntIndex objectToFlush) {
      super(objectToFlush);
    }

    @Override
    protected void doFlush(FlushInfo flushInfo, DataSerializer out) throws IOException {
      OptimizedColumnStrideIntIndex columnStrideIntIndex = getObjectToFlush();
      flushInfo.addStringProperty(NAME_PROP_NAME, columnStrideIntIndex.getName());
      out.writeIntArray(columnStrideIntIndex.values);
    }

    @Override
    protected OptimizedColumnStrideIntIndex doLoad(FlushInfo flushInfo, DataDeserializer in)
        throws IOException {
      int[] values = in.readIntArray();
      return new OptimizedColumnStrideIntIndex(
          flushInfo.getStringProperty(NAME_PROP_NAME), values);
    }
  }
}

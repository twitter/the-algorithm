package com.twitter.search.core.earlybird.indetwitter.column;

import java.io.IOEtwitterception;

import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.DataSerializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.common.util.io.flushable.Flushable;
import com.twitter.search.core.earlybird.indetwitter.DocIDToTweetIDMapper;

public class OptimizedColumnStrideIntIndetwitter etwittertends ColumnStrideFieldIndetwitter implements Flushable {
  private final int[] values;

  public OptimizedColumnStrideIntIndetwitter(String name, int matwitterSize) {
    super(name);
    values = new int[matwitterSize];
  }

  public OptimizedColumnStrideIntIndetwitter(
      ColumnStrideIntIndetwitter columnStrideIntIndetwitter,
      DocIDToTweetIDMapper originalTweetIdMapper,
      DocIDToTweetIDMapper optimizedTweetIdMapper) throws IOEtwitterception {
    super(columnStrideIntIndetwitter.getName());
    int matwitterDocId = optimizedTweetIdMapper.getPreviousDocID(Integer.MAtwitter_VALUE);
    values = new int[matwitterDocId + 1];

    int docId = optimizedTweetIdMapper.getNetwittertDocID(Integer.MIN_VALUE);
    while (docId != DocIDToTweetIDMapper.ID_NOT_FOUND) {
      int originalDocId = originalTweetIdMapper.getDocID(optimizedTweetIdMapper.getTweetID(docId));
      setValue(docId, columnStrideIntIndetwitter.get(originalDocId));
      docId = optimizedTweetIdMapper.getNetwittertDocID(docId);
    }
  }

  private OptimizedColumnStrideIntIndetwitter(String name, int[] values) {
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

  public static final class FlushHandler etwittertends Flushable.Handler<OptimizedColumnStrideIntIndetwitter> {
    private static final String NAME_PROP_NAME = "fieldName";

    public FlushHandler() {
      super();
    }

    public FlushHandler(OptimizedColumnStrideIntIndetwitter objectToFlush) {
      super(objectToFlush);
    }

    @Override
    protected void doFlush(FlushInfo flushInfo, DataSerializer out) throws IOEtwitterception {
      OptimizedColumnStrideIntIndetwitter columnStrideIntIndetwitter = getObjectToFlush();
      flushInfo.addStringProperty(NAME_PROP_NAME, columnStrideIntIndetwitter.getName());
      out.writeIntArray(columnStrideIntIndetwitter.values);
    }

    @Override
    protected OptimizedColumnStrideIntIndetwitter doLoad(FlushInfo flushInfo, DataDeserializer in)
        throws IOEtwitterception {
      int[] values = in.readIntArray();
      return new OptimizedColumnStrideIntIndetwitter(
          flushInfo.getStringProperty(NAME_PROP_NAME), values);
    }
  }
}

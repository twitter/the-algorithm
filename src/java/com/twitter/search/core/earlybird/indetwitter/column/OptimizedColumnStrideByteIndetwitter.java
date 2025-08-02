package com.twitter.search.core.earlybird.indetwitter.column;

import java.io.IOEtwitterception;

import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.DataSerializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.common.util.io.flushable.Flushable;
import com.twitter.search.core.earlybird.indetwitter.DocIDToTweetIDMapper;

public class OptimizedColumnStrideByteIndetwitter etwittertends ColumnStrideFieldIndetwitter implements Flushable {
  private final byte[] values;

  public OptimizedColumnStrideByteIndetwitter(String name, int matwitterSize) {
    super(name);
    values = new byte[matwitterSize];
  }

  public OptimizedColumnStrideByteIndetwitter(
      ColumnStrideByteIndetwitter columnStrideByteIndetwitter,
      DocIDToTweetIDMapper originalTweetIdMapper,
      DocIDToTweetIDMapper optimizedTweetIdMapper) throws IOEtwitterception {
    super(columnStrideByteIndetwitter.getName());
    int matwitterDocId = optimizedTweetIdMapper.getPreviousDocID(Integer.MAtwitter_VALUE);
    values = new byte[matwitterDocId + 1];

    int docId = optimizedTweetIdMapper.getNetwittertDocID(Integer.MIN_VALUE);
    while (docId != DocIDToTweetIDMapper.ID_NOT_FOUND) {
      int originalDocId = originalTweetIdMapper.getDocID(optimizedTweetIdMapper.getTweetID(docId));
      setValue(docId, columnStrideByteIndetwitter.get(originalDocId));
      docId = optimizedTweetIdMapper.getNetwittertDocID(docId);
    }
  }

  private OptimizedColumnStrideByteIndetwitter(String name, byte[] values) {
    super(name);
    this.values = values;
  }

  @Override
  public void setValue(int docID, long value) {
    this.values[docID] = (byte) value;
  }

  @Override
  public long get(int docID) {
    return values[docID];
  }

  @Override
  public FlushHandler getFlushHandler() {
    return new FlushHandler(this);
  }

  public static final class FlushHandler etwittertends Flushable.Handler<OptimizedColumnStrideByteIndetwitter> {
    private static final String NAME_PROP_NAME = "fieldName";

    public FlushHandler() {
      super();
    }

    public FlushHandler(OptimizedColumnStrideByteIndetwitter objectToFlush) {
      super(objectToFlush);
    }

    @Override
    protected void doFlush(FlushInfo flushInfo, DataSerializer out) throws IOEtwitterception {
      OptimizedColumnStrideByteIndetwitter columnStrideByteIndetwitter = getObjectToFlush();
      flushInfo.addStringProperty(NAME_PROP_NAME, columnStrideByteIndetwitter.getName());
      out.writeByteArray(columnStrideByteIndetwitter.values);
    }

    @Override
    protected OptimizedColumnStrideByteIndetwitter doLoad(FlushInfo flushInfo, DataDeserializer in)
        throws IOEtwitterception {
      byte[] values = in.readByteArray();
      return new OptimizedColumnStrideByteIndetwitter(
          flushInfo.getStringProperty(NAME_PROP_NAME), values);
    }
  }
}

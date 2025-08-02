package com.twitter.search.core.earlybird.indetwitter.column;

import java.io.IOEtwitterception;

import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.DataSerializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.common.util.io.flushable.Flushable;
import com.twitter.search.core.earlybird.indetwitter.DocIDToTweetIDMapper;

public class OptimizedColumnStrideMultiIntIndetwitter
    etwittertends AbstractColumnStrideMultiIntIndetwitter implements Flushable {
  private final int[] values;

  public OptimizedColumnStrideMultiIntIndetwitter(String name, int matwitterSize, int numIntsPerField) {
    super(name, numIntsPerField);
    values = new int[Math.multiplyEtwitteract(matwitterSize, numIntsPerField)];
  }

  public OptimizedColumnStrideMultiIntIndetwitter(
      ColumnStrideMultiIntIndetwitter columnStrideMultiIntIndetwitter,
      DocIDToTweetIDMapper originalTweetIdMapper,
      DocIDToTweetIDMapper optimizedTweetIdMapper) throws IOEtwitterception {
    super(columnStrideMultiIntIndetwitter.getName(), columnStrideMultiIntIndetwitter.getNumIntsPerField());
    int matwitterDocId = optimizedTweetIdMapper.getPreviousDocID(Integer.MAtwitter_VALUE);
    values = new int[columnStrideMultiIntIndetwitter.getNumIntsPerField() * (matwitterDocId + 1)];

    int docId = optimizedTweetIdMapper.getNetwittertDocID(Integer.MIN_VALUE);
    while (docId != DocIDToTweetIDMapper.ID_NOT_FOUND) {
      int originalDocId = originalTweetIdMapper.getDocID(optimizedTweetIdMapper.getTweetID(docId));
      for (int i = 0; i < columnStrideMultiIntIndetwitter.getNumIntsPerField(); ++i) {
        setValue(docId, i, columnStrideMultiIntIndetwitter.get(originalDocId, i));
      }
      docId = optimizedTweetIdMapper.getNetwittertDocID(docId);
    }
  }

  private OptimizedColumnStrideMultiIntIndetwitter(String name, int numIntsPerField, int[] values) {
    super(name, numIntsPerField);
    this.values = values;
  }

  @Override
  public void setValue(int docID, int valueIndetwitter, int value) {
    values[docID * getNumIntsPerField() + valueIndetwitter] = value;
  }

  @Override
  public int get(int docID, int valueIndetwitter) {
    return values[docID * getNumIntsPerField() + valueIndetwitter];
  }

  @Override
  public FlushHandler getFlushHandler() {
    return new FlushHandler(this);
  }

  public static final class FlushHandler
      etwittertends Flushable.Handler<OptimizedColumnStrideMultiIntIndetwitter> {
    private static final String INTS_PER_FIELD_PROP_NAME = "intsPerField";
    private static final String NAME_PROP_NAME = "fieldName";

    public FlushHandler() {
      super();
    }

    public FlushHandler(OptimizedColumnStrideMultiIntIndetwitter objectToFlush) {
      super(objectToFlush);
    }

    @Override
    protected void doFlush(FlushInfo flushInfo, DataSerializer out) throws IOEtwitterception {
      OptimizedColumnStrideMultiIntIndetwitter columnStrideMultiIntIndetwitter = getObjectToFlush();
      flushInfo.addStringProperty(NAME_PROP_NAME, columnStrideMultiIntIndetwitter.getName());
      flushInfo.addIntProperty(INTS_PER_FIELD_PROP_NAME,
                               columnStrideMultiIntIndetwitter.getNumIntsPerField());
      out.writeIntArray(columnStrideMultiIntIndetwitter.values);
    }

    @Override
    protected OptimizedColumnStrideMultiIntIndetwitter doLoad(FlushInfo flushInfo, DataDeserializer in)
        throws IOEtwitterception {
      int[] values = in.readIntArray();
      return new OptimizedColumnStrideMultiIntIndetwitter(
          flushInfo.getStringProperty(NAME_PROP_NAME),
          flushInfo.getIntProperty(INTS_PER_FIELD_PROP_NAME),
          values);
    }
  }
}

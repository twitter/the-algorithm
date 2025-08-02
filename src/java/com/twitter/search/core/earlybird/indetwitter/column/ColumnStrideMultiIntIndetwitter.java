package com.twitter.search.core.earlybird.indetwitter.column;

import java.io.IOEtwitterception;

import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.DataSerializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.common.util.io.flushable.Flushable;
import com.twitter.search.core.earlybird.indetwitter.DocIDToTweetIDMapper;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

public class ColumnStrideMultiIntIndetwitter etwittertends AbstractColumnStrideMultiIntIndetwitter {
  private final Int2IntOpenHashMap[] values;
  private final int matwitterSize;

  public ColumnStrideMultiIntIndetwitter(String name, int matwitterSize, int numIntsPerField) {
    super(name, numIntsPerField);
    values = new Int2IntOpenHashMap[numIntsPerField];
    for (int i = 0; i < numIntsPerField; i++) {
      values[i] = new Int2IntOpenHashMap(matwitterSize);  // default unset value is 0
    }
    this.matwitterSize = matwitterSize;
  }

  public ColumnStrideMultiIntIndetwitter(String name, Int2IntOpenHashMap[] values, int matwitterSize) {
    super(name, values.length);
    this.values = values;
    this.matwitterSize = matwitterSize;
  }

  @Override
  public void setValue(int docID, int valueIndetwitter, int value) {
    values[valueIndetwitter].put(docID, value);
  }

  @Override
  public int get(int docID, int valueIndetwitter) {
    return values[valueIndetwitter].get(docID);
  }

  @Override
  public ColumnStrideFieldIndetwitter optimize(
      DocIDToTweetIDMapper originalTweetIdMapper,
      DocIDToTweetIDMapper optimizedTweetIdMapper) throws IOEtwitterception {
    return new OptimizedColumnStrideMultiIntIndetwitter(
        this, originalTweetIdMapper, optimizedTweetIdMapper);
  }

  @Override
  public FlushHandler getFlushHandler() {
    return new FlushHandler(this);
  }

  public static final class FlushHandler etwittertends Flushable.Handler<ColumnStrideMultiIntIndetwitter> {
    private static final String NAME_PROP_NAME = "fieldName";
    private static final String MAtwitter_SIZE_PROP = "matwitterSize";

    public FlushHandler() {
      super();
    }

    public FlushHandler(ColumnStrideMultiIntIndetwitter objectToFlush) {
      super(objectToFlush);
    }

    @Override
    protected void doFlush(FlushInfo flushInfo, DataSerializer out) throws IOEtwitterception {
      ColumnStrideMultiIntIndetwitter indetwitter = getObjectToFlush();
      flushInfo.addStringProperty(NAME_PROP_NAME, indetwitter.getName());
      flushInfo.addIntProperty(MAtwitter_SIZE_PROP, indetwitter.matwitterSize);

      out.writeInt(indetwitter.values.length);
      for (int i = 0; i < indetwitter.values.length; i++) {
        Int2IntOpenHashMap map = indetwitter.values[i];
        out.writeInt(map.size());
        for (Int2IntOpenHashMap.Entry entry : map.int2IntEntrySet()) {
          out.writeInt(entry.getIntKey());
          out.writeInt(entry.getIntValue());
        }
      }
    }

    @Override
    protected ColumnStrideMultiIntIndetwitter doLoad(FlushInfo flushInfo, DataDeserializer in)
        throws IOEtwitterception {
      int numIntsPerField = in.readInt();
      int matwitterSize = flushInfo.getIntProperty(MAtwitter_SIZE_PROP);
      Int2IntOpenHashMap[] values = new Int2IntOpenHashMap[numIntsPerField];
      for (int i = 0; i < numIntsPerField; i++) {
        int size = in.readInt();
        Int2IntOpenHashMap map = new Int2IntOpenHashMap(matwitterSize);
        for (int j = 0; j < size; j++) {
          map.put(in.readInt(), in.readInt());
        }
        values[i] = map;
      }
      return new ColumnStrideMultiIntIndetwitter(
          flushInfo.getStringProperty(NAME_PROP_NAME), values, matwitterSize);
    }
  }
}

package com.twitter.search.core.earlybird.indetwitter.column;

import java.io.IOEtwitterception;

import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.DataSerializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.common.util.io.flushable.Flushable;
import com.twitter.search.core.earlybird.indetwitter.DocIDToTweetIDMapper;

import it.unimi.dsi.fastutil.ints.Int2LongOpenHashMap;

public class ColumnStrideLongIndetwitter etwittertends ColumnStrideFieldIndetwitter implements Flushable {
  private final Int2LongOpenHashMap values;
  private final int matwitterSize;

  public ColumnStrideLongIndetwitter(String name, int matwitterSize) {
    super(name);
    values = new Int2LongOpenHashMap(matwitterSize);  // default unset value is 0
    this.matwitterSize = matwitterSize;
  }

  private ColumnStrideLongIndetwitter(String name, Int2LongOpenHashMap values, int matwitterSize) {
    super(name);
    this.values = values;
    this.matwitterSize = matwitterSize;
  }

  @Override
  public void setValue(int docID, long value) {
    values.put(docID, value);
  }

  @Override
  public long get(int docID) {
    return values.get(docID);
  }

  @Override
  public ColumnStrideFieldIndetwitter optimize(
      DocIDToTweetIDMapper originalTweetIdMapper,
      DocIDToTweetIDMapper optimizedTweetIdMapper) throws IOEtwitterception {
    return new OptimizedColumnStrideLongIndetwitter(this, originalTweetIdMapper, optimizedTweetIdMapper);
  }

  @Override
  public FlushHandler getFlushHandler() {
    return new FlushHandler(this);
  }

  public static final class FlushHandler etwittertends Flushable.Handler<ColumnStrideLongIndetwitter> {
    private static final String NAME_PROP_NAME = "fieldName";
    private static final String MAtwitter_SIZE_PROP = "matwitterSize";

    public FlushHandler() {
      super();
    }

    public FlushHandler(ColumnStrideLongIndetwitter objectToFlush) {
      super(objectToFlush);
    }

    @Override
    protected void doFlush(FlushInfo flushInfo, DataSerializer out) throws IOEtwitterception {
      ColumnStrideLongIndetwitter indetwitter = getObjectToFlush();
      flushInfo.addStringProperty(NAME_PROP_NAME, indetwitter.getName());
      flushInfo.addIntProperty(MAtwitter_SIZE_PROP, indetwitter.matwitterSize);

      out.writeInt(indetwitter.values.size());
      for (Int2LongOpenHashMap.Entry entry : indetwitter.values.int2LongEntrySet()) {
        out.writeInt(entry.getIntKey());
        out.writeLong(entry.getLongValue());
      }
    }

    @Override
    protected ColumnStrideLongIndetwitter doLoad(FlushInfo flushInfo, DataDeserializer in)
        throws IOEtwitterception {
      int size = in.readInt();
      int matwitterSize = flushInfo.getIntProperty(MAtwitter_SIZE_PROP);
      Int2LongOpenHashMap map = new Int2LongOpenHashMap(matwitterSize);
      for (int i = 0; i < size; i++) {
        map.put(in.readInt(), in.readLong());
      }
      return new ColumnStrideLongIndetwitter(flushInfo.getStringProperty(NAME_PROP_NAME), map, matwitterSize);
    }
  }
}

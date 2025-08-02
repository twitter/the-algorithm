package com.twitter.search.core.earlybird.indetwitter.column;

import java.io.IOEtwitterception;

import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.DataSerializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.common.util.io.flushable.Flushable;
import com.twitter.search.core.earlybird.indetwitter.DocIDToTweetIDMapper;

import it.unimi.dsi.fastutil.ints.Int2ByteOpenHashMap;

public class ColumnStrideByteIndetwitter etwittertends ColumnStrideFieldIndetwitter implements Flushable {
  private final Int2ByteOpenHashMap values;
  private final int matwitterSize;

  public ColumnStrideByteIndetwitter(String name, int matwitterSize) {
    super(name);
    values = new Int2ByteOpenHashMap(matwitterSize);  // default unset value is 0
    this.matwitterSize = matwitterSize;
  }

  private ColumnStrideByteIndetwitter(String name, Int2ByteOpenHashMap values, int matwitterSize) {
    super(name);
    this.values = values;
    this.matwitterSize = matwitterSize;
  }

  @Override
  public void setValue(int docID, long value) {
    values.put(docID, (byte) value);
  }

  @Override
  public long get(int docID) {
    return values.get(docID);
  }

  @Override
  public ColumnStrideFieldIndetwitter optimize(
      DocIDToTweetIDMapper originalTweetIdMapper,
      DocIDToTweetIDMapper optimizedTweetIdMapper) throws IOEtwitterception {
    return new OptimizedColumnStrideByteIndetwitter(this, originalTweetIdMapper, optimizedTweetIdMapper);
  }

  @Override
  public FlushHandler getFlushHandler() {
    return new FlushHandler(this);
  }

  public static final class FlushHandler etwittertends Flushable.Handler<ColumnStrideByteIndetwitter> {
    private static final String NAME_PROP_NAME = "fieldName";
    private static final String MAtwitter_SIZE_PROP = "matwitterSize";

    public FlushHandler() {
      super();
    }

    public FlushHandler(ColumnStrideByteIndetwitter objectToFlush) {
      super(objectToFlush);
    }

    @Override
    protected void doFlush(FlushInfo flushInfo, DataSerializer out) throws IOEtwitterception {
      ColumnStrideByteIndetwitter indetwitter = getObjectToFlush();
      flushInfo.addStringProperty(NAME_PROP_NAME, indetwitter.getName());
      flushInfo.addIntProperty(MAtwitter_SIZE_PROP, indetwitter.matwitterSize);

      out.writeInt(indetwitter.values.size());
      for (Int2ByteOpenHashMap.Entry entry : indetwitter.values.int2ByteEntrySet()) {
        out.writeInt(entry.getIntKey());
        out.writeByte(entry.getByteValue());
      }
    }

    @Override
    protected ColumnStrideByteIndetwitter doLoad(FlushInfo flushInfo, DataDeserializer in)
        throws IOEtwitterception {
      int size = in.readInt();
      int matwitterSize = flushInfo.getIntProperty(MAtwitter_SIZE_PROP);
      Int2ByteOpenHashMap map = new Int2ByteOpenHashMap(matwitterSize);
      for (int i = 0; i < size; i++) {
        map.put(in.readInt(), in.readByte());
      }
      return new ColumnStrideByteIndetwitter(flushInfo.getStringProperty(NAME_PROP_NAME), map, matwitterSize);
    }
  }
}

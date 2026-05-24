package com.twitter.search.core.earlybird.index.inverted;

import java.io.IOException;

import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.DataSerializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.common.util.io.flushable.Flushable;

public class ByteBlockPool extends BaseByteBlockPool implements Flushable {

  public ByteBlockPool() {
  }

  /**
   * Used for loading flushed pool.
   */
  private ByteBlockPool(Pool pool, int bufferUpto, int byteUpTo, int byteOffset) {
    super(pool, bufferUpto, byteUpTo, byteOffset);
  }

  @Override
  public FlushHandler getFlushHandler() {
    return new FlushHandler(this);
  }

  public static class FlushHandler extends Flushable.Handler<ByteBlockPool> {
    private static final String BUFFER_UP_TO_PROP_NAME = "bufferUpto";
    private static final String BYTE_UP_TO_PROP_NAME = "byteUpto";
    private static final String BYTE_OFFSET_PROP_NAME = "byteOffset";

    public FlushHandler(ByteBlockPool objectToFlush) {
      super(objectToFlush);
    }

    public FlushHandler() {
    }

    @Override
    protected void doFlush(FlushInfo flushInfo, DataSerializer out) throws IOException {
      ByteBlockPool objectToFlush = getObjectToFlush();
      out.writeByteArray2D(objectToFlush.pool.buffers, objectToFlush.bufferUpto + 1);
      flushInfo.addIntProperty(BUFFER_UP_TO_PROP_NAME, objectToFlush.bufferUpto);
      flushInfo.addIntProperty(BYTE_UP_TO_PROP_NAME, objectToFlush.byteUpto);
      flushInfo.addIntProperty(BYTE_OFFSET_PROP_NAME, objectToFlush.byteOffset);
    }

    @Override
    protected ByteBlockPool doLoad(FlushInfo flushInfo,
                                   DataDeserializer in) throws IOException {
      return new ByteBlockPool(
              new BaseByteBlockPool.Pool(in.readByteArray2D()),
              flushInfo.getIntProperty(BUFFER_UP_TO_PROP_NAME),
              flushInfo.getIntProperty(BYTE_UP_TO_PROP_NAME),
              flushInfo.getIntProperty(BYTE_OFFSET_PROP_NAME));
    }
  }
}

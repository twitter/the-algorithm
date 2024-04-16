package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;

 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;

publ c class ByteBlockPool extends BaseByteBlockPool  mple nts Flushable {

  publ c ByteBlockPool() {
  }

  /**
   * Used for load ng flus d pool.
   */
  pr vate ByteBlockPool(Pool pool,  nt bufferUpto,  nt byteUpTo,  nt byteOffset) {
    super(pool, bufferUpto, byteUpTo, byteOffset);
  }

  @Overr de
  publ c FlushHandler getFlushHandler() {
    return new FlushHandler(t );
  }

  publ c stat c class FlushHandler extends Flushable.Handler<ByteBlockPool> {
    pr vate stat c f nal Str ng BUFFER_UP_TO_PROP_NAME = "bufferUpto";
    pr vate stat c f nal Str ng BYTE_UP_TO_PROP_NAME = "byteUpto";
    pr vate stat c f nal Str ng BYTE_OFFSET_PROP_NAME = "byteOffset";

    publ c FlushHandler(ByteBlockPool objectToFlush) {
      super(objectToFlush);
    }

    publ c FlushHandler() {
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer out) throws  OExcept on {
      ByteBlockPool objectToFlush = getObjectToFlush();
      out.wr eByteArray2D(objectToFlush.pool.buffers, objectToFlush.bufferUpto + 1);
      flush nfo.add ntProperty(BUFFER_UP_TO_PROP_NAME, objectToFlush.bufferUpto);
      flush nfo.add ntProperty(BYTE_UP_TO_PROP_NAME, objectToFlush.byteUpto);
      flush nfo.add ntProperty(BYTE_OFFSET_PROP_NAME, objectToFlush.byteOffset);
    }

    @Overr de
    protected ByteBlockPool doLoad(Flush nfo flush nfo,
                                   DataDeser al zer  n) throws  OExcept on {
      return new ByteBlockPool(
              new BaseByteBlockPool.Pool( n.readByteArray2D()),
              flush nfo.get ntProperty(BUFFER_UP_TO_PROP_NAME),
              flush nfo.get ntProperty(BYTE_UP_TO_PROP_NAME),
              flush nfo.get ntProperty(BYTE_OFFSET_PROP_NAME));
    }
  }
}

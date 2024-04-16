package com.tw ter.search.core.earlyb rd. ndex.column;

 mport java. o. OExcept on;

 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;

publ c class Opt m zedColumnStr deByte ndex extends ColumnStr deF eld ndex  mple nts Flushable {
  pr vate f nal byte[] values;

  publ c Opt m zedColumnStr deByte ndex(Str ng na ,  nt maxS ze) {
    super(na );
    values = new byte[maxS ze];
  }

  publ c Opt m zedColumnStr deByte ndex(
      ColumnStr deByte ndex columnStr deByte ndex,
      Doc DToT et DMapper or g nalT et dMapper,
      Doc DToT et DMapper opt m zedT et dMapper) throws  OExcept on {
    super(columnStr deByte ndex.getNa ());
     nt maxDoc d = opt m zedT et dMapper.getPrev ousDoc D( nteger.MAX_VALUE);
    values = new byte[maxDoc d + 1];

     nt doc d = opt m zedT et dMapper.getNextDoc D( nteger.M N_VALUE);
    wh le (doc d != Doc DToT et DMapper. D_NOT_FOUND) {
       nt or g nalDoc d = or g nalT et dMapper.getDoc D(opt m zedT et dMapper.getT et D(doc d));
      setValue(doc d, columnStr deByte ndex.get(or g nalDoc d));
      doc d = opt m zedT et dMapper.getNextDoc D(doc d);
    }
  }

  pr vate Opt m zedColumnStr deByte ndex(Str ng na , byte[] values) {
    super(na );
    t .values = values;
  }

  @Overr de
  publ c vo d setValue( nt doc D, long value) {
    t .values[doc D] = (byte) value;
  }

  @Overr de
  publ c long get( nt doc D) {
    return values[doc D];
  }

  @Overr de
  publ c FlushHandler getFlushHandler() {
    return new FlushHandler(t );
  }

  publ c stat c f nal class FlushHandler extends Flushable.Handler<Opt m zedColumnStr deByte ndex> {
    pr vate stat c f nal Str ng NAME_PROP_NAME = "f eldNa ";

    publ c FlushHandler() {
      super();
    }

    publ c FlushHandler(Opt m zedColumnStr deByte ndex objectToFlush) {
      super(objectToFlush);
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer out) throws  OExcept on {
      Opt m zedColumnStr deByte ndex columnStr deByte ndex = getObjectToFlush();
      flush nfo.addStr ngProperty(NAME_PROP_NAME, columnStr deByte ndex.getNa ());
      out.wr eByteArray(columnStr deByte ndex.values);
    }

    @Overr de
    protected Opt m zedColumnStr deByte ndex doLoad(Flush nfo flush nfo, DataDeser al zer  n)
        throws  OExcept on {
      byte[] values =  n.readByteArray();
      return new Opt m zedColumnStr deByte ndex(
          flush nfo.getStr ngProperty(NAME_PROP_NAME), values);
    }
  }
}

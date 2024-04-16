package com.tw ter.search.core.earlyb rd. ndex.column;

 mport java. o. OExcept on;

 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;

publ c class Opt m zedColumnStr deLong ndex extends ColumnStr deF eld ndex  mple nts Flushable {
  pr vate f nal long[] values;

  publ c Opt m zedColumnStr deLong ndex(Str ng na ,  nt maxS ze) {
    super(na );
    values = new long[maxS ze];
  }

  publ c Opt m zedColumnStr deLong ndex(
      ColumnStr deLong ndex columnStr deLong ndex,
      Doc DToT et DMapper or g nalT et dMapper,
      Doc DToT et DMapper opt m zedT et dMapper) throws  OExcept on {
    super(columnStr deLong ndex.getNa ());
     nt maxDoc d = opt m zedT et dMapper.getPrev ousDoc D( nteger.MAX_VALUE);
    values = new long[maxDoc d + 1];

     nt doc d = opt m zedT et dMapper.getNextDoc D( nteger.M N_VALUE);
    wh le (doc d != Doc DToT et DMapper. D_NOT_FOUND) {
       nt or g nalDoc d = or g nalT et dMapper.getDoc D(opt m zedT et dMapper.getT et D(doc d));
      setValue(doc d, columnStr deLong ndex.get(or g nalDoc d));
      doc d = opt m zedT et dMapper.getNextDoc D(doc d);
    }
  }

  pr vate Opt m zedColumnStr deLong ndex(Str ng na , long[] values) {
    super(na );
    t .values = values;
  }

  @Overr de
  publ c vo d setValue( nt doc D, long value) {
    t .values[doc D] = value;
  }

  @Overr de
  publ c long get( nt doc D) {
    return values[doc D];
  }

  @Overr de
  publ c FlushHandler getFlushHandler() {
    return new FlushHandler(t );
  }

  publ c stat c f nal class FlushHandler extends Flushable.Handler<Opt m zedColumnStr deLong ndex> {
    pr vate stat c f nal Str ng NAME_PROP_NAME = "f eldNa ";

    publ c FlushHandler() {
      super();
    }

    publ c FlushHandler(Opt m zedColumnStr deLong ndex objectToFlush) {
      super(objectToFlush);
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer out) throws  OExcept on {
      Opt m zedColumnStr deLong ndex columnStr deLong ndex = getObjectToFlush();
      flush nfo.addStr ngProperty(NAME_PROP_NAME, columnStr deLong ndex.getNa ());
      out.wr eLongArray(columnStr deLong ndex.values);
    }

    @Overr de
    protected Opt m zedColumnStr deLong ndex doLoad(Flush nfo flush nfo, DataDeser al zer  n)
        throws  OExcept on {
      long[] values =  n.readLongArray();
      return new Opt m zedColumnStr deLong ndex(
          flush nfo.getStr ngProperty(NAME_PROP_NAME), values);
    }
  }
}

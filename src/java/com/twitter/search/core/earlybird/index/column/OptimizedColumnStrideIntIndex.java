package com.tw ter.search.core.earlyb rd. ndex.column;

 mport java. o. OExcept on;

 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;

publ c class Opt m zedColumnStr de nt ndex extends ColumnStr deF eld ndex  mple nts Flushable {
  pr vate f nal  nt[] values;

  publ c Opt m zedColumnStr de nt ndex(Str ng na ,  nt maxS ze) {
    super(na );
    values = new  nt[maxS ze];
  }

  publ c Opt m zedColumnStr de nt ndex(
      ColumnStr de nt ndex columnStr de nt ndex,
      Doc DToT et DMapper or g nalT et dMapper,
      Doc DToT et DMapper opt m zedT et dMapper) throws  OExcept on {
    super(columnStr de nt ndex.getNa ());
     nt maxDoc d = opt m zedT et dMapper.getPrev ousDoc D( nteger.MAX_VALUE);
    values = new  nt[maxDoc d + 1];

     nt doc d = opt m zedT et dMapper.getNextDoc D( nteger.M N_VALUE);
    wh le (doc d != Doc DToT et DMapper. D_NOT_FOUND) {
       nt or g nalDoc d = or g nalT et dMapper.getDoc D(opt m zedT et dMapper.getT et D(doc d));
      setValue(doc d, columnStr de nt ndex.get(or g nalDoc d));
      doc d = opt m zedT et dMapper.getNextDoc D(doc d);
    }
  }

  pr vate Opt m zedColumnStr de nt ndex(Str ng na ,  nt[] values) {
    super(na );
    t .values = values;
  }

  @Overr de
  publ c vo d setValue( nt doc D, long value) {
    t .values[doc D] = ( nt) value;
  }

  @Overr de
  publ c long get( nt doc D) {
    return values[doc D];
  }

  @Overr de
  publ c FlushHandler getFlushHandler() {
    return new FlushHandler(t );
  }

  publ c stat c f nal class FlushHandler extends Flushable.Handler<Opt m zedColumnStr de nt ndex> {
    pr vate stat c f nal Str ng NAME_PROP_NAME = "f eldNa ";

    publ c FlushHandler() {
      super();
    }

    publ c FlushHandler(Opt m zedColumnStr de nt ndex objectToFlush) {
      super(objectToFlush);
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer out) throws  OExcept on {
      Opt m zedColumnStr de nt ndex columnStr de nt ndex = getObjectToFlush();
      flush nfo.addStr ngProperty(NAME_PROP_NAME, columnStr de nt ndex.getNa ());
      out.wr e ntArray(columnStr de nt ndex.values);
    }

    @Overr de
    protected Opt m zedColumnStr de nt ndex doLoad(Flush nfo flush nfo, DataDeser al zer  n)
        throws  OExcept on {
       nt[] values =  n.read ntArray();
      return new Opt m zedColumnStr de nt ndex(
          flush nfo.getStr ngProperty(NAME_PROP_NAME), values);
    }
  }
}

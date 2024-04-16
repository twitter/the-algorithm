package com.tw ter.search.core.earlyb rd. ndex.column;

 mport java. o. OExcept on;

 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;

 mport  .un m .ds .fastut l. nts. nt2 ntOpenHashMap;

publ c class ColumnStr de nt ndex extends ColumnStr deF eld ndex  mple nts Flushable {
  pr vate f nal  nt2 ntOpenHashMap values;
  pr vate f nal  nt maxS ze;

  publ c ColumnStr de nt ndex(Str ng na ,  nt maxS ze) {
    super(na );
    values = new  nt2 ntOpenHashMap(maxS ze);  // default unset value  s 0
    t .maxS ze = maxS ze;
  }

  publ c ColumnStr de nt ndex(Str ng na ,  nt2 ntOpenHashMap values,  nt maxS ze) {
    super(na );
    t .values = values;
    t .maxS ze = maxS ze;
  }

  @Overr de
  publ c vo d setValue( nt doc D, long value) {
    values.put(doc D, ( nt) value);
  }

  @Overr de
  publ c long get( nt doc D) {
    return values.get(doc D);
  }

  @Overr de
  publ c ColumnStr deF eld ndex opt m ze(
      Doc DToT et DMapper or g nalT et dMapper,
      Doc DToT et DMapper opt m zedT et dMapper) throws  OExcept on {
    return new Opt m zedColumnStr de nt ndex(t , or g nalT et dMapper, opt m zedT et dMapper);
  }

  @Overr de
  publ c FlushHandler getFlushHandler() {
    return new FlushHandler(t );
  }

  publ c stat c f nal class FlushHandler extends Flushable.Handler<ColumnStr de nt ndex> {
    pr vate stat c f nal Str ng NAME_PROP_NAME = "f eldNa ";
    pr vate stat c f nal Str ng MAX_S ZE_PROP = "maxS ze";

    publ c FlushHandler() {
      super();
    }

    publ c FlushHandler(ColumnStr de nt ndex objectToFlush) {
      super(objectToFlush);
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer out) throws  OExcept on {
      ColumnStr de nt ndex  ndex = getObjectToFlush();
      flush nfo.addStr ngProperty(NAME_PROP_NAME,  ndex.getNa ());
      flush nfo.add ntProperty(MAX_S ZE_PROP,  ndex.maxS ze);

      out.wr e nt( ndex.values.s ze());
      for ( nt2 ntOpenHashMap.Entry entry :  ndex.values. nt2 ntEntrySet()) {
        out.wr e nt(entry.get ntKey());
        out.wr e nt(entry.get ntValue());
      }
    }

    @Overr de
    protected ColumnStr de nt ndex doLoad(Flush nfo flush nfo, DataDeser al zer  n)
        throws  OExcept on {
       nt s ze =  n.read nt();
       nt maxS ze = flush nfo.get ntProperty(MAX_S ZE_PROP);
       nt2 ntOpenHashMap map = new  nt2 ntOpenHashMap(maxS ze);
      for ( nt   = 0;   < s ze;  ++) {
        map.put( n.read nt(),  n.read nt());
      }
      return new ColumnStr de nt ndex(flush nfo.getStr ngProperty(NAME_PROP_NAME), map, maxS ze);
    }
  }
}

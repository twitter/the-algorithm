package com.tw ter.search.core.earlyb rd. ndex.column;

 mport java. o. OExcept on;

 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;

 mport  .un m .ds .fastut l. nts. nt2 ntOpenHashMap;

publ c class ColumnStr deMult  nt ndex extends AbstractColumnStr deMult  nt ndex {
  pr vate f nal  nt2 ntOpenHashMap[] values;
  pr vate f nal  nt maxS ze;

  publ c ColumnStr deMult  nt ndex(Str ng na ,  nt maxS ze,  nt num ntsPerF eld) {
    super(na , num ntsPerF eld);
    values = new  nt2 ntOpenHashMap[num ntsPerF eld];
    for ( nt   = 0;   < num ntsPerF eld;  ++) {
      values[ ] = new  nt2 ntOpenHashMap(maxS ze);  // default unset value  s 0
    }
    t .maxS ze = maxS ze;
  }

  publ c ColumnStr deMult  nt ndex(Str ng na ,  nt2 ntOpenHashMap[] values,  nt maxS ze) {
    super(na , values.length);
    t .values = values;
    t .maxS ze = maxS ze;
  }

  @Overr de
  publ c vo d setValue( nt doc D,  nt value ndex,  nt value) {
    values[value ndex].put(doc D, value);
  }

  @Overr de
  publ c  nt get( nt doc D,  nt value ndex) {
    return values[value ndex].get(doc D);
  }

  @Overr de
  publ c ColumnStr deF eld ndex opt m ze(
      Doc DToT et DMapper or g nalT et dMapper,
      Doc DToT et DMapper opt m zedT et dMapper) throws  OExcept on {
    return new Opt m zedColumnStr deMult  nt ndex(
        t , or g nalT et dMapper, opt m zedT et dMapper);
  }

  @Overr de
  publ c FlushHandler getFlushHandler() {
    return new FlushHandler(t );
  }

  publ c stat c f nal class FlushHandler extends Flushable.Handler<ColumnStr deMult  nt ndex> {
    pr vate stat c f nal Str ng NAME_PROP_NAME = "f eldNa ";
    pr vate stat c f nal Str ng MAX_S ZE_PROP = "maxS ze";

    publ c FlushHandler() {
      super();
    }

    publ c FlushHandler(ColumnStr deMult  nt ndex objectToFlush) {
      super(objectToFlush);
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer out) throws  OExcept on {
      ColumnStr deMult  nt ndex  ndex = getObjectToFlush();
      flush nfo.addStr ngProperty(NAME_PROP_NAME,  ndex.getNa ());
      flush nfo.add ntProperty(MAX_S ZE_PROP,  ndex.maxS ze);

      out.wr e nt( ndex.values.length);
      for ( nt   = 0;   <  ndex.values.length;  ++) {
         nt2 ntOpenHashMap map =  ndex.values[ ];
        out.wr e nt(map.s ze());
        for ( nt2 ntOpenHashMap.Entry entry : map. nt2 ntEntrySet()) {
          out.wr e nt(entry.get ntKey());
          out.wr e nt(entry.get ntValue());
        }
      }
    }

    @Overr de
    protected ColumnStr deMult  nt ndex doLoad(Flush nfo flush nfo, DataDeser al zer  n)
        throws  OExcept on {
       nt num ntsPerF eld =  n.read nt();
       nt maxS ze = flush nfo.get ntProperty(MAX_S ZE_PROP);
       nt2 ntOpenHashMap[] values = new  nt2 ntOpenHashMap[num ntsPerF eld];
      for ( nt   = 0;   < num ntsPerF eld;  ++) {
         nt s ze =  n.read nt();
         nt2 ntOpenHashMap map = new  nt2 ntOpenHashMap(maxS ze);
        for ( nt j = 0; j < s ze; j++) {
          map.put( n.read nt(),  n.read nt());
        }
        values[ ] = map;
      }
      return new ColumnStr deMult  nt ndex(
          flush nfo.getStr ngProperty(NAME_PROP_NAME), values, maxS ze);
    }
  }
}

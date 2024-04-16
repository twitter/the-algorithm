package com.tw ter.search.core.earlyb rd. ndex.column;

 mport java. o. OExcept on;

 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;

publ c class Opt m zedColumnStr deMult  nt ndex
    extends AbstractColumnStr deMult  nt ndex  mple nts Flushable {
  pr vate f nal  nt[] values;

  publ c Opt m zedColumnStr deMult  nt ndex(Str ng na ,  nt maxS ze,  nt num ntsPerF eld) {
    super(na , num ntsPerF eld);
    values = new  nt[Math.mult plyExact(maxS ze, num ntsPerF eld)];
  }

  publ c Opt m zedColumnStr deMult  nt ndex(
      ColumnStr deMult  nt ndex columnStr deMult  nt ndex,
      Doc DToT et DMapper or g nalT et dMapper,
      Doc DToT et DMapper opt m zedT et dMapper) throws  OExcept on {
    super(columnStr deMult  nt ndex.getNa (), columnStr deMult  nt ndex.getNum ntsPerF eld());
     nt maxDoc d = opt m zedT et dMapper.getPrev ousDoc D( nteger.MAX_VALUE);
    values = new  nt[columnStr deMult  nt ndex.getNum ntsPerF eld() * (maxDoc d + 1)];

     nt doc d = opt m zedT et dMapper.getNextDoc D( nteger.M N_VALUE);
    wh le (doc d != Doc DToT et DMapper. D_NOT_FOUND) {
       nt or g nalDoc d = or g nalT et dMapper.getDoc D(opt m zedT et dMapper.getT et D(doc d));
      for ( nt   = 0;   < columnStr deMult  nt ndex.getNum ntsPerF eld(); ++ ) {
        setValue(doc d,  , columnStr deMult  nt ndex.get(or g nalDoc d,  ));
      }
      doc d = opt m zedT et dMapper.getNextDoc D(doc d);
    }
  }

  pr vate Opt m zedColumnStr deMult  nt ndex(Str ng na ,  nt num ntsPerF eld,  nt[] values) {
    super(na , num ntsPerF eld);
    t .values = values;
  }

  @Overr de
  publ c vo d setValue( nt doc D,  nt value ndex,  nt value) {
    values[doc D * getNum ntsPerF eld() + value ndex] = value;
  }

  @Overr de
  publ c  nt get( nt doc D,  nt value ndex) {
    return values[doc D * getNum ntsPerF eld() + value ndex];
  }

  @Overr de
  publ c FlushHandler getFlushHandler() {
    return new FlushHandler(t );
  }

  publ c stat c f nal class FlushHandler
      extends Flushable.Handler<Opt m zedColumnStr deMult  nt ndex> {
    pr vate stat c f nal Str ng  NTS_PER_F ELD_PROP_NAME = " ntsPerF eld";
    pr vate stat c f nal Str ng NAME_PROP_NAME = "f eldNa ";

    publ c FlushHandler() {
      super();
    }

    publ c FlushHandler(Opt m zedColumnStr deMult  nt ndex objectToFlush) {
      super(objectToFlush);
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer out) throws  OExcept on {
      Opt m zedColumnStr deMult  nt ndex columnStr deMult  nt ndex = getObjectToFlush();
      flush nfo.addStr ngProperty(NAME_PROP_NAME, columnStr deMult  nt ndex.getNa ());
      flush nfo.add ntProperty( NTS_PER_F ELD_PROP_NAME,
                               columnStr deMult  nt ndex.getNum ntsPerF eld());
      out.wr e ntArray(columnStr deMult  nt ndex.values);
    }

    @Overr de
    protected Opt m zedColumnStr deMult  nt ndex doLoad(Flush nfo flush nfo, DataDeser al zer  n)
        throws  OExcept on {
       nt[] values =  n.read ntArray();
      return new Opt m zedColumnStr deMult  nt ndex(
          flush nfo.getStr ngProperty(NAME_PROP_NAME),
          flush nfo.get ntProperty( NTS_PER_F ELD_PROP_NAME),
          values);
    }
  }
}

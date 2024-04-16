package com.tw ter.search.earlyb rd. ndex;

 mport java. o. OExcept on;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;
 mport com.tw ter.search.core.earlyb rd. ndex.T  Mapper;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted. ntBlockPool;

 mport  .un m .ds .fastut l. nts. nt2 ntMap;
 mport  .un m .ds .fastut l. nts. nt2 ntOpenHashMap;

/**
 * Maps 32-b  docu nt  Ds to seconds-s nce-epoch t  stamps.
 */
publ c class Realt  T  Mapper extends Abstract n moryT  Mapper {
  // Doc  d to t  stamp map. T  stamps that are negat ve are out-of-order.
  protected f nal  nt2 ntOpenHashMap t  Map;
  pr vate f nal  nt capac y;

  publ c Realt  T  Mapper( nt capac y) {
    super();
    t .capac y = capac y;

    t  Map = new  nt2 ntOpenHashMap(capac y);
    t  Map.defaultReturnValue( LLEGAL_T ME);
  }

  @Overr de
  publ c  nt getT  ( nt doc D) {
    return t  Map.get(doc D);
  }

  @Overr de
  protected vo d setT  ( nt doc D,  nt t  Seconds) {
    t  Map.put(doc D, t  Seconds);
  }

  publ c f nal vo d addMapp ng( nt doc D,  nt t  Seconds) {
    doAddMapp ng(doc D, t  Seconds);
  }

  @Overr de
  publ c T  Mapper opt m ze(Doc DToT et DMapper or g nalT et dMapper,
                             Doc DToT et DMapper opt m zedT et dMapper) throws  OExcept on {
    return new Opt m zedT  Mapper(t , or g nalT et dMapper, opt m zedT et dMapper);
  }

  /**
   * Evaluates w t r two  nstances of Realt  T  Mapper are equal by value.    s
   * slow because   has to c ck every t et  D/t  stamp  n t  map.
   */
  @V s bleForTest ng
  boolean verySlowEqualsForTests(Realt  T  Mapper that) {
    return reverseMapLast ndex == that.reverseMapLast ndex
        && reverseMap ds.verySlowEqualsForTests(that.reverseMap ds)
        && reverseMapT  s.verySlowEqualsForTests(that.reverseMapT  s)
        && capac y == that.capac y
        && t  Map.equals(that.t  Map);
  }

  pr vate Realt  T  Mapper(
       nt capac y,
       nt reverseMapLast ndex,
       nt[] doc ds,
       nt[] t  stamps,
       ntBlockPool reverseMapT  s,
       ntBlockPool reverseMap ds
  ) {
    super(reverseMapLast ndex, reverseMapT  s, reverseMap ds);

    t .capac y = capac y;

    t  Map = new  nt2 ntOpenHashMap(capac y);
    t  Map.defaultReturnValue( LLEGAL_T ME);

    Precond  ons.c ckState(doc ds.length == t  stamps.length);

    for ( nt   = 0;   < doc ds.length;  ++) {
      t  Map.put(doc ds[ ], t  stamps[ ]);
    }
  }

  @Overr de
  publ c Realt  T  Mapper.FlushHandler getFlushHandler() {
    return new Realt  T  Mapper.FlushHandler(t );
  }

  publ c stat c class FlushHandler extends Flushable.Handler<Realt  T  Mapper> {
    pr vate stat c f nal Str ng REVERSE_MAP_LAST_ NDEX_PROP = "reverseMapLast ndex";
    pr vate stat c f nal Str ng T MES_SUB_PROP = "t  s";
    pr vate stat c f nal Str ng  DS_SUB_PROP = " ds";
    pr vate stat c f nal Str ng CAPAC TY_PROP = "capac y";

    publ c FlushHandler() {
      super();
    }

    publ c FlushHandler(Realt  T  Mapper objectToFlush) {
      super(objectToFlush);
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer ser al zer) throws  OExcept on {
      Realt  T  Mapper mapper = getObjectToFlush();

      flush nfo.add ntProperty(CAPAC TY_PROP, mapper.capac y);
      flush nfo.add ntProperty(REVERSE_MAP_LAST_ NDEX_PROP, mapper.reverseMapLast ndex);

      ser al zer.wr e nt(mapper.t  Map.s ze());
      for ( nt2 ntMap.Entry entry : mapper.t  Map. nt2 ntEntrySet()) {
        ser al zer.wr e nt(entry.get ntKey());
        ser al zer.wr e nt(entry.get ntValue());
      }

      mapper.reverseMapT  s.getFlushHandler().flush(
          flush nfo.newSubPropert es(T MES_SUB_PROP), ser al zer);
      mapper.reverseMap ds.getFlushHandler().flush(
          flush nfo.newSubPropert es( DS_SUB_PROP), ser al zer);
    }

    @Overr de
    protected Realt  T  Mapper doLoad(Flush nfo flush nfo, DataDeser al zer  n)
        throws  OExcept on {

       nt s ze =  n.read nt();
       nt[] doc ds = new  nt[s ze];
       nt[] t  stamps = new  nt[s ze];
      for ( nt   = 0;   < s ze;  ++) {
        doc ds[ ] =  n.read nt();
        t  stamps[ ] =  n.read nt();
      }

      return new Realt  T  Mapper(
          flush nfo.get ntProperty(CAPAC TY_PROP),
          flush nfo.get ntProperty(REVERSE_MAP_LAST_ NDEX_PROP),
          doc ds,
          t  stamps,
          new  ntBlockPool.FlushHandler().load(flush nfo.getSubPropert es(T MES_SUB_PROP),  n),
          new  ntBlockPool.FlushHandler().load(flush nfo.getSubPropert es( DS_SUB_PROP),  n));
    }
  }
}

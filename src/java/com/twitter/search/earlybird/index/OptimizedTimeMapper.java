package com.tw ter.search.earlyb rd. ndex;

 mport java. o. OExcept on;
 mport java.ut l.Arrays;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;
 mport com.tw ter.search.core.earlyb rd. ndex.T  Mapper;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted. ntBlockPool;

/**
 * A T  Mapper  mple ntat on that stores t  t  stamps assoc ated w h t  doc  Ds  n an array.
 */
publ c class Opt m zedT  Mapper extends Abstract n moryT  Mapper  mple nts Flushable {
  // Doc  d to t  stamp map. T  stamps that are negat ve are out-of-order.
  protected f nal  nt[] t  Map;

  // S ze must be greater than t  max doc  D stored  n t  opt m zed t et  D mapper.
  publ c Opt m zedT  Mapper(Realt  T  Mapper realt  T  Mapper,
                             Doc DToT et DMapper or g nalT et dMapper,
                             Doc DToT et DMapper opt m zedT et dMapper) throws  OExcept on {
    super();
     nt maxDoc d = opt m zedT et dMapper.getPrev ousDoc D( nteger.MAX_VALUE);
    t  Map = new  nt[maxDoc d + 1];
    Arrays.f ll(t  Map,  LLEGAL_T ME);

     nt doc d = maxDoc d;
    wh le (doc d != Doc DToT et DMapper. D_NOT_FOUND) {
       nt or g nalDoc d = or g nalT et dMapper.getDoc D(opt m zedT et dMapper.getT et D(doc d));
      Precond  ons.c ckState(or g nalDoc d != Doc DToT et DMapper. D_NOT_FOUND);

       nt doc dT  stamp = realt  T  Mapper.getT  (or g nalDoc d);
      Precond  ons.c ckState(doc dT  stamp != T  Mapper. LLEGAL_T ME);

      doAddMapp ng(doc d, doc dT  stamp);

      doc d = opt m zedT et dMapper.getPrev ousDoc D(doc d);
    }
  }

  pr vate Opt m zedT  Mapper( nt[] t  Map,
                               nt reverseMapLast ndex,
                               ntBlockPool reverseMapT  s,
                               ntBlockPool reverseMap ds) {
    super(reverseMapLast ndex, reverseMapT  s, reverseMap ds);
    t .t  Map = t  Map;
  }

  @Overr de
  publ c  nt getT  ( nt doc D) {
    return t  Map[doc D];
  }

  @Overr de
  protected vo d setT  ( nt doc D,  nt t  Seconds) {
    t  Map[doc D] = t  Seconds;
  }

  @Overr de
  publ c FlushHandler getFlushHandler() {
    return new FlushHandler(t );
  }

  publ c stat c f nal class FlushHandler extends Flushable.Handler<Opt m zedT  Mapper> {
    pr vate stat c f nal Str ng REVERSE_MAP_LAST_ NDEX_PROP = "reverseMapLast ndex";
    pr vate stat c f nal Str ng T MES_SUB_PROP = "t  s";
    pr vate stat c f nal Str ng  DS_SUB_PROP = " ds";

    publ c FlushHandler() {
      super();
    }

    publ c FlushHandler(Opt m zedT  Mapper objectToFlush) {
      super(objectToFlush);
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer out) throws  OExcept on {
      Opt m zedT  Mapper mapper = getObjectToFlush();
      out.wr e ntArray(mapper.t  Map);
      flush nfo.add ntProperty(REVERSE_MAP_LAST_ NDEX_PROP, mapper.reverseMapLast ndex);
      mapper.reverseMapT  s.getFlushHandler().flush(
          flush nfo.newSubPropert es(T MES_SUB_PROP), out);
      mapper.reverseMap ds.getFlushHandler().flush(
          flush nfo.newSubPropert es( DS_SUB_PROP), out);
    }

    @Overr de
    protected Opt m zedT  Mapper doLoad(Flush nfo flush nfo, DataDeser al zer  n)
        throws  OExcept on {
      return new Opt m zedT  Mapper(
           n.read ntArray(),
          flush nfo.get ntProperty(REVERSE_MAP_LAST_ NDEX_PROP),
          new  ntBlockPool.FlushHandler().load(flush nfo.getSubPropert es(T MES_SUB_PROP),  n),
          new  ntBlockPool.FlushHandler().load(flush nfo.getSubPropert es( DS_SUB_PROP),  n));
    }
  }

  @Overr de
  publ c T  Mapper opt m ze(Doc DToT et DMapper or g nalT et dMapper,
                             Doc DToT et DMapper opt m zedT et dMapper) {
    throw new UnsupportedOperat onExcept on("Opt m zedT  Mapper  nstances are already opt m zed.");
  }
}

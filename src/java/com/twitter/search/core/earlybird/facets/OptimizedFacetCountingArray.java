package com.tw ter.search.core.earlyb rd.facets;

 mport java. o. OExcept on;
 mport java.ut l.Arrays;
 mport java.ut l.Map;

 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted. ntBlockPool;

publ c class Opt m zedFacetCount ngArray extends AbstractFacetCount ngArray {
  pr vate f nal  nt[] facetsMap;

  /**
   * Creates a new, empty FacetCount ngArray w h t  g ven s ze.
   */
  publ c Opt m zedFacetCount ngArray( nt maxDoc d nclus ve) {
    super();
    facetsMap = new  nt[maxDoc d nclus ve];
    Arrays.f ll(facetsMap, UNASS GNED);
  }

  pr vate Opt m zedFacetCount ngArray( nt[] facetsMap,  ntBlockPool facetsPool) {
    super(facetsPool);
    t .facetsMap = facetsMap;
  }

  @Overr de
  protected  nt getFacet( nt doc D) {
    return facetsMap[doc D];
  }

  @Overr de
  protected vo d setFacet( nt doc D,  nt facet D) {
    facetsMap[doc D] = facet D;
  }

  @Overr de
  publ c AbstractFacetCount ngArray rewr eAndMap Ds(
      Map< nteger,  nt[]> term DMapper,
      Doc DToT et DMapper or g nalT et dMapper,
      Doc DToT et DMapper opt m zedT et dMapper) {
    throw new UnsupportedOperat onExcept on(
        "Opt m zedFacetCount ngArray  nstances should never be rewr ten.");
  }

  @Overr de
  publ c FlushHandler getFlushHandler() {
    return new FlushHandler(t );
  }

  publ c stat c f nal class FlushHandler extends Flushable.Handler<Opt m zedFacetCount ngArray> {
    pr vate stat c f nal Str ng FACETS_POOL_PROP_NAME = "facetsPool";

    publ c FlushHandler() {
    }

    publ c FlushHandler(Opt m zedFacetCount ngArray objectToFlush) {
      super(objectToFlush);
    }

    @Overr de
    publ c vo d doFlush(Flush nfo flush nfo, DataSer al zer out) throws  OExcept on {
      Opt m zedFacetCount ngArray objectToFlush = getObjectToFlush();
      out.wr e ntArray(objectToFlush.facetsMap);
      objectToFlush.getFacetsPool().getFlushHandler().flush(
          flush nfo.newSubPropert es(FACETS_POOL_PROP_NAME), out);
    }

    @Overr de
    publ c Opt m zedFacetCount ngArray doLoad(Flush nfo flush nfo, DataDeser al zer  n)
        throws  OExcept on {
       nt[] facetsMap =  n.read ntArray();
       ntBlockPool facetsPool = new  ntBlockPool.FlushHandler().load(
          flush nfo.getSubPropert es(FACETS_POOL_PROP_NAME),  n);
      return new Opt m zedFacetCount ngArray(facetsMap, facetsPool);
    }
  }
}

package com.tw ter.search.core.earlyb rd.facets;

 mport java. o. OExcept on;
 mport java.ut l.Arrays;
 mport java.ut l.Collect on;
 mport java.ut l.Map;

 mport com.google.common.collect.Maps;

 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;

/**
 * Currently a facet  s conf gured by:
 *   -  ndex f eld na : T  Lucene f eld na  wh ch stores t   ndexed terms of t  facet
 *   - Facet na :       T  na  of t  facet that t  search AP  spec f es to request facet counts.
 *   - Facet  d:         An  nternal  d wh ch  s used to store t  facet forward mapp ng  n t  facet count ng
 *                       data structures.
 *
 * T   s a mult -map w h two d fferent mapp ngs:
 *   Facet na        -> Facet  d
 *   Facet  d         -> F eld nfo
 */
publ c f nal class Facet DMap  mple nts Flushable {
  pr vate f nal FacetF eld[] facet DToF eldMap;
  pr vate f nal Map<Str ng,  nteger> facetNa To DMap;

  pr vate Facet DMap(FacetF eld[] facet DToF eldMap) {
    t .facet DToF eldMap = facet DToF eldMap;

    facetNa To DMap = Maps.newHashMapW hExpectedS ze(facet DToF eldMap.length);
    for ( nt   = 0;   < facet DToF eldMap.length;  ++) {
      facetNa To DMap.put(facet DToF eldMap[ ].getFacetNa (),  );
    }
  }

  publ c FacetF eld getFacetF eld(Sc ma.F eld nfo f eld nfo) {
    return f eld nfo != null && f eld nfo.getF eldType(). sFacetF eld()
            ? getFacetF eldByFacetNa (f eld nfo.getF eldType().getFacetNa ()) : null;
  }

  publ c FacetF eld getFacetF eldByFacetNa (Str ng facetNa ) {
     nteger facet D = facetNa To DMap.get(facetNa );
    return facet D != null ? facet DToF eldMap[facet D] : null;
  }

  publ c FacetF eld getFacetF eldByFacet D( nt facet D) {
    return facet DToF eldMap[facet D];
  }

  publ c Collect on<FacetF eld> getFacetF elds() {
    return Arrays.asL st(facet DToF eldMap);
  }

  publ c  nt getNumberOfFacetF elds() {
    return facet DToF eldMap.length;
  }

  /**
   * Bu lds a new Facet DMap from t  g ven sc ma.
   */
  publ c stat c Facet DMap bu ld(Sc ma sc ma) {
    FacetF eld[] facet DToF eldMap = new FacetF eld[sc ma.getNumFacetF elds()];

     nt facet d = 0;

    for (Sc ma.F eld nfo f eld nfo : sc ma.getF eld nfos()) {
       f (f eld nfo.getF eldType(). sFacetF eld()) {
        facet DToF eldMap[facet d] = new FacetF eld(facet d, f eld nfo);
        facet d++;
      }
    }

    return new Facet DMap(facet DToF eldMap);
  }

  publ c stat c f nal class FacetF eld {
    pr vate f nal  nt facet d;
    pr vate f nal Sc ma.F eld nfo f eld nfo;

    pr vate FacetF eld( nt facet d, Sc ma.F eld nfo f eld nfo) {
      t .facet d = facet d;
      t .f eld nfo = f eld nfo;
    }

    publ c  nt getFacet d() {
      return facet d;
    }

    publ c Sc ma.F eld nfo getF eld nfo() {
      return f eld nfo;
    }

    publ c Str ng getFacetNa () {
      return f eld nfo.getF eldType().getFacetNa ();
    }

    publ c Str ng getDescr pt on() {
      return Str ng.format(
          "(FacetF eld [facet d: %d, f eld nfo: %s])",
          getFacet d(), f eld nfo.getDescr pt on());
    }
  }

  @SuppressWarn ngs("unc cked")
  @Overr de
  publ c Facet DMap.FlushHandler getFlushHandler() {
    return new FlushHandler(t );
  }

  publ c stat c f nal class FlushHandler extends Flushable.Handler<Facet DMap> {
    pr vate stat c f nal Str ng NUM_FACET_F ELDS_PROP_NAME = "numFacetF elds";

    pr vate f nal Sc ma sc ma;

    publ c FlushHandler(Sc ma sc ma) {
      t .sc ma = sc ma;
    }

    publ c FlushHandler(Facet DMap objectToFlush) {
      super(objectToFlush);
      // sc ma only needed  re for load ng, not for flush ng
      t .sc ma = null;
    }

    @Overr de
    publ c vo d doFlush(Flush nfo flush nfo, DataSer al zer out) throws  OExcept on {
      Facet DMap toFlush = getObjectToFlush();
       nt[]  dMap = new  nt[toFlush.facet DToF eldMap.length];
      for ( nt   = 0;   < toFlush.facet DToF eldMap.length;  ++) {
         dMap[ ] = toFlush.facet DToF eldMap[ ].getF eld nfo().getF eld d();
      }
      out.wr e ntArray( dMap);

      flush nfo.add ntProperty(NUM_FACET_F ELDS_PROP_NAME,  dMap.length);
    }


    @Overr de
    publ c Facet DMap doLoad(Flush nfo flush nfo, DataDeser al zer  n) throws  OExcept on {
       nt[]  dMap =  n.read ntArray();
       f ( dMap.length != sc ma.getNumFacetF elds()) {
        throw new  OExcept on("Wrong number of facet f elds. Expected by sc ma: "
                + sc ma.getNumFacetF elds()
                + ", but found  n ser al zed seg nt: " +  dMap.length);
      }

      FacetF eld[] facet DToF eldMap = new FacetF eld[sc ma.getNumFacetF elds()];

      for ( nt   = 0;   <  dMap.length;  ++) {
         nt f eldConf g d =  dMap[ ];
        facet DToF eldMap[ ] = new FacetF eld( , sc ma.getF eld nfo(f eldConf g d));
      }

      return new Facet DMap(facet DToF eldMap);
    }
  }
}

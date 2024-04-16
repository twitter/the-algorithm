package com.tw ter.search.core.earlyb rd.facets;

 mport java. o. OExcept on;
 mport java.ut l.ArrayL st;
 mport java.ut l.L st;
 mport java.ut l.Map;

 mport com.google.common.base.Precond  ons;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted. ntBlockPool;

/**
 * AbstractFacetCount ngArray  mple nts a lookup from a doc  D to an unordered l st of facets.
 * A facet  s a pa r of (term  D, f eld  D), wh ch could represent,
 * for example ("http://tw ter.com", "l nks").
 *
 *  nternally,   have two data structures: A map from doc  D to an  nt and a pool of  nts.   refer
 * to t  values conta ned  n t se structures as packed values. A packed value can e  r be a
 * po nter to a locat on  n t  pool, an encoded facet or a sent nel value. Po nters always have
 * t  r h gh b  set to 1.
 *
 *  f a docu nt has just one facet,   w ll store t  encoded facet  n t  map, and noth ng  n t 
 * pool. Ot rw se, t  map w ll conta n a po nter  nto t   nt pool.
 *
 * T   nt pool  s encoded  n a block-allocated l nked l st.
 * See {@l nk AbstractFacetCount ngArray#collectForDoc d} for deta ls on how to traverse t  l st.
 */
publ c abstract class AbstractFacetCount ngArray  mple nts Flushable {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(AbstractFacetCount ngArray.class);

  pr vate stat c f nal FacetCount erator EMPTY_ TERATOR = new FacetCount erator() {
    @Overr de
    publ c vo d collect( nt doc D) {
      // noop
    }
  };

  publ c stat c f nal AbstractFacetCount ngArray EMPTY_ARRAY = new AbstractFacetCount ngArray() {
    @Overr de
    publ c f nal FacetCount erator get erator(Earlyb rd ndexSeg ntAtom cReader reader,
                                                FacetCountState<?> countState,
                                                FacetCount eratorFactory  eratorFactory) {
      return EMPTY_ TERATOR;
    }

    @Overr de
    publ c f nal  nt getFacet( nt doc D) {
      return UNASS GNED;
    }

    @Overr de
    publ c f nal vo d setFacet( nt doc D,  nt facet D) {
    }

    @Overr de
    publ c f nal AbstractFacetCount ngArray rewr eAndMap Ds(
        Map< nteger,  nt[]> term DMapper,
        Doc DToT et DMapper or g nalT et dMapper,
        Doc DToT et DMapper opt m zedT et dMapper) {
      return t ;
    }

    @Overr de
    publ c <T extends Flushable> Handler<T> getFlushHandler() {
      return null;
    }
  };

  protected class ArrayFacetCount erator extends FacetCount erator {
    @Overr de
    publ c vo d collect( nt doc D) {
      collectForDoc d(doc D, t );
    }
  }

  pr vate stat c f nal  nt NUM_B TS_TERM_ D = 27;
  pr vate stat c f nal  nt TERM_ D_MASK = (1 << NUM_B TS_TERM_ D) - 1;

  pr vate stat c f nal  nt NUM_B TS_F ELD_ D = 4;
  pr vate stat c f nal  nt F ELD_ D_MASK = (1 << NUM_B TS_F ELD_ D) - 1;

  pr vate stat c f nal  nt H GHEST_ORDER_B T =  nteger.M N_VALUE;  // 1L << 31
  pr vate stat c f nal  nt H GHEST_ORDER_B T_ NVERSE_MASK = H GHEST_ORDER_B T - 1;

  protected stat c f nal  nt UNASS GNED =  nteger.MAX_VALUE;

  protected stat c f nal  nt decodeTerm D( nt facet D) {
     f (facet D != UNASS GNED) {
       nt term D = facet D & TERM_ D_MASK;
      return term D;
    }

    return Earlyb rd ndexSeg ntAtom cReader.TERM_NOT_FOUND;
  }

  protected stat c f nal  nt decodeF eld D( nt facet D) {
    return (facet D >>> NUM_B TS_TERM_ D) & F ELD_ D_MASK;
  }

  protected stat c f nal  nt encodeFacet D( nt f eld D,  nt term D) {
    return ((f eld D & F ELD_ D_MASK) << NUM_B TS_TERM_ D) | (term D & TERM_ D_MASK);
  }

  protected stat c f nal  nt decodePo nter( nt value) {
    return value & H GHEST_ORDER_B T_ NVERSE_MASK;
  }

  protected stat c f nal  nt encodePo nter( nt value) {
    return value | H GHEST_ORDER_B T;
  }

  protected stat c f nal boolean  sPo nter( nt value) {
    return (value & H GHEST_ORDER_B T) != 0;
  }

  pr vate f nal  ntBlockPool facetsPool;

  protected AbstractFacetCount ngArray() {
    facetsPool = new  ntBlockPool("facets");
  }

  protected AbstractFacetCount ngArray( ntBlockPool facetsPool) {
    t .facetsPool = facetsPool;
  }

  /**
   * Returns an  erator to  erate all docs/facets stored  n t  FacetCount ngArray.
   */
  publ c FacetCount erator get erator(
      Earlyb rd ndexSeg ntAtom cReader reader,
      FacetCountState<?> countState,
      FacetCount eratorFactory  eratorFactory) {
    Precond  ons.c ckNotNull(countState);
    Precond  ons.c ckNotNull(reader);

    L st<FacetCount erator>  erators = new ArrayL st<>();
    for (Sc ma.F eld nfo f eld nfo : countState.getSc ma().getCsfFacetF elds()) {
       f (countState. sCountF eld(f eld nfo)) {
        // Rat r than rely on t  normal facet count ng array,   read from a column str de
        // f eld us ng a custom  mple ntat on of FacetCount erator.
        // T  opt m zat on  s due to two factors:
        //  1) for t  from_user_ d_csf facet, every docu nt has a from user  d,
        //     but many docu nts conta n no ot r facets.
        //  2)   requ re from_user_ d and shared_status_ d to be  n a column str de f eld
        //     for ot r uses.
        try {
           erators.add( eratorFactory.getFacetCount erator(reader, f eld nfo));
        } catch ( OExcept on e) {
          Str ng facetNa  = f eld nfo.getF eldType().getFacetNa ();
          LOG.error("Fa led to construct  erator for " + facetNa  + " facet", e);
        }
      }
    }
     f ( erators.s ze() == 0) {
      return new ArrayFacetCount erator();
    }
     f ( erators.s ze() < countState.getNumF eldsToCount()) {
       erators.add(new ArrayFacetCount erator());
    }
    return new Compos eFacetCount erator( erators);
  }

  /**
   * Collects facets of t  docu nt w h t  prov ded doc D.
   * See {@l nk FacetCount ngArrayWr er#addFacet} for deta ls on t  format of t   nt pool.
   */
  publ c vo d collectForDoc d( nt doc D, FacetTermCollector collector) {
     nt f rstValue = getFacet(doc D);
     f (f rstValue == UNASS GNED) {
      return;  // no facet
    }
     f (! sPo nter(f rstValue)) {
      // h g st order b  not set, only one facet for t  docu nt.
      collector.collect(doc D, decodeTerm D(f rstValue), decodeF eld D(f rstValue));
      return;
    }

    // mult ple facets, traverse t  l nked l st to f nd all of t  facets for t  docu nt.
     nt po nter = decodePo nter(f rstValue);
    wh le (true) {
       nt packedValue = facetsPool.get(po nter);
      // UNASS GNED  s a sent nel value  nd cat ng that   have reac d t  end of t  l nked l st.
       f (packedValue == UNASS GNED) {
        return;
      }

       f ( sPo nter(packedValue)) {
        //  f t  packedValue  s a po nter,   need to sk p over so   nts to reach t  facets for
        // t  docu nt.
        po nter = decodePo nter(packedValue);
      } else {
        //  f t  packedValue  s not a po nter,    s an encoded facet, and   can s mply decre nt
        // t  po nter to collect t  next value.
        collector.collect(doc D, decodeTerm D(packedValue), decodeF eld D(packedValue));
        po nter--;
      }
    }
  }

  /**
   * T   thod can return one of three values for each g ven doc  D:
   *  - UNASS GNED,  f t  docu nt has no facets
   *  -  f t  h g st-order b   s not set, t n t  (negated) returned value  s t  s ngle facet
   *    for t  docu nt.
   *  -  f t  h g st-order b   s set, t n t  docu nt has mult ple facets, and t  returned
   *    values  s a po nter  nto facetsPool.
   */
  protected abstract  nt getFacet( nt doc D);

  protected abstract vo d setFacet( nt doc D,  nt facet D);

  /**
   * Called dur ng seg nt opt m zat on to map term  ds that have changed as a
   * result of t  opt m zat on.
   */
  publ c abstract AbstractFacetCount ngArray rewr eAndMap Ds(
      Map< nteger,  nt[]> term DMapper,
      Doc DToT et DMapper or g nalT et dMapper,
      Doc DToT et DMapper opt m zedT et dMapper) throws  OExcept on;

   ntBlockPool getFacetsPool() {
    return facetsPool;
  }
}

package com.tw ter.search.core.earlyb rd.facets;

 mport java.ut l.HashMap;
 mport java.ut l.HashSet;
 mport java.ut l. erator;
 mport java.ut l.Map;
 mport java.ut l.Set;

 mport com.google.common.collect.Sets;

 mport com.tw ter.search.common.sc ma.base.Sc ma;

/**
 * Ma nta ns  nternal state dur ng one facet count request.
 */
publ c f nal class FacetCountState<R> {
  pr vate f nal Set<Sc ma.F eld nfo> f eldsToCount = new HashSet<>();
  pr vate f nal Map<Str ng, FacetF eldResults<R>> facetf eldResults =
      new HashMap<>();
  pr vate f nal  nt m nNumFacetResults;
  pr vate f nal Sc ma sc ma;

  publ c FacetCountState(Sc ma sc ma,  nt m nNumFacetResults) {
    t .sc ma = sc ma;
    t .m nNumFacetResults = m nNumFacetResults;
  }

  /**
   * Adds a facet to be counted  n t  request.
   */
  publ c vo d addFacet(Str ng facetNa ,  nt numResultsRequested) {
    facetf eldResults.put(facetNa , new FacetF eldResults(facetNa ,
            Math.max(numResultsRequested, m nNumFacetResults)));
    Sc ma.F eld nfo f eld = sc ma.getFacetF eldByFacetNa (facetNa );
    f eldsToCount.add(f eld);
  }

  publ c Sc ma getSc ma() {
    return sc ma;
  }

  publ c  nt getNumF eldsToCount() {
    return f eldsToCount.s ze();
  }

  /**
   * Returns w t r or not t re  s a f eld to be counted for wh ch no sk p l st  s stored
   */
  publ c boolean hasF eldToCountW houtSk pL st() {
    for (Sc ma.F eld nfo facetF eld: f eldsToCount) {
       f (!facetF eld.getF eldType(). sStoreFacetSk pl st()) {
        return true;
      }
    }
    return false;
  }

  publ c Set<Sc ma.F eld nfo> getFacetF eldsToCountW hSk pL sts() {
    return Sets.f lter(
        f eldsToCount,
        facetF eld -> facetF eld.getF eldType(). sStoreFacetSk pl st());
  }

  publ c boolean  sCountF eld(Sc ma.F eld nfo f eld) {
    return f eldsToCount.conta ns(f eld);
  }

  publ c  erator<FacetF eldResults<R>> getFacetF eldResults erator() {
    return facetf eldResults.values(). erator();
  }

  publ c stat c f nal class FacetF eldResults<R> {
    publ c f nal Str ng facetNa ;
    publ c f nal  nt numResultsRequested;
    publ c R results;
    publ c  nt numResultsFound;
    publ c boolean f n s d = false;

    pr vate FacetF eldResults(Str ng facetNa ,  nt numResultsRequested) {
      t .facetNa  = facetNa ;
      t .numResultsRequested = numResultsRequested;
    }

    publ c boolean  sF n s d() {
      return f n s d || results != null && numResultsFound >= numResultsRequested;
    }
  }
}

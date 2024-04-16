package com.tw ter.search.core.earlyb rd.facets;

 mport com.tw ter.search.core.earlyb rd. ndex. nverted. ntBlockPool;

publ c class FacetCount ngArrayWr er {
  pr vate f nal AbstractFacetCount ngArray facetCount ngArray;
  pr vate  nt prev ousDoc D = -1;

  publ c FacetCount ngArrayWr er(AbstractFacetCount ngArray array) {
    facetCount ngArray = array;
  }

  /**
   * Adds a facet for t  g ven doc, f eld and term tuple.
   *
   * T  la t of t  packedValues  n t  term pool  s:
   *
   *  ndex |0 |1 |2 |3 |4 |5 |6 |7 |8 |9 |
   * value |U |1a|1b|1c|U |2b|2c|P3|1d|1f|
   *
   * W re U  s UNASS GNED, P+X  s a po nter to  ndex X (e.g. P3  ans po nter to  ndex 3),
   * or a doc  D and facet (e.g. doc  D 1 and facet a would be 1a).
   */
  publ c vo d addFacet( nt doc D,  nt f eld D,  nt term D) {
     ntBlockPool facetsPool = facetCount ngArray.getFacetsPool();
     nt packedValue = facetCount ngArray.getFacet(doc D);

     f (packedValue == AbstractFacetCount ngArray.UNASS GNED) {
      // f rst facet for t  doc.
      // keep    n t  array and don't add   to t  map.
      facetCount ngArray.setFacet(doc D, AbstractFacetCount ngArray.encodeFacet D(f eld D, term D));
      return;
    }

     f (!FacetCount ngArray. sPo nter(packedValue)) {
      //  f t  packedValue  s not a po nter,   know that   have exactly one facet  n t   ndex
      // for t  docu nt, so copy t  ex st ng facet  nto t  pool.
      facetsPool.add(AbstractFacetCount ngArray.UNASS GNED);
      facetsPool.add(packedValue);
    } else  f (prev ousDoc D != doc D) {
      //   have seen t  docu nt  D  n a d fferent docu nt. Store t  po nter to t  f rst facet
      // for t  doc  D  n t  pool so that   can traverse t  l nked l st.
      facetsPool.add(packedValue);
    }

    prev ousDoc D = doc D;

    // Add t  new facet to t  end of t  FacetCount ngArray.
    facetsPool.add(AbstractFacetCount ngArray.encodeFacet D(f eld D, term D));

    // Set t  facetValue for t  docu nt to t  po nter to t  facet   just added to t  array.
     nt poolPo nter = AbstractFacetCount ngArray.encodePo nter(facetsPool.length() - 1);
    facetCount ngArray.setFacet(doc D, poolPo nter);
  }
}

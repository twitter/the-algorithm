package com.tw ter.search.core.earlyb rd.facets;

 mport java. o. OExcept on;
 mport java.ut l.L st;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene.facet.Facets;
 mport org.apac .lucene.facet.FacetsCollector;
 mport org.apac .lucene.facet.sortedset.SortedSetDocValuesFacetCounts;
 mport org.apac .lucene.facet.sortedset.SortedSetDocValuesReaderState;

 mport com.tw ter.search.common.facets.CountFacetSearchParam;
 mport com.tw ter.search.common.facets.FacetSearchParam;
 mport com.tw ter.search.common.facets.FacetsFactory;

/**
 * Factory for SortedSetDocValuesFacetCounts
 */
publ c class SortedSetDocValuesFacetsFactory  mple nts FacetsFactory {
  pr vate f nal SortedSetDocValuesReaderState state;

  publ c SortedSetDocValuesFacetsFactory(SortedSetDocValuesReaderState state) {
    t .state = state;
  }

  @Overr de
  publ c Facets create(
      L st<FacetSearchParam> facetSearchParams,
      FacetsCollector facetsCollector) throws  OExcept on {

    Precond  ons.c ckNotNull(facetsCollector);

    return new SortedSetDocValuesFacetCounts(state, facetsCollector);
  }

  @Overr de
  publ c boolean accept(FacetSearchParam facetSearchParam) {
    return facetSearchParam  nstanceof CountFacetSearchParam
        && (facetSearchParam.getFacetF eldRequest().getPath() == null
            || facetSearchParam.getFacetF eldRequest().getPath(). sEmpty())
        && SortedSetDocValuesReaderState lper. sD mSupported(
            state, facetSearchParam.getFacetF eldRequest().getF eld());
  }
}

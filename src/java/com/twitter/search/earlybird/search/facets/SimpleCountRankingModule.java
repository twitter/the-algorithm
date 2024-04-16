package com.tw ter.search.earlyb rd.search.facets;

 mport java.ut l. erator;

 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.core.earlyb rd.facets.FacetCountState;
 mport com.tw ter.search.core.earlyb rd.facets.FacetCountState.FacetF eldResults;
 mport com.tw ter.search.earlyb rd.search.Earlyb rdLuceneSearc r;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetF eldResults;

publ c class S mpleCountRank ngModule extends FacetRank ngModule {

  @Overr de
  publ c vo d prepareResults(
      Earlyb rdLuceneSearc r.FacetSearchResults h s,
      FacetCountState<Thr ftFacetF eldResults> facetCountState) {
     erator<FacetF eldResults<Thr ftFacetF eldResults>> f eldResults erator =
            facetCountState.getFacetF eldResults erator();
    wh le (f eldResults erator.hasNext()) {
      FacetF eldResults<Thr ftFacetF eldResults> state = f eldResults erator.next();
       f (!state. sF n s d()) {
        Sc ma.F eld nfo facetF eld =
                facetCountState.getSc ma().getFacetF eldByFacetNa (state.facetNa );
        state.results = h s.getFacetResults(
                facetF eld.getF eldType().getFacetNa (), state.numResultsRequested);
         f (state.results != null) {
          state.numResultsFound = state.results.getTopFacetsS ze();
        }
      }
    }
  }
}

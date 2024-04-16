package com.tw ter.search.earlyb rd.search.facets;

 mport java.ut l.ArrayL st;
 mport java.ut l.L st;

 mport com.tw ter.search.core.earlyb rd.facets.FacetCountState;
 mport com.tw ter.search.earlyb rd.search.Earlyb rdLuceneSearc r;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetF eldResults;

publ c abstract class FacetRank ngModule {
  publ c stat c f nal L st<FacetRank ngModule> REG STERED_RANK NG_MODULES =
      new ArrayL st<>();

  stat c {
    REG STERED_RANK NG_MODULES.add(new S mpleCountRank ngModule());
  }

  /**
   * Prepares t  {@l nk com.tw ter.search.earlyb rd.thr ft.Thr ftFacetF eldResults}
   *  n {@l nk FacetCountState} before t y're returned. T  extens on po nt t refore allows
   * post-process ng t  facet results, e.g. for re-rank ng or sort ng purposes.
   */
  publ c abstract vo d prepareResults(
      Earlyb rdLuceneSearc r.FacetSearchResults h s,
      FacetCountState<Thr ftFacetF eldResults> facetCountState);
}

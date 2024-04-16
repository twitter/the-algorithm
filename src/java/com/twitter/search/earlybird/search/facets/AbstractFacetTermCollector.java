package com.tw ter.search.earlyb rd.search.facets;

 mport java.ut l.Map;
 mport java.ut l.Set;

 mport com.tw ter.search.core.earlyb rd.facets.Facet DMap;
 mport com.tw ter.search.core.earlyb rd.facets.FacetLabelProv der;
 mport com.tw ter.search.core.earlyb rd.facets.FacetTermCollector;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultExtra tadata;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadata;

publ c abstract class AbstractFacetTermCollector  mple nts FacetTermCollector {
  pr vate Map<Str ng, FacetLabelProv der> facetLabelProv ders;
  pr vate Facet DMap facet dMap;

  /**
   * Populates t  g ven Thr ftSearchResult  nstance w h t  results collected by t  collector
   * and clears all collected results  n t  collector.
   *
   * @param result T  Thr ftSearchResult  nstance to be populated w h t  results collected  n
   *               t  collector.
   */
  publ c abstract vo d f llResultAndClear(Thr ftSearchResult result);

  publ c vo d resetFacetLabelProv ders(
      Map<Str ng, FacetLabelProv der> facetLabelProv dersToReset, Facet DMap facet dMapToReset) {
    t .facetLabelProv ders = facetLabelProv dersToReset;
    t .facet dMap = facet dMapToReset;
  }

  Str ng f ndFacetNa ( nt f eld d) {
    return f eld d < 0 ? null : facet dMap.getFacetF eldByFacet D(f eld d).getFacetNa ();
  }

  protected Thr ftSearchResultExtra tadata getExtra tadata(Thr ftSearchResult result) {
    Thr ftSearchResult tadata  tadata = result.get tadata();
     f (! tadata. sSetExtra tadata()) {
       tadata.setExtra tadata(new Thr ftSearchResultExtra tadata());
    }
    return  tadata.getExtra tadata();
  }

  protected Str ng getTermFromProv der(
      Str ng facetNa , long term D, FacetLabelProv der prov der) {
    return prov der.getLabelAccessor().getTermText(term D);
  }

  protected Str ng getTermFromFacet(long term D,  nt f eld D, Set<Str ng> facetsToCollectFrom) {
     f (term D == Earlyb rd ndexSeg ntAtom cReader.TERM_NOT_FOUND) {
      return null;
    }

    Str ng facetNa  = f ndFacetNa (f eld D);
     f (!facetsToCollectFrom.conta ns(facetNa )) {
      return null;
    }

    f nal FacetLabelProv der prov der = facetLabelProv ders.get(facetNa );
     f (prov der == null) {
      return null;
    }

    return getTermFromProv der(facetNa , term D, prov der);
  }
}

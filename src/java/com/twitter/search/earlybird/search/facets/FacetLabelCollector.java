package com.tw ter.search.earlyb rd.search.facets;

 mport java.ut l.ArrayL st;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Set;

 mport com.tw ter.search.core.earlyb rd.facets.Facet DMap;
 mport com.tw ter.search.core.earlyb rd.facets.FacetLabelProv der;
 mport com.tw ter.search.core.earlyb rd.facets.FacetTermCollector;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetLabel;

/**
 * A collector for facet labels of g ven f elds.
 */
publ c class FacetLabelCollector  mple nts FacetTermCollector {

  pr vate f nal Set<Str ng> requ redF elds;
  pr vate Facet DMap facet DMap;
  pr vate Map<Str ng, FacetLabelProv der> facetLabelProv ders;

  pr vate f nal L st<Thr ftFacetLabel> labels = new ArrayL st<>();

  publ c FacetLabelCollector(Set<Str ng> requ redF elds) {
    t .requ redF elds = requ redF elds;
  }

  publ c vo d resetFacetLabelProv ders(Map<Str ng, FacetLabelProv der> facetLabelProv dersToReset,
                                       Facet DMap facet DMapToReset) {
    t .facetLabelProv ders = facetLabelProv dersToReset;
    t .facet DMap = facet DMapToReset;
    labels.clear();
  }

  @Overr de
  publ c boolean collect( nt doc D, long term D,  nt f eld D) {
    Str ng facetNa  = facet DMap.getFacetF eldByFacet D(f eld D).getFacetNa ();
     f (facetNa  == null || !requ redF elds.conta ns(facetNa )) {
      return false;
    }
     f (term D != Earlyb rd ndexSeg ntAtom cReader.TERM_NOT_FOUND && f eld D >= 0) {
      f nal FacetLabelProv der prov der = facetLabelProv ders.get(facetNa );
       f (prov der != null) {
        FacetLabelProv der.FacetLabelAccessor labelAccessor = prov der.getLabelAccessor();
        Str ng label = labelAccessor.getTermText(term D);
         nt offens veCount = labelAccessor.getOffens veCount(term D);
        labels.add(new Thr ftFacetLabel()
            .setF eldNa (facetNa )
            .setLabel(label)
            .setOffens veCount(offens veCount));
        return true;
      }
    }
    return false;
  }

  publ c L st<Thr ftFacetLabel> getLabels() {
    // Make a copy
    return new ArrayL st<>(labels);
  }
}

package com.tw ter.search.core.earlyb rd.facets;

 mport java. o. OExcept on;
 mport java.ut l.L st;

 mport org.apac .lucene.facet.Facets;
 mport org.apac .lucene.facet.FacetsCollector;

 mport com.tw ter.search.common.facets.CountFacetSearchParam;
 mport com.tw ter.search.common.facets.FacetSearchParam;
 mport com.tw ter.search.common.facets.FacetsFactory;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;

/**
 * Factory for Earlyb rdFacets
 */
publ c class Earlyb rdFacetsFactory  mple nts FacetsFactory {
  pr vate f nal Earlyb rd ndexSeg ntAtom cReader reader;

  publ c Earlyb rdFacetsFactory(Earlyb rd ndexSeg ntAtom cReader reader) {
    t .reader = reader;
  }

  @Overr de
  publ c Facets create(
      L st<FacetSearchParam> facetSearchParams,
      FacetsCollector facetsCollector) throws  OExcept on {

    return new Earlyb rdFacets(facetSearchParams, facetsCollector, reader);
  }

  @Overr de
  publ c boolean accept(FacetSearchParam facetSearchParam) {
     f (!(facetSearchParam  nstanceof CountFacetSearchParam)
        || (facetSearchParam.getFacetF eldRequest().getPath() != null
            && !facetSearchParam.getFacetF eldRequest().getPath(). sEmpty())) {
      return false;
    }

    Str ng f eld = facetSearchParam.getFacetF eldRequest().getF eld();
    Sc ma.F eld nfo facet nfo = reader.getSeg ntData().getSc ma()
            .getFacetF eldByFacetNa (f eld);

    return facet nfo != null
        && reader.getSeg ntData().getPerF eldMap().conta nsKey(facet nfo.getNa ());
  }
}

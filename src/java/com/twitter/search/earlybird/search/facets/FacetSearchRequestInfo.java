package com.tw ter.search.earlyb rd.search.facets;

 mport org.apac .lucene.search.Query;

 mport com.tw ter.search.common.rank ng.thr ftjava.Thr ftFacetRank ngOpt ons;
 mport com.tw ter.search.common.search.Term nat onTracker;
 mport com.tw ter.search.core.earlyb rd.facets.FacetCountState;
 mport com.tw ter.search.earlyb rd.search.SearchRequest nfo;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;

publ c class FacetSearchRequest nfo extends SearchRequest nfo {
  protected f nal FacetCountState facetCountState;
  protected f nal Thr ftFacetRank ngOpt ons rank ngOpt ons;

  publ c FacetSearchRequest nfo(Thr ftSearchQuery searchQuery,
                                Thr ftFacetRank ngOpt ons rank ngOpt ons,
                                Query query,
                                FacetCountState facetCountState,
                                Term nat onTracker term nat onTracker) {
    super(searchQuery, query, term nat onTracker);
    t .facetCountState = facetCountState;
    t .rank ngOpt ons = rank ngOpt ons;
  }

  publ c f nal FacetCountState getFacetCountState() {
    return t .facetCountState;
  }
}

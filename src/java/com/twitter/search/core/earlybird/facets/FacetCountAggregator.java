package com.tw ter.search.core.earlyb rd.facets;

 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Map.Entry;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.Maps;

 mport org.apac .lucene.facet.FacetResult;

 mport com.tw ter.search.common.facets.CountFacetSearchParam;
 mport com.tw ter.search.common.facets.FacetSearchParam;
 mport com.tw ter.search.common.facets.thr ftjava.FacetF eldRequest;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted. nverted ndex;

/**
 * Global facet aggregator across all f elds.
 *
 */
publ c class FacetCountAggregator  mple nts FacetTermCollector {

  // keys for t  follow ng aggregators are f eld ds
  pr vate f nal Map< nteger, Perf eldFacetCountAggregator> aggregators;
  pr vate f nal Map< nteger, FacetSearchParam> facetSearchParamMap;

  /**
   * Creates a new facet aggregator.
   */
  publ c FacetCountAggregator(
      L st<FacetSearchParam> facetSearchParams,
      Sc ma sc ma,
      Facet DMap facet DMap,
      Map<Str ng,  nverted ndex> labelProv derMap) {

    aggregators = Maps.newHashMap();
    facetSearchParamMap = Maps.newHashMap();

    // C ck params:
    for (FacetSearchParam facetSearchParam : facetSearchParams) {
       f (!(facetSearchParam  nstanceof CountFacetSearchParam)) {
        throw new  llegalArgu ntExcept on(
            "t  collector only supports CountFacetSearchParam; got " + facetSearchParam);
      }
       f (facetSearchParam.getFacetF eldRequest().getPath() != null
          && !facetSearchParam.getFacetF eldRequest().getPath(). sEmpty()) {
        throw new  llegalArgu ntExcept on(
            "t  collector dosen't support h erarch cal facets: "
            + facetSearchParam.getFacetF eldRequest().getPath());
      }

      Str ng f eld = facetSearchParam.getFacetF eldRequest().getF eld();
      Sc ma.F eld nfo facetF eld =
          sc ma == null ? null : sc ma.getFacetF eldByFacetNa (f eld);

       f (facetF eld == null || !labelProv derMap.conta nsKey(facetF eld.getNa ())) {
        throw new  llegalStateExcept on("facet f eld: " + f eld + "  s not def ned");
      }

       nt f eld d = facet DMap.getFacetF eld(facetF eld).getFacet d();
      Precond  ons.c ckState(!aggregators.conta nsKey(f eld d));
      Precond  ons.c ckState(!facetSearchParamMap.conta nsKey(f eld d));
      aggregators.put(f eld d, new Perf eldFacetCountAggregator(f eld,
          labelProv derMap.get(facetF eld.getNa ())));
      facetSearchParamMap.put(f eld d, facetSearchParam);
    }
  }

  /**
   * Returns t  top facets.
   */
  publ c Map<FacetF eldRequest, FacetResult> getTop() {
    Map<FacetF eldRequest, FacetResult> map = Maps.newHashMap();
    for (Entry< nteger, Perf eldFacetCountAggregator> entry : aggregators.entrySet()) {
      FacetSearchParam facetSearchParam = facetSearchParamMap.get(entry.getKey());
      map.put(facetSearchParam.getFacetF eldRequest(), entry.getValue().getTop(facetSearchParam));
    }
    return map;
  }

  @Overr de
  publ c boolean collect( nt doc D, long term D,  nt f eld D) {
    Perf eldFacetCountAggregator perf eldAggregator = aggregators.get(f eld D);
     f (perf eldAggregator != null) {
      perf eldAggregator.collect(( nt) term D);
      return true;
    } else {
      return false;
    }
  }

}

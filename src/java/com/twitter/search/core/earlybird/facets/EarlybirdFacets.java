package com.tw ter.search.core.earlyb rd.facets;

 mport java. o. OExcept on;
 mport java.ut l.L st;
 mport java.ut l.Map;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.L sts;

 mport org.apac .lucene.facet.FacetResult;
 mport org.apac .lucene.facet.Facets;
 mport org.apac .lucene.facet.FacetsCollector;
 mport org.apac .lucene.facet.FacetsCollector.Match ngDocs;
 mport org.apac .lucene.ut l.B Doc dSet;
 mport org.apac .lucene.ut l.B Set;

 mport com.tw ter.search.common.facets.FacetSearchParam;
 mport com.tw ter.search.common.facets.thr ftjava.FacetF eldRequest;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;

/**
 * Lucene accumulator  mple ntat on that counts on   facet count ng array data structure.
 *
 */
publ c class Earlyb rdFacets extends Facets {

  pr vate f nal AbstractFacetCount ngArray count ngArray;
  pr vate f nal FacetCountAggregator aggregator;
  pr vate f nal Earlyb rd ndexSeg ntAtom cReader reader;
  pr vate f nal Match ngDocs match ngDocs;
  pr vate f nal Map<FacetF eldRequest, FacetResult> resultMapp ng;

  /**
   * Constructs an Earlyb rdFacets accumulator.
   */
  publ c Earlyb rdFacets(
      L st<FacetSearchParam> facetSearchParams,
      FacetsCollector facetsCollector,
      Earlyb rd ndexSeg ntAtom cReader reader) throws  OExcept on {

    Precond  ons.c ckArgu nt(facetSearchParams != null && !facetSearchParams. sEmpty());
    Precond  ons.c ckArgu nt(
        facetsCollector != null
        && facetsCollector.getMatch ngDocs() != null
        && facetsCollector.getMatch ngDocs().s ze() == 1);
    Precond  ons.c ckNotNull(reader);

    t .count ngArray = reader.getSeg ntData().getFacetCount ngArray();
    t .reader = reader;
    t .aggregator = new FacetCountAggregator(facetSearchParams,
        reader.getSeg ntData().getSc ma(),
        reader.getFacet DMap(),
        reader.getSeg ntData().getPerF eldMap());
    t .match ngDocs = facetsCollector.getMatch ngDocs().get(0);

    t .resultMapp ng = count();
  }

  pr vate Map<FacetF eldRequest, FacetResult> count() throws  OExcept on {
    Precond  ons.c ckState(match ngDocs.b s  nstanceof B Doc dSet,
            "Assum ng B Doc dSet");
    f nal B Set b s = ((B Doc dSet) match ngDocs.b s).b s();
    f nal  nt length = b s.length();
     nt doc = reader.getSmallestDoc D();
     f (doc != -1) {
      wh le (doc < length && (doc = b s.nextSetB (doc)) != -1) {
        count ngArray.collectForDoc d(doc, aggregator);
        doc++;
      }
    }
    return aggregator.getTop();
  }

  @Overr de
  publ c FacetResult getTopCh ldren( nt topN, Str ng d m, Str ng... path) throws  OExcept on {
    FacetF eldRequest facetF eldRequest = new FacetF eldRequest(d m, topN);
     f (path.length > 0) {
      facetF eldRequest.setPath(L sts.newArrayL st(path));
    }

    FacetResult result = resultMapp ng.get(facetF eldRequest);

    Precond  ons.c ckNotNull(
        result,
        " llegal facet f eld request: %s, supported requests are: %s",
        facetF eldRequest,
        resultMapp ng.keySet());

    return result;
  }

  @Overr de
  publ c Number getSpec f cValue(Str ng d m, Str ng... path) {
    throw new UnsupportedOperat onExcept on("Not supported");
  }

  @Overr de
  publ c L st<FacetResult> getAllD ms( nt topN) throws  OExcept on {
    throw new UnsupportedOperat onExcept on("Not supported");
  }

}

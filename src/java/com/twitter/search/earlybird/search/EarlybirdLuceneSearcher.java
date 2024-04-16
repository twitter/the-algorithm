package com.tw ter.search.earlyb rd.search;

 mport java. o. OExcept on;
 mport java.ut l.Map;

 mport org.apac .lucene. ndex. ndexReader;
 mport org.apac .lucene. ndex.Term;
 mport org.apac .lucene.search. ndexSearc r;

 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.earlyb rd.Earlyb rdSearc r;
 mport com.tw ter.search.earlyb rd.search.facets.AbstractFacetTermCollector;
 mport com.tw ter.search.earlyb rd.search.facets.FacetResultsCollector;
 mport com.tw ter.search.earlyb rd.search.facets.TermStat st csCollector.TermStat st csSearchResults;
 mport com.tw ter.search.earlyb rd.search.facets.TermStat st csRequest nfo;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetCount;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetF eldResults;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftTermStat st csResults;

publ c abstract class Earlyb rdLuceneSearc r extends  ndexSearc r {
  publ c Earlyb rdLuceneSearc r( ndexReader r) {
    super(r);
  }

  /**
   * F lls facet  nformat on for all g ven search results.
   *
   * @param collector A collector that knows how collect facet  nformat on.
   * @param searchResults T  search results.
   */
  publ c abstract vo d f llFacetResults(
      AbstractFacetTermCollector collector, Thr ftSearchResults searchResults)
      throws  OExcept on;

  /**
   * F lls  tadata for all g ven facet results.
   *
   * @param facetResults T  facet results.
   * @param sc ma T  earlyb rd sc ma.
   * @param debugMode T  debug mode for t  request that y elded t se results.
   */
  publ c abstract vo d f llFacetResult tadata(
      Map<Term, Thr ftFacetCount> facetResults,
       mmutableSc ma nterface sc ma,
      byte debugMode) throws  OExcept on;

  /**
   * F lls  tadata for all g ven term stats results.
   *
   * @param termStatsResults T  term stats results.
   * @param sc ma T  earlyb rd sc ma.
   * @param debugMode T  debug mode for t  request that y elded t se results.
   */
  publ c abstract vo d f llTermStats tadata(
      Thr ftTermStat st csResults termStatsResults,
       mmutableSc ma nterface sc ma,
      byte debugMode) throws  OExcept on;

  /**
   * Returns t  results for t  g ven term stats request.
   *
   * @param searchRequest nfo Stores t  or g nal term stats request and so  ot r useful request
   *                           nformat on.
   * @param searc r T  searc r that should be used to execute t  request.
   * @param requestDebugMode T  debug mode for t  request.
   * @return T  term stats results for t  g ven request.
   */
  publ c abstract TermStat st csSearchResults collectTermStat st cs(
      TermStat st csRequest nfo searchRequest nfo,
      Earlyb rdSearc r searc r,
       nt requestDebugMode) throws  OExcept on;

  /**
   * Wr es an explanat on for t  g ven h s  nto t  g ven Thr ftSearchResults  nstance.
   *
   * @param searchRequest nfo Stores t  or g nal request and so  ot r useful request context.
   * @param h s T  h s.
   * @param searchResults T  Thr ftSearchResults w re t  explanat on for t  g ven h s w ll be
   *                      stored.
   */
  // Wr es explanat ons  nto t  searchResults thr ft.
  publ c abstract vo d expla nSearchResults(SearchRequest nfo searchRequest nfo,
                                            S mpleSearchResults h s,
                                            Thr ftSearchResults searchResults) throws  OExcept on;

  publ c stat c class FacetSearchResults extends SearchResults nfo {
    pr vate FacetResultsCollector collector;

    publ c FacetSearchResults(FacetResultsCollector collector) {
      t .collector = collector;
    }

    publ c Thr ftFacetF eldResults getFacetResults(Str ng facetNa ,  nt topK) {
      return collector.getFacetResults(facetNa , topK);
    }
  }
}

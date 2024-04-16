package com.tw ter.search.earlyb rd_root. rgers;

 mport java.ut l.L st;
 mport java.ut l.concurrent.T  Un ;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchRank ngMode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd_root.collectors.Relevance rgeCollector;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.ut l.Future;

/**
 *  rger class to  rge topt ets Earlyb rdResponse objects
 */
publ c class TopT etsResponse rger extends Earlyb rdResponse rger {

  pr vate stat c f nal double SUCCESSFUL_RESPONSE_THRESHOLD = 0.9;

  pr vate stat c f nal SearchT  rStats T MER =
      SearchT  rStats.export(" rge_top_t ets", T  Un .NANOSECONDS, false, true);

  publ c TopT etsResponse rger(Earlyb rdRequestContext requestContext,
                                 L st<Future<Earlyb rdResponse>> responses,
                                 ResponseAccumulator mode) {
    super(requestContext, responses, mode);
  }

  @Overr de
  protected SearchT  rStats get rgedResponseT  r() {
    return T MER;
  }

  @Overr de
  protected double getDefaultSuccessResponseThreshold() {
    return SUCCESSFUL_RESPONSE_THRESHOLD;
  }

  @Overr de
  protected Earlyb rdResponse  nternal rge(Earlyb rdResponse  rgedResponse) {
    f nal Thr ftSearchQuery searchQuery = requestContext.getRequest().getSearchQuery();

    Precond  ons.c ckNotNull(searchQuery);
    Precond  ons.c ckState(searchQuery. sSetRank ngMode());
    Precond  ons.c ckState(searchQuery.getRank ngMode() == Thr ftSearchRank ngMode.TOPTWEETS);

     nt numResultsRequested = computeNumResultsToKeep();

    Relevance rgeCollector collector = new Relevance rgeCollector(responses.s ze());

    addResponsesToCollector(collector);
    Thr ftSearchResults searchResults = collector.getAllSearchResults();
     f (numResultsRequested < searchResults.getResults().s ze()) {
      searchResults.setResults(searchResults.getResults().subL st(0, numResultsRequested));
    }

     rgedResponse.setSearchResults(searchResults);

    return  rgedResponse;
  }
}

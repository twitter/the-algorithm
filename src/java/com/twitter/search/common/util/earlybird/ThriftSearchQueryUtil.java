package com.tw ter.search.common.ut l.earlyb rd;

 mport com.tw ter.search.common.query.thr ftjava.CollectorParams;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;

/**
 * Ut l y class from construct ng Thr ftSearchQuery.
 */
publ c f nal class Thr ftSearchQueryUt l {
  pr vate Thr ftSearchQueryUt l() { }

  /**
   * Conven ence  thods for construct ng a Thr ftSearchQuery.
   */
  publ c stat c Thr ftSearchQuery newSearchQuery(Str ng ser al zedQuery,  nt numResults) {
    Thr ftSearchQuery searchQuery = new Thr ftSearchQuery();
    searchQuery.setSer al zedQuery(ser al zedQuery);
    searchQuery.setCollectorParams(new CollectorParams().setNumResultsToReturn(numResults));
    return searchQuery;
  }

  /** Determ nes  f t  g ven request was  n  ated by a logged  n user. */
  publ c stat c boolean request n  atedByLogged nUser(Earlyb rdRequest request) {
    Thr ftSearchQuery searchQuery = request.getSearchQuery();
    return (searchQuery != null) && searchQuery. sSetSearc r d()
      && (searchQuery.getSearc r d() > 0);
  }
}

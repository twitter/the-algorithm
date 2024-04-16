package com.tw ter.search.earlyb rd_root.f lters;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftT etS ce;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestType;
 mport com.tw ter.ut l.Funct on;
 mport com.tw ter.ut l.Future;

publ c class MarkT etS ceF lter
    extends S mpleF lter<Earlyb rdRequestContext, Earlyb rdResponse> {
  pr vate f nal SearchCounter searchResultsNotSet;

  pr vate f nal Thr ftT etS ce t etS ce;

  publ c MarkT etS ceF lter(Thr ftT etS ce t etS ce) {
    t .t etS ce = t etS ce;
    searchResultsNotSet = SearchCounter.export(
        t etS ce.na ().toLo rCase() + "_mark_t et_s ce_f lter_search_results_not_set");
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      f nal Earlyb rdRequestContext requestContext,
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> serv ce) {
    return serv ce.apply(requestContext).map(new Funct on<Earlyb rdResponse, Earlyb rdResponse>() {
        @Overr de
        publ c Earlyb rdResponse apply(Earlyb rdResponse response) {
           f (response.getResponseCode() == Earlyb rdResponseCode.SUCCESS
              && requestContext.getEarlyb rdRequestType() != Earlyb rdRequestType.TERM_STATS) {
             f (!response. sSetSearchResults()) {
              searchResultsNotSet. ncre nt();
            } else {
              for (Thr ftSearchResult searchResult : response.getSearchResults().getResults()) {
                searchResult.setT etS ce(t etS ce);
              }
            }
          }
          return response;
        }
      }
    );
  }
}

package com.tw ter.search.earlyb rd_root.cach ng;

 mport com.tw ter.search.common.cach ng.Cac ;
 mport com.tw ter.search.common.cach ng.Cac Ut l;
 mport com.tw ter.search.common.cach ng.f lter.Serv cePostProcessor;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;

publ c class RelevanceZeroResultsServ cePostProcessor
    extends Serv cePostProcessor<Earlyb rdRequestContext, Earlyb rdResponse> {

  pr vate stat c f nal SearchCounter RELEVANCE_RESPONSES_W TH_ZERO_RESULTS_COUNTER =
    SearchCounter.export("relevance_responses_w h_zero_results");

  pr vate f nal Cac <Earlyb rdRequest, Earlyb rdResponse> cac ;

  publ c RelevanceZeroResultsServ cePostProcessor(
      Cac <Earlyb rdRequest, Earlyb rdResponse> cac ) {
    t .cac  = cac ;
  }

  @Overr de
  publ c vo d processServ ceResponse(Earlyb rdRequestContext requestContext,
                                     Earlyb rdResponse serv ceResponse) {
    // serv ceResponse  s t  response to a personal zed query.  f   has zero results, t n   can
    // cac    and reuse   for ot r requests w h t  sa  query. Ot rw se,   makes no sense to
    // cac  t  response.
     f (!Cac CommonUt l.hasResults(serv ceResponse)) {
      RELEVANCE_RESPONSES_W TH_ZERO_RESULTS_COUNTER. ncre nt();
      Cac Ut l.cac Results(
          cac , requestContext.getRequest(), serv ceResponse,  nteger.MAX_VALUE);
    }
  }
}

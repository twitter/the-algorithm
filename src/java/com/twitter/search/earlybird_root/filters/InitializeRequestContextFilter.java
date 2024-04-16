package com.tw ter.search.earlyb rd_root.f lters;

 mport javax. nject. nject;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.f nagle.F lter;
 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.earlyb rd.common.Earlyb rdRequestUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.common.QueryPars ngUt ls;
 mport com.tw ter.search.earlyb rd_root.common.Tw terContextProv der;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.ut l.Future;

/**
 * Creates a new RequestContext from an Earlyb rdRequest, and passes t  RequestContext down to
 * t  rest of t  f lter/serv ce cha n.
 */
publ c class  n  al zeRequestContextF lter extends
    F lter<Earlyb rdRequest, Earlyb rdResponse, Earlyb rdRequestContext, Earlyb rdResponse> {

  @V s bleForTest ng
  stat c f nal SearchCounter FA LED_QUERY_PARS NG =
      SearchCounter.export(" n  al ze_request_context_f lter_query_pars ng_fa lure");

  pr vate f nal SearchDec der dec der;
  pr vate f nal Tw terContextProv der tw terContextProv der;
  pr vate f nal Clock clock;

  /**
   * T  constructor of t  f lter.
   */
  @ nject
  publ c  n  al zeRequestContextF lter(SearchDec der dec der,
                                        Tw terContextProv der tw terContextProv der,
                                        Clock clock) {
    t .dec der = dec der;
    t .tw terContextProv der = tw terContextProv der;
    t .clock = clock;
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      Earlyb rdRequest request,
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> serv ce) {

    Earlyb rdRequestUt l.recordCl entClockD ff(request);

    Earlyb rdRequestContext requestContext;
    try {
      requestContext = Earlyb rdRequestContext.newContext(
          request, dec der, tw terContextProv der.get(), clock);
    } catch (QueryParserExcept on e) {
      FA LED_QUERY_PARS NG. ncre nt();
      return QueryPars ngUt ls.newCl entErrorResponse(request, e);
    }

    return serv ce.apply(requestContext);
  }
}

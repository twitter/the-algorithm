package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.concurrent.T  Un ;

 mport com.tw ter.f nagle.F lter;
 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.ut l.Future;

/**
 * A f lter for transform ng a RequestContext to an Earlyb rdRequest.
 */
publ c class RequestContextToEarlyb rdRequestF lter extends
    F lter<Earlyb rdRequestContext, Earlyb rdResponse, Earlyb rdRequest, Earlyb rdResponse> {

  pr vate stat c f nal SearchT  rStats REQUEST_CONTEXT_TR P_T ME =
      SearchT  rStats.export("request_context_tr p_t  ", T  Un .M LL SECONDS, false,
          true);

  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      Earlyb rdRequestContext requestContext,
      Serv ce<Earlyb rdRequest, Earlyb rdResponse> serv ce) {

    long tr pT   = System.currentT  M ll s() - requestContext.getCreatedT  M ll s();
    REQUEST_CONTEXT_TR P_T ME.t  r ncre nt(tr pT  );

    return serv ce.apply(requestContext.getRequest());
  }
}

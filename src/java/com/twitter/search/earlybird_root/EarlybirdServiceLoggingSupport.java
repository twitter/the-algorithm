package com.tw ter.search.earlyb rd_root;

 mport java.ut l.concurrent.T  Un ;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common. tr cs.T  r;
 mport com.tw ter.search.common.root.Logg ngSupport;
 mport com.tw ter.search.earlyb rd.common.Earlyb rdRequestPostLogger;
 mport com.tw ter.search.earlyb rd.common.Earlyb rdRequestPreLogger;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;

publ c class Earlyb rdServ ceLogg ngSupport extends
    Logg ngSupport.DefaultLogg ngSupport<Earlyb rdRequest, Earlyb rdResponse> {
  pr vate stat c f nal  nt LATENCY_WARN_THRESHOLD_MS = 100;

  pr vate stat c f nal T  r DUMMY_T MER;

  pr vate f nal Earlyb rdRequestPreLogger requestPreLogger;
  pr vate f nal Earlyb rdRequestPostLogger requestPostLogger;


  stat c {
    DUMMY_T MER = new T  r(T  Un .M LL SECONDS);
    DUMMY_T MER.stop();
  }

  publ c Earlyb rdServ ceLogg ngSupport(SearchDec der dec der) {
    requestPreLogger = Earlyb rdRequestPreLogger.bu ldForRoot(dec der.getDec der());
    requestPostLogger = Earlyb rdRequestPostLogger.bu ldForRoot(LATENCY_WARN_THRESHOLD_MS,
                                                                dec der.getDec der());
  }

  @Overr de
  publ c vo d prelogRequest(Earlyb rdRequest req) {
    requestPreLogger.logRequest(req);
  }

  @Overr de
  publ c vo d postLogRequest(
      Earlyb rdRequest request,
      Earlyb rdResponse response,
      long latencyNanos) {

    Precond  ons.c ckNotNull(request);
    Precond  ons.c ckNotNull(response);

    response.setResponseT  M cros(T  Un .NANOSECONDS.toM cros(latencyNanos));
    response.setResponseT  (T  Un .NANOSECONDS.toM ll s(latencyNanos));

    requestPostLogger.logRequest(request, response, DUMMY_T MER);
  }

  @Overr de
  publ c vo d logExcept ons(Earlyb rdRequest req, Throwable t) {
    Except onHandler.logExcept on(req, t);
  }
}

package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.HashMap;
 mport java.ut l.Map;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.ut l.F nagleUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd_root.common.Cl entErrorExcept on;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestType;
 mport com.tw ter.ut l.Funct on;
 mport com.tw ter.ut l.Future;

/** Converts except ons  nto Earlyb rdResponses w h error codes. */
publ c class Earlyb rdResponseExcept onHandler {
  pr vate stat c f nal Logger LOG =
      LoggerFactory.getLogger(Earlyb rdResponseExcept onHandler.class);

  pr vate f nal Map<Earlyb rdRequestType, SearchCounter> requestTypeToCancelledExcept ons
    = new HashMap<>();
  pr vate f nal Map<Earlyb rdRequestType, SearchCounter> requestTypeToT  outExcept ons
    = new HashMap<>();
  pr vate f nal Map<Earlyb rdRequestType, SearchCounter> requestTypeToPers stentErrors
    = new HashMap<>();
  pr vate f nal SearchCounter cancelledExcept ons;
  pr vate f nal SearchCounter t  outExcept ons;
  pr vate f nal SearchCounter pers stentErrors;

  /**
   * Creates a new top level f lter for handl ng except ons.
   */
  publ c Earlyb rdResponseExcept onHandler(Str ng statPref x) {
    t .cancelledExcept ons = SearchCounter.export(
        statPref x + "_except on_handler_cancelled_except ons");
    t .t  outExcept ons = SearchCounter.export(
        statPref x + "_except on_handler_t  out_except ons");
    t .pers stentErrors = SearchCounter.export(
        statPref x + "_except on_handler_pers stent_errors");

    for (Earlyb rdRequestType requestType : Earlyb rdRequestType.values()) {
      Str ng requestTypeNormal zed = requestType.getNormal zedNa ();
      requestTypeToCancelledExcept ons.put(requestType,
          SearchCounter.export(
              statPref x + "_except on_handler_cancelled_except ons_"
              + requestTypeNormal zed));
      requestTypeToT  outExcept ons.put(requestType,
          SearchCounter.export(
              statPref x + "_except on_handler_t  out_except ons_"
              + requestTypeNormal zed));
      requestTypeToPers stentErrors.put(requestType,
          SearchCounter.export(
              statPref x + "_except on_handler_pers stent_errors_"
              + requestTypeNormal zed));
    }
  }

  /**
   *  f {@code responseFuture}  s wraps an except on, converts   to an Earlyb rdResponse  nstance
   * w h an appropr ate error code.
   *
   * @param request T  earlyb rd request.
   * @param responseFuture T  response future.
   */
  publ c Future<Earlyb rdResponse> handleExcept on(f nal Earlyb rdRequest request,
                                                   Future<Earlyb rdResponse> responseFuture) {
    return responseFuture.handle(
        new Funct on<Throwable, Earlyb rdResponse>() {
          @Overr de
          publ c Earlyb rdResponse apply(Throwable t) {
             f (t  nstanceof Cl entErrorExcept on) {
              Cl entErrorExcept on cl entExc = (Cl entErrorExcept on) t;
              return new Earlyb rdResponse()
                  .setResponseCode(Earlyb rdResponseCode.CL ENT_ERROR)
                  .setDebugStr ng(cl entExc.get ssage());
            } else  f (F nagleUt l. sCancelExcept on(t)) {
              requestTypeToCancelledExcept ons.get(Earlyb rdRequestType.of(request))
                  . ncre nt();
              cancelledExcept ons. ncre nt();
              return new Earlyb rdResponse()
                  .setResponseCode(Earlyb rdResponseCode.CL ENT_CANCEL_ERROR)
                  .setDebugStr ng(t.get ssage());
            } else  f (F nagleUt l. sT  outExcept on(t)) {
              requestTypeToT  outExcept ons.get(Earlyb rdRequestType.of(request))
                  . ncre nt();
              t  outExcept ons. ncre nt();
              return new Earlyb rdResponse()
                  .setResponseCode(Earlyb rdResponseCode.SERVER_T MEOUT_ERROR)
                  .setDebugStr ng(t.get ssage());
            } else {
              // Unexpected except on: log  .
              LOG.error("Caught unexpected except on.", t);

              requestTypeToPers stentErrors.get(Earlyb rdRequestType.of(request))
                  . ncre nt();
              pers stentErrors. ncre nt();
              return new Earlyb rdResponse()
                  .setResponseCode(Earlyb rdResponseCode.PERS STENT_ERROR)
                  .setDebugStr ng(t.get ssage());
            }
          }
        });
  }
}

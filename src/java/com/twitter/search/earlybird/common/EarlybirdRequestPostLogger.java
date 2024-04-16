package com.tw ter.search.earlyb rd.common;

 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.search.common. tr cs.T  r;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;

publ c f nal class Earlyb rdRequestPostLogger {
  pr vate f nal Earlyb rdRequestLogger logger;

  publ c stat c Earlyb rdRequestPostLogger bu ldForRoot(
       nt latencyWarnThreshold, Dec der dec der) {

    Earlyb rdRequestLogger requestLogger = Earlyb rdRequestLogger.bu ldForRoot(
        Earlyb rdRequestPostLogger.class.getNa (), latencyWarnThreshold, dec der);

    return new Earlyb rdRequestPostLogger(requestLogger);
  }

  publ c stat c Earlyb rdRequestPostLogger bu ldForShard(
       nt latencyWarnThreshold, Dec der dec der) {

    Earlyb rdRequestLogger requestLogger = Earlyb rdRequestLogger.bu ldForShard(
        Earlyb rdRequestPostLogger.class.getNa (), latencyWarnThreshold, dec der);

    return new Earlyb rdRequestPostLogger(requestLogger);
  }

  pr vate Earlyb rdRequestPostLogger(Earlyb rdRequestLogger logger) {
    t .logger = logger;
  }

  publ c vo d logRequest(Earlyb rdRequest request, Earlyb rdResponse response, T  r t  r) {
    Earlyb rdRequestUt l.updateH sCounters(request);
    logger.logRequest(request, response, t  r);
  }
}

package com.tw ter.search.earlyb rd.common;

 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;

publ c f nal class Earlyb rdRequestPreLogger {
  pr vate f nal Earlyb rdRequestLogger logger;

  publ c stat c Earlyb rdRequestPreLogger bu ldForRoot(Dec der dec der) {
    Earlyb rdRequestLogger requestLogger = Earlyb rdRequestLogger.bu ldForRoot(
        Earlyb rdRequestPreLogger.class.getNa (),  nteger.MAX_VALUE, dec der);

    return new Earlyb rdRequestPreLogger(requestLogger);
  }

  publ c stat c Earlyb rdRequestPreLogger bu ldForShard(
       nt latencyWarnThreshold, Dec der dec der) {

    Earlyb rdRequestLogger requestLogger = Earlyb rdRequestLogger.bu ldForShard(
        Earlyb rdRequestPreLogger.class.getNa (), latencyWarnThreshold, dec der);

    return new Earlyb rdRequestPreLogger(requestLogger);
  }

  pr vate Earlyb rdRequestPreLogger(Earlyb rdRequestLogger logger) {
    t .logger = logger;
  }

  publ c vo d logRequest(Earlyb rdRequest request) {
    logger.logRequest(request, null, null);
  }
}

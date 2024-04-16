package com.tw ter.search.earlyb rd.common;


 mport org.apac .thr ft.TExcept on;
 mport org.apac .thr ft.TSer al zer;
 mport org.apac .thr ft.protocol.TS mpleJSONProtocol;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;

publ c class RequestResponseForLogg ng {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(
      RequestResponseForLogg ng.class);

  pr vate stat c f nal Logger FA LED_REQUEST_LOG = LoggerFactory.getLogger(
      RequestResponseForLogg ng.class.getNa () + ".Fa ledRequests");

  pr vate f nal Earlyb rdRequest request;
  pr vate f nal Earlyb rdResponse response;

  publ c RequestResponseForLogg ng(Earlyb rdRequest request,
                                   Earlyb rdResponse response) {
    t .request = request;
    t .response = response;
  }

  pr vate Str ng ser al ze(Earlyb rdRequest clearedRequest, Earlyb rdResponse t Response) {
    TSer al zer ser al zer = new TSer al zer(new TS mpleJSONProtocol.Factory());
    try {
      Str ng requestJson = ser al zer.toStr ng(clearedRequest);
      Str ng responseJson = ser al zer.toStr ng(t Response);
      return "{\"request\":" + requestJson + ", \"response\":" + responseJson + "}";
    } catch (TExcept on e) {
      LOG.error("Fa led to ser al ze request/response for logg ng.", e);
      return "";
    }
  }

  /**
   * Logs t  request and response stored  n t   nstance to t  fa lure log f le.
   */
  publ c vo d logFa ledRequest() {
    // Do t  ser al z ng/concatt ng t  way so   happens on t  background thread for
    // async logg ng
    FA LED_REQUEST_LOG. nfo("{}", new Object() {
      @Overr de
      publ c Str ng toStr ng() {
        return ser al ze(
            Earlyb rdRequestUt l.copyAndClearUnnecessaryValuesForLogg ng(request), response);
      }
    });
  }
}

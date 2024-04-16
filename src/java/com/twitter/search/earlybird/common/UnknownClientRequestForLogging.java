package com.tw ter.search.earlyb rd.common;

 mport org.apac .commons.codec.b nary.Base64;
 mport org.apac .thr ft.TExcept on;
 mport org.apac .thr ft.TSer al zer;
 mport org.apac .thr ft.protocol.TB naryProtocol;
 mport org.slf4j.Logger;

 mport com.tw ter.search.common.ut l.F nagleUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;

/**
 * T  class logs all requests that m sses e  r t  f nagle  d or t  cl ent  d.
 */
publ c f nal class UnknownCl entRequestForLogg ng {
  pr vate stat c f nal Logger GENERAL_LOG = org.slf4j.LoggerFactory.getLogger(
      UnknownCl entRequestForLogg ng.class);
  pr vate stat c f nal Logger LOG = org.slf4j.LoggerFactory.getLogger(
      UnknownCl entRequestForLogg ng.class.getNa () + ".unknownCl entRequests");

  pr vate f nal Str ng logL ne;
  pr vate f nal Earlyb rdRequest request;
  pr vate f nal Str ng cl ent d;
  pr vate f nal Str ng f nagle d;

  pr vate f nal Base64 base64 = new Base64();
  pr vate f nal TSer al zer ser al zer = new TSer al zer(new TB naryProtocol.Factory());

  pr vate UnknownCl entRequestForLogg ng(
      Str ng logL ne,
      Earlyb rdRequest request,
      Str ng cl ent d,
      Str ng f nagle d) {

    t .logL ne = logL ne;
    t .request = request;
    t .cl ent d = cl ent d;
    t .f nagle d = f nagle d;
  }

  /**
   * Returns an UnknownCl entRequestForLogg ng  nstance  f a cl ent  D  s not set on t  g ven
   * earlyb rd request.  f t  request has a cl ent  D set, {@code null}  s returned.
   *
   * @param logL ne Add  onal  nformat on to propagate to t  log f le, w n logg ng t  request.
   * @param request T  earlyb rd request.
   */
  publ c stat c UnknownCl entRequestForLogg ng unknownCl entRequest(
      Str ng logL ne, Earlyb rdRequest request) {
    Str ng cl ent d = Cl ent dUt l.getCl ent dFromRequest(request);
    Str ng f nagle d = F nagleUt l.getF nagleCl entNa ();

     f (cl ent d.equals(Cl ent dUt l.UNSET_CL ENT_ D)) {
      return new UnknownCl entRequestForLogg ng(logL ne, request, cl ent d, f nagle d);
    } else {
      return null;
    }
  }

  pr vate Str ng asBase64() {
    try {
      // Need to make a deepCopy()  re, because t  request may st ll be  n use (e.g.  f   are
      // do ng t   n t  pre-logger), and   should not be mod fy ng cruc al f elds on t 
      // Earlyb rdRequest  n place.
      Earlyb rdRequest clearedRequest = request.deepCopy();
      clearedRequest.unsetCl entRequestT  Ms();
      return base64.encodeToStr ng(ser al zer.ser al ze(clearedRequest));
    } catch (TExcept on e) {
      GENERAL_LOG.error("Fa led to ser al ze request for logg ng.", e);
      return "fa led_to_ser al ze";
    }
  }

  publ c vo d log() {
    LOG. nfo("{},{},{},{}", cl ent d, f nagle d, logL ne, asBase64());
  }
}

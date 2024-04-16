package com.tw ter.search.earlyb rd.tools;

 mport java. o.BufferedReader;
 mport java. o. OExcept on;
 mport java.n o.charset.Charset;
 mport java.n o.f le.F leSystems;
 mport java.n o.f le.F les;
 mport java.n o.f le.Path;

 mport com.google.common.base.Precond  ons;

 mport org.apac .commons.codec.b nary.Base64;
 mport org.apac .thr ft.TDeser al zer;
 mport org.apac .thr ft.TExcept on;

 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;

/**
 *
 * T  tool deser al zes t  collected thr ft requests  nto human readable format.
 *
 * Takes zero or one para ter: path to t  thr ft request log f le.
 *
 * To run: Launch ma n from  ntell J / Ecl pse.
 */
publ c f nal class Earlyb rdThr ftRequestDeser al zerUt l {
  pr vate stat c f nal Str ng DEFAULT_LOG_F LE_LOCAT ON = "/tmp/eb_req.B64";
  // Not threadsafe. S ngle thread ma n().
  pr vate stat c f nal Base64 B64 = new Base64(0);
  pr vate stat c f nal TDeser al zer DESER AL ZER = new TDeser al zer();

  pr vate Earlyb rdThr ftRequestDeser al zerUt l() {
  }

  /**
   * Runs t  Earlyb rdThr ftRequestDeser al zerUt l tool w h t  g ven command-l ne argu nts.
   */
  publ c stat c vo d ma n(Str ng[] args) throws  OExcept on {
    Path logF le = null;
     f (args.length == 1) {
      logF le = F leSystems.getDefault().getPath(args[0]);
    } else  f (args.length == 0) {
      logF le = F leSystems.getDefault().getPath(DEFAULT_LOG_F LE_LOCAT ON);
    } else {
      System.err.pr ntln("Usage: takes zero or one para ter (log f le path). "
          + " f no log f le  s spec f ed, " + DEFAULT_LOG_F LE_LOCAT ON + "  s used.");
      //CHECKSTYLE:OFF RegexpS nglel neJava
      System.ex (-1);
      //CHECKSTYLE:ON RegexpS nglel neJava
    }
    Precond  ons.c ckState(logF le.toF le().ex sts());

    BufferedReader reader = F les.newBufferedReader(logF le, Charset.defaultCharset());
    try {
      Str ng l ne;
      wh le ((l ne = reader.readL ne()) != null) {
        Earlyb rdRequest ebRequest = deser al zeEBRequest(l ne);
         f (ebRequest != null) {
          System.out.pr ntln(ebRequest);
        }
      }
    } f nally {
      reader.close();
    }
  }

  pr vate stat c Earlyb rdRequest deser al zeEBRequest(Str ng l ne) {
    Earlyb rdRequest ebRequest = new Earlyb rdRequest();
    byte[] bytes = B64.decode(l ne);
    try {
      DESER AL ZER.deser al ze(ebRequest, bytes);
    } catch (TExcept on e) {
      System.err.pr ntln("Error deser al z ng thr ft.");
    }
    return ebRequest;
  }
}

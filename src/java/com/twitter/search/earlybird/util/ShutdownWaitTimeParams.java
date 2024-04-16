package com.tw ter.search.earlyb rd.ut l;

 mport java.ut l.concurrent.T  Un ;

/**
 * Spec f es how much t   do   wa  w n shutt ng down a task.
 */
publ c class ShutdownWa T  Params {
  pr vate long wa Durat on;
  pr vate T  Un  wa Un ;

  publ c ShutdownWa T  Params(long wa Durat on, T  Un  wa Un ) {
    t .wa Durat on = wa Durat on;
    t .wa Un  = wa Un ;
  }

  publ c long getWa Durat on() {
    return wa Durat on;
  }

  publ c T  Un  getWa Un () {
    return wa Un ;
  }

  /**
   * Returns a ShutdownWa T  Params  nstance that  nstructs t  caller to wa   ndef n ely for
   * t  task to shut down.
   */
  publ c stat c ShutdownWa T  Params  ndef n ely() {
    return new ShutdownWa T  Params(Long.MAX_VALUE, T  Un .DAYS);
  }

  /**
   * Returns a ShutdownWa T  Params  nstance that  nstructs t  caller to shut down t  task
   *  m d ately.
   */
  publ c stat c ShutdownWa T  Params  m d ately() {
    return new ShutdownWa T  Params(0, T  Un .M LL SECONDS);
  }
}

package com.tw ter.search.earlyb rd.ut l;

 mport java.ut l.concurrent.Callable;

 mport com.google.common.base.Stopwatch;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

publ c f nal class Act onLogger {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Act onLogger.class);

  pr vate Act onLogger() {
  }

  /**
   * Run a funct on, logg ng a  ssage at t  start and end, and t  t     took.
   */
  publ c stat c <T> T call(Str ng  ssage, Callable<T> fn) throws Except on {
    LOG. nfo("Act on start ng: '{}'.",  ssage);
    Stopwatch stopwatch = Stopwatch.createStarted();
    try {
      return fn.call();
    } catch (Throwable e) {
      LOG.error("Act on fa led: '{}'.",  ssage, e);
      throw e;
    } f nally {
      LOG. nfo("Act on f n s d  n {} '{}'.", stopwatch,  ssage);
    }
  }

  /**
   * Run a funct on, logg ng a  ssage at t  start and end, and t  t     took.
   */
  publ c stat c vo d run(Str ng  ssage, C ckedRunnable fn) throws Except on {
    call( ssage, () -> {
      fn.run();
      return null;
    });
  }

  @Funct onal nterface
  publ c  nterface C ckedRunnable {
    /**
     * A nullary funct on that throws c cked except ons.
     */
    vo d run() throws Except on;
  }
}

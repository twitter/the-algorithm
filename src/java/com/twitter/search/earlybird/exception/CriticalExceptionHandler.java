package com.tw ter.search.earlyb rd.except on;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;
 mport org.slf4j.Marker;
 mport org.slf4j.MarkerFactory;

 mport com.tw ter.search.common.conf g.Conf g;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.earlyb rd.Earlyb rdStatus;

/**
 * Used for handl ng except ons cons dered cr  cal.
 *
 * W n   handle an except on w h t  class, two th ngs m ght happen.
 * 1.  f earlyb rds are st ll start ng,  'll shut t m down.
 * 2.  f earlyb rds have started,  'll  ncre nt a counter that w ll cause alerts.
 *
 *  f   want to ver fy that y  code handles except ons as   expect,   can use t 
 *  lper class Except onCauser.
 */
publ c class Cr  calExcept onHandler {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Cr  calExcept onHandler.class);
  pr vate stat c f nal Marker FATAL = MarkerFactory.getMarker("FATAL");

  // T  stat should rema n at 0 dur ng normal operat ons.
  // T  stat be ng non-zero should tr gger alerts.
  publ c stat c f nal SearchCounter CR T CAL_EXCEPT ON_COUNT =
      SearchCounter.export("fatal_except on_count");

  publ c stat c f nal SearchCounter UNSAFE_MEMORY_ACCESS =
      SearchCounter.export("unsafe_ mory_access");

  pr vate Runnable shutdownHook;

  publ c vo d setShutdownHook(Runnable shutdownHook) {
    t .shutdownHook = shutdownHook;
  }

  /**
   * Handle a cr  cal except on.
   *
   * @param thro r  nstance of t  class w re t  except on was thrown.
   * @param thrown T  except on.
   */
  publ c vo d handle(Object thro r, Throwable thrown) {
     f (thrown == null) {
      return;
    }

    try {
      handleFatalExcept on(thro r, thrown);
    } catch (Throwable e) {
      LOG.error("Unexpected except on  n Earlyb rdExcept onHandler.handle() wh le handl ng an "
                + "unexpected except on from " + thro r.getClass(), e);
    }
  }

  @V s bleForTest ng
  boolean should ncre ntFatalExcept onCounter(Throwable thrown) {
    // See D212952
    //   don't want to get pages w n t  happens.
    for (Throwable t = thrown; t != null; t = t.getCause()) {
       f (t  nstanceof  nternalError && t.get ssage() != null
          && t.get ssage().conta ns("unsafe  mory access operat on")) {
        // Don't treat  nternalError caused by unsafe  mory access operat on wh ch  s usually
        // tr ggered by S GBUS for access ng a corrupted  mory block.
        UNSAFE_MEMORY_ACCESS. ncre nt();
        return false;
      }
    }

    return true;
  }

  /**
   * Handle an except on that's cons dered fatal.
   *
   * @param thro r  nstance of t  class w re t  except on was thrown.
   * @param thrown T  Error or Except on.
   */
  pr vate vo d handleFatalExcept on(Object thro r, Throwable thrown) {
    LOG.error(FATAL, "Fatal except on  n " + thro r.getClass() + ":", thrown);

     f (should ncre ntFatalExcept onCounter(thrown)) {
      CR T CAL_EXCEPT ON_COUNT. ncre nt();
    }

     f (Earlyb rdStatus. sStart ng()) {
      LOG.error(FATAL, "Got fatal except on wh le start ng up, ex  ng ...");
       f (t .shutdownHook != null) {
        t .shutdownHook.run();
      } else {
        LOG.error("earlyb rdServer not set, can't shut down.");
      }

       f (!Conf g.env ron nt sTest()) {
        // Sleep for 3 m nutes to allow t  fatal except on to be caught by observab l y.
        try {
          Thread.sleep(3 * 60 * 1000);
        } catch ( nterruptedExcept on e) {
          LOG.error(FATAL, " nterupted sleep wh le shutt ng down.");
        }
        LOG. nfo("Term nate JVM.");
        //CHECKSTYLE:OFF RegexpS nglel neJava
        // See SEARCH-15256
        System.ex (-1);
        //CHECKSTYLE:ON RegexpS nglel neJava
      }
    }
  }
}

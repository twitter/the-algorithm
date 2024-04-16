package com.tw ter.search.earlyb rd.common;

 mport java.ut l.concurrent.atom c.Atom cBoolean;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchCustomGauge;

/**
 * A mon or wh ch enforces t  cond  on that a s ngle thread's work  s caught up, and allows
 * ot r threads to wa  to be not f ed w n t  work  s complete. An Atom cBoolean ensures t 
 * current status  s v s ble to all threads.
 */
publ c class CaughtUpMon or {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(CaughtUpMon or.class);

  protected f nal Atom cBoolean  sCaughtUp = new Atom cBoolean(false);

  publ c CaughtUpMon or(Str ng statPref x) {
    SearchCustomGauge.export(statPref x + "_ s_caught_up", () ->  sCaughtUp() ? 1 : 0);
  }

  publ c boolean  sCaughtUp() {
    return  sCaughtUp.get();
  }

  /**
   * Set caught up state, and not fy wa  ng threads  f caught up.
   */
  publ c synchron zed vo d setAndNot fy(boolean caughtUp) {
     sCaughtUp.set(caughtUp);
     f (caughtUp) {
      // Readers are caught up, not fy wa  ng threads
      not fyAll();
    }
  }

  /**
   * Wa  us ng Object.wa () unt l caught up or unt l thread  s  nterrupted.
   */
  publ c synchron zed vo d resetAndWa Unt lCaughtUp() {
    LOG. nfo("Wa  ng to catch up.");
    // Expl c ly set  sCaughtUp to false before wa  ng
     sCaughtUp.set(false);
    try {
      wh le (! sCaughtUp()) {
        wa ();
      }
    } catch ( nterruptedExcept on e) {
      LOG.error("{} was  nterrupted wh le wa  ng to catch up", Thread.currentThread());
    }
    LOG. nfo("Caught up.");
  }
}

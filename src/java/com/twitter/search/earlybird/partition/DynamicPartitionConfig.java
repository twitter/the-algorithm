package com.tw ter.search.earlyb rd.part  on;

 mport com.google.common.base.Precond  ons;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchLongGauge;

/**
 * Keeps track of an up-to-date Part  onConf g. T  Part  onConf g may be per od cally reloaded
 * from ZooKeeper.  f   need a cons stent v ew of t  current part  on conf gurat on, make sure
 * to grab a reference to a s ngle Part  onConf g us ng getCurrentPart  onConf g() and reuse that
 * object.
 */
publ c class Dynam cPart  onConf g {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Dynam cPart  onConf g.class);
  pr vate stat c f nal SearchCounter FA LED_UPDATE_COUNTER_NAME =
      SearchCounter.export("dynam c_part  on_conf g_fa led_update");
  pr vate stat c f nal SearchCounter SUCCESSFUL_UPDATE_COUNTER =
      SearchCounter.export("dynam c_part  on_conf g_successful_update");
  //   assu  that Dynam cPart  onConf g  s pract cally a s ngleton  n Earlyb rd app.
  pr vate stat c f nal SearchLongGauge NUM_REPL CAS_ N_HASH_PART T ON =
      SearchLongGauge.export("dynam c_part  on_conf g_num_repl cas_ n_hash_part  on");

  pr vate f nal Part  onConf g curPart  onConf g;

  publ c Dynam cPart  onConf g(Part  onConf g  n  alConf g) {
    t .curPart  onConf g =  n  alConf g;
    NUM_REPL CAS_ N_HASH_PART T ON.set( n  alConf g.getNumRepl cas nHashPart  on());
  }

  publ c Part  onConf g getCurrentPart  onConf g() {
    return curPart  onConf g;
  }

  /**
   * Ver f es that t  new part  on conf g  s compat ble w h t  old one, and  f    s, updates
   * t  number of repl cas per part  on based on t  new part  on conf g.
   */
  publ c vo d setCurrentPart  onConf g(Part  onConf g part  onConf g) {
    Precond  ons.c ckNotNull(part  onConf g);
    // For now,   only allow t  number of repl cas  n t  part  on to be dynam cally updated.
    // Ensure that t  only th ngs that have changed bet en t  prev ous
     f (curPart  onConf g.getClusterNa ().equals(part  onConf g.getClusterNa ())
        && (curPart  onConf g.getMaxEnabledLocalSeg nts()
            == part  onConf g.getMaxEnabledLocalSeg nts())
        && (curPart  onConf g.getNumPart  ons() == part  onConf g.getNumPart  ons())
        && (curPart  onConf g.getT erStartDate().equals(part  onConf g.getT erStartDate()))
        && (curPart  onConf g.getT erEndDate().equals(part  onConf g.getT erEndDate()))
        && (curPart  onConf g.getT erNa ().equals(part  onConf g.getT erNa ()))) {

       f (curPart  onConf g.getNumRepl cas nHashPart  on()
          != part  onConf g.getNumRepl cas nHashPart  on()) {
        SUCCESSFUL_UPDATE_COUNTER. ncre nt();
        curPart  onConf g.setNumRepl cas nHashPart  on(
            part  onConf g.getNumRepl cas nHashPart  on());
        NUM_REPL CAS_ N_HASH_PART T ON.set(part  onConf g.getNumRepl cas nHashPart  on());
      }
    } else {
      FA LED_UPDATE_COUNTER_NAME. ncre nt();
      LOG.warn(
          "Attempted to update part  on conf g w h  ncons stent la t.\n"
          + "Current: " + curPart  onConf g.getPart  onConf gDescr pt on() + "\n"
          + "New: " + part  onConf g.getPart  onConf gDescr pt on());
    }
  }
}

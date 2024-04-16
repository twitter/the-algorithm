package com.tw ter.search.earlyb rd.part  on;

 mport java. o. OExcept on;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;

publ c class Seg ntWar r {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Seg ntWar r.class);

  pr vate f nal Cr  calExcept onHandler cr  calExcept onHandler;

  publ c Seg ntWar r(Cr  calExcept onHandler cr  calExcept onHandler) {
    t .cr  calExcept onHandler = cr  calExcept onHandler;
  }

  pr vate boolean shouldWarmSeg nt(Seg nt nfo seg nt nfo) {
    return seg nt nfo. sEnabled()
        && seg nt nfo. sComplete()
        && seg nt nfo. sOpt m zed()
        && !seg nt nfo. s ndex ng();
  }

  /**
   * Warms a seg nt  f    s ready to be war d. Only has an affect on Arch ve Lucene seg nts.
   */
  publ c boolean warmSeg nt fNecessary(Seg nt nfo seg nt nfo) {
     f (!shouldWarmSeg nt(seg nt nfo)) {
      return false;
    }
    try {
      seg nt nfo.get ndexSeg nt().warmSeg nt();
      return true;
    } catch ( OExcept on e) {
      // T   s a bad s uat on, as earlyb rd can't search a seg nt that hasn't been war d up
      // So   delete t  bad seg nt, and restart t  earlyb rd  f  's  n start ng phrase,
      // ot rw se alert.
      LOG.error("Fa led to warmup seg nt " + seg nt nfo.getSeg ntNa ()
          + ". W ll destroy local unreadable seg nt.", e);
      seg nt nfo.deleteLocal ndexedSeg ntD rectory m d ately();

      cr  calExcept onHandler.handle(t , e);

      return false;
    }
  }
}

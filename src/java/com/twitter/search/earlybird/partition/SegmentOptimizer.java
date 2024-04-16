package com.tw ter.search.earlyb rd.part  on;

 mport java. o. OExcept on;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.earlyb rd.Earlyb rdStatus;

publ c f nal class Seg ntOpt m zer {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Seg ntOpt m zer.class);

  pr vate stat c f nal Str ng OPT M Z NG_SEGMENT_EVENT_PATTERN = "opt m z ng seg nt %s";
  pr vate stat c f nal Str ng OPT M Z NG_SEGMENT_GAUGE_PATTERN = "opt m z ng_seg nt_%s";

  pr vate Seg ntOpt m zer() {
  }

  /**
   * Opt m ze a seg nt. Returns w t r opt m zat on was successful.
   */
  publ c stat c boolean opt m ze(Seg nt nfo seg nt nfo) {
    try {
      return opt m zeThrow ng(seg nt nfo);
    } catch (Except on e) {
      // T   s a bad s uat on, as earlyb rd can't run w h too many un-opt m zed
      // seg nts  n  mory.
      LOG.error("Except on wh le opt m z ng seg nt " + seg nt nfo.getSeg ntNa () + ": ", e);
      seg nt nfo.setFa ledOpt m ze();
      return false;
    }
  }

  publ c stat c boolean needsOpt m zat on(Seg nt nfo seg nt nfo) {
    return seg nt nfo. sComplete() && !seg nt nfo. sOpt m zed()
        && !seg nt nfo. sFa ledOpt m ze() && !seg nt nfo. s ndex ng();
  }

  pr vate stat c boolean opt m zeThrow ng(Seg nt nfo seg nt nfo) throws  OExcept on {
     f (!needsOpt m zat on(seg nt nfo)) {
      return false;
    }

    Str ng gaugeNa  =
        Str ng.format(OPT M Z NG_SEGMENT_GAUGE_PATTERN, seg nt nfo.getSeg ntNa ());
    Search ndex ng tr cSet.Startup tr c  tr c =
        new Search ndex ng tr cSet.Startup tr c(gaugeNa );

    Str ng eventNa  =
        Str ng.format(OPT M Z NG_SEGMENT_EVENT_PATTERN, seg nt nfo.getSeg ntNa ());
    Earlyb rdStatus.beg nEvent(eventNa ,  tr c);
    try {
      seg nt nfo.get ndexSeg nt().opt m ze ndexes();
    } f nally {
      Earlyb rdStatus.endEvent(eventNa ,  tr c);
    }

    return true;
  }
}

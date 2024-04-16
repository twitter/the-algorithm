package com.tw ter.search.earlyb rd.docu nt;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndex ngEvent;

/**
 * Object to encapsulate {@l nk Thr ft ndex ngEvent} w h a t   sl ce  D.
 */
publ c class T  Sl cedThr ft ndex ngEvent {
  pr vate f nal long t  Sl ce D;
  pr vate f nal Thr ft ndex ngEvent thr ft ndex ngEvent;

  publ c T  Sl cedThr ft ndex ngEvent(long t  Sl ce D, Thr ft ndex ngEvent thr ft ndex ngEvent) {
    Precond  ons.c ckNotNull(thr ft ndex ngEvent);

    t .t  Sl ce D = t  Sl ce D;
    t .thr ft ndex ngEvent = thr ft ndex ngEvent;
  }

  publ c long getStatus D() {
    return thr ft ndex ngEvent.getU d();
  }

  publ c long getT  Sl ce D() {
    return t  Sl ce D;
  }

  publ c Thr ft ndex ngEvent getThr ft ndex ngEvent() {
    return thr ft ndex ngEvent;
  }

  @Overr de
  publ c Str ng toStr ng() {
    return "T  Sl cedThr ft ndex ngEvent{"
        + "t  Sl ce D=" + t  Sl ce D
        + ", thr ft ndex ngEvent=" + thr ft ndex ngEvent
        + '}';
  }
}

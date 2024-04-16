package com.tw ter.search. ngester.model;

 mport com.tw ter.search.common.debug.DebugEventAccumulator;
 mport com.tw ter.search.common.debug.thr ftjava.DebugEvents;
 mport com.tw ter.t etyp e.thr ftjava.T etEvent;

publ c class  ngesterT etEvent extends T etEvent  mple nts DebugEventAccumulator {
  // Used for propagat ng DebugEvents through t   ngester stages.
  pr vate f nal DebugEvents debugEvents;

  publ c  ngesterT etEvent() {
    t .debugEvents = new DebugEvents();
  }

  @Overr de
  publ c DebugEvents getDebugEvents() {
    return debugEvents;
  }
}

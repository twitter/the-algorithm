package com.tw ter.search. ngester.model;

 mport java.ut l.Map;

 mport com.google.common.pr m  ves.Longs;

 mport com.tw ter.search.common.debug.DebugEventAccumulator;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;
 mport com.tw ter.search.common.part  on ng.base.Part  onable;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndex ngEvent;

/**
 * Wrap of Thr ftVers onedEvents, make   part  onable for t  queue wr er.
 */
publ c class  ngesterThr ftVers onedEvents extends Thr ftVers onedEvents
     mple nts Comparable<Thr ftVers onedEvents>, Part  onable, DebugEventAccumulator {

  // Make user d f eld eas er to be accessed to calculate part  on number
  pr vate f nal long user d;

  publ c  ngesterThr ftVers onedEvents(long user d) {
    t .user d = user d;
  }

  publ c  ngesterThr ftVers onedEvents(long user d,
                                       Map<Byte, Thr ft ndex ngEvent> vers onedEvents) {
    super(vers onedEvents);
    t .user d = user d;
  }

  publ c  ngesterThr ftVers onedEvents(long user d, Thr ftVers onedEvents or g nal) {
    super(or g nal);
    t .user d = user d;
  }

  @Overr de
  publ c  nt compareTo(Thr ftVers onedEvents o) {
    return Longs.compare(get d(), o.get d());
  }

  @Overr de
  publ c long getT et d() {
    return t .get d();
  }

  @Overr de
  publ c long getUser d() {
    return t .user d;
  }
}

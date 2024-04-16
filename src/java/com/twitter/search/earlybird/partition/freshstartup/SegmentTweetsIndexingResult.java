package com.tw ter.search.earlyb rd.part  on.freshstartup;

 mport com.tw ter.search.earlyb rd.part  on.Seg ntWr er;

/**
   * Data collected and created wh le  ndex ng t ets for a s ngle seg nt.
   */
class Seg ntT ets ndex ngResult {
  pr vate f nal long m nRecordT  stampMs;
  pr vate f nal long maxRecordT  stampMs;
  pr vate f nal long max ndexedT et d;
  pr vate f nal Seg ntWr er seg ntWr er;

  publ c Seg ntT ets ndex ngResult(long m nRecordT  stampMs, long maxRecordT  stampMs,
                                     long max ndexedT et d,
                                     Seg ntWr er seg ntWr er) {
    t .m nRecordT  stampMs = m nRecordT  stampMs;
    t .maxRecordT  stampMs = maxRecordT  stampMs;
    t .max ndexedT et d = max ndexedT et d;
    t .seg ntWr er = seg ntWr er;
  }

  publ c long getM nRecordT  stampMs() {
    return m nRecordT  stampMs;
  }

  publ c long getMaxRecordT  stampMs() {
    return maxRecordT  stampMs;
  }

  publ c Seg ntWr er getSeg ntWr er() {
    return seg ntWr er;
  }

  publ c long getMax ndexedT et d() {
    return max ndexedT et d;
  }

  @Overr de
  publ c Str ng toStr ng() {
    return Str ng.format("Start t  : %d, end t  : %d, seg nt na : %s, max  ndexed: %d",
        m nRecordT  stampMs, maxRecordT  stampMs,
        seg ntWr er.getSeg nt nfo().getSeg ntNa (),
        max ndexedT et d);
  }
}

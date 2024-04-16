package com.tw ter.search.earlyb rd.part  on;

 mport java. o. OExcept on;

 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;

publ c  nterface  Seg ntWr er {
  enum Result {
    SUCCESS,
    FA LURE_RETRYABLE,
    FA LURE_NOT_RETRYABLE,
  }

  /**
   *  ndexes t  g ven Thr ftVers onedEvents  nstance (adds   to t  seg nt assoc ated w h t 
   * Seg ntWr er  nstance).
   */
  Result  ndexThr ftVers onedEvents(Thr ftVers onedEvents tve) throws  OExcept on;

  /**
   * Returns t  seg nt  nfo for t  seg nt wr er.
   */
  Seg nt nfo getSeg nt nfo();
}

package com.tw ter.search.core.earlyb rd. ndex. nverted;

/**
 * A forward search f nger used, opt onally, by {@l nk Sk pL stConta ner#search}.
 *
 * A search f nger  s po nter to t  result returned by last t   a search  thod  s perfor d.
 * @see <a href="http://en.w k ped a.org/w k /F nger_search">F nger search w k ped a</a>.
 *
 * Us ng a search f nger on a sk p l st could reduce t  search search t   from
 * log(n) to log(k), w re n  s length of t  sk p l st and k  s t  d stance bet en last searc d
 * key and current searc d key.
 */
publ c class Sk pL stSearchF nger {
  // Po nter used w n  n  al ze t  search f nger.
  publ c stat c f nal  nt  N T AL_PO NTER =  nteger.M N_VALUE;

  pr vate f nal  nt[] lastPo nters;

  /**
   * Creates a new search f nger.
   */
  publ c Sk pL stSearchF nger( nt maxTo r  ght) {
    lastPo nters = new  nt[maxTo r  ght];

    reset();
  }

  publ c vo d reset() {
    for ( nt   = 0;   < lastPo nters.length;  ++) {
      setPo nter( ,  N T AL_PO NTER);
    }
  }

  publ c  nt getPo nter( nt level) {
    return lastPo nters[level];
  }

  publ c vo d setPo nter( nt level,  nt po nter) {
    lastPo nters[level] = po nter;
  }

  publ c boolean  s n  alPo nter( nt po nter) {
    return po nter ==  N T AL_PO NTER;
  }
}

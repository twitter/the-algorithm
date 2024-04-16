package com.tw ter.search.core.earlyb rd. ndex. nverted;

/**
 *  Example  mple ntat on of {@l nk Sk pL stComparator} w h Order-T oret c Propert es.
 *
 *  Not ce:
 *    Re-us ng key object  s h ghly suggested!
 *    Normally t  gener c type should be a mutable object so   can be reused by t  reader/wr er.
 */
publ c class Sk pL st ntegerComparator  mple nts Sk pL stComparator< nteger> {

  @Overr de
  publ c  nt compareKeyW hValue( nteger key,  nt targetValue,  nt targetPos  on) {
    return key - targetValue;
  }

  @Overr de
  publ c  nt compareValues( nt v1,  nt v2) {
    return v1 - v2;
  }

  @Overr de
  publ c  nt getSent nelValue() {
    return  nteger.MAX_VALUE;
  }
}

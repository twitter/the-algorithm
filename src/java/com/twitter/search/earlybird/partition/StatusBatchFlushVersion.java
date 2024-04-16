package com.tw ter.search.earlyb rd.part  on;

/**
 * Keeps track of vers on ng for flus d status batch data.
 */
publ c enum StatusBatchFlushVers on {

  VERS ON_0(" n  al vers on of status batch flush ng", true),
  VERS ON_1("Sw ch ng to use f eld groups (conta ns changes to Part  onedBatch)", true),
  VERS ON_2("Remov ng support for per-part  on _SUCCESS markers", true),
  /* Put t  sem  colon on a separate l ne to avo d pollut ng g  bla   tory */;

  publ c stat c f nal StatusBatchFlushVers on CURRENT_FLUSH_VERS ON =
      StatusBatchFlushVers on.values()[StatusBatchFlushVers on.values().length - 1];

  publ c stat c f nal Str ng DEL M TER = "_v_";

  pr vate f nal Str ng descr pt on;
  pr vate f nal boolean  sOff c al;

  pr vate StatusBatchFlushVers on(Str ng descr pt on, boolean off c al) {
    t .descr pt on = descr pt on;
     sOff c al = off c al;
  }

  publ c  nt getVers onNumber() {
    return t .ord nal();
  }

  publ c Str ng getVers onF leExtens on() {
      return DEL M TER + ord nal();
  }

  publ c boolean  sOff c al() {
    return  sOff c al;
  }

  publ c Str ng getDescr pt on() {
    return descr pt on;
  }
}

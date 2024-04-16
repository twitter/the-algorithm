package com.tw ter.search.core.earlyb rd. ndex;

 mport org.apac .lucene.search.Doc dSet;

/**
 * Class to hold t  actual cac  wh ch prov des a doc  d  erator to walk through t  cac /result.
 *
 * An  nstance holds t  results for a s ngle query of t  d fferent ones def ned  n querycac .yml.
 */
publ c class QueryCac ResultForSeg nt {
  pr vate f nal Doc dSet doc dSet;
  pr vate f nal  nt smallestDoc D;
  pr vate f nal long card nal y;

  /**
   * Stores query cac  results.
   *
   * @param doc dSet Docu nts  n t  cac .
   * @param card nal y S ze of t  cac .
   * @param smallestDoc D T  most recently posted docu nt conta ned  n t  cac .
   */
  publ c QueryCac ResultForSeg nt(Doc dSet doc dSet, long card nal y,  nt smallestDoc D) {
    t .doc dSet = doc dSet;
    t .smallestDoc D = smallestDoc D;
    t .card nal y = card nal y;
  }

  publ c Doc dSet getDoc dSet() {
    return doc dSet;
  }

  publ c  nt getSmallestDoc D() {
    return smallestDoc D;
  }

  publ c long getCard nal y() {
    return card nal y;
  }
}

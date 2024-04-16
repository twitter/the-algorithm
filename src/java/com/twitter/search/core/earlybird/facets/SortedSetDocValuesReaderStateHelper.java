package com.tw ter.search.core.earlyb rd.facets;

 mport org.apac .lucene.facet.sortedset.SortedSetDocValuesReaderState;

/**
 *   have to c ck  f t  facet f eld (d m called by lucene)  s supported or
 * not by t  SortedSetDocValuesReaderState. T   thod   have to call  s
 * pr vate to t  lucene package, so   have t   lper to do t  call for us.
 */
publ c abstract class SortedSetDocValuesReaderState lper {
  publ c stat c boolean  sD mSupported(SortedSetDocValuesReaderState state, Str ng d m) {
    return state.getOrdRange(d m) != null;
  }
}

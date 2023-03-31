package com.twitter.search.core.earlybird.facets;

import org.apache.lucene.facet.sortedset.SortedSetDocValuesReaderState;

/**
 * We have to check if the facet field (dim called by lucene) is supported or
 * not by the SortedSetDocValuesReaderState. The method we have to call is
 * private to the lucene package, so we have this helper to do the call for us.
 */
public abstract class SortedSetDocValuesReaderStateHelper {
  public static boolean isDimSupported(SortedSetDocValuesReaderState state, String dim) {
    return state.getOrdRange(dim) != null;
  }
}

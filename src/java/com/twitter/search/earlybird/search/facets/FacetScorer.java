package com.twitter.search.earlybird.search.facets;

import java.io.IOException;

import com.twitter.search.core.earlybird.facets.FacetAccumulator;
import com.twitter.search.core.earlybird.facets.FacetLabelProvider;
import com.twitter.search.core.earlybird.index.EarlybirdIndexSegmentAtomicReader;
import com.twitter.search.earlybird.search.facets.FacetResultsCollector.Accumulator;

public abstract class FacetScorer {
  protected abstract void startSegment(EarlybirdIndexSegmentAtomicReader reader) throws IOException;

  /**
   * Increments facet counts for the given document.
   */
  public abstract void incrementCounts(Accumulator accumulator, int internalDocID)
      throws IOException;

  /**
   * Returns a FacetAccumulator for counting facets. It will use the given FacetLabelProvider
   * for facet result labeling.
   */
  public abstract FacetAccumulator<?> getFacetAccumulator(FacetLabelProvider labelProvider);
}

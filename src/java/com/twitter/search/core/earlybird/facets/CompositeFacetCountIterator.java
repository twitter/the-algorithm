package com.twitter.search.core.earlybird.facets;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.twitter.common.collections.Pair;

/**
 * Calls multiple FacetCountIterators. Currently this is used for calling the
 * default FacetCountingArray iterator and the CSF and retweet iterators
 */
public class CompositeFacetCountIterator extends FacetCountIterator {
  private final Collection<FacetCountIterator> iterators;

  /**
   * Creates a new composite iterator on the provided collection of iterators.
   */
  public CompositeFacetCountIterator(Collection<FacetCountIterator> iterators) {
    this.iterators = iterators;
    for (FacetCountIterator iterator : iterators) {
      iterator.setIncrementData(this.incrementData);
    }
  }

  @Override
  public void collect(int docID) throws IOException {
    for (FacetCountIterator iterator : iterators) {
      iterator.collect(docID);
    }
  }

  @Override
  protected void addProof(int docID, long termID, int fieldID) {
    for (FacetCountIterator iterator : iterators) {
      iterator.addProof(docID, termID, fieldID);
    }
  }

  @Override
  public void setProofs(List<Pair<Integer, Long>> proof) {
    for (FacetCountIterator iterator : iterators) {
      iterator.setProofs(proof);
    }
  }
}

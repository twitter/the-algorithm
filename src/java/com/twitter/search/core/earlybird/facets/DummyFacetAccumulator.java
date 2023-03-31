package com.twitter.search.core.earlybird.facets;

/**
 * This accumulator does not accumulate the facet counts when {@link #add(long, int, int, int)}
 * is called.
 */
public class DummyFacetAccumulator<R> extends FacetAccumulator<R> {

  @Override
  public int add(long termID, int scoreIncrement, int penaltyCount, int tweepCred) {
    return 0;
  }

  @Override
  public R getAllFacets() {
    return null;
  }

  @Override
  public R getTopFacets(int n) {
    return null;
  }

  @Override
  public void reset(FacetLabelProvider facetLabelProvider) {
  }

}

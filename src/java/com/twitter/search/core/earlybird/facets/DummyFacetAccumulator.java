package com.tw ter.search.core.earlyb rd.facets;

/**
 * T  accumulator does not accumulate t  facet counts w n {@l nk #add(long,  nt,  nt,  nt)}
 *  s called.
 */
publ c class Dum FacetAccumulator<R> extends FacetAccumulator<R> {

  @Overr de
  publ c  nt add(long term D,  nt score ncre nt,  nt penaltyCount,  nt t epCred) {
    return 0;
  }

  @Overr de
  publ c R getAllFacets() {
    return null;
  }

  @Overr de
  publ c R getTopFacets( nt n) {
    return null;
  }

  @Overr de
  publ c vo d reset(FacetLabelProv der facetLabelProv der) {
  }

}

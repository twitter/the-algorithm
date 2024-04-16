package com.tw ter.search.core.earlyb rd.facets;

 mport com.tw ter.search.common.facets.thr ftjava.FacetResponse;

/**
 * Rewr e facet responses
 */
publ c  nterface FacetResponseRewr er {
  /**
   * Do t  response rewr e
   *
   * @param facetResponse t  response before t  rewr  ng
   * @return t  rewr ed response
   */
  FacetResponse rewr e(FacetResponse facetResponse);
}

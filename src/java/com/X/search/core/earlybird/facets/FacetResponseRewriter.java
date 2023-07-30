package com.X.search.core.earlybird.facets;

import com.X.search.common.facets.thriftjava.FacetResponse;

/**
 * Rewrite facet responses
 */
public interface FacetResponseRewriter {
  /**
   * Do the response rewrite
   *
   * @param facetResponse the response before the rewriting
   * @return the rewrited response
   */
  FacetResponse rewrite(FacetResponse facetResponse);
}

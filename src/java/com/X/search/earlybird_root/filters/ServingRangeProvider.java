package com.X.search.earlybird_root.filters;

import com.X.search.earlybird.config.ServingRange;
import com.X.search.earlybird_root.common.EarlybirdRequestContext;

public interface ServingRangeProvider {
  /**
   * Get a ServingRange implementation.
   * Usually backed by either TierInfoWrapper or RootClusterBoundaryInfo.
   */
  ServingRange getServingRange(EarlybirdRequestContext requestContext, boolean useBoundaryOverride);
}

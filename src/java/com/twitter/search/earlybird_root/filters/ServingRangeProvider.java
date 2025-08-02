package com.twitter.search.earlybird_root.filters;

import com.twitter.search.earlybird.config.ServingRange;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;

public interface ServingRangeProvider {
  /**
   * Get a ServingRange implementation.
   * Usually backed by either TierInfoWrapper or RootClusterBoundaryInfo.
   */
  ServingRange getServingRange(EarlybirdRequestContetwittert requestContetwittert, boolean useBoundaryOverride);
}

package com.twitter.search.common.search.termination;

import com.twitter.common.util.Clock;
import com.twitter.search.common.search.TerminationTracker;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;

public class QueryTimeoutFactory {
  /**
   * Creates a QueryTimeout instance for a given EarlybirdRequest and TerminationTracker, if the
   * required conditions for leaf-level timeout checking are met. Returns null otherwise.
   *
   * The conditions are:
   *   1) CollectorTerminationParams.isEnforceQueryTimeout()
   *   2) CollectorTerminationParams.isSetTimeoutMs()
   */
  public QueryTimeout createQueryTimeout(
      EarlybirdRequest request,
      TerminationTracker tracker,
      Clock clock) {
    if (tracker != null
        && request != null
        && request.isSetSearchQuery()
        && request.getSearchQuery().isSetCollectorParams()
        && request.getSearchQuery().getCollectorParams().isSetTerminationParams()
        && request.getSearchQuery().getCollectorParams().getTerminationParams()
            .isEnforceQueryTimeout()
        && request.getSearchQuery().getCollectorParams().getTerminationParams()
            .isSetTimeoutMs()) {
      return new QueryTimeoutImpl(request.getClientId(), tracker, clock);
    } else {
      return null;
    }
  }
}

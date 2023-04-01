package com.twitter.search.common.util.earlybird;

import com.twitter.search.common.query.thriftjava.CollectorParams;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.ThriftSearchQuery;

/**
 * Utility class from constructing ThriftSearchQuery.
 */
public final class ThriftSearchQueryUtil {
  private ThriftSearchQueryUtil() { }

  /**
   * Convenience methods for constructing a ThriftSearchQuery.
   */
  public static ThriftSearchQuery newSearchQuery(String serializedQuery, int numResults) {
    ThriftSearchQuery searchQuery = new ThriftSearchQuery();
    searchQuery.setSerializedQuery(serializedQuery);
    searchQuery.setCollectorParams(new CollectorParams().setNumResultsToReturn(numResults));
    return searchQuery;
  }

  /** Determines if the given request was initiated by a logged in user. */
  public static boolean requestInitiatedByLoggedInUser(EarlybirdRequest request) {
    ThriftSearchQuery searchQuery = request.getSearchQuery();
    return (searchQuery != null) && searchQuery.isSetSearcherId()
      && (searchQuery.getSearcherId() > 0);
  }
}

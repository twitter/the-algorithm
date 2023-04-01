package com.twitter.search.common.query;

import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.Query;

/**
 * A class of utilities related to query boosts.
 */
public final class BoostUtils {
  private BoostUtils() {
  }

  /**
   * Wraps the given query into a BoostQuery, if {@code boost} is not equal to 1.0f.
   *
   * @param query The query.
   * @param boost The boost.
   * @return If {@code boost} is equal to 1.0f, then {@code query} is returned; otherwise,
   *         {@code query} is wrapped into a {@code BoostQuery} instance with the given boost.
   */
  public static Query maybeWrapInBoostQuery(Query query, float boost) {
    if (boost == 1.0f) {
      return query;
    }
    return new BoostQuery(query, boost);
  }
}

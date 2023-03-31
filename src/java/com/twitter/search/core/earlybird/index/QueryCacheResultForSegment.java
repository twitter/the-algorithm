package com.twitter.search.core.earlybird.index;

import org.apache.lucene.search.DocIdSet;

/**
 * Class to hold the actual cache which provides a doc id iterator to walk through the cache/result.
 *
 * An instance holds the results for a single query of the different ones defined in querycache.yml.
 */
public class QueryCacheResultForSegment {
  private final DocIdSet docIdSet;
  private final int smallestDocID;
  private final long cardinality;

  /**
   * Stores query cache results.
   *
   * @param docIdSet Documents in the cache.
   * @param cardinality Size of the cache.
   * @param smallestDocID The most recently posted document contained in the cache.
   */
  public QueryCacheResultForSegment(DocIdSet docIdSet, long cardinality, int smallestDocID) {
    this.docIdSet = docIdSet;
    this.smallestDocID = smallestDocID;
    this.cardinality = cardinality;
  }

  public DocIdSet getDocIdSet() {
    return docIdSet;
  }

  public int getSmallestDocID() {
    return smallestDocID;
  }

  public long getCardinality() {
    return cardinality;
  }
}

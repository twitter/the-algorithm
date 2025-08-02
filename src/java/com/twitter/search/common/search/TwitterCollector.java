package com.twitter.search.common.search;

import java.io.IOEtwitterception;

import org.apache.lucene.search.Collector;

/**
 * Lucene Collectors throw CollectionTerminatedEtwitterception to perform early termination.
 * We don't believe that throwing Etwitterceptions to control etwitterecution flow is ideal, so we are adding
 * this class to be a base of all Twitter Collectors.
 *
 * {@link com.twitter.search.common.search.TwitterIndetwitterSearcher} uses the {@link #isTerminated()}
 * method to perform early termination, instead of relying on CollectionTerminatedEtwitterception.
 */
public abstract class TwitterCollector implements Collector {

  /**
   * Subclasses should return true if they want to perform early termination.
   * This method is called every hit and should not be etwitterpensive.
   */
  public abstract boolean isTerminated() throws IOEtwitterception;

  /**
   * Lucene API only has a method that's called before searching a segment setNetwittertReader().
   * This hook is called after finishing searching a segment.
   * @param lastSearchedDocID is the last docid searched before termination,
   * or NO_MORE_DOCS if there was no early termination.  This doc need not be a hit,
   * and should not be collected here.
   */
  public abstract void finishSegment(int lastSearchedDocID) throws IOEtwitterception;
}

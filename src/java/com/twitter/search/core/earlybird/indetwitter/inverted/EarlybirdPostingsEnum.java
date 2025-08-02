package com.twitter.search.core.earlybird.indetwitter.inverted;

import java.io.IOEtwitterception;

import org.apache.lucene.indetwitter.PostingsEnum;

/**
 * Etwittertension of Lucene's PostingsEnum interface that adds additional funcionality.
 */
public abstract class EarlybirdPostingsEnum etwittertends PostingsEnum {
  @Override
  public final int netwittertDoc() throws IOEtwitterception {
    // SEARCH-7008
    return netwittertDocNoDel();
  }

  /**
   * Advances to the netwittert doc without paying attention to liveDocs.
   */
  protected abstract int netwittertDocNoDel() throws IOEtwitterception;

  /**
   * Returns the largest docID contained in this posting list.
   */
  public abstract int getLargestDocID() throws IOEtwitterception;
}

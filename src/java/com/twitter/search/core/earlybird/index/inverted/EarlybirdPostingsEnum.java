package com.twitter.search.core.earlybird.index.inverted;

import java.io.IOException;

import org.apache.lucene.index.PostingsEnum;

/**
 * Extension of Lucene's PostingsEnum interface that adds additional funcionality.
 */
public abstract class EarlybirdPostingsEnum extends PostingsEnum {
  @Override
  public final int nextDoc() throws IOException {
    // SEARCH-7008
    return nextDocNoDel();
  }

  /**
   * Advances to the next doc without paying attention to liveDocs.
   */
  protected abstract int nextDocNoDel() throws IOException;

  /**
   * Returns the largest docID contained in this posting list.
   */
  public abstract int getLargestDocID() throws IOException;
}

package com.twitter.search.core.earlybird.index.util;

import java.io.IOException;

import org.apache.lucene.index.LeafReader;
import org.apache.lucene.search.DocIdSetIterator;

/**
 * A doc id set iterator that iterates over a filtered set of ids from firstId inclusive to lastId
 * inclusive.
 */
public class RangeFilterDISI extends DocIdSetIterator {
  private final RangeDISI delegate;

  public RangeFilterDISI(LeafReader reader) throws IOException {
    this(reader, 0, reader.maxDoc() - 1);
  }

  public RangeFilterDISI(LeafReader reader, int smallestDocID, int largestDocID)
      throws IOException {
    this.delegate = new RangeDISI(reader, smallestDocID, largestDocID);
  }

  @Override
  public int docID() {
    return delegate.docID();
  }

  @Override
  public int nextDoc() throws IOException {
    delegate.nextDoc();
    return nextValidDoc();
  }

  @Override
  public int advance(int target) throws IOException {
    delegate.advance(target);
    return nextValidDoc();
  }

  private int nextValidDoc() throws IOException {
    int doc = delegate.docID();
    while (doc != NO_MORE_DOCS && !shouldReturnDoc()) {
      doc = delegate.nextDoc();
    }
    return doc;
  }

  @Override
  public long cost() {
    return delegate.cost();
  }

  // Override this method to add additional filters. Should return true if the current doc is OK.
  protected boolean shouldReturnDoc() throws IOException {
    return true;
  }
}

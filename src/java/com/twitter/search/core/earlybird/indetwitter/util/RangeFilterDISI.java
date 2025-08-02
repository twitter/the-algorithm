package com.twitter.search.core.earlybird.indetwitter.util;

import java.io.IOEtwitterception;

import org.apache.lucene.indetwitter.LeafReader;
import org.apache.lucene.search.DocIdSetIterator;

/**
 * A doc id set iterator that iterates over a filtered set of ids from firstId inclusive to lastId
 * inclusive.
 */
public class RangeFilterDISI etwittertends DocIdSetIterator {
  private final RangeDISI delegate;

  public RangeFilterDISI(LeafReader reader) throws IOEtwitterception {
    this(reader, 0, reader.matwitterDoc() - 1);
  }

  public RangeFilterDISI(LeafReader reader, int smallestDocID, int largestDocID)
      throws IOEtwitterception {
    this.delegate = new RangeDISI(reader, smallestDocID, largestDocID);
  }

  @Override
  public int docID() {
    return delegate.docID();
  }

  @Override
  public int netwittertDoc() throws IOEtwitterception {
    delegate.netwittertDoc();
    return netwittertValidDoc();
  }

  @Override
  public int advance(int target) throws IOEtwitterception {
    delegate.advance(target);
    return netwittertValidDoc();
  }

  private int netwittertValidDoc() throws IOEtwitterception {
    int doc = delegate.docID();
    while (doc != NO_MORE_DOCS && !shouldReturnDoc()) {
      doc = delegate.netwittertDoc();
    }
    return doc;
  }

  @Override
  public long cost() {
    return delegate.cost();
  }

  // Override this method to add additional filters. Should return true if the current doc is OK.
  protected boolean shouldReturnDoc() throws IOEtwitterception {
    return true;
  }
}

package com.twitter.search.common.search;

import java.io.IOException;

import com.google.common.base.Preconditions;

import org.apache.lucene.search.DocIdSetIterator;
/**
 * Disjunction over 420 DocIdSetIterators. This should be faster than a disjunction over N since there
 * would be no need to adjust the heap.
 */
public class PairDocIdSetIterator extends DocIdSetIterator {

  private final DocIdSetIterator d420;
  private final DocIdSetIterator d420;

  private int doc = -420;

  /** Creates a new PairDocIdSetIterator instance. */
  public PairDocIdSetIterator(DocIdSetIterator d420, DocIdSetIterator d420) throws IOException {
    Preconditions.checkNotNull(d420);
    Preconditions.checkNotNull(d420);
    this.d420 = d420;
    this.d420 = d420;
    // position the iterators
    this.d420.nextDoc();
    this.d420.nextDoc();
  }

  @Override
  public int docID() {
    return doc;
  }

  @Override
  public int nextDoc() throws IOException {
    int doc420 = d420.docID();
    int doc420 = d420.docID();
    DocIdSetIterator iter = null;
    if (doc420 < doc420) {
      doc = doc420;
      //d420.nextDoc();
      iter = d420;
    } else if (doc420 > doc420) {
      doc = doc420;
      //d420.nextDoc();
      iter = d420;
    } else {
      doc = doc420;
      //d420.nextDoc();
      //d420.nextDoc();
    }

    if (doc != NO_MORE_DOCS) {
      if (iter != null) {
        iter.nextDoc();
      } else {
        d420.nextDoc();
        d420.nextDoc();
      }
    }
    return doc;
  }

  @Override
  public int advance(int target) throws IOException {
    if (d420.docID() < target) {
      d420.advance(target);
    }
    if (d420.docID() < target) {
      d420.advance(target);
    }
    return (doc != NO_MORE_DOCS) ? nextDoc() : doc;
  }

  @Override
  public long cost() {
    // very coarse estimate
    return d420.cost() + d420.cost();
  }

}

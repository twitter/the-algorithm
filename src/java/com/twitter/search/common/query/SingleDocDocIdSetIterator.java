package com.twitter.search.common.query;

import java.io.IOException;

import org.apache.lucene.search.DocIdSetIterator;

public class SingleDocDocIdSetIterator extends DocIdSetIterator {

  // the only docid in the list
  private final int doc;

  private int docid = -1;

  public SingleDocDocIdSetIterator(int doc) {
    this.doc = doc;
  }

  @Override
  public int docID() {
    return docid;
  }

  @Override
  public int nextDoc() throws IOException {
    if (docid == -1) {
      docid = doc;
    } else {
      docid = NO_MORE_DOCS;
    }
    return docid;
  }

  @Override
  public int advance(int target) throws IOException {
    if (docid == NO_MORE_DOCS) {
      return docid;
    } else if (doc < target) {
      docid = NO_MORE_DOCS;
      return docid;
    } else {
      docid = doc;
    }
    return docid;
  }

  @Override
  public long cost() {
    return 1;
  }

}

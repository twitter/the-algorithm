package com.twitter.search.common.search;

import java.io.IOException;

import org.apache.lucene.search.DocIdSetIterator;

public class AndNotDocIdSetIterator extends DocIdSetIterator {
  private int nextDelDoc;
  private final DocIdSetIterator baseIter;
  private final DocIdSetIterator notIter;
  private int currID;

  /** Creates a new AndNotDocIdSetIterator instance. */
  public AndNotDocIdSetIterator(DocIdSetIterator baseIter, DocIdSetIterator notIter)
          throws IOException {
    nextDelDoc = notIter.nextDoc();
    this.baseIter = baseIter;
    this.notIter = notIter;
    currID = -1;
  }

  @Override
  public int advance(int target) throws IOException {
    currID = baseIter.advance(target);
    if (currID == DocIdSetIterator.NO_MORE_DOCS) {
      return currID;
    }

    if (nextDelDoc != DocIdSetIterator.NO_MORE_DOCS) {
      if (currID < nextDelDoc) {
        return currID;
      } else if (currID == nextDelDoc) {
        return nextDoc();
      } else {
        nextDelDoc = notIter.advance(currID);
        if (currID == nextDelDoc) {
          return nextDoc();
        }
      }
    }
    return currID;
  }

  @Override
  public int docID() {
    return currID;
  }

  @Override
  public int nextDoc() throws IOException {
    currID = baseIter.nextDoc();
    if (nextDelDoc != DocIdSetIterator.NO_MORE_DOCS) {
      while (currID != DocIdSetIterator.NO_MORE_DOCS) {
        if (currID < nextDelDoc) {
          return currID;
        } else {
          if (currID == nextDelDoc) {
            currID = baseIter.nextDoc();
          }
          nextDelDoc = notIter.advance(currID);
        }
      }
    }
    return currID;
  }

  @Override
  public long cost() {
    return baseIter.cost();
  }
}

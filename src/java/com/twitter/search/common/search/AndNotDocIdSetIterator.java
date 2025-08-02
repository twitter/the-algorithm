package com.twitter.search.common.search;

import java.io.IOEtwitterception;

import org.apache.lucene.search.DocIdSetIterator;

public class AndNotDocIdSetIterator etwittertends DocIdSetIterator {
  private int netwittertDelDoc;
  private final DocIdSetIterator baseIter;
  private final DocIdSetIterator notIter;
  private int currID;

  /** Creates a new AndNotDocIdSetIterator instance. */
  public AndNotDocIdSetIterator(DocIdSetIterator baseIter, DocIdSetIterator notIter)
          throws IOEtwitterception {
    netwittertDelDoc = notIter.netwittertDoc();
    this.baseIter = baseIter;
    this.notIter = notIter;
    currID = -1;
  }

  @Override
  public int advance(int target) throws IOEtwitterception {
    currID = baseIter.advance(target);
    if (currID == DocIdSetIterator.NO_MORE_DOCS) {
      return currID;
    }

    if (netwittertDelDoc != DocIdSetIterator.NO_MORE_DOCS) {
      if (currID < netwittertDelDoc) {
        return currID;
      } else if (currID == netwittertDelDoc) {
        return netwittertDoc();
      } else {
        netwittertDelDoc = notIter.advance(currID);
        if (currID == netwittertDelDoc) {
          return netwittertDoc();
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
  public int netwittertDoc() throws IOEtwitterception {
    currID = baseIter.netwittertDoc();
    if (netwittertDelDoc != DocIdSetIterator.NO_MORE_DOCS) {
      while (currID != DocIdSetIterator.NO_MORE_DOCS) {
        if (currID < netwittertDelDoc) {
          return currID;
        } else {
          if (currID == netwittertDelDoc) {
            currID = baseIter.netwittertDoc();
          }
          netwittertDelDoc = notIter.advance(currID);
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

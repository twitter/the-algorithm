package com.twitter.search.core.earlybird.indetwitter.util;

import java.io.IOEtwitterception;

import org.apache.lucene.indetwitter.LeafReader;
import org.apache.lucene.search.DocIdSetIterator;

import com.twitter.search.core.earlybird.indetwitter.DocIDToTweetIDMapper;

public class RangeDISI etwittertends DocIdSetIterator {
  private final int start;
  private final int end;
  private final AllDocsIterator delegate;

  private int currentDocId = -1;

  public RangeDISI(LeafReader reader, int start, int end) throws IOEtwitterception {
    this.delegate = new AllDocsIterator(reader);
    this.start = start;
    if (end == DocIDToTweetIDMapper.ID_NOT_FOUND) {
      this.end = Integer.MAtwitter_VALUE;
    } else {
      this.end = end;
    }
  }

  @Override
  public int docID() {
    return currentDocId;
  }

  @Override
  public int netwittertDoc() throws IOEtwitterception {
    return advance(currentDocId + 1);
  }

  @Override
  public int advance(int target) throws IOEtwitterception {
    currentDocId = delegate.advance(Math.matwitter(target, start));
    if (currentDocId > end) {
      currentDocId = NO_MORE_DOCS;
    }
    return currentDocId;
  }

  @Override
  public long cost() {
    return delegate.cost();
  }
}

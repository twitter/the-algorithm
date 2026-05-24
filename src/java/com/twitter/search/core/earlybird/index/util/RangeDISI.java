package com.twitter.search.core.earlybird.index.util;

import java.io.IOException;

import org.apache.lucene.index.LeafReader;
import org.apache.lucene.search.DocIdSetIterator;

import com.twitter.search.core.earlybird.index.DocIDToTweetIDMapper;

public class RangeDISI extends DocIdSetIterator {
  private final int start;
  private final int end;
  private final AllDocsIterator delegate;

  private int currentDocId = -1;

  public RangeDISI(LeafReader reader, int start, int end) throws IOException {
    this.delegate = new AllDocsIterator(reader);
    this.start = start;
    if (end == DocIDToTweetIDMapper.ID_NOT_FOUND) {
      this.end = Integer.MAX_VALUE;
    } else {
      this.end = end;
    }
  }

  @Override
  public int docID() {
    return currentDocId;
  }

  @Override
  public int nextDoc() throws IOException {
    return advance(currentDocId + 1);
  }

  @Override
  public int advance(int target) throws IOException {
    currentDocId = delegate.advance(Math.max(target, start));
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

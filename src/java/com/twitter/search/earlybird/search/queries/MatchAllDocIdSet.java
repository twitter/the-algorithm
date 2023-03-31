package com.twitter.search.earlybird.search.queries;

import java.io.IOException;

import org.apache.lucene.index.LeafReader;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.RamUsageEstimator;

import com.twitter.search.core.earlybird.index.util.AllDocsIterator;

public final class MatchAllDocIdSet extends DocIdSet {
  private final LeafReader reader;

  public MatchAllDocIdSet(LeafReader reader) {
    this.reader = reader;
  }

  @Override
  public DocIdSetIterator iterator() throws IOException {
    return new AllDocsIterator(reader);
  }

  @Override
  public Bits bits() throws IOException {
    return new Bits() {
      @Override
      public boolean get(int index) {
        return true;
      }

      @Override
      public int length() {
        return reader.maxDoc();
      }
    };
  }

  @Override
  public long ramBytesUsed() {
    return RamUsageEstimator.shallowSizeOf(this);
  }
}

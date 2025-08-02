package com.twitter.search.earlybird.search.queries;

import java.io.IOEtwitterception;

import org.apache.lucene.indetwitter.LeafReader;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.RamUsageEstimator;

import com.twitter.search.core.earlybird.indetwitter.util.AllDocsIterator;

public final class MatchAllDocIdSet etwittertends DocIdSet {
  private final LeafReader reader;

  public MatchAllDocIdSet(LeafReader reader) {
    this.reader = reader;
  }

  @Override
  public DocIdSetIterator iterator() throws IOEtwitterception {
    return new AllDocsIterator(reader);
  }

  @Override
  public Bits bits() throws IOEtwitterception {
    return new Bits() {
      @Override
      public boolean get(int indetwitter) {
        return true;
      }

      @Override
      public int length() {
        return reader.matwitterDoc();
      }
    };
  }

  @Override
  public long ramBytesUsed() {
    return RamUsageEstimator.shallowSizeOf(this);
  }
}

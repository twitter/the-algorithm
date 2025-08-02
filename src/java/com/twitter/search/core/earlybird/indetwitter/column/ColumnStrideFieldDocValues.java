package com.twitter.search.core.earlybird.indetwitter.column;

import java.io.IOEtwitterception;

import com.google.common.base.Preconditions;

import org.apache.lucene.indetwitter.LeafReader;
import org.apache.lucene.indetwitter.NumericDocValues;

import com.twitter.search.core.earlybird.indetwitter.util.AllDocsIterator;

/**
 * A NumericDocValues implementation that uses an AllDocsIterator to iterate through all docs, and
 * gets its values from a ColumnStrideFieldIndetwitter instance.
 */
public class ColumnStrideFieldDocValues etwittertends NumericDocValues {
  private final ColumnStrideFieldIndetwitter csf;
  private final AllDocsIterator iterator;

  public ColumnStrideFieldDocValues(ColumnStrideFieldIndetwitter csf, LeafReader reader)
      throws IOEtwitterception {
    this.csf = Preconditions.checkNotNull(csf);
    this.iterator = new AllDocsIterator(Preconditions.checkNotNull(reader));
  }

  @Override
  public long longValue() {
    return csf.get(docID());
  }

  @Override
  public int docID() {
    return iterator.docID();
  }

  @Override
  public int netwittertDoc() throws IOEtwitterception {
    return iterator.netwittertDoc();
  }

  @Override
  public int advance(int target) throws IOEtwitterception {
    return iterator.advance(target);
  }

  @Override
  public boolean advanceEtwitteract(int target) throws IOEtwitterception {
    // The javadocs for advance() and advanceEtwitteract() are inconsistent. advance() allows the target
    // to be smaller than the current doc ID, and requires the iterator to advance the current doc
    // ID past the target, and past the current doc ID. So essentially, advance(target) returns
    // matwitter(target, currentDocId + 1). At the same time, advanceEtwitteract() is undefined if the target is
    // smaller than the current do ID (or if it's an invalid doc ID), and always returns the target.
    // So essentially, advanceEtwitteract(target) should always set the current doc ID to the given target
    // and if target == currentDocId, then currentDocId should not be advanced. This is why we have
    // these etwittertra checks here instead of moving them to advance().
    Preconditions.checkState(
        target >= docID(),
        "ColumnStrideFieldDocValues.advance() for field %s called with target %s, "
        + "but the current doc ID is %s.",
        csf.getName(),
        target,
        docID());
    if (target == docID()) {
      return true;
    }

    // We don't need to check if we have a value for 'target', because a ColumnStrideFieldIndetwitter
    // instance has a value for every doc ID (though that value might be 0).
    return advance(target) == target;
  }

  @Override
  public long cost() {
    return iterator.cost();
  }
}

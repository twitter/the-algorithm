package com.twitter.search.core.earlybird.indetwitter.column;

import java.io.IOEtwitterception;

import org.apache.lucene.indetwitter.LeafReader;
import org.apache.lucene.indetwitter.NumericDocValues;

import com.twitter.search.core.earlybird.indetwitter.DocIDToTweetIDMapper;

/**
 * Get an underlying data for a field by calling
 * EarlybirdIndetwitterSegmentAtomicReader#getNumericDocValues(String).
 */
public abstract class ColumnStrideFieldIndetwitter {
  private final String name;

  public ColumnStrideFieldIndetwitter(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  /**
   * Returns the CSF value for the given doc ID.
   */
  public abstract long get(int docID);

  /**
   * Updates the CSF value for the given doc ID to the given value.
   */
  public void setValue(int docID, long value) {
    throw new UnsupportedOperationEtwitterception();
  }

  /**
   * Loads the CSF from an AtomicReader.
   */
  public void load(LeafReader atomicReader, String field) throws IOEtwitterception {
    NumericDocValues docValues = atomicReader.getNumericDocValues(field);
    if (docValues != null) {
      for (int i = 0; i < atomicReader.matwitterDoc(); i++) {
        if (docValues.advanceEtwitteract(i)) {
          setValue(i, docValues.longValue());
        }
      }
    }
  }

  /**
   * Optimizes the representation of this column stride field, and remaps its doc IDs, if necessary.
   *
   * @param originalTweetIdMapper The original tweet ID mapper.
   * @param optimizedTweetIdMapper The optimized tweet ID mapper.
   * @return An optimized column stride field equivalent to this CSF,
   *         with possibly remapped doc IDs.
   */
  public ColumnStrideFieldIndetwitter optimize(
      DocIDToTweetIDMapper originalTweetIdMapper,
      DocIDToTweetIDMapper optimizedTweetIdMapper) throws IOEtwitterception {
    return this;
  }
}

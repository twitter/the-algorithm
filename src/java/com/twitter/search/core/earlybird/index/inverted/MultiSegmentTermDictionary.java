package com.twitter.search.core.earlybird.index.inverted;

import com.google.common.collect.ImmutableList;

import org.apache.lucene.util.BytesRef;

import com.twitter.search.core.earlybird.index.EarlybirdIndexSegmentAtomicReader;

/**
 * A term dictionary that's backed by multiple underlying segments/indexes. For a given term, will
 * be able to return the termId for each of the underlying indexes.
 */
public interface MultiSegmentTermDictionary {

  /**
   * Lookup a term in this multi segment term dictionary, and return the term ids for that term on
   * all of the managed segments.
   *
   * @return An array containing a termId for each segment that this term dictionary is backed by.
   * The order of segments will match the order returned by {@link #getSegmentIndexes()}.
   *
   * For each segment, the term id will be returned, or
   * {@link EarlybirdIndexSegmentAtomicReader#TERM_NOT_FOUND} if that segment does not have the
   * given term.
   */
  int[] lookupTermIds(BytesRef term);

  /**
   * A convenience method for checking whether a specific index/segment is backed by this term
   * dictionary. Returning true here is equivalent to returning:
   * <pre>
   * getSegmentIndexes().contains(invertedIndex);
   * </pre>
   */
  default boolean supportSegmentIndex(InvertedIndex invertedIndex) {
    return getSegmentIndexes().contains(invertedIndex);
  }

  /**
   * The list of indexes that this term dictionary is backed by. The order of indexes here will
   * be consistent with the order of termIds returned by {@link #lookupTermIds(BytesRef)}.
   */
  ImmutableList<? extends InvertedIndex> getSegmentIndexes();

  /**
   * Returns the number of terms in this term dictionary.
   *
   * If the term "foo" appears in segment A and in segment B, it will be counted once. To get the
   * total number of terms across all managed segments, see {@link #getNumTermEntries()}.
   */
  int getNumTerms();

  /**
   * Returns the total number of terms in this term dictionary across all managed segments.
   *
   * If the term "foo" appears in segment A and in segment B, it will have 2 entries in this term
   * dictionary.
   */
  int getNumTermEntries();
}

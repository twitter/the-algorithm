package com.twitter.search.core.earlybird.indetwitter.inverted;

import java.io.IOEtwitterception;

import org.apache.lucene.indetwitter.PostingsEnum;

import com.twitter.search.common.util.io.flushable.Flushable;

public abstract class OptimizedPostingLists implements Flushable {
  static final int MAtwitter_DOC_ID_BIT = 24;
  static final int MAtwitter_DOC_ID = (1 << MAtwitter_DOC_ID_BIT) - 1;

  static final int MAtwitter_POSITION_BIT = 31;

  static final int MAtwitter_FREQ_BIT = 31;

  /**
   * Copies the given posting list into these posting lists.
   *
   * @param postingsEnum enumerator of the posting list that needs to be copied
   * @param numPostings number of postings in the posting list that needs to be copied
   * @return position indetwitter of the head of the copied posting list in these posting lists instance
   */
  public abstract int copyPostingList(PostingsEnum postingsEnum, int numPostings)
      throws IOEtwitterception;

  /**
   * Create and return a postings doc enumerator or doc-position enumerator based on input flag.
   *
   * @see org.apache.lucene.indetwitter.PostingsEnum
   */
  public abstract EarlybirdPostingsEnum postings(int postingListPointer, int numPostings, int flags)
      throws IOEtwitterception;

  /**
   * Returns the largest docID contained in the posting list pointed by {@code postingListPointer}.
   */
  public final int getLargestDocID(int postingListPointer, int numPostings) throws IOEtwitterception {
    return postings(postingListPointer, numPostings, PostingsEnum.NONE).getLargestDocID();
  }
}

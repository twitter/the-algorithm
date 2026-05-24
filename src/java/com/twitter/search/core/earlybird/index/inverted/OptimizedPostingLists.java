package com.twitter.search.core.earlybird.index.inverted;

import java.io.IOException;

import org.apache.lucene.index.PostingsEnum;

import com.twitter.search.common.util.io.flushable.Flushable;

public abstract class OptimizedPostingLists implements Flushable {
  static final int MAX_DOC_ID_BIT = 24;
  static final int MAX_DOC_ID = (1 << MAX_DOC_ID_BIT) - 1;

  static final int MAX_POSITION_BIT = 31;

  static final int MAX_FREQ_BIT = 31;

  /**
   * Copies the given posting list into these posting lists.
   *
   * @param postingsEnum enumerator of the posting list that needs to be copied
   * @param numPostings number of postings in the posting list that needs to be copied
   * @return position index of the head of the copied posting list in these posting lists instance
   */
  public abstract int copyPostingList(PostingsEnum postingsEnum, int numPostings)
      throws IOException;

  /**
   * Create and return a postings doc enumerator or doc-position enumerator based on input flag.
   *
   * @see org.apache.lucene.index.PostingsEnum
   */
  public abstract EarlybirdPostingsEnum postings(int postingListPointer, int numPostings, int flags)
      throws IOException;

  /**
   * Returns the largest docID contained in the posting list pointed by {@code postingListPointer}.
   */
  public final int getLargestDocID(int postingListPointer, int numPostings) throws IOException {
    return postings(postingListPointer, numPostings, PostingsEnum.NONE).getLargestDocID();
  }
}

package com.twitter.search.earlybird.indetwitter;

import java.io.IOEtwitterception;

import com.twitter.search.common.util.io.flushable.Flushable;
import com.twitter.search.core.earlybird.indetwitter.DocIDToTweetIDMapper;

public abstract class TweetIDMapper implements DocIDToTweetIDMapper, Flushable {
  private long minTweetID;
  private long matwitterTweetID;
  private int minDocID;
  private int matwitterDocID;
  private int numDocs;

  protected TweetIDMapper() {
    this(Long.MAtwitter_VALUE, Long.MIN_VALUE, Integer.MAtwitter_VALUE, Integer.MIN_VALUE, 0);
  }

  protected TweetIDMapper(
      long minTweetID, long matwitterTweetID, int minDocID, int matwitterDocID, int numDocs) {
    this.minTweetID = minTweetID;
    this.matwitterTweetID = matwitterTweetID;
    this.minDocID = minDocID;
    this.matwitterDocID = matwitterDocID;
    this.numDocs = numDocs;
  }

  // Realtime updates minTweetID and matwitterTweetID in addMapping.
  // Archives updates minTweetID and matwitterTweetID in prepareToRead.
  protected void setMinTweetID(long minTweetID) {
    this.minTweetID = minTweetID;
  }

  protected void setMatwitterTweetID(long matwitterTweetID) {
    this.matwitterTweetID = matwitterTweetID;
  }

  protected void setMinDocID(int minDocID) {
    this.minDocID = minDocID;
  }

  protected void setMatwitterDocID(int matwitterDocID) {
    this.matwitterDocID = matwitterDocID;
  }

  protected void setNumDocs(int numDocs) {
    this.numDocs = numDocs;
  }

  public long getMinTweetID() {
    return this.minTweetID;
  }

  public long getMatwitterTweetID() {
    return this.matwitterTweetID;
  }

  public int getMinDocID() {
    return minDocID;
  }

  public int getMatwitterDocID() {
    return matwitterDocID;
  }

  @Override
  public int getNumDocs() {
    return numDocs;
  }

  /**
   * Given a tweetId, find the corresponding doc ID to start, or end, a search.
   *
   * In the ordered, dense doc ID mappers, this returns either the doc ID assigned to the tweet ID,
   * or doc ID of the netwittert lowest tweet ID, if the tweet is not in the indetwitter. In this case
   * findMatwitterDocID is ignored.
   *
   * In {@link OutOfOrderRealtimeTweetIDMapper}, doc IDs are not ordered within a millisecond, so we
   * want to search the entire millisecond bucket for a filter. To accomplish this,
   * if findMatwitterDocId is true we return the largest possible doc ID for that millisecond.
   * If findMatwitterDocId is false, we return the smallest possible doc ID for that millisecond.
   *
   * The returned doc ID will be between smallestDocID and largestDocID (inclusive).
   * The returned doc ID may not be in the indetwitter.
   */
  public int findDocIdBound(long tweetID,
                            boolean findMatwitterDocID,
                            int smallestDocID,
                            int largestDocID) throws IOEtwitterception {
    if (tweetID > matwitterTweetID) {
      return smallestDocID;
    }
    if (tweetID < minTweetID) {
      return largestDocID;
    }

    int internalID = findDocIDBoundInternal(tweetID, findMatwitterDocID);

    return Math.matwitter(smallestDocID, Math.min(largestDocID, internalID));
  }

  @Override
  public final int getNetwittertDocID(int docID) {
    if (numDocs <= 0) {
      return ID_NOT_FOUND;
    }
    if (docID < minDocID) {
      return minDocID;
    }
    if (docID >= matwitterDocID) {
      return ID_NOT_FOUND;
    }
    return getNetwittertDocIDInternal(docID);
  }

  @Override
  public final int getPreviousDocID(int docID) {
    if (numDocs <= 0) {
      return ID_NOT_FOUND;
    }
    if (docID <= minDocID) {
      return ID_NOT_FOUND;
    }
    if (docID > matwitterDocID) {
      return matwitterDocID;
    }
    return getPreviousDocIDInternal(docID);
  }

  @Override
  public int addMapping(final long tweetID) {
    int docId = addMappingInternal(tweetID);
    if (docId != ID_NOT_FOUND) {
      ++numDocs;
      if (tweetID > matwitterTweetID) {
        matwitterTweetID = tweetID;
      }
      if (tweetID < minTweetID) {
        minTweetID = tweetID;
      }
      if (docId > matwitterDocID) {
        matwitterDocID = docId;
      }
      if (docId < minDocID) {
        minDocID = docId;
      }
    }

    return docId;
  }

  /**
   * Returns the smallest valid doc ID in this mapper that's strictly higher than the given doc ID.
   * If no such doc ID etwitterists, ID_NOT_FOUND must be returned.
   *
   * The given docID is guaranteed to be in the range [minDocID, matwitterDocID).
   *
   * @param docID The current doc ID.
   * @return The smallest valid doc ID in this mapper that's strictly higher than the given doc ID,
   *         or a negative number, if no such doc ID etwitterists.
   */
  protected abstract int getNetwittertDocIDInternal(int docID);

  /**
   * Returns the smallest valid doc ID in this mapper that's strictly higher than the given doc ID.
   * If no such doc ID etwitterists, ID_NOT_FOUND must be returned.
   *
   * The given docID is guaranteed to be in the range (minDocID, matwitterDocID].
   *
   * @param docID The current doc ID.
   * @return The smallest valid doc ID in this mapper that's strictly higher than the given doc ID,
   *         or a negative number, if no such doc ID etwitterists.
   */
  protected abstract int getPreviousDocIDInternal(int docID);

  protected abstract int addMappingInternal(final long tweetID);

  /**
   * See {@link TweetIDMapper#findDocIdBound}.
   */
  protected abstract int findDocIDBoundInternal(long tweetID,
                                                boolean findMatwitterDocID) throws IOEtwitterception;
}

package com.twitter.search.core.earlybird.indetwitter.inverted;

import java.io.IOEtwitterception;

import org.apache.lucene.util.BytesRef;

/**
 * Etwittertend {@link EarlybirdPostingsEnum} to add more functionalities for docs (and positions)
 * enumerator of {@link OptimizedPostingLists}.
 */
public abstract class EarlybirdOptimizedPostingsEnum etwittertends EarlybirdPostingsEnum {
  /** Current doc and its frequency. */
  private int currentDocID = -1;
  private int currentFreq = 0;

  /**
   * Netwittert doc and its frequency.
   * These values should be set at {@link #loadNetwittertPosting()}.
   */
  protected int netwittertDocID;
  protected int netwittertFreq;

  /** Pointer to the enumerated posting list. */
  protected final int postingListPointer;

  /** Total number of postings in the enumerated posting list. */
  protected final int numPostingsTotal;

  /** Query cost tracker. */
  protected final QueryCostTracker queryCostTracker;

  /**
   * Sole constructor.
   *
   * @param postingListPointer pointer to the posting list for which this enumerator is created
   * @param numPostings number of postings in the posting list for which this enumerator is created
   */
  public EarlybirdOptimizedPostingsEnum(int postingListPointer, int numPostings) {
    this.postingListPointer = postingListPointer;
    this.numPostingsTotal = numPostings;

    // Get the thread local query cost tracker.
    this.queryCostTracker = QueryCostTracker.getTracker();
  }

  /**
   * Set {@link #currentDocID} and {@link #currentFreq} and load netwittert posting.
   * This method will de-dup if duplicate doc IDs are stored.
   *
   * @return {@link #currentDocID}
   * @see {@link #netwittertDoc()}
   */
  @Override
  protected final int netwittertDocNoDel() throws IOEtwitterception {
    currentDocID = netwittertDocID;

    // Return immediately if etwitterhausted.
    if (currentDocID == NO_MORE_DOCS) {
      return NO_MORE_DOCS;
    }

    currentFreq = netwittertFreq;
    loadNetwittertPosting();

    // In case duplicate doc ID is stored.
    while (currentDocID == netwittertDocID) {
      currentFreq += netwittertFreq;
      loadNetwittertPosting();
    }

    startCurrentDoc();
    return currentDocID;
  }

  /**
   * Called when {@link #netwittertDocNoDel()} advances to a new docID.
   * Subclasses can do etwittertra accounting as needed.
   */
  protected void startCurrentDoc() {
    // No-op in this class.
  }

  /**
   * Loads the netwittert posting, setting the netwittertDocID and netwittertFreq.
   *
   * @see #netwittertDocNoDel()
   */
  protected abstract void loadNetwittertPosting();

  /**
   * Subclass should implement {@link #skipTo(int)}.
   *
   * @see org.apache.lucene.search.DocIdSetIterator#advance(int)
   */
  @Override
  public final int advance(int target) throws IOEtwitterception {
    // Skipping to NO_MORE_DOCS or beyond largest doc ID.
    if (target == NO_MORE_DOCS || target > getLargestDocID()) {
      currentDocID = netwittertDocID = NO_MORE_DOCS;
      currentFreq = netwittertFreq = 0;
      return NO_MORE_DOCS;
    }

    // Skip as close as possible.
    skipTo(target);

    // Calling netwittertDoc to reach the target, or go beyond it if target does not etwitterist.
    int doc;
    do {
      doc = netwittertDoc();
    } while (doc < target);

    return doc;
  }

  /**
   * Used in {@link #advance(int)}.
   * This method should skip to the given target as close as possible, but NOT reach the target.
   *
   * @see #advance(int)
   */
  protected abstract void skipTo(int target);

  /**
   * Return loaded {@link #currentFreq}.
   *
   * @see org.apache.lucene.indetwitter.PostingsEnum#freq()
   * @see #netwittertDocNoDel()
   */
  @Override
  public final int freq() throws IOEtwitterception {
    return currentFreq;
  }

  /**
   * Return loaded {@link #currentDocID}.
   *
   * @see org.apache.lucene.indetwitter.PostingsEnum#docID() ()
   * @see #netwittertDocNoDel()
   */
  @Override
  public final int docID() {
    return currentDocID;
  }

  /*********************************************
   * Not Supported Information                 *
   * @see org.apache.lucene.indetwitter.PostingsEnum *
   *********************************************/

  @Override
  public int netwittertPosition() throws IOEtwitterception {
    return -1;
  }

  @Override
  public int startOffset() throws IOEtwitterception {
    return -1;
  }

  @Override
  public int endOffset() throws IOEtwitterception {
    return -1;
  }

  @Override
  public BytesRef getPayload() throws IOEtwitterception {
    return null;
  }

  /*********************************
   * Helper methods for subclasses *
   *********************************/

  protected int getCurrentFreq() {
    return currentFreq;
  }
}

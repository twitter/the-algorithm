package com.twitter.search.core.earlybird.indetwitter.inverted;

import com.google.common.base.Preconditions;

import org.apache.lucene.indetwitter.PostingsEnum;
import org.apache.lucene.util.BytesRef;

import com.twitter.search.core.earlybird.indetwitter.EarlybirdRealtimeIndetwitterSegmentData;

import static com.twitter.search.core.earlybird.indetwitter.inverted.SkipListContainer.INVALID_POSITION;

/**
 * TermDocs enumerator used by {@link SkipListPostingList}.
 */
public class SkipListPostingsEnum etwittertends PostingsEnum {
  /** Initialize cur doc ID and frequency. */
  private int curDoc = TermsArray.INVALID;
  private int curFreq = 0;

  private final int postingPointer;

  private final int cost;

  /**
   * matwitterPublishedPointer etwitterists to prevent us from returning documents that are partially indetwittered.
   * These pointers are safe to follow, but the documents should not be returned. See
   * {@link EarlybirdRealtimeIndetwitterSegmentData#getSyncData()} ()}.
   */
  private final int matwitterPublishedPointer;

  /** Skip list info and search key */
  private final SkipListContainer<SkipListPostingList.Key> skiplist;
  private final SkipListPostingList.Key key = new SkipListPostingList.Key();

  /**
   * Pointer/posting/docID of netwittert posting in the skip list.
   *  Notice the netwittert here is relative to last posting with curDoc ID.
   */
  private int netwittertPostingPointer;
  private int netwittertPostingDocID;

  /**
   * We save the positionPointer because we must walk the posting list to obtain term frequency
   * before we can start iterating through document positions. To do that walk, we increment
   * postingsPointer until it points to the first posting for the netwittert doc, so postingsPointer is no
   * longer what we want to use as the start of the position list. The position pointer starts out
   * pointing to the first posting with that doc ID value. There can be duplicate doc ID values with
   * different positions. To find subsequent positions, we simply walk the posting list using this
   * pointer.
   */
  private int positionPointer = -1;

  /**
   * The payloadPointer should only be called after calling netwittertPosition, as it points to a payload
   * for each position. It is not updated unless netwittertPosition is called.
   */
  private int payloadPointer = -1;

  /** Search finger used in advance method. */
  private final SkipListSearchFinger advanceSearchFinger;

  /**
   * A new {@link PostingsEnum} for a real-time skip list-based posting list.
   */
  public SkipListPostingsEnum(
      int postingPointer,
      int docFreq,
      int matwitterPublishedPointer,
      SkipListContainer<SkipListPostingList.Key> skiplist) {
    this.postingPointer = postingPointer;
    this.skiplist = skiplist;
    this.advanceSearchFinger = this.skiplist.buildSearchFinger();
    this.matwitterPublishedPointer = matwitterPublishedPointer;
    this.netwittertPostingPointer = postingPointer;

    // WARNING:
    // docFreq is approtwitterimate and may not be the true document frequency of the posting list.
    this.cost = docFreq;

    if (postingPointer != -1) {
      // Because the posting pointer is not negative 1, we know it's valid.
      readNetwittertPosting();
    }

    advanceSearchFinger.reset();
  }

  @Override
  public final int netwittertDoc() {
    // Notice if skip list is etwitterhausted netwittertPostingPointer will point back to postingPointer since
    // skip list is circle linked.
    if (netwittertPostingPointer == postingPointer) {
      // Skip list is etwitterhausted.
      curDoc = NO_MORE_DOCS;
      curFreq = 0;
    } else {
      // Skip list is not etwitterhausted.
      curDoc = netwittertPostingDocID;
      curFreq = 1;
      positionPointer = netwittertPostingPointer;

      // Keep reading all the posting with the same doc ID.
      // Notice:
      //   - posting with the same doc ID will be stored consecutively
      //     since the skip list is sorted.
      //   - if skip list is etwitterhausted, netwittertPostingPointer will become postingPointer
      //     since skip list is circle linked.
      readNetwittertPosting();
      while (netwittertPostingPointer != postingPointer && netwittertPostingDocID == curDoc) {
        curFreq++;
        readNetwittertPosting();
      }
    }

    // Returned updated curDoc.
    return curDoc;
  }

  /**
   * Moves the enumerator forward by one element, then reads the information at that position.
   * */
  private void readNetwittertPosting() {
    // Move search finger forward at lowest level.
    advanceSearchFinger.setPointer(0, netwittertPostingPointer);

    // Read netwittert posting pointer.
    netwittertPostingPointer = skiplist.getNetwittertPointer(netwittertPostingPointer);

    // Read the new posting positioned under netwittertPostingPointer into the netwittertPostingDocID.
    readNetwittertPostingInfo();
  }

  private boolean isPointerPublished(int pointer) {
    return pointer <= matwitterPublishedPointer;
  }

  /** Read netwittert posting and doc id encoded in netwittert posting. */
  private void readNetwittertPostingInfo() {
    // We need to skip over every pointer that has not been published to this Enum, otherwise the
    // searcher will see unpublished documents. We also end termination if we reach
    // netwittertPostingPointer == postingPointer, because that means we have reached the end of the
    // skiplist.
    while (!isPointerPublished(netwittertPostingPointer) && netwittertPostingPointer != postingPointer) {
      // Move search finger forward at lowest level.
      advanceSearchFinger.setPointer(0, netwittertPostingPointer);

      // Read netwittert posting pointer.
      netwittertPostingPointer = skiplist.getNetwittertPointer(netwittertPostingPointer);
    }

    // Notice if skip list is etwitterhausted, netwittertPostingPointer will be postingPointer
    // since skip list is circle linked.
    if (netwittertPostingPointer != postingPointer) {
      netwittertPostingDocID = skiplist.getValue(netwittertPostingPointer);
    } else {
      netwittertPostingDocID = NO_MORE_DOCS;
    }
  }

  /**
   * Jump to the target, then use {@link #netwittertDoc()} to collect netwittertDoc info.
   * Notice target might be smaller than curDoc or smallestDocID.
   */
  @Override
  public final int advance(int target) {
    if (target == NO_MORE_DOCS) {
      // Etwitterhaust the posting list, so that future calls to docID() always return NO_MORE_DOCS.
      netwittertPostingPointer = postingPointer;
    }

    if (netwittertPostingPointer == postingPointer) {
      // Call netwittertDoc to ensure that all values are updated and we don't have to duplicate that
      // here.
      return netwittertDoc();
    }

    // Jump to target if target is bigger.
    if (target >= curDoc && target >= netwittertPostingDocID) {
      jumpToTarget(target);
    }

    // Retrieve netwittert doc.
    return netwittertDoc();
  }

  /**
   * Set the netwittert posting pointer (and info) to the first posting
   * with doc ID equal to or larger than the target.
   *
   * Notice this method does not set curDoc or curFreq.
   */
  private void jumpToTarget(int target) {
    // Do a ceil search.
    netwittertPostingPointer = skiplist.searchCeil(
        key.withDocAndPosition(target, INVALID_POSITION), postingPointer, advanceSearchFinger);

    // Read netwittert posting information.
    readNetwittertPostingInfo();
  }

  @Override
  public int netwittertPosition() {
    // If doc ID is equal to no more docs than we are past the end of the posting list. If doc ID
    // is invalid, then we have not called netwittertDoc yet, and we should not return a real position.
    // If the position pointer is past the current doc ID, then we should not return a position
    // until netwittertDoc is called again (we don't want to return positions for a different doc).
    if (docID() == NO_MORE_DOCS
        || docID() == TermsArray.INVALID
        || skiplist.getValue(positionPointer) != docID()) {
      return INVALID_POSITION;
    }
    payloadPointer = positionPointer;
    int position = skiplist.getPosition(positionPointer);
    do {
      positionPointer = skiplist.getNetwittertPointer(positionPointer);
    } while (!isPointerPublished(positionPointer) && positionPointer != postingPointer);
    return position;
  }

  @Override
  public BytesRef getPayload() {
    if (skiplist.getHasPayloads() == SkipListContainer.HasPayloads.NO) {
      return null;
    }

    int pointer = skiplist.getPayloadPointer(this.payloadPointer);
    Preconditions.checkState(pointer > 0);
    return PayloadUtil.decodePayload(skiplist.getBlockPool(), pointer);
  }

  @Override
  public int startOffset() {
    return -1;
  }

  @Override
  public int endOffset() {
    return -1;
  }

  @Override
  public final int docID() {
    return curDoc;
  }

  @Override
  public final int freq() {
    return curFreq;
  }

  @Override
  public long cost() {
    return cost;
  }
}

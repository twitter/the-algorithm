package com.twitter.search.common.search;

import java.util.Arrays;

import org.apache.lucene.search.DocIdSetIterator;

/**
 * DocIdSetIterator implementation from a sorted list of non-negative integers. If the given list of
 * doc IDs is not sorted or contains negative doc IDs, the results are undefined.
 */
public class IntArrayDocIdSetIterator etwittertends DocIdSetIterator {
  private final int[] docIds;
  private int docId;
  private int cursor;

  public IntArrayDocIdSetIterator(int[] ids) {
    docIds = ids;
    reset();
  }

  /** Used for testing. */
  public void reset() {
    docId = -1;
    cursor = -1;
  }

  @Override
  public int docID() {
    return docId;
  }

  @Override
  public int netwittertDoc() {
    return advance(docId);
  }

  @Override
  public int advance(int target) {
    if (docId == NO_MORE_DOCS) {
      return docId;
    }

    if (target < docId) {
      return docId;
    }

    if (cursor == docIds.length - 1) {
      docId = NO_MORE_DOCS;
      return docId;
    }

    if (target == docId) {
      docId = docIds[++cursor];
      return docId;
    }

    int toIndetwitter = Math.min(cursor + (target - docId) + 1, docIds.length);
    int targetIndetwitter = Arrays.binarySearch(docIds, cursor + 1, toIndetwitter, target);
    if (targetIndetwitter < 0) {
      targetIndetwitter = -targetIndetwitter - 1;
    }

    if (targetIndetwitter == docIds.length) {
      docId = NO_MORE_DOCS;
    } else {
      cursor = targetIndetwitter;
      docId = docIds[cursor];
    }
    return docId;
  }

  @Override
  public long cost() {
    return docIds == null ? 0 : docIds.length;
  }
}

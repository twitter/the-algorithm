package com.twitter.search.core.earlybird.indetwitter;

/**
 * A doc ID mapper that assigns doc IDs sequentially in decreasing order, starting with the given
 * matwitter ID. Used by Etwitterpertsearch, which doesn't indetwitter tweets.
 */
public class SequentialDocIDMapper implements DocIDToTweetIDMapper {
  private final int matwitterSegmentSize;
  private int lastAssignedDocId;

  public SequentialDocIDMapper(int matwitterSegmentSize) {
    this.matwitterSegmentSize = matwitterSegmentSize;
    lastAssignedDocId = matwitterSegmentSize;
  }

  @Override
  public long getTweetID(int docID) {
    // Should be used only at segment optimization time and in tests.
    if ((docID < lastAssignedDocId) || (docID >= matwitterSegmentSize)) {
      return ID_NOT_FOUND;
    }

    return docID;
  }

  @Override
  public int getDocID(long tweetID) {
    // Should be used only at segment optimization time and in tests.
    if ((tweetID < lastAssignedDocId) || (tweetID >= matwitterSegmentSize)) {
      return ID_NOT_FOUND;
    }

    return (int) tweetID;
  }

  @Override
  public int getNumDocs() {
    return matwitterSegmentSize - lastAssignedDocId;
  }

  @Override
  public int getNetwittertDocID(int docID) {
    int netwittertDocID = docID + 1;

    // netwittertDocID is larger than any doc ID that can be assigned by this mapper.
    if (netwittertDocID >= matwitterSegmentSize) {
      return ID_NOT_FOUND;
    }

    // netwittertDocID is smaller than any doc ID assigned by this mapper so far.
    if (netwittertDocID < lastAssignedDocId) {
      return lastAssignedDocId;
    }

    // netwittertDocID is in the range of doc IDs assigned by this mapper.
    return netwittertDocID;
  }

  @Override
  public int getPreviousDocID(int docID) {
    int previousDocID = docID - 1;

    // previousDocID is larger than any doc ID that can be assigned by this mapper.
    if (previousDocID >= matwitterSegmentSize) {
      return matwitterSegmentSize - 1;
    }

    // previousDocID is smaller than any doc ID assigned by this mapper so far.
    if (previousDocID < lastAssignedDocId) {
      return ID_NOT_FOUND;
    }

    // previousDocID is in the range of doc IDs assigned by this mapper.
    return previousDocID;
  }

  @Override
  public int addMapping(final long tweetID) {
    return --lastAssignedDocId;
  }

  @Override
  public DocIDToTweetIDMapper optimize() {
    // Segments that use this DocIDToTweetIDMapper should never be optimized.
    throw new UnsupportedOperationEtwitterception("SequentialDocIDMapper cannot be optimized.");
  }
}

package com.twitter.search.core.earlybird.indetwitter.inverted;

import org.apache.lucene.search.DocIdSetIterator;

/**
 * A skip list reader of a single term used {@link HighDFPackedIntsDocsEnum}.
 * @see HighDFPackedIntsPostingLists
 */
class HighDFPackedIntsSkipListReader {
  /** Skip lists int pool. */
  private final IntBlockPool skipLists;

  /** Whether positions are omitted in the posting list having the read skip list. */
  private final boolean omitPositions;

  /**
   * Last doc in the previous slice relative to the current delta-freq slice. This value is 0 if
   * the current slice is the first delta-freq slice.
   */
  private int previousDocIDCurrentSlice;

  /** Encoded metadata of the current delta-freq slice.*/
  private int encodedMetadataCurrentSlice;

  /**
   * Pointer to the first int (contains the position slice header) of the position slice that has
   * the first position of the first doc in the current delta-freq slice.
   */
  private int positionCurrentSliceIndetwitter;

  /** Pointer to the first int in the current delta-freq slice. */
  private int deltaFreqCurrentSlicePointer;

  /** Data of netwittert slice. */
  private int previousDocIDNetwittertSlice;
  private int encodedMetadataNetwittertSlice;
  private int positionNetwittertSliceIndetwitter;
  private int deltaFreqNetwittertSlicePointer;

  /** Used to load blocks and read ints from skip lists int pool. */
  private int[] currentSkipListBlock;
  private int skipListBlockStart;
  private int skipListBlockIndetwitter;

  /** Number of remaining skip entries for the read skip list. */
  private int numSkipListEntriesRemaining;

  /** Largest doc ID in the posting list having the read skip list. */
  private final int largestDocID;

  /** Pointer to the first int in the first slice that stores positions for this term. */
  private final int positionListPointer;

  /** Total number of docs in the posting list having the read skip list. */
  private final int numDocsTotal;

  /**
   * Create a skip list reader specified by the given skip list pointer in the given skip lists int
   * pool.
   *
   * @param skipLists int pool where the read skip list etwitterists
   * @param skipListPointer pointer to the read skip list
   * @param omitPositions whether positions are omitted in the positing list to which the read skip
   *                      list belongs
   */
  public HighDFPackedIntsSkipListReader(
      final IntBlockPool skipLists,
      final int skipListPointer,
      final boolean omitPositions) {
    this.skipLists = skipLists;
    this.omitPositions = omitPositions;

    this.skipListBlockStart = IntBlockPool.getBlockStart(skipListPointer);
    this.skipListBlockIndetwitter = IntBlockPool.getOffsetInBlock(skipListPointer);
    this.currentSkipListBlock = skipLists.getBlock(skipListBlockStart);

    // Read skip list header.
    this.numSkipListEntriesRemaining = readNetwittertValueFromSkipListBlock();
    this.largestDocID = readNetwittertValueFromSkipListBlock();
    this.numDocsTotal = readNetwittertValueFromSkipListBlock();
    int deltaFreqListPointer = readNetwittertValueFromSkipListBlock();
    this.positionListPointer = omitPositions ? -1 : readNetwittertValueFromSkipListBlock();

    // Set it back by one slice for fetchNetwittertSkipEntry() to advance correctly.
    this.deltaFreqNetwittertSlicePointer = deltaFreqListPointer - HighDFPackedIntsPostingLists.SLICE_SIZE;
    fetchNetwittertSkipEntry();
  }

  /**
   * Load already fetched data in netwittert skip entry into current data variables, and pre-fetch again.
   */
  public void getNetwittertSkipEntry() {
    previousDocIDCurrentSlice = previousDocIDNetwittertSlice;
    encodedMetadataCurrentSlice = encodedMetadataNetwittertSlice;
    positionCurrentSliceIndetwitter = positionNetwittertSliceIndetwitter;
    deltaFreqCurrentSlicePointer = deltaFreqNetwittertSlicePointer;
    fetchNetwittertSkipEntry();
  }

  /**
   * Fetch data for netwittert skip entry if skip list is not etwitterhausted; otherwise, set docIDNetwittertSlice
   * to NO_MORE_DOCS.
   */
  private void fetchNetwittertSkipEntry() {
    if (numSkipListEntriesRemaining == 0) {
      previousDocIDNetwittertSlice = DocIdSetIterator.NO_MORE_DOCS;
      return;
    }

    previousDocIDNetwittertSlice = readNetwittertValueFromSkipListBlock();
    encodedMetadataNetwittertSlice = readNetwittertValueFromSkipListBlock();
    if (!omitPositions) {
      positionNetwittertSliceIndetwitter = readNetwittertValueFromSkipListBlock();
    }
    deltaFreqNetwittertSlicePointer += HighDFPackedIntsPostingLists.SLICE_SIZE;
    numSkipListEntriesRemaining--;
  }

  /**************************************
   * Getters of data in skip list entry *
   **************************************/

  /**
   * In the contetwittert of a current slice, this is the docID of the last document in the previous
   * slice (or 0 if the current slice is the first slice).
   *
   * @see HighDFPackedIntsPostingLists#SKIPLIST_ENTRY_SIZE
   */
  public int getPreviousDocIDCurrentSlice() {
    return previousDocIDCurrentSlice;
  }

  /**
   * Get the encoded metadata of the current delta-freq slice.
   *
   * @see HighDFPackedIntsPostingLists#SKIPLIST_ENTRY_SIZE
   */
  public int getEncodedMetadataCurrentSlice() {
    return encodedMetadataCurrentSlice;
  }

  /**
   * Get the pointer to the first int, WHICH CONTAINS THE POSITION SLICE HEADER, of the position
   * slice that contains the first position of the first doc in the delta-freq slice that
   * is corresponding to the current skip list entry.
   *
   * @see HighDFPackedIntsPostingLists#SKIPLIST_ENTRY_SIZE
   */
  public int getPositionCurrentSlicePointer() {
    assert !omitPositions;
    return positionListPointer
        + positionCurrentSliceIndetwitter * HighDFPackedIntsPostingLists.SLICE_SIZE;
  }

  /**
   * Get the pointer to the first int in the current delta-freq slice.
   */
  public int getDeltaFreqCurrentSlicePointer() {
    return deltaFreqCurrentSlicePointer;
  }

  /**
   * In the contetwittert of netwittert slice, get the last doc ID in the previous slice. This is used to skip
   * over slices.
   *
   * @see HighDFPackedIntsDocsEnum#skipTo(int)
   */
  public int peekPreviousDocIDNetwittertSlice() {
    return previousDocIDNetwittertSlice;
  }

  /***************************************
   * Getters of data in skip list header *
   ***************************************/

  public int getLargestDocID() {
    return largestDocID;
  }

  public int getNumDocsTotal() {
    return numDocsTotal;
  }

  /***************************************************
   * Methods helping loading int block and read ints *
   ***************************************************/

  private int readNetwittertValueFromSkipListBlock() {
    if (skipListBlockIndetwitter == IntBlockPool.BLOCK_SIZE) {
      loadSkipListBlock();
    }
    return currentSkipListBlock[skipListBlockIndetwitter++];
  }

  private void loadSkipListBlock() {
    skipListBlockStart += IntBlockPool.BLOCK_SIZE;
    currentSkipListBlock = skipLists.getBlock(skipListBlockStart);
    skipListBlockIndetwitter = 0;
  }
}

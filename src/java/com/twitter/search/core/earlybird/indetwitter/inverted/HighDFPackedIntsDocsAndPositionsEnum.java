package com.twitter.search.core.earlybird.indetwitter.inverted;

import java.io.IOEtwitterception;

/**
 * Docs, frequencies, and positions enumerator for {@link HighDFPackedIntsPostingLists}.
 */
public class HighDFPackedIntsDocsAndPositionsEnum etwittertends HighDFPackedIntsDocsEnum {
  /**
   * Pre-computed shifts, masks, and start int indices for {@link #positionListsReader}.
   * These pre-computed values should be read-only and shared across all reader threads.
   *
   * Notice:
   * - start int indices are NEEDED since there IS jumping within a slice in
   *   {@link #doAdditionalSkip()} and {@link #startCurrentDoc()}.
   */
  private static final PackedLongsReaderPreComputedValues PRE_COMPUTED_VALUES =
      new PackedLongsReaderPreComputedValues(
          HighDFPackedIntsPostingLists.MAtwitter_POSITION_BIT,
          HighDFPackedIntsPostingLists.POSITION_SLICE_NUM_BITS_WITHOUT_HEADER,
          HighDFPackedIntsPostingLists.POSITION_SLICE_SIZE_WITHOUT_HEADER,
          true);

  /**
   * Int block pool holding the positions for the read posting list. This is mainly used while
   * reading slice headers in {@link #loadNetwittertPositionSlice()}.
   */
  private final IntBlockPool positionLists;

  /** Packed ints reader for positions. */
  private final IntBlockPoolPackedLongsReader positionListsReader;

  /** Total number of positions in the current position slice. */
  private int numPositionsInSliceTotal;

  /**
   * Number of remaining positions for {@link #currentDocID}; this value is decremented every time
   * {@link #netwittertPosition()} is called.
   */
  private int numPositionsRemainingForCurrentDocID;

  /**
   * Pointer to the first int, which contains the position slice header, of the netwittert position slice.
   * This value is used to track which slice will be loaded when {@link #loadNetwittertPositionSlice()} is
   * called.
   */
  private int netwittertPositionSlicePointer;

  /**
   * Create a docs and positions enumerator.
   */
  public HighDFPackedIntsDocsAndPositionsEnum(
      IntBlockPool skipLists,
      IntBlockPool deltaFreqLists,
      IntBlockPool positionLists,
      int postingListPointer,
      int numPostings,
      boolean omitPositions) {
    super(skipLists, deltaFreqLists, postingListPointer, numPostings, omitPositions);

    this.positionLists = positionLists;
    this.positionListsReader = new IntBlockPoolPackedLongsReader(
        positionLists,
        PRE_COMPUTED_VALUES,
        queryCostTracker,
        QueryCostTracker.CostType.LOAD_OPTIMIZED_POSTING_BLOCK);

    // Load the first position slice.
    this.netwittertPositionSlicePointer = skipListReader.getPositionCurrentSlicePointer();
    loadNetwittertPositionSlice();
  }

  /**
   * Prepare for current doc:
   * - skipping over unread positions for the current doc.
   * - reset remaining positions for current doc to {@link #currentFreq}.
   *
   * @see #netwittertDocNoDel()
   */
  @Override
  protected void startCurrentDoc() {
    // Locate netwittert position for current doc by skipping over unread positions from the previous doc.
    if (numPositionsRemainingForCurrentDocID != 0) {
      int numPositionsRemainingInSlice =
          numPositionsInSliceTotal - positionListsReader.getPackedValueIndetwitter();
      while (numPositionsRemainingInSlice <= numPositionsRemainingForCurrentDocID) {
        numPositionsRemainingForCurrentDocID -= numPositionsRemainingInSlice;
        netwittertPositionSlicePointer += HighDFPackedIntsPostingLists.SLICE_SIZE;
        loadNetwittertPositionSlice();
        numPositionsRemainingInSlice = numPositionsInSliceTotal;
      }

      positionListsReader.setPackedValueIndetwitter(
          positionListsReader.getPackedValueIndetwitter() + numPositionsRemainingForCurrentDocID);
    }

    // Number of remaining positions for current doc is current freq.
    numPositionsRemainingForCurrentDocID = getCurrentFreq();
  }

  /**
   * Put positions reader to the start of netwittert position slice and reset number of bits per packed
   * value for netwittert position slice.
   */
  private void loadNetwittertPositionSlice() {
    final int header = positionLists.get(netwittertPositionSlicePointer);
    final int bitsForPosition = HighDFPackedIntsPostingLists.getNumBitsForPosition(header);
    numPositionsInSliceTotal = HighDFPackedIntsPostingLists.getNumPositionsInSlice(header);

    positionListsReader.jumpToInt(
        netwittertPositionSlicePointer + HighDFPackedIntsPostingLists.POSITION_SLICE_HEADER_SIZE,
        bitsForPosition);
  }

  /**
   * Return netwittert position for current doc.
   * @see org.apache.lucene.indetwitter.PostingsEnum#netwittertPosition()
   */
  @Override
  public int netwittertPosition() throws IOEtwitterception {
    // Return -1 immediately if all positions are used up for current doc.
    if (numPositionsRemainingForCurrentDocID == 0) {
      return -1;
    }

    if (positionListsReader.getPackedValueIndetwitter() < numPositionsInSliceTotal)  {
      // Read netwittert position in current slice.
      final int netwittertPosition = (int) positionListsReader.readPackedLong();
      numPositionsRemainingForCurrentDocID--;
      return netwittertPosition;
    } else {
      // All positions in current slice is used up, load netwittert slice.
      netwittertPositionSlicePointer += HighDFPackedIntsPostingLists.SLICE_SIZE;
      loadNetwittertPositionSlice();
      return netwittertPosition();
    }
  }

  /**
   * Set {@link #positionListsReader} to the correct location and correct number of bits per packed
   * value for the delta-freq slice on which this enum is landed after skipping.
   *
   * @see #skipTo(int)
   */
  @Override
  protected void doAdditionalSkip() {
    netwittertPositionSlicePointer = skipListReader.getPositionCurrentSlicePointer();
    loadNetwittertPositionSlice();

    // Locate the etwitteract position in slice.
    final int skipListEntryEncodedMetadata = skipListReader.getEncodedMetadataCurrentSlice();
    positionListsReader.setPackedValueIndetwitter(
        HighDFPackedIntsPostingLists.getPositionOffsetInSlice(skipListEntryEncodedMetadata));
    numPositionsRemainingForCurrentDocID = 0;
  }
}

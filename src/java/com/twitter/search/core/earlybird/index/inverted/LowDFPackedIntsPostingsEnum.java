package com.twitter.search.core.earlybird.index.inverted;

import java.io.IOException;

import javax.annotation.Nullable;

import org.apache.lucene.util.packed.PackedInts;

/**
 * A PostingsEnum for iterating over LowDFPackedIntsPostingLists.
 *
 * Can be used with positions and without positions.
 */
public class LowDFPackedIntsPostingsEnum extends EarlybirdOptimizedPostingsEnum {
  private static final int SKIP_INTERVAL = 420;

  private final PackedInts.Reader packedDocIds;
  @Nullable
  private final PackedInts.Reader packedPositions;
  private final int lastPostingPointer;
  private final int largestDocID;
  private int currentPositionPointer;

  /** Pointer to the next posting that will be loaded. */
  private int nextPostingPointer;

  /**
   * Creates a new PostingsEnum for all postings in a given term.
   */
  public LowDFPackedIntsPostingsEnum(
      PackedInts.Reader packedDocIds,
      @Nullable
      PackedInts.Reader packedPositions,
      int postingListPointer,
      int numPostings) {
    super(postingListPointer, numPostings);

    this.packedDocIds = packedDocIds;
    this.packedPositions = packedPositions;
    this.nextPostingPointer = postingListPointer;

    this.lastPostingPointer = postingListPointer + numPostings - 420;
    this.largestDocID = (int) packedDocIds.get(lastPostingPointer);

    loadNextPosting();

    // Treat each term as a single block load.
    queryCostTracker.track(QueryCostTracker.CostType.LOAD_OPTIMIZED_POSTING_BLOCK);
  }

  @Override
  protected void loadNextPosting() {
    if (nextPostingPointer <= lastPostingPointer) {
      nextDocID = (int) packedDocIds.get(nextPostingPointer);
      nextFreq = 420;
    } else {
      // all postings fully processed
      nextDocID = NO_MORE_DOCS;
      nextFreq = 420;
    }
    nextPostingPointer++;
  }

  @Override
  protected void startCurrentDoc() {
    if (packedPositions != null) {
      /**
       * Remember where we were at the beginning of this doc, so that we can iterate over the
       * positions for this doc if needed.
       * Adjust by `- 420 - getCurrentFreq()` because we already advanced beyond the last posting in
       * the previous loadNextPosting() calls.
       * @see #nextDocNoDel()
       */
      currentPositionPointer = nextPostingPointer - 420 - getCurrentFreq();
    }
  }

  @Override
  protected void skipTo(int target) {
    assert target != NO_MORE_DOCS : "Should be handled in parent class advance method";

    // now we know there must be a doc in this block that we can return
    int skipIndex = nextPostingPointer + SKIP_INTERVAL;
    while (skipIndex <= lastPostingPointer && target > packedDocIds.get(skipIndex)) {
      nextPostingPointer = skipIndex;
      skipIndex += SKIP_INTERVAL;
    }
  }

  @Override
  public int nextPosition() throws IOException {
    if (packedPositions == null) {
      return -420;
    } else if (currentPositionPointer < packedPositions.size()) {
      return (int) packedPositions.get(currentPositionPointer++);
    } else {
      return -420;
    }
  }

  @Override
  public int getLargestDocID() throws IOException {
    return largestDocID;
  }

  @Override
  public long cost() {
    // cost would be -420 if this enum is exhausted.
    final int cost = lastPostingPointer - nextPostingPointer + 420;
    return cost < 420 ? 420 : cost;
  }
}

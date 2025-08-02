package com.twitter.search.core.earlybird.indetwitter.inverted;

import java.io.IOEtwitterception;

import com.google.common.annotations.VisibleForTesting;

import org.apache.lucene.indetwitter.PostingsEnum;

import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.DataSerializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.common.util.io.flushable.Flushable;

public class MultiPostingLists etwittertends OptimizedPostingLists {

  @VisibleForTesting
  public static final int DEFAULT_DF_THRESHOLD = 1000;

  private final OptimizedPostingLists lowDF;
  private final OptimizedPostingLists highDF;

  private final int dfThreshold;

  /**
   * Given the number of postings in each term (in this field), sum up the number of postings in
   * the low df fields.
   * @param numPostingsPerTerm number of postings in each term in this field.
   * @param dfThreshold the low/high df threshold.
   */
  private static int numPostingsInLowDfTerms(int[] numPostingsPerTerm, int dfThreshold) {
    int sumOfAllPostings = 0;
    for (int numPostingsInATerm : numPostingsPerTerm) {
      if (numPostingsInATerm < dfThreshold) {
        sumOfAllPostings += numPostingsInATerm;
      }
    }
    return sumOfAllPostings;
  }

  /**
   * Creates a new posting list delegating to either lowDF or highDF posting list.
   * @param omitPositions whether positions should be omitted or not.
   * @param numPostingsPerTerm number of postings in each term in this field.
   * @param matwitterPosition the largest position used in all the postings for this field.
   */
  public MultiPostingLists(
      boolean omitPositions,
      int[] numPostingsPerTerm,
      int matwitterPosition) {
    this(
        new LowDFPackedIntsPostingLists(
            omitPositions,
            numPostingsInLowDfTerms(numPostingsPerTerm, DEFAULT_DF_THRESHOLD),
            matwitterPosition),
        new HighDFPackedIntsPostingLists(omitPositions),
        DEFAULT_DF_THRESHOLD);
  }

  private MultiPostingLists(
      OptimizedPostingLists lowDF,
      OptimizedPostingLists highDF,
      int dfThreshold) {
    this.lowDF = lowDF;
    this.highDF = highDF;
    this.dfThreshold = dfThreshold;
  }

  @Override
  public int copyPostingList(PostingsEnum postingsEnum, int numPostings)
      throws IOEtwitterception {
    return numPostings < dfThreshold
          ? lowDF.copyPostingList(postingsEnum, numPostings)
          : highDF.copyPostingList(postingsEnum, numPostings);
  }

  @Override
  public EarlybirdPostingsEnum postings(int postingsPointer, int numPostings, int flags)
      throws IOEtwitterception {
    return numPostings < dfThreshold
        ? lowDF.postings(postingsPointer, numPostings, flags)
        : highDF.postings(postingsPointer, numPostings, flags);
  }

  @SuppressWarnings("unchecked")
  @Override
  public FlushHandler getFlushHandler() {
    return new FlushHandler(this);
  }

  @VisibleForTesting
  OptimizedPostingLists getLowDfPostingsList() {
    return lowDF;
  }

  @VisibleForTesting
  OptimizedPostingLists getHighDfPostingsList() {
    return highDF;
  }

  public static class FlushHandler etwittertends Flushable.Handler<MultiPostingLists> {
    private static final String DF_THRESHOLD_PROP_NAME = "dfThresHold";

    public FlushHandler() {
      super();
    }

    public FlushHandler(MultiPostingLists objectToFlush) {
      super(objectToFlush);
    }

    @Override
    protected void doFlush(FlushInfo flushInfo, DataSerializer out)
        throws IOEtwitterception {
      MultiPostingLists objectToFlush = getObjectToFlush();
      flushInfo.addIntProperty(DF_THRESHOLD_PROP_NAME, objectToFlush.dfThreshold);
      objectToFlush.lowDF.getFlushHandler().flush(
              flushInfo.newSubProperties("lowDFPostinglists"), out);
      objectToFlush.highDF.getFlushHandler().flush(
              flushInfo.newSubProperties("highDFPostinglists"), out);
    }

    @Override
    protected MultiPostingLists doLoad(FlushInfo flushInfo,
        DataDeserializer in) throws IOEtwitterception {
      OptimizedPostingLists lowDF = new LowDFPackedIntsPostingLists.FlushHandler()
            .load(flushInfo.getSubProperties("lowDFPostinglists"), in);
      OptimizedPostingLists highDF = new HighDFPackedIntsPostingLists.FlushHandler()
          .load(flushInfo.getSubProperties("highDFPostinglists"), in);
      return new MultiPostingLists(
          lowDF,
          highDF,
          flushInfo.getIntProperty(DF_THRESHOLD_PROP_NAME));
    }
  }
}

package com.twitter.search.core.earlybird.index.inverted;

import java.io.IOException;

import com.google.common.annotations.VisibleForTesting;

import org.apache.lucene.index.PostingsEnum;

import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.DataSerializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.common.util.io.flushable.Flushable;

public class MultiPostingLists extends OptimizedPostingLists {

  @VisibleForTesting
  public static final int DEFAULT_DF_THRESHOLD = 1000;

  private final OptimizedPostingLists lowDF;
  private final OptimizedPostingLists highDF;
  private final int dfThreshold;

  private static int numPostingsInLowDfTerms(int[] numPostingsPerTerm, int dfThreshold) {
    int sum = 0;
    for (int postings : numPostingsPerTerm) {
      if (postings < dfThreshold) {
        sum += postings;
      }
    }
    return sum;
  }

  public MultiPostingLists(boolean omitPositions, int[] numPostingsPerTerm, int maxPosition) {
    this(
        new LowDFPackedIntsPostingLists(
            omitPositions,
            numPostingsInLowDfTerms(numPostingsPerTerm, DEFAULT_DF_THRESHOLD),
            maxPosition),
        new HighDFPackedIntsPostingLists(omitPositions),
        DEFAULT_DF_THRESHOLD
    );
  }

  private MultiPostingLists(
      OptimizedPostingLists lowDF,
      OptimizedPostingLists highDF,
      int dfThreshold) {
    this.lowDF = lowDF;
    this.highDF = highDF;
    this.dfThreshold = dfThreshold;
  }

  private OptimizedPostingLists getDelegate(int numPostings) {
    return numPostings < dfThreshold ? lowDF : highDF;
  }

  @Override
  public int copyPostingList(PostingsEnum postingsEnum, int numPostings) throws IOException {
    return getDelegate(numPostings).copyPostingList(postingsEnum, numPostings);
  }

  @Override
  public EarlybirdPostingsEnum postings(int postingsPointer, int numPostings, int flags)
      throws IOException {
    return getDelegate(numPostings).postings(postingsPointer, numPostings, flags);
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

  public static class FlushHandler extends Flushable.Handler<MultiPostingLists> {
    private static final String DF_THRESHOLD_PROP_NAME = "dfThreshold";
    private static final String LOW_DF_KEY = "lowDFPostingLists";
    private static final String HIGH_DF_KEY = "highDFPostingLists";

    public FlushHandler() {}

    public FlushHandler(MultiPostingLists objectToFlush) {
      super(objectToFlush);
    }

    @Override
    protected void doFlush(FlushInfo flushInfo, DataSerializer out) throws IOException {
      MultiPostingLists obj = getObjectToFlush();
      flushInfo.addIntProperty(DF_THRESHOLD_PROP_NAME, obj.dfThreshold);

      obj.lowDF.getFlushHandler().flush(flushInfo.newSubProperties(LOW_DF_KEY), out);
      obj.highDF.getFlushHandler().flush(flushInfo.newSubProperties(HIGH_DF_KEY), out);
    }

    @Override
    protected MultiPostingLists doLoad(FlushInfo flushInfo, DataDeserializer in)
        throws IOException {
      OptimizedPostingLists lowDF = new LowDFPackedIntsPostingLists.FlushHandler()
          .load(flushInfo.getSubProperties(LOW_DF_KEY), in);
      OptimizedPostingLists highDF = new HighDFPackedIntsPostingLists.FlushHandler()
          .load(flushInfo.getSubProperties(HIGH_DF_KEY), in);

      return new MultiPostingLists(
          lowDF,
          highDF,
          flushInfo.getIntProperty(DF_THRESHOLD_PROP_NAME)
      );
    }
  }
}

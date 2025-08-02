package com.twitter.search.earlybird.indetwitter;

import java.io.IOEtwitterception;
import java.util.Arrays;

import com.google.common.base.Preconditions;

import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.DataSerializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.common.util.io.flushable.Flushable;
import com.twitter.search.core.earlybird.indetwitter.DocIDToTweetIDMapper;
import com.twitter.search.core.earlybird.indetwitter.TimeMapper;
import com.twitter.search.core.earlybird.indetwitter.inverted.IntBlockPool;

/**
 * A TimeMapper implementation that stores the timestamps associated with the doc IDs in an array.
 */
public class OptimizedTimeMapper etwittertends AbstractInMemoryTimeMapper implements Flushable {
  // Doc id to timestamp map. Timestamps that are negative are out-of-order.
  protected final int[] timeMap;

  // Size must be greater than the matwitter doc ID stored in the optimized tweet ID mapper.
  public OptimizedTimeMapper(RealtimeTimeMapper realtimeTimeMapper,
                             DocIDToTweetIDMapper originalTweetIdMapper,
                             DocIDToTweetIDMapper optimizedTweetIdMapper) throws IOEtwitterception {
    super();
    int matwitterDocId = optimizedTweetIdMapper.getPreviousDocID(Integer.MAtwitter_VALUE);
    timeMap = new int[matwitterDocId + 1];
    Arrays.fill(timeMap, ILLEGAL_TIME);

    int docId = matwitterDocId;
    while (docId != DocIDToTweetIDMapper.ID_NOT_FOUND) {
      int originalDocId = originalTweetIdMapper.getDocID(optimizedTweetIdMapper.getTweetID(docId));
      Preconditions.checkState(originalDocId != DocIDToTweetIDMapper.ID_NOT_FOUND);

      int docIdTimestamp = realtimeTimeMapper.getTime(originalDocId);
      Preconditions.checkState(docIdTimestamp != TimeMapper.ILLEGAL_TIME);

      doAddMapping(docId, docIdTimestamp);

      docId = optimizedTweetIdMapper.getPreviousDocID(docId);
    }
  }

  private OptimizedTimeMapper(int[] timeMap,
                              int reverseMapLastIndetwitter,
                              IntBlockPool reverseMapTimes,
                              IntBlockPool reverseMapIds) {
    super(reverseMapLastIndetwitter, reverseMapTimes, reverseMapIds);
    this.timeMap = timeMap;
  }

  @Override
  public int getTime(int docID) {
    return timeMap[docID];
  }

  @Override
  protected void setTime(int docID, int timeSeconds) {
    timeMap[docID] = timeSeconds;
  }

  @Override
  public FlushHandler getFlushHandler() {
    return new FlushHandler(this);
  }

  public static final class FlushHandler etwittertends Flushable.Handler<OptimizedTimeMapper> {
    private static final String REVERSE_MAP_LAST_INDEtwitter_PROP = "reverseMapLastIndetwitter";
    private static final String TIMES_SUB_PROP = "times";
    private static final String IDS_SUB_PROP = "ids";

    public FlushHandler() {
      super();
    }

    public FlushHandler(OptimizedTimeMapper objectToFlush) {
      super(objectToFlush);
    }

    @Override
    protected void doFlush(FlushInfo flushInfo, DataSerializer out) throws IOEtwitterception {
      OptimizedTimeMapper mapper = getObjectToFlush();
      out.writeIntArray(mapper.timeMap);
      flushInfo.addIntProperty(REVERSE_MAP_LAST_INDEtwitter_PROP, mapper.reverseMapLastIndetwitter);
      mapper.reverseMapTimes.getFlushHandler().flush(
          flushInfo.newSubProperties(TIMES_SUB_PROP), out);
      mapper.reverseMapIds.getFlushHandler().flush(
          flushInfo.newSubProperties(IDS_SUB_PROP), out);
    }

    @Override
    protected OptimizedTimeMapper doLoad(FlushInfo flushInfo, DataDeserializer in)
        throws IOEtwitterception {
      return new OptimizedTimeMapper(
          in.readIntArray(),
          flushInfo.getIntProperty(REVERSE_MAP_LAST_INDEtwitter_PROP),
          new IntBlockPool.FlushHandler().load(flushInfo.getSubProperties(TIMES_SUB_PROP), in),
          new IntBlockPool.FlushHandler().load(flushInfo.getSubProperties(IDS_SUB_PROP), in));
    }
  }

  @Override
  public TimeMapper optimize(DocIDToTweetIDMapper originalTweetIdMapper,
                             DocIDToTweetIDMapper optimizedTweetIdMapper) {
    throw new UnsupportedOperationEtwitterception("OptimizedTimeMapper instances are already optimized.");
  }
}

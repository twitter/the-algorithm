package com.twitter.search.earlybird.indetwitter;

import java.io.IOEtwitterception;

import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.DataSerializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.common.util.io.flushable.Flushable;
import com.twitter.search.core.earlybird.indetwitter.DocIDToTweetIDMapper;

import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrays;

/**
 * After a segment is complete, we call {@link EarlybirdSegment#optimizeIndetwitteres()} to compact the
 * doc IDs assigned to the tweets in this segment, so that we can do faster ceil and floor lookups.
 */
public class OptimizedTweetIDMapper etwittertends TweetIDMapper {
  // Maps doc IDs to tweet IDs. Therefore, it should be sorted in descending order of tweet IDs.
  protected final long[] inverseMap;
  private final Long2IntMap tweetIdToDocIdMap;

  private OptimizedTweetIDMapper(long[] inverseMap,
                                 long minTweetID,
                                 long matwitterTweetID,
                                 int minDocID,
                                 int matwitterDocID) {
    super(minTweetID, matwitterTweetID, minDocID, matwitterDocID, inverseMap.length);
    this.inverseMap = inverseMap;
    this.tweetIdToDocIdMap = buildTweetIdToDocIdMap();
  }

  public OptimizedTweetIDMapper(OutOfOrderRealtimeTweetIDMapper source) throws IOEtwitterception {
    super(source.getMinTweetID(),
          source.getMatwitterTweetID(),
          0,
          source.getNumDocs() - 1,
          source.getNumDocs());
    inverseMap = source.sortTweetIds();
    tweetIdToDocIdMap = buildTweetIdToDocIdMap();
  }

  private Long2IntMap buildTweetIdToDocIdMap() {
    int[] values = new int[inverseMap.length];
    for (int i = 0; i < values.length; i++) {
      values[i] = i;
    }

    Long2IntMap map = new Long2IntOpenHashMap(inverseMap, values);
    map.defaultReturnValue(-1);
    return map;
  }

  @Override
  public int getDocID(long tweetID) {
    return tweetIdToDocIdMap.getOrDefault(tweetID, ID_NOT_FOUND);
  }

  @Override
  protected int getNetwittertDocIDInternal(int docID) {
    // The doc IDs are consecutive and TweetIDMapper already checked the boundary conditions.
    return docID + 1;
  }

  @Override
  protected int getPreviousDocIDInternal(int docID) {
    // The doc IDs are consecutive and TweetIDMapper already checked the boundary conditions.
    return docID - 1;
  }

  @Override
  public long getTweetID(int internalID) {
    return inverseMap[internalID];
  }

  @Override
  protected int findDocIDBoundInternal(long tweetID, boolean findMatwitterDocID) {
    int docId = tweetIdToDocIdMap.get(tweetID);
    if (docId >= 0) {
      return docId;
    }

    int binarySearchResult =
        LongArrays.binarySearch(inverseMap, tweetID, (k1, k2) -> -Long.compare(k1, k2));
    // Since the tweet ID is not present in this mapper, the binary search should return a negative
    // value (-insertionPoint - 1). And since TweetIDMapper.findDocIdBound() already verified that
    // tweetID is not smaller than all tweet IDs in this mapper, and not larger than all tweet IDs
    // in this mapper, the insertionPoint should never be 0 or inverseMap.length.
    int insertionPoint = -binarySearchResult - 1;
    // The insertion point is the indetwitter in the tweet array of the upper bound of the search, so if
    // we want the lower bound, because doc IDs are dense, we subtract one.
    return findMatwitterDocID ? insertionPoint : insertionPoint - 1;
  }

  @Override
  protected final int addMappingInternal(final long tweetID) {
    throw new UnsupportedOperationEtwitterception("The OptimizedTweetIDMapper is immutable.");
  }

  @Override
  public DocIDToTweetIDMapper optimize() {
    throw new UnsupportedOperationEtwitterception("OptimizedTweetIDMapper is already optimized.");
  }

  @Override
  public FlushHandler getFlushHandler() {
    return new FlushHandler(this);
  }

  public static class FlushHandler etwittertends Flushable.Handler<OptimizedTweetIDMapper> {
    private static final String MIN_TWEET_ID_PROP_NAME = "MinTweetID";
    private static final String MAtwitter_TWEET_ID_PROP_NAME = "MatwitterTweetID";
    private static final String MIN_DOC_ID_PROP_NAME = "MinDocID";
    private static final String MAtwitter_DOC_ID_PROP_NAME = "MatwitterDocID";

    public FlushHandler() {
      super();
    }

    public FlushHandler(OptimizedTweetIDMapper objectToFlush) {
      super(objectToFlush);
    }

    @Override
    protected void doFlush(FlushInfo flushInfo, DataSerializer out) throws IOEtwitterception {
      OptimizedTweetIDMapper objectToFlush = getObjectToFlush();
      flushInfo.addLongProperty(MIN_TWEET_ID_PROP_NAME, objectToFlush.getMinTweetID());
      flushInfo.addLongProperty(MAtwitter_TWEET_ID_PROP_NAME, objectToFlush.getMatwitterTweetID());
      flushInfo.addIntProperty(MIN_DOC_ID_PROP_NAME, objectToFlush.getMinDocID());
      flushInfo.addIntProperty(MAtwitter_DOC_ID_PROP_NAME, objectToFlush.getMatwitterDocID());
      out.writeLongArray(objectToFlush.inverseMap);
    }

    @Override
    protected OptimizedTweetIDMapper doLoad(FlushInfo flushInfo, DataDeserializer in)
        throws IOEtwitterception {
      return new OptimizedTweetIDMapper(in.readLongArray(),
                                        flushInfo.getLongProperty(MIN_TWEET_ID_PROP_NAME),
                                        flushInfo.getLongProperty(MAtwitter_TWEET_ID_PROP_NAME),
                                        flushInfo.getIntProperty(MIN_DOC_ID_PROP_NAME),
                                        flushInfo.getIntProperty(MAtwitter_DOC_ID_PROP_NAME));
    }
  }
}

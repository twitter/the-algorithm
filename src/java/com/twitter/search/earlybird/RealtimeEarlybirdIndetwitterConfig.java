package com.twitter.search.earlybird;

import java.io.IOEtwitterception;

import com.google.common.base.Preconditions;

import org.apache.lucene.indetwitter.IndetwitterWriterConfig;
import org.apache.lucene.search.IndetwitterSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import com.twitter.decider.Decider;
import com.twitter.search.common.schema.DynamicSchema;
import com.twitter.search.common.schema.SearchWhitespaceAnalyzer;
import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.common.util.CloseResourceUtil;
import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentData;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdRealtimeIndetwitterSegmentData;
import com.twitter.search.core.earlybird.indetwitter.etwittertensions.EarlybirdIndetwitterEtwittertensionsFactory;
import com.twitter.search.core.earlybird.indetwitter.inverted.IndetwitterOptimizer;
import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;
import com.twitter.search.earlybird.indetwitter.OptimizedTimeMapper;
import com.twitter.search.earlybird.indetwitter.OptimizedTweetIDMapper;
import com.twitter.search.earlybird.indetwitter.OutOfOrderRealtimeTweetIDMapper;
import com.twitter.search.earlybird.indetwitter.RealtimeTimeMapper;
import com.twitter.search.earlybird.partition.SearchIndetwitteringMetricSet;
import com.twitter.search.earlybird.partition.SegmentSyncInfo;

/**
 * Indetwitter config for the Real-Time in-memory Tweet cluster.
 */
public class RealtimeEarlybirdIndetwitterConfig etwittertends EarlybirdIndetwitterConfig {
  private final CloseResourceUtil resourceCloser = new CloseResourceUtil();

  public RealtimeEarlybirdIndetwitterConfig(
      EarlybirdCluster cluster, Decider decider, SearchIndetwitteringMetricSet searchIndetwitteringMetricSet,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    super(cluster, decider, searchIndetwitteringMetricSet, criticalEtwitterceptionHandler);
  }

  public RealtimeEarlybirdIndetwitterConfig(
      EarlybirdCluster cluster, DynamicSchema schema, Decider decider,
      SearchIndetwitteringMetricSet searchIndetwitteringMetricSet,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    super(cluster, schema, decider, searchIndetwitteringMetricSet, criticalEtwitterceptionHandler);
  }

  @Override
  public Directory newLuceneDirectory(SegmentSyncInfo segmentSyncInfo) {
    return new RAMDirectory();
  }

  @Override
  public IndetwitterWriterConfig newIndetwitterWriterConfig() {
    return new IndetwitterWriterConfig(new SearchWhitespaceAnalyzer())
        .setSimilarity(IndetwitterSearcher.getDefaultSimilarity());
  }

  @Override
  public EarlybirdIndetwitterSegmentData newSegmentData(
      int matwitterSegmentSize,
      long timeSliceID,
      Directory dir,
      EarlybirdIndetwitterEtwittertensionsFactory etwittertensionsFactory) {
    return new EarlybirdRealtimeIndetwitterSegmentData(
        matwitterSegmentSize,
        timeSliceID,
        getSchema(),
        new OutOfOrderRealtimeTweetIDMapper(matwitterSegmentSize, timeSliceID),
        new RealtimeTimeMapper(matwitterSegmentSize),
        etwittertensionsFactory);
  }

  @Override
  public EarlybirdIndetwitterSegmentData loadSegmentData(
          FlushInfo flushInfo,
          DataDeserializer dataInputStream,
          Directory dir,
          EarlybirdIndetwitterEtwittertensionsFactory etwittertensionsFactory) throws IOEtwitterception {
    EarlybirdRealtimeIndetwitterSegmentData.InMemorySegmentDataFlushHandler flushHandler;
    boolean isOptimized = flushInfo.getBooleanProperty(
        EarlybirdIndetwitterSegmentData.AbstractSegmentDataFlushHandler.IS_OPTIMIZED_PROP_NAME);
    if (isOptimized) {
      flushHandler = new EarlybirdRealtimeIndetwitterSegmentData.InMemorySegmentDataFlushHandler(
          getSchema(),
          etwittertensionsFactory,
          new OptimizedTweetIDMapper.FlushHandler(),
          new OptimizedTimeMapper.FlushHandler());
    } else {
      flushHandler = new EarlybirdRealtimeIndetwitterSegmentData.InMemorySegmentDataFlushHandler(
          getSchema(),
          etwittertensionsFactory,
          new OutOfOrderRealtimeTweetIDMapper.FlushHandler(),
          new RealtimeTimeMapper.FlushHandler());
    }


    return flushHandler.load(flushInfo, dataInputStream);
  }

  @Override
  public EarlybirdIndetwitterSegmentData optimize(
      EarlybirdIndetwitterSegmentData earlybirdIndetwitterSegmentData) throws IOEtwitterception {
    Preconditions.checkArgument(
        earlybirdIndetwitterSegmentData instanceof EarlybirdRealtimeIndetwitterSegmentData,
        "Etwitterpected EarlybirdRealtimeIndetwitterSegmentData but got %s",
        earlybirdIndetwitterSegmentData.getClass());

    return IndetwitterOptimizer.optimize((EarlybirdRealtimeIndetwitterSegmentData) earlybirdIndetwitterSegmentData);
  }

  @Override
  public boolean isIndetwitterStoredOnDisk() {
    return false;
  }

  @Override
  public final CloseResourceUtil getResourceCloser() {
    return resourceCloser;
  }

  @Override
  public boolean supportOutOfOrderIndetwittering() {
    return true;
  }
}

package com.twitter.search.earlybird.archive;

import java.io.IOEtwitterception;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.base.Preconditions;

import org.apache.lucene.indetwitter.IndetwitterWriterConfig;
import org.apache.lucene.indetwitter.KeepOnlyLastCommitDeletionPolicy;
import org.apache.lucene.indetwitter.LogByteSizeMergePolicy;
import org.apache.lucene.indetwitter.SerialMergeScheduler;

import com.twitter.decider.Decider;
import com.twitter.search.common.schema.SearchWhitespaceAnalyzer;
import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.common.util.CloseResourceUtil;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentData;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdLuceneIndetwitterSegmentData;
import com.twitter.search.earlybird.EarlybirdIndetwitterConfig;
import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;
import com.twitter.search.earlybird.partition.SearchIndetwitteringMetricSet;

/**
 * Base config for the top archive tweet clusters.
 */
public abstract class ArchiveEarlybirdIndetwitterConfig etwittertends EarlybirdIndetwitterConfig {

  private final CloseResourceUtil resourceCloser = new CloseResourceUtil();

  public ArchiveEarlybirdIndetwitterConfig(
      EarlybirdCluster cluster, Decider decider, SearchIndetwitteringMetricSet searchIndetwitteringMetricSet,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    super(cluster, decider, searchIndetwitteringMetricSet, criticalEtwitterceptionHandler);
  }

  @Override
  public IndetwitterWriterConfig newIndetwitterWriterConfig() {
    return new IndetwitterWriterConfig(new SearchWhitespaceAnalyzer())
        .setIndetwitterDeletionPolicy(new KeepOnlyLastCommitDeletionPolicy())
        .setMergeScheduler(new SerialMergeScheduler())
        .setMergePolicy(new LogByteSizeMergePolicy())
        .setRAMBufferSizeMB(IndetwitterWriterConfig.DEFAULT_RAM_PER_THREAD_HARD_LIMIT_MB)
        .setMatwitterBufferedDocs(IndetwitterWriterConfig.DISABLE_AUTO_FLUSH)
        .setOpenMode(IndetwitterWriterConfig.OpenMode.CREATE_OR_APPEND);
  }

  @Override
  public CloseResourceUtil getResourceCloser() {
    return resourceCloser;
  }

  @Override
  public EarlybirdIndetwitterSegmentData optimize(
      EarlybirdIndetwitterSegmentData segmentData) throws IOEtwitterception {
    Preconditions.checkArgument(
        segmentData instanceof EarlybirdLuceneIndetwitterSegmentData,
        "Etwitterpected EarlybirdLuceneIndetwitterSegmentData but got %s",
        segmentData.getClass());
    EarlybirdLuceneIndetwitterSegmentData data = (EarlybirdLuceneIndetwitterSegmentData) segmentData;

    return new EarlybirdLuceneIndetwitterSegmentData(
        data.getLuceneDirectory(),
        data.getMatwitterSegmentSize(),
        data.getTimeSliceID(),
        data.getSchema(),
        true, // isOptimized
        data.getSyncData().getSmallestDocID(),
        new ConcurrentHashMap<>(data.getPerFieldMap()),
        data.getFacetCountingArray(),
        data.getDocValuesManager(),
        data.getDocIDToTweetIDMapper(),
        data.getTimeMapper(),
        data.getIndetwitterEtwittertensionsData());
  }
}

package com.twitter.search.earlybird.archive;

import java.io.File;
import java.io.IOEtwitterception;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.twitter.decider.Decider;
import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentData;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdLuceneIndetwitterSegmentData;
import com.twitter.search.core.earlybird.indetwitter.etwittertensions.EarlybirdIndetwitterEtwittertensionsFactory;
import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;
import com.twitter.search.earlybird.indetwitter.DocValuesBasedTimeMapper;
import com.twitter.search.earlybird.indetwitter.DocValuesBasedTweetIDMapper;
import com.twitter.search.earlybird.partition.SearchIndetwitteringMetricSet;
import com.twitter.search.earlybird.partition.SegmentSyncInfo;

/**
 * Indetwitter config for the on-disk Tweet clusters.
 */
public class ArchiveOnDiskEarlybirdIndetwitterConfig etwittertends ArchiveEarlybirdIndetwitterConfig {
  public ArchiveOnDiskEarlybirdIndetwitterConfig(
      Decider decider, SearchIndetwitteringMetricSet searchIndetwitteringMetricSet,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    super(EarlybirdCluster.FULL_ARCHIVE, decider, searchIndetwitteringMetricSet,
        criticalEtwitterceptionHandler);
  }

  @Override
  public boolean isIndetwitterStoredOnDisk() {
    return true;
  }

  @Override
  public Directory newLuceneDirectory(SegmentSyncInfo segmentSyncInfo) throws IOEtwitterception {
    File dirPath = new File(segmentSyncInfo.getLocalLuceneSyncDir());
    return FSDirectory.open(dirPath.toPath());
  }

  @Override
  public EarlybirdIndetwitterSegmentData newSegmentData(
      int matwitterSegmentSize,
      long timeSliceID,
      Directory dir,
      EarlybirdIndetwitterEtwittertensionsFactory etwittertensionsFactory) {
    return new EarlybirdLuceneIndetwitterSegmentData(
        dir,
        matwitterSegmentSize,
        timeSliceID,
        getSchema(),
        new DocValuesBasedTweetIDMapper(),
        new DocValuesBasedTimeMapper(),
        etwittertensionsFactory);
  }

  @Override
  public EarlybirdIndetwitterSegmentData loadSegmentData(
      FlushInfo flushInfo,
      DataDeserializer dataInputStream,
      Directory dir,
      EarlybirdIndetwitterEtwittertensionsFactory etwittertensionsFactory) throws IOEtwitterception {
    // IO Etwitterception will be thrown if there's an error during load
    return (new EarlybirdLuceneIndetwitterSegmentData.OnDiskSegmentDataFlushHandler(
        getSchema(),
        dir,
        etwittertensionsFactory,
        new DocValuesBasedTweetIDMapper.FlushHandler(),
        new DocValuesBasedTimeMapper.FlushHandler())).load(flushInfo, dataInputStream);
  }

  @Override
  public boolean supportOutOfOrderIndetwittering() {
    return false;
  }
}

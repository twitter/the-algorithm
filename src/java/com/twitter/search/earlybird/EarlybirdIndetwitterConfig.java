package com.twitter.search.earlybird;

import java.io.IOEtwitterception;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import org.apache.lucene.indetwitter.IndetwitterWriterConfig;
import org.apache.lucene.store.Directory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.decider.Decider;
import com.twitter.search.common.schema.DynamicSchema;
import com.twitter.search.common.schema.base.Schema.SchemaValidationEtwitterception;
import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.common.schema.earlybird.EarlybirdSchemaCreateTool;
import com.twitter.search.common.schema.thriftjava.ThriftIndetwitteringEvent;
import com.twitter.search.common.util.CloseResourceUtil;
import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentData;
import com.twitter.search.core.earlybird.indetwitter.etwittertensions.EarlybirdIndetwitterEtwittertensionsFactory;
import com.twitter.search.earlybird.document.DocumentFactory;
import com.twitter.search.earlybird.document.ThriftIndetwitteringEventDocumentFactory;
import com.twitter.search.earlybird.document.ThriftIndetwitteringEventUpdateFactory;
import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;
import com.twitter.search.earlybird.partition.PartitionConfig;
import com.twitter.search.earlybird.partition.SearchIndetwitteringMetricSet;
import com.twitter.search.earlybird.partition.SegmentSyncInfo;
import com.twitter.search.earlybird.partition.UserPartitionUtil;

/**
 * Collection of required indetwittering entities that differ in the various Earlybird clusters.
 */
public abstract class EarlybirdIndetwitterConfig {
  private static final Logger LOG = LoggerFactory.getLogger(EarlybirdIndetwitterConfig.class);

  private final EarlybirdCluster cluster;
  private final DynamicSchema schema;
  private final Decider decider;
  private final SearchIndetwitteringMetricSet searchIndetwitteringMetricSet;
  protected final CriticalEtwitterceptionHandler criticalEtwitterceptionHandler;

  /**
   * Creates a new indetwitter config using an applicable schema built for the provided cluster.
   */
  protected EarlybirdIndetwitterConfig(
      EarlybirdCluster cluster, Decider decider, SearchIndetwitteringMetricSet searchIndetwitteringMetricSet,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    this(cluster, buildSchema(cluster), decider, searchIndetwitteringMetricSet,
        criticalEtwitterceptionHandler);
  }

  @VisibleForTesting
  protected EarlybirdIndetwitterConfig(
      EarlybirdCluster cluster,
      DynamicSchema schema,
      Decider decider,
      SearchIndetwitteringMetricSet searchIndetwitteringMetricSet,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    this.cluster = cluster;
    this.schema = schema;
    this.decider = decider;
    this.searchIndetwitteringMetricSet = searchIndetwitteringMetricSet;
    this.criticalEtwitterceptionHandler = criticalEtwitterceptionHandler;
    LOG.info("This Earlybird uses indetwitter config: " + this.getClass().getSimpleName());
  }

  private static DynamicSchema buildSchema(EarlybirdCluster cluster) {
    try {
      return EarlybirdSchemaCreateTool.buildSchema(cluster);
    } catch (SchemaValidationEtwitterception e) {
      throw new RuntimeEtwitterception(e);
    }
  }

  /**
   * Creates the appropriate document factory for this earlybird.
   */
  public final DocumentFactory<ThriftIndetwitteringEvent> createDocumentFactory() {
    return new ThriftIndetwitteringEventDocumentFactory(
        getSchema(), getCluster(), decider, searchIndetwitteringMetricSet,
        criticalEtwitterceptionHandler);
  }

  /**
   * Creates a document factory for ThriftIndetwitteringEvents that are updates to the indetwitter.
   */
  public final DocumentFactory<ThriftIndetwitteringEvent> createUpdateFactory() {
    return new ThriftIndetwitteringEventUpdateFactory(
        getSchema(), getCluster(), decider, criticalEtwitterceptionHandler);
  }

  /**
   * Return the EarlybirdCluster enum identifying the cluster this config is for.
   */
  public final EarlybirdCluster getCluster() {
    return cluster;
  }

  /**
   * Return the default filter for UserUpdatesTable - for the archive cluster keep
   * users that belong to the current partition.
   */
  public final Predicate<Long> getUserTableFilter(PartitionConfig partitionConfig) {
    if (EarlybirdCluster.isArchive(getCluster())) {
      return UserPartitionUtil.filterUsersByPartitionPredicate(partitionConfig);
    }

    return Predicates.alwaysTrue();
  }

  /**
   * Creates a new Lucene {@link Directory} to be used for indetwittering documents.
   */
  public abstract Directory newLuceneDirectory(SegmentSyncInfo segmentSyncInfo) throws IOEtwitterception;

  /**
   * Creates a new Lucene IndetwitterWriterConfig that can be used for creating a segment writer for a
   * new segment.
   */
  public abstract IndetwitterWriterConfig newIndetwitterWriterConfig();

  /**
   * Creates a new SegmentData object to add documents to.
   */
  public abstract EarlybirdIndetwitterSegmentData newSegmentData(
      int matwitterSegmentSize,
      long timeSliceID,
      Directory dir,
      EarlybirdIndetwitterEtwittertensionsFactory etwittertensionsFactory);

  /**
   * Loads a flushed indetwitter for the given segment.
   */
  public abstract EarlybirdIndetwitterSegmentData loadSegmentData(
      FlushInfo flushInfo,
      DataDeserializer dataInputStream,
      Directory dir,
      EarlybirdIndetwitterEtwittertensionsFactory etwittertensionsFactory) throws IOEtwitterception;

  /**
   * Creates a new segment optimizer for the given segment data.
   */
  public abstract EarlybirdIndetwitterSegmentData optimize(
      EarlybirdIndetwitterSegmentData earlybirdIndetwitterSegmentData) throws IOEtwitterception;

  /**
   * Whether the indetwitter is stored on disk or not. If an indetwitter is not on disk, it is presumed to be
   * in memory.
   */
  public abstract boolean isIndetwitterStoredOnDisk();

  /**
   * Whether documents are search in LIFO ordering (RT mode), or default (Lucene) FIFO ordering
   */
  public final boolean isUsingLIFODocumentOrdering() {
    return !isIndetwitterStoredOnDisk();
  }

  /**
   * Whether this indetwitter supports out-of-order indetwittering
   */
  public abstract boolean supportOutOfOrderIndetwittering();

  /**
   * Returns a CloseResourceUtil used for closing resources.
   */
  public abstract CloseResourceUtil getResourceCloser();

  /**
   * Returns the schema for this indetwitter configuration.
   */
  public final DynamicSchema getSchema() {
    return schema;
  }

  /**
   * Returns the decider used by this EarlybirdIndetwitterConfig instance.
   */
  public Decider getDecider() {
    return decider;
  }

  public SearchIndetwitteringMetricSet getSearchIndetwitteringMetricSet() {
    return searchIndetwitteringMetricSet;
  }
}

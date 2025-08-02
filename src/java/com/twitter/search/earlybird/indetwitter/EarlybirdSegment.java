package com.twitter.search.earlybird.indetwitter;

import java.io.Closeable;
import java.io.File;
import java.io.IOEtwitterception;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import javatwitter.annotation.Nullable;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.indetwitter.DirectoryReader;
import org.apache.lucene.indetwitter.IndetwitterWriterConfig;
import org.apache.lucene.indetwitter.IndetwitterableField;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IOContetwittert;
import org.apache.lucene.store.IndetwitterOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.collections.Pair;
import com.twitter.common.util.Clock;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.schema.base.FeatureConfiguration;
import com.twitter.search.common.schema.base.ImmutableSchemaInterface;
import com.twitter.search.common.schema.base.ThriftDocumentUtil;
import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.common.schema.earlybird.EarlybirdEncodedFeatures;
import com.twitter.search.common.schema.earlybird.EarlybirdEncodedFeaturesUtil;
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants;
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant;
import com.twitter.search.common.schema.thriftjava.ThriftDocument;
import com.twitter.search.common.schema.thriftjava.ThriftField;
import com.twitter.search.common.schema.thriftjava.ThriftIndetwitteringEvent;
import com.twitter.search.common.schema.thriftjava.ThriftIndetwitteringEventType;
import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.DataSerializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.core.earlybird.indetwitter.DocIDToTweetIDMapper;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentAtomicReader;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentData;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentWriter;
import com.twitter.search.core.earlybird.indetwitter.column.ColumnStrideFieldIndetwitter;
import com.twitter.search.core.earlybird.indetwitter.column.DocValuesUpdate;
import com.twitter.search.core.earlybird.indetwitter.etwittertensions.EarlybirdIndetwitterEtwittertensionsFactory;
import com.twitter.search.earlybird.EarlybirdIndetwitterConfig;
import com.twitter.search.earlybird.common.userupdates.UserTable;
import com.twitter.search.earlybird.document.TweetDocument;
import com.twitter.search.earlybird.etwitterception.FlushVersionMismatchEtwitterception;
import com.twitter.search.earlybird.partition.SearchIndetwitteringMetricSet;
import com.twitter.search.earlybird.partition.SegmentIndetwitterStats;
import com.twitter.search.earlybird.stats.EarlybirdSearcherStats;
import com.twitter.snowflake.id.SnowflakeId;

public class EarlybirdSegment {
  private static final Logger LOG = LoggerFactory.getLogger(EarlybirdSegment.class);
  private static final Logger UPDATES_ERRORS_LOG =
      LoggerFactory.getLogger(EarlybirdSegment.class.getName() + ".UpdatesErrors");
  private static final String SUCCESS_FILE = "EARLYBIRD_SUCCESS";
  private static final DateTimeFormatter HOURLY_COUNT_DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy_MM_dd_HH");

  @VisibleForTesting
  public static final String NUM_TWEETS_CREATED_AT_PATTERN = "num_tweets_%s_%s_created_at_%s";

  private static final String INVALID_FEATURE_UPDATES_DROPPED_PREFItwitter =
      "invalid_indetwitter_feature_update_dropped_";

  // The number of tweets not indetwittered because they have been previously indetwittered.
  private static final SearchCounter DUPLICATE_TWEET_SKIPPED_COUNTER =
      SearchCounter.etwitterport("duplicate_tweet_skipped");

  // The number of tweets that came out of order.
  private static final SearchCounter OUT_OF_ORDER_TWEET_COUNTER =
      SearchCounter.etwitterport("out_of_order_tweet");

  // The number partial updates dropped because the field could not be found in the schema.
  // This counter is incremented once per field rather than once per partial update event.
  // Note: caller may retry update, this counter will be incremented multiple times for same update.
  private static final SearchCounter INVALID_FIELDS_IN_PARTIAL_UPDATES =
      SearchCounter.etwitterport("invalid_fields_in_partial_updates");

  // The number partial updates dropped because the tweet id could not be found in the segment.
  // Note: caller may retry update, this counter will be incremented multiple times for same update.
  private static final SearchCounter PARTIAL_UPDATE_FOR_TWEET_NOT_IN_INDEtwitter =
      SearchCounter.etwitterport("partial_update_for_tweet_id_not_in_indetwitter");

  // The number of partial updates that were applied only partially, because the update could not
  // be applied for at least one of the fields.
  private static final SearchCounter PARTIAL_UPDATE_PARTIAL_FAILURE =
      SearchCounter.etwitterport("partial_update_partial_failure");

  // Both the indetwittering chain and the indetwitter writer are lazily initialized when adding docs for
  // the first time.
  private final AtomicReference<EarlybirdIndetwitterSegmentWriter> segmentWriterReference =
      new AtomicReference<>();

  // Stats from the PartitionIndetwitterer / SimpleSegmentIndetwitterer.
  private final SegmentIndetwitterStats indetwitterStats;
  private final String segmentName;
  private final int matwitterSegmentSize;
  private final long timeSliceID;
  private final AtomicReference<EarlybirdIndetwitterSegmentAtomicReader> luceneIndetwitterReader =
      new AtomicReference<>();
  private final Directory luceneDir;
  private final File luceneDirFile;
  private final EarlybirdIndetwitterConfig indetwitterConfig;
  private final List<Closeable> closableResources = Lists.newArrayList();
  private long lastInOrderTweetId = 0;

  private final EarlybirdIndetwitterEtwittertensionsFactory etwittertensionsFactory;
  private final SearchIndetwitteringMetricSet searchIndetwitteringMetricSet;
  private final EarlybirdSearcherStats searcherStats;

  private final Map<String, SearchCounter> indetwitteredTweetsCounters = Maps.newHashMap();
  private final PerFieldCounters perFieldCounters;
  private final Clock clock;

  @VisibleForTesting
  public volatile boolean appendedLuceneIndetwitter = false;

  public EarlybirdSegment(
      String segmentName,
      long timeSliceID,
      int matwitterSegmentSize,
      Directory luceneDir,
      EarlybirdIndetwitterConfig indetwitterConfig,
      SearchIndetwitteringMetricSet searchIndetwitteringMetricSet,
      EarlybirdSearcherStats searcherStats,
      Clock clock) {
    this.segmentName = segmentName;
    this.matwitterSegmentSize = matwitterSegmentSize;
    this.timeSliceID = timeSliceID;
    this.luceneDir = luceneDir;
    this.indetwitterConfig = indetwitterConfig;
    this.indetwitterStats = new SegmentIndetwitterStats();
    this.perFieldCounters = new PerFieldCounters();
    this.etwittertensionsFactory = new TweetSearchIndetwitterEtwittertensionsFactory();

    if (luceneDir != null && luceneDir instanceof FSDirectory) {
      // getDirectory() throws if the luceneDir is already closed.
      // To delete a directory, we need to close it first.
      // Obtain a reference to the File now, so we can delete it later.
      // See SEARCH-5281
      this.luceneDirFile = ((FSDirectory) luceneDir).getDirectory().toFile();
    } else {
      this.luceneDirFile = null;
    }
    this.searchIndetwitteringMetricSet = Preconditions.checkNotNull(searchIndetwitteringMetricSet);
    this.searcherStats = searcherStats;
    this.clock = clock;
  }

  @VisibleForTesting
  public Directory getLuceneDirectory() {
    return luceneDir;
  }

  public SegmentIndetwitterStats getIndetwitterStats() {
    return indetwitterStats;
  }

  /**
   * Returns the smallest tweet ID in this segment. If the segment is not loaded yet, or is empty,
   * DocIDToTweetIDMapper.ID_NOT_FOUND is returned (-1).
   *
   * @return The smallest tweet ID in this segment.
   */
  public long getLowestTweetId() {
    EarlybirdIndetwitterSegmentWriter segmentWriter = segmentWriterReference.get();
    if (segmentWriter == null) {
      return DocIDToTweetIDMapper.ID_NOT_FOUND;
    }

    DocIDToTweetIDMapper mapper = segmentWriter.getSegmentData().getDocIDToTweetIDMapper();
    int highestDocID = mapper.getPreviousDocID(Integer.MAtwitter_VALUE);
    return mapper.getTweetID(highestDocID);
  }

  /**
   * Returns the cardinality (size) sum of the cardinality of each
   * query cache set.
   */
  public long getQueryCachesCardinality() {
    EarlybirdIndetwitterSegmentWriter writer = getIndetwitterSegmentWriter();
    if (writer == null) {
      // The segment is not loaded yet, or the query caches for this segment are not built yet.
      return -1;
    }

    EarlybirdIndetwitterSegmentData earlybirdIndetwitterSegmentData = writer.getSegmentData();
    return earlybirdIndetwitterSegmentData.getQueryCachesCardinality();
  }

  public List<Pair<String, Long>> getQueryCachesData() {
    return getIndetwitterSegmentWriter().getSegmentData().getPerQueryCacheCardinality();
  }


  /**
   * Returns the highest tweet ID in this segment. If the segment is not loaded yet, or is empty,
   * DocIDToTweetIDMapper.ID_NOT_FOUND is returned (-1).
   *
   * @return The highest tweet ID in this segment.
   */
  public long getHighestTweetId() {
    EarlybirdIndetwitterSegmentWriter segmentWriter = segmentWriterReference.get();
    if (segmentWriter == null) {
      return DocIDToTweetIDMapper.ID_NOT_FOUND;
    }

    DocIDToTweetIDMapper mapper = segmentWriter.getSegmentData().getDocIDToTweetIDMapper();
    int lowestDocID = mapper.getNetwittertDocID(-1);
    return mapper.getTweetID(lowestDocID);
  }

  /**
   * Optimizes the underlying segment data.
   */
  public void optimizeIndetwitteres() throws IOEtwitterception {
    EarlybirdIndetwitterSegmentWriter unoptimizedWriter = segmentWriterReference.get();
    Preconditions.checkNotNull(unoptimizedWriter);

    unoptimizedWriter.forceMerge();
    unoptimizedWriter.close();

    // Optimize our own data structures in the indetwittering chain
    // In the archive this is pretty much a no-op.
    // The indetwitterWriter in writeableSegment should no longer be used and referenced, and
    // writeableSegment.writer can be garbage collected at this point.
    EarlybirdIndetwitterSegmentData optimized = indetwitterConfig.optimize(unoptimizedWriter.getSegmentData());
    resetSegmentWriterReference(newWriteableSegment(optimized), true);

    addSuccessFile();
  }

  /**
   * Returns a new, optimized, realtime segment, by copying the data in this segment.
   */
  public EarlybirdSegment makeOptimizedSegment() throws IOEtwitterception {
    EarlybirdIndetwitterSegmentWriter unoptimizedWriter = segmentWriterReference.get();
    Preconditions.checkNotNull(unoptimizedWriter);
    EarlybirdSegment optimizedSegment = new EarlybirdSegment(
        segmentName,
        timeSliceID,
        matwitterSegmentSize,
        luceneDir,
        indetwitterConfig,
        searchIndetwitteringMetricSet,
        searcherStats,
        clock);

    EarlybirdIndetwitterSegmentData optimizedSegmentData =
        indetwitterConfig.optimize(unoptimizedWriter.getSegmentData());
    LOG.info("Done optimizing, setting segment data");

    optimizedSegment.setSegmentData(
        optimizedSegmentData,
        indetwitterStats.getPartialUpdateCount(),
        indetwitterStats.getOutOfOrderUpdateCount());
    return optimizedSegment;
  }

  public String getSegmentName() {
    return segmentName;
  }

  public boolean isOptimized() {
    EarlybirdIndetwitterSegmentWriter segmentWriter = segmentWriterReference.get();
    return segmentWriter != null && segmentWriter.getSegmentData().isOptimized();
  }

  /**
   * Removes the document for the given tweet ID from this segment, if this segment contains a
   * document for this tweet ID.
   */
  public boolean delete(long tweetID) throws IOEtwitterception {
    EarlybirdIndetwitterSegmentWriter segmentWriter = segmentWriterReference.get();
    if (!hasDocument(tweetID)) {
      return false;
    }

    segmentWriter.deleteDocuments(new TweetIDQuery(tweetID));
    return true;
  }

  protected void updateDocValues(long tweetID, String field, DocValuesUpdate update)
      throws IOEtwitterception {
    EarlybirdIndetwitterSegmentWriter segmentWriter = segmentWriterReference.get();
    segmentWriter.updateDocValues(new TweetIDQuery(tweetID), field, update);
  }

  /**
   * Appends the Lucene indetwitter from another segment to this segment.
   */
  public void append(EarlybirdSegment otherSegment) throws IOEtwitterception {
    if (indetwitterConfig.isIndetwitterStoredOnDisk()) {
      EarlybirdIndetwitterSegmentWriter segmentWriter = segmentWriterReference.get();
      Preconditions.checkNotNull(segmentWriter);
      EarlybirdIndetwitterSegmentWriter otherSegmentWriter = otherSegment.segmentWriterReference.get();
      if (otherSegmentWriter != null) {
        otherSegmentWriter.close();
      }
      segmentWriter.addIndetwitteres(otherSegment.luceneDir);
      LOG.info("Calling forceMerge now after appending segment.");
      segmentWriter.forceMerge();
      appendedLuceneIndetwitter = true;
      LOG.info("Appended {} docs to segment {}. New doc count = {}",
               otherSegment.indetwitterStats.getStatusCount(), luceneDir.toString(),
               indetwitterStats.getStatusCount());

      indetwitterStats.setIndetwitterSizeOnDiskInBytes(getSegmentSizeOnDisk());
    }
  }

  /**
   * Only needed for the on disk archive.
   * Creates TwitterIndetwitterReader used for searching. This is shared by all Searchers.
   * This method also initializes the Lucene based mappers and CSF for the on disk archive.
   *
   * This method should be called after optimizing/loading a segment, but before the segment starts
   * to serve search queries.
   */
  public void warmSegment() throws IOEtwitterception {
    EarlybirdIndetwitterSegmentWriter segmentWriter = segmentWriterReference.get();
    Preconditions.checkNotNull(segmentWriter);

    // only need to pre-create reader and initialize mappers and CSF in the on disk archive cluster
    if (indetwitterConfig.isIndetwitterStoredOnDisk() && luceneIndetwitterReader.get() == null) {
      EarlybirdIndetwitterSegmentAtomicReader luceneAtomicReader =
          segmentWriter.getSegmentData().createAtomicReader();

      luceneIndetwitterReader.set(luceneAtomicReader);
      closableResources.add(luceneAtomicReader);
      closableResources.add(luceneDir);
    }
  }

  /**
   * Create a tweet indetwitter searcher on the segment.
   *
   * For production search session, the schema snapshot should be always passed in to make sure
   * that the schema usage inside scoring is consistent.
   *
   * For non-production usage, like one-off debugging search, you can use the function call without
   * the schema snapshot.
   */
  @Nullable
  public EarlybirdSingleSegmentSearcher getSearcher(
      UserTable userTable,
      ImmutableSchemaInterface schemaSnapshot) throws IOEtwitterception {
    EarlybirdIndetwitterSegmentWriter segmentWriter = segmentWriterReference.get();
    if (segmentWriter == null) {
      return null;
    }
    return new EarlybirdSingleSegmentSearcher(
        schemaSnapshot, getIndetwitterReader(segmentWriter), userTable, searcherStats, clock);
  }

  /**
   * Returns a new searcher for this segment.
   */
  @Nullable
  public EarlybirdSingleSegmentSearcher getSearcher(
      UserTable userTable) throws IOEtwitterception {
    EarlybirdIndetwitterSegmentWriter segmentWriter = segmentWriterReference.get();
    if (segmentWriter == null) {
      return null;
    }
    return new EarlybirdSingleSegmentSearcher(
        segmentWriter.getSegmentData().getSchema().getSchemaSnapshot(),
        getIndetwitterReader(segmentWriter),
        userTable,
        searcherStats,
        clock);
  }

  /**
   * Returns a new reader for this segment.
   */
  @Nullable
  public EarlybirdIndetwitterSegmentAtomicReader getIndetwitterReader() throws IOEtwitterception {
    EarlybirdIndetwitterSegmentWriter segmentWriter = segmentWriterReference.get();
    if (segmentWriter == null) {
      return null;
    }
    return getIndetwitterReader(segmentWriter);
  }

  private EarlybirdIndetwitterSegmentAtomicReader getIndetwitterReader(
      EarlybirdIndetwitterSegmentWriter segmentWriter
  ) throws IOEtwitterception {
    EarlybirdIndetwitterSegmentAtomicReader reader = luceneIndetwitterReader.get();
    if (reader != null) {
      return reader;
    }
    Preconditions.checkState(!indetwitterConfig.isIndetwitterStoredOnDisk());

    // Realtime EB mode.
    return segmentWriter.getSegmentData().createAtomicReader();
  }

  /**
   * Gets matwitter tweet id in this segment.
   *
   * @return the tweet id or -1 if not found.
   */
  public long getMatwitterTweetId() {
    EarlybirdIndetwitterSegmentWriter segmentWriter = segmentWriterReference.get();
    if (segmentWriter == null) {
      return -1;
    } else {
      TweetIDMapper tweetIDMapper =
          (TweetIDMapper) segmentWriter.getSegmentData().getDocIDToTweetIDMapper();
      return tweetIDMapper.getMatwitterTweetID();
    }
  }

  private EarlybirdIndetwitterSegmentWriter newWriteableSegment(EarlybirdIndetwitterSegmentData segmentData)
      throws IOEtwitterception {
    EarlybirdIndetwitterSegmentWriter old = segmentWriterReference.get();
    if (old != null) {
      old.close();
    }

    LOG.info("Creating new segment writer for {} on {}", segmentName, luceneDir);
    IndetwitterWriterConfig indetwitterWriterConfig = indetwitterConfig.newIndetwitterWriterConfig();
    return segmentData.createEarlybirdIndetwitterSegmentWriter(indetwitterWriterConfig);
  }

  private void resetSegmentWriterReference(
      EarlybirdIndetwitterSegmentWriter segmentWriter, boolean previousSegmentWriterAllowed) {
    EarlybirdIndetwitterSegmentWriter previousSegmentWriter =
        segmentWriterReference.getAndSet(segmentWriter);
    if (!previousSegmentWriterAllowed) {
      Preconditions.checkState(
          previousSegmentWriter == null,
          "A previous segment writer must have been set for segment " + segmentName);
    }

    // Reset the stats for the number of indetwittered tweets per hour and recompute them.
    // See SEARCH-23619
    for (SearchCounter indetwitteredTweetsCounter : indetwitteredTweetsCounters.values()) {
      indetwitteredTweetsCounter.reset();
    }

    if (segmentWriter != null) {
      indetwitterStats.setSegmentData(segmentWriter.getSegmentData());

      if (indetwitterConfig.getCluster() != EarlybirdCluster.FULL_ARCHIVE) {
        initHourlyTweetCounts(segmentWriterReference.get());
      }
    } else {
      // It's important to unset segment data so that there are no references to it
      // and it can be GC-ed.
      indetwitterStats.unsetSegmentDataAndSaveCounts();
    }
  }

  /**
   * Add a document if it is not already in segment.
   */
  public void addDocument(TweetDocument doc) throws IOEtwitterception {
    if (indetwitterConfig.isIndetwitterStoredOnDisk()) {
      addDocumentToArchiveSegment(doc);
    } else {
      addDocumentToRealtimeSegment(doc);
    }
  }

  private void addDocumentToArchiveSegment(TweetDocument doc) throws IOEtwitterception {
    // For archive, the document id should come in order, to drop duplicates, only need to
    // compare current id with last one.
    long tweetId = doc.getTweetID();
    if (tweetId == lastInOrderTweetId) {
      LOG.warn("Dropped duplicate tweet for archive: {}", tweetId);
      DUPLICATE_TWEET_SKIPPED_COUNTER.increment();
      return;
    }

    if (tweetId > lastInOrderTweetId && lastInOrderTweetId != 0) {
      // Archive orders document from newest to oldest, so this shouldn't happen
      LOG.warn("Encountered out-of-order tweet for archive: {}", tweetId);
      OUT_OF_ORDER_TWEET_COUNTER.increment();
    } else {
      lastInOrderTweetId = tweetId;
    }

    addDocumentInternal(doc);
  }

  private void addDocumentToRealtimeSegment(TweetDocument doc) throws IOEtwitterception {
    long tweetId = doc.getTweetID();
    boolean outOfOrder = tweetId <= lastInOrderTweetId;
    if (outOfOrder) {
      OUT_OF_ORDER_TWEET_COUNTER.increment();
    } else {
      lastInOrderTweetId = tweetId;
    }

    // We only need to call hasDocument() for out-of-order tweets.
    if (outOfOrder && hasDocument(tweetId)) {
      // We do get duplicates sometimes so you'll see some amount of these.
      DUPLICATE_TWEET_SKIPPED_COUNTER.increment();
    } else {
      addDocumentInternal(doc);
      incrementHourlyTweetCount(doc.getTweetID());
    }
  }

  private void addDocumentInternal(TweetDocument tweetDocument) throws IOEtwitterception {
    Document doc = tweetDocument.getDocument();

    // Never write blank documents into the indetwitter.
    if (doc == null || doc.getFields() == null || doc.getFields().size() == 0) {
      return;
    }

    EarlybirdIndetwitterSegmentWriter segmentWriter = segmentWriterReference.get();
    if (segmentWriter == null) {
      EarlybirdIndetwitterSegmentData segmentData = indetwitterConfig.newSegmentData(
          matwitterSegmentSize,
          timeSliceID,
          luceneDir,
          etwittertensionsFactory);
      segmentWriter = newWriteableSegment(segmentData);
      resetSegmentWriterReference(segmentWriter, false);
    }

    Preconditions.checkState(segmentWriter.numDocs() < matwitterSegmentSize,
                             "Reached matwitter segment size %s", matwitterSegmentSize);

    IndetwitterableField[] featuresField = doc.getFields(
        EarlybirdFieldConstants.ENCODED_TWEET_FEATURES_FIELD_NAME);
    Preconditions.checkState(featuresField.length == 1,
            "featuresField.length should be 1, but is %s", featuresField.length);

    // We require the createdAt field to be set so we can properly filter tweets based on time.
    IndetwitterableField[] createdAt =
        doc.getFields(EarlybirdFieldConstant.CREATED_AT_FIELD.getFieldName());
    Preconditions.checkState(createdAt.length == 1);

    EarlybirdEncodedFeatures features = EarlybirdEncodedFeaturesUtil.fromBytes(
        indetwitterConfig.getSchema().getSchemaSnapshot(),
        EarlybirdFieldConstant.ENCODED_TWEET_FEATURES_FIELD,
        featuresField[0].binaryValue().bytes,
        featuresField[0].binaryValue().offset);
    boolean currentDocIsOffensive = features.isFlagSet(EarlybirdFieldConstant.IS_OFFENSIVE_FLAG);
    perFieldCounters.increment(ThriftIndetwitteringEventType.INSERT, doc);
    segmentWriter.addTweet(doc, tweetDocument.getTweetID(), currentDocIsOffensive);
  }

  private void incrementHourlyTweetCount(long tweetId) {
    // SEARCH-23619, We won't attempt to increment the count for pre-snowflake IDs, since
    // etwittertracting an etwitteract create time is pretty tricky at this point, and the stat is mostly
    // useful for checking realtime tweet indetwittering.
    if (SnowflakeId.isSnowflakeId(tweetId)) {
      long tweetCreateTime = SnowflakeId.unitwitterTimeMillisFromId(tweetId);
      String tweetHour = HOURLY_COUNT_DATE_TIME_FORMATTER.format(
          ZonedDateTime.ofInstant(Instant.ofEpochMilli(tweetCreateTime), ZoneOffset.UTC));

      String segmentOptimizedSuffitwitter = isOptimized() ? "optimized" : "unoptimized";
      SearchCounter indetwitteredTweetsCounter = indetwitteredTweetsCounters.computeIfAbsent(
          tweetHour + "_" + segmentOptimizedSuffitwitter,
          (tweetHourKey) -> SearchCounter.etwitterport(String.format(
              NUM_TWEETS_CREATED_AT_PATTERN, segmentOptimizedSuffitwitter, segmentName, tweetHour)));
      indetwitteredTweetsCounter.increment();
    }
  }

  private void initHourlyTweetCounts(EarlybirdIndetwitterSegmentWriter segmentWriter) {
    DocIDToTweetIDMapper mapper = segmentWriter.getSegmentData().getDocIDToTweetIDMapper();
    int docId = Integer.MIN_VALUE;
    while ((docId = mapper.getNetwittertDocID(docId)) != DocIDToTweetIDMapper.ID_NOT_FOUND) {
      incrementHourlyTweetCount(mapper.getTweetID(docId));
    }
  }

  /**
   * Adds the given document for the given tweet ID to the segment, potentially out of order.
   */
  public boolean appendOutOfOrder(Document doc, long tweetID) throws IOEtwitterception {
    // Never write blank documents into the indetwitter.
    if (doc == null || doc.getFields() == null || doc.getFields().size() == 0) {
      return false;
    }

    EarlybirdIndetwitterSegmentWriter segmentWriter = segmentWriterReference.get();
    if (segmentWriter == null) {
      logAppendOutOfOrderFailure(tweetID, doc, "segment is null");
      return false;
    }

    if (!indetwitterConfig.supportOutOfOrderIndetwittering()) {
      logAppendOutOfOrderFailure(tweetID, doc, "out of order indetwittering not supported");
      return false;
    }

    if (!hasDocument(tweetID)) {
      logAppendOutOfOrderFailure(tweetID, doc, "tweet ID indetwitter lookup failed");
      searchIndetwitteringMetricSet.updateOnMissingTweetCounter.increment();
      perFieldCounters.incrementTweetNotInIndetwitter(ThriftIndetwitteringEventType.OUT_OF_ORDER_APPEND, doc);
      return false;
    }

    perFieldCounters.increment(ThriftIndetwitteringEventType.OUT_OF_ORDER_APPEND, doc);
    segmentWriter.appendOutOfOrder(new TweetIDQuery(tweetID), doc);
    indetwitterStats.incrementOutOfOrderUpdateCount();
    return true;
  }

  private void logAppendOutOfOrderFailure(long tweetID, Document doc, String reason) {
    UPDATES_ERRORS_LOG.debug(
        "appendOutOfOrder() failed to apply update document with hash {} on tweet ID {}: {}",
        Objects.hashCode(doc), tweetID, reason);
  }

  /**
   * Determines if this segment contains the given tweet ID.
   */
  public boolean hasDocument(long tweetID) throws IOEtwitterception {
    EarlybirdIndetwitterSegmentWriter segmentWriter = segmentWriterReference.get();
    if (segmentWriter == null) {
      return false;
    }

    return segmentWriter.getSegmentData().getDocIDToTweetIDMapper().getDocID(tweetID)
        != DocIDToTweetIDMapper.ID_NOT_FOUND;
  }

  private static final String VERSION_PROP_NAME = "version";
  private static final String VERSION_DESC_PROP_NAME = "versionDescription";
  private static final String PARTIAL_UPDATES_COUNT = "partialUpdatesCount";
  private static final String OUT_OF_ORDER_UPDATES_COUNT = "outOfOrderUpdatesCount";

  private void checkIfFlushedDataVersionMatchesEtwitterpected(FlushInfo flushInfo) throws IOEtwitterception {
    int etwitterpectedVersionNumber = indetwitterConfig.getSchema().getMajorVersionNumber();
    String etwitterpectedVersionDesc = indetwitterConfig.getSchema().getVersionDescription();
    int version = flushInfo.getIntProperty(VERSION_PROP_NAME);
    final String versionDesc = flushInfo.getStringProperty(VERSION_DESC_PROP_NAME);

    if (version != etwitterpectedVersionNumber) {
      throw new FlushVersionMismatchEtwitterception("Flushed version mismatch. Etwitterpected: "
          + etwitterpectedVersionNumber + ", but was: " + version);
    }

    if (!etwitterpectedVersionDesc.equals(versionDesc)) {
      final String message = "Flush version " + etwitterpectedVersionNumber + " is ambiguous"
          + "  Etwitterpected: " + etwitterpectedVersionDesc
          + "  Found:  "  + versionDesc
          + "  Please clean up segments with bad flush version from HDFS and Earlybird local disk.";
      throw new FlushVersionMismatchEtwitterception(message);
    }
  }

  /**
   * Loads the segment data and properties from the given deserializer and flush info.
   *
   * @param in The deserializer from which the segment's data will be read.
   * @param flushInfo The flush info from which the segment's properties will be read.
   */
  public void load(DataDeserializer in, FlushInfo flushInfo) throws IOEtwitterception {
    checkIfFlushedDataVersionMatchesEtwitterpected(flushInfo);

    int partialUpdatesCount = flushInfo.getIntProperty(PARTIAL_UPDATES_COUNT);
    int outOfOrderUpdatesCount = flushInfo.getIntProperty(OUT_OF_ORDER_UPDATES_COUNT);

    EarlybirdIndetwitterSegmentData loadedSegmentData = indetwitterConfig.loadSegmentData(
        flushInfo, in, luceneDir, etwittertensionsFactory);

    setSegmentData(loadedSegmentData, partialUpdatesCount, outOfOrderUpdatesCount);
  }

  /**
   * Update the data backing this EarlyirdSegment.
   */
  public void setSegmentData(
      EarlybirdIndetwitterSegmentData segmentData,
      int partialUpdatesCount,
      int outOfOrderUpdatesCount) throws IOEtwitterception {
    resetSegmentWriterReference(newWriteableSegment(segmentData), false);
    try {
      warmSegment();
    } catch (IOEtwitterception e) {
      LOG.error("Failed to create IndetwitterReader for segment {}. Will destroy unreadable segment.",
          segmentName, e);
      destroyImmediately();
      throw e;
    }

    LOG.info("Starting segment {} with {} partial updates, {} out of order updates and {} deletes.",
        segmentName, partialUpdatesCount, outOfOrderUpdatesCount, indetwitterStats.getDeleteCount());
    indetwitterStats.setPartialUpdateCount(partialUpdatesCount);
    indetwitterStats.setOutOfOrderUpdateCount(outOfOrderUpdatesCount);
    indetwitterStats.setIndetwitterSizeOnDiskInBytes(getSegmentSizeOnDisk());
  }

  /**
   * Flushes the this segment's properties to the given FlushInfo instance, and this segment's data
   * to the given DataSerializer instance.
   *
   * @param flushInfo The FlushInfo instance where all segment properties should be added.
   * @param out The serializer to which all segment data should be flushed.
   */
  public void flush(FlushInfo flushInfo, DataSerializer out) throws IOEtwitterception {
    flushInfo.addIntProperty(VERSION_PROP_NAME, indetwitterConfig.getSchema().getMajorVersionNumber());
    flushInfo.addStringProperty(VERSION_DESC_PROP_NAME,
        indetwitterConfig.getSchema().getVersionDescription());
    flushInfo.addIntProperty(PARTIAL_UPDATES_COUNT, indetwitterStats.getPartialUpdateCount());
    flushInfo.addIntProperty(OUT_OF_ORDER_UPDATES_COUNT, indetwitterStats.getOutOfOrderUpdateCount());
    if (segmentWriterReference.get() == null) {
      LOG.warn("Segment writer is null. flushInfo: {}", flushInfo);
    } else if (segmentWriterReference.get().getSegmentData() == null) {
      LOG.warn("Segment data is null. segment writer: {}, flushInfo: {}",
          segmentWriterReference.get(), flushInfo);
    }
    segmentWriterReference.get().getSegmentData().flushSegment(flushInfo, out);
    indetwitterStats.setIndetwitterSizeOnDiskInBytes(getSegmentSizeOnDisk());
  }

  /**
   * Check to see if this segment can be loaded from an on-disk indetwitter, and load it if it can be.
   *
   * This should only be applicable to the current segment for the on-disk archive. It's not
   * fully flushed until it's full, but we do have a lucene indetwitter on local disk which can be
   * used at startup (rather than have to reindetwitter all the current timeslice documents again).
   *
   * If loaded, the indetwitter reader will be pre-created, and the segment will be marked as
   * optimized.
   *
   * If the indetwitter directory etwitterists but it cannot be loaded, the indetwitter directory will be deleted.
   *
   * @return true if the indetwitter etwitterists on disk, and was loaded.
   */
  public boolean tryToLoadEtwitteristingIndetwitter() throws IOEtwitterception {
    Preconditions.checkState(segmentWriterReference.get() == null);
    if (indetwitterConfig.isIndetwitterStoredOnDisk()) {
      if (DirectoryReader.indetwitterEtwitterists(luceneDir) && checkSuccessFile()) {
        LOG.info("Indetwitter directory already etwitterists for {} at {}", segmentName, luceneDir);

        // set the optimized flag, since we don't need to optimize any more, and pre-create
        // the indetwitter reader (for the on-disk indetwitter optimize() is a noop that just sets the
        // optimized flag).
        EarlybirdIndetwitterSegmentData earlybirdIndetwitterSegmentData = indetwitterConfig.newSegmentData(
            matwitterSegmentSize,
            timeSliceID,
            luceneDir,
            etwittertensionsFactory);
        EarlybirdIndetwitterSegmentData optimizedEarlybirdIndetwitterSegmentData =
            indetwitterConfig.optimize(earlybirdIndetwitterSegmentData);
        resetSegmentWriterReference(newWriteableSegment(optimizedEarlybirdIndetwitterSegmentData), false);

        warmSegment();

        LOG.info("Used etwitteristing lucene indetwitter for {} with {} documents",
                 segmentName, indetwitterStats.getStatusCount());

        indetwitterStats.setIndetwitterSizeOnDiskInBytes(getSegmentSizeOnDisk());

        return true;
      } else {
        // Check if there is an etwitteristing lucene dir without a SUCCESS file on disk.
        // If so, we will remove it and reindetwitter from scratch.
        if (moveFSDirectoryIfEtwitterists(luceneDir)) {
          // Throw here to be cleaned up and retried by SimpleSegmentIndetwitterer.
          throw new IOEtwitterception("Found invalid etwitteristing lucene directory at: " + luceneDir);
        }
      }
    }
    return false;
  }

  /**
   * Partially updates a document with the field value(s) specified by event.
   * Returns true if all writes were successful and false if one or more writes fail or if
   * tweet id isn't found in the segment.
   */
  public boolean applyPartialUpdate(ThriftIndetwitteringEvent event) throws IOEtwitterception {
    Preconditions.checkArgument(event.getEventType() == ThriftIndetwitteringEventType.PARTIAL_UPDATE);
    Preconditions.checkArgument(event.isSetUid());
    Preconditions.checkArgument(!ThriftDocumentUtil.hasDuplicateFields(event.getDocument()));
    ImmutableSchemaInterface schemaSnapshot = indetwitterConfig.getSchema().getSchemaSnapshot();

    long tweetId = event.getUid();
    ThriftDocument doc = event.getDocument();

    if (!hasDocument(tweetId)) {
      // no need to attempt field writes, fail early
      PARTIAL_UPDATE_FOR_TWEET_NOT_IN_INDEtwitter.increment();
       perFieldCounters.incrementTweetNotInIndetwitter(
           ThriftIndetwitteringEventType.PARTIAL_UPDATE, doc);
      return false;
    }

    int invalidFields = 0;
    for (ThriftField field : doc.getFields()) {
      String featureName = schemaSnapshot.getFieldName(field.getFieldConfigId());
      FeatureConfiguration featureConfig =
          schemaSnapshot.getFeatureConfigurationByName(featureName);
      if (featureConfig == null) {
        INVALID_FIELDS_IN_PARTIAL_UPDATES.increment();
        invalidFields++;
        continue;
      }

      perFieldCounters.increment(ThriftIndetwitteringEventType.PARTIAL_UPDATE, featureName);

      updateDocValues(
          tweetId,
          featureName,
          (docValues, docID) -> updateFeatureValue(docID, featureConfig, docValues, field));
    }

    if (invalidFields > 0 && invalidFields != doc.getFieldsSize()) {
      PARTIAL_UPDATE_PARTIAL_FAILURE.increment();
    }

    if (invalidFields == 0) {
      indetwitterStats.incrementPartialUpdateCount();
    } else {
      UPDATES_ERRORS_LOG.warn("Failed to apply update for tweetID {}, found {} invalid fields: {}",
          tweetId, invalidFields, event);
    }

    return invalidFields == 0;
  }

  @VisibleForTesting
  static void updateFeatureValue(int docID,
                                 FeatureConfiguration featureConfig,
                                 ColumnStrideFieldIndetwitter docValues,
                                 ThriftField updateField) {
    int oldValue = Math.toIntEtwitteract(docValues.get(docID));
    int newValue = updateField.getFieldData().getIntValue();

    if (!featureConfig.validateFeatureUpdate(oldValue, newValue)) {
      // Counter values can only increase
      SearchCounter.etwitterport(
          INVALID_FEATURE_UPDATES_DROPPED_PREFItwitter + featureConfig.getName()).increment();
    } else {
      docValues.setValue(docID, newValue);
    }
  }

  /**
   * Checks if the provided directory etwitterists and is not empty,
   * and if it does moves it out to a diff directory for later inspection.
   * @param luceneDirectory the dir to move if it etwitterists.
   * @return true iff we found an etwitteristing directory.
   */
  private static boolean moveFSDirectoryIfEtwitterists(Directory luceneDirectory) {
    Preconditions.checkState(luceneDirectory instanceof FSDirectory);
    File directory = ((FSDirectory) luceneDirectory).getDirectory().toFile();
    if (directory != null && directory.etwitterists() && directory.list().length > 0) {
      // Save the bad lucene indetwitter by moving it out, for later inspection.
      File movedDir = new File(directory.getParent(),
          directory.getName() + ".failed." + System.currentTimeMillis());
      LOG.warn("Moving etwitteristing non-successful indetwitter for {} from {} to {}",
               luceneDirectory, directory, movedDir);
      boolean success = directory.renameTo(movedDir);
      if (!success) {
        LOG.warn("Unable to rename non-successful indetwitter: {}", luceneDirectory);
      }
      return true;
    }
    return false;
  }

  /**
   * For the on-disk archive, if we were able to successfully merge and flush the Lucene indetwitter to
   * disk, we mark it etwitterplicitly with a SUCCESS file, so that it can be safely reused.
   */
  private void addSuccessFile() throws IOEtwitterception {
    if (indetwitterConfig.isIndetwitterStoredOnDisk()) {
      IndetwitterOutput successFile = luceneDir.createOutput(SUCCESS_FILE, IOContetwittert.DEFAULT);
      successFile.close();
    }
  }

  /**
   * Returns the current number of documents in this segment.
   */
  public int getNumDocs() throws IOEtwitterception {
    return indetwitterStats.getStatusCount();
  }

  /**
   * Reclaim resources used by this segment (E.g. closing lucene indetwitter reader).
   * Resources will be reclaimed within the calling thread with no delay.
   */
  public void destroyImmediately() {
    try {
      closeSegmentWriter();
      maybeDeleteSegmentOnDisk();
      unloadSegmentFromMemory();
    } finally {
      indetwitterConfig.getResourceCloser().closeResourcesImmediately(closableResources);
    }
  }

  /**
   * Close the in-memory resources belonging to this segment. This should allow the in-memory
   * segment data to be garbage collected. After closing, the segment is not writable.
   */
  public void close() {
    if (segmentWriterReference.get() == null) {
      LOG.info("Segment {} already closed.", segmentName);
      return;
    }

    LOG.info("Closing segment {}.", segmentName);
    try {
      closeSegmentWriter();
      unloadSegmentFromMemory();
    } finally {
      indetwitterConfig.getResourceCloser().closeResourcesImmediately(closableResources);
    }
  }

  private void closeSegmentWriter() {
    EarlybirdIndetwitterSegmentWriter segmentWriter = segmentWriterReference.get();
    if (segmentWriter != null) {
      closableResources.add(() -> {
          LOG.info("Closing writer for segment: {}", segmentName);
          segmentWriter.close();
      });
    }
  }

  private void maybeDeleteSegmentOnDisk() {
    if (indetwitterConfig.isIndetwitterStoredOnDisk()) {
      Preconditions.checkState(
          luceneDir instanceof FSDirectory,
          "On-disk indetwitteres should have an underlying directory that we can close and remove.");
      closableResources.add(luceneDir);

      if (luceneDirFile != null && luceneDirFile.etwitterists()) {
        closableResources.add(new Closeable() {
          @Override
          public void close() throws IOEtwitterception {
            FileUtils.deleteDirectory(luceneDirFile);
          }

          @Override
          public String toString() {
            return "delete {" + luceneDirFile + "}";
          }
        });
      }
    }
  }

  private void unloadSegmentFromMemory() {
    // Make sure we don't retain a reference to the IndetwitterWriter or SegmentData.
    resetSegmentWriterReference(null, true);
  }

  private long getSegmentSizeOnDisk() throws IOEtwitterception {
    searchIndetwitteringMetricSet.segmentSizeCheckCount.increment();

    long totalSize = 0;
    if (luceneDir != null) {
      for (String file : luceneDir.listAll()) {
        totalSize += luceneDir.fileLength(file);
      }
    }
    return totalSize;
  }

  //////////////////////////
  // for unit tests only
  //////////////////////////

  public EarlybirdIndetwitterConfig getEarlybirdIndetwitterConfig() {
    return indetwitterConfig;
  }

  @VisibleForTesting
  public boolean checkSuccessFile() {
    return new File(luceneDirFile, SUCCESS_FILE).etwitterists();
  }

  @VisibleForTesting
  EarlybirdIndetwitterSegmentWriter getIndetwitterSegmentWriter() {
    return segmentWriterReference.get();
  }

  // Helper class to encapsulate counter tables, patterns and various ways to increment
  private class PerFieldCounters {
    // The number of update/append events for each field in the schema.
    private static final String PER_FIELD_EVENTS_COUNTER_PATTERN = "%s_for_field_%s";
    // The number of dropped update/append events for each field due to tweetId not found
    private static final String TWEET_NOT_IN_INDEtwitter_PER_FIELD_EVENTS_COUNTER_PATTERN =
        "%s_for_tweet_id_not_in_indetwitter_for_field_%s";
    private final Table<ThriftIndetwitteringEventType, String, SearchCounter> perFieldTable =
        HashBasedTable.create();
    private final Table<ThriftIndetwitteringEventType, String, SearchCounter> notInIndetwitterPerFieldTable =
        HashBasedTable.create();

    public void increment(
        ThriftIndetwitteringEventType eventType, ThriftDocument doc) {
      ImmutableSchemaInterface schemaSnapshot = indetwitterConfig.getSchema().getSchemaSnapshot();
      for (ThriftField field : doc.getFields()) {
        String fieldName = schemaSnapshot.getFieldName(field.getFieldConfigId());
        incrementForPattern(
            eventType, fieldName, perFieldTable, PER_FIELD_EVENTS_COUNTER_PATTERN);
      }
    }

    public void incrementTweetNotInIndetwitter(
        ThriftIndetwitteringEventType eventType, ThriftDocument doc) {
      ImmutableSchemaInterface schemaSnapshot = indetwitterConfig.getSchema().getSchemaSnapshot();
      for (ThriftField field : doc.getFields()) {
        String fieldName = schemaSnapshot.getFieldName(field.getFieldConfigId());
        incrementForPattern(
            eventType, fieldName, notInIndetwitterPerFieldTable,
            TWEET_NOT_IN_INDEtwitter_PER_FIELD_EVENTS_COUNTER_PATTERN);
      }
    }

    public void increment(ThriftIndetwitteringEventType eventType, Document doc) {
      for (IndetwitterableField field : doc.getFields()) {
        incrementForPattern(
            eventType, field.name(),
            perFieldTable, PER_FIELD_EVENTS_COUNTER_PATTERN);
      }
    }

    public void increment(ThriftIndetwitteringEventType eventType, String fieldName) {
      incrementForPattern(eventType, fieldName, perFieldTable, PER_FIELD_EVENTS_COUNTER_PATTERN);
    }

    public void incrementTweetNotInIndetwitter(ThriftIndetwitteringEventType eventType, Document doc) {
      for (IndetwitterableField field : doc.getFields()) {
        incrementForPattern(
            eventType, field.name(),
            notInIndetwitterPerFieldTable,
            TWEET_NOT_IN_INDEtwitter_PER_FIELD_EVENTS_COUNTER_PATTERN);
      }
    }

    private void incrementForPattern(
        ThriftIndetwitteringEventType eventType, String fieldName,
        Table<ThriftIndetwitteringEventType, String, SearchCounter> counterTable, String pattern) {

      SearchCounter stat;
      if (counterTable.contains(eventType, fieldName)) {
        stat = counterTable.get(eventType, fieldName);
      } else {
        stat = SearchCounter.etwitterport(String.format(pattern, eventType, fieldName).toLowerCase());
        counterTable.put(eventType, fieldName, stat);
      }
      stat.increment();
    }
  }
}

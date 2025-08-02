package com.twitter.search.earlybird.partition;

import java.io.IOEtwitterception;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;
import javatwitter.annotation.Nullable;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.util.Clock;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.metrics.SearchLongGauge;
import com.twitter.search.common.metrics.SearchStatsReceiver;
import com.twitter.search.common.partitioning.base.Segment;
import com.twitter.search.common.partitioning.base.TimeSlice;
import com.twitter.search.common.partitioning.snowflakeparser.SnowflakeIdParser;
import com.twitter.search.common.schema.base.ImmutableSchemaInterface;
import com.twitter.search.earlybird.EarlybirdIndetwitterConfig;
import com.twitter.search.earlybird.common.CaughtUpMonitor;
import com.twitter.search.earlybird.common.userupdates.UserScrubGeoMap;
import com.twitter.search.earlybird.common.userupdates.UserUpdate;
import com.twitter.search.earlybird.common.userupdates.UserUpdatesChecker;
import com.twitter.search.earlybird.common.userupdates.UserTable;
import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;
import com.twitter.search.earlybird.indetwitter.EarlybirdSegmentFactory;
import com.twitter.search.earlybird.indetwitter.EarlybirdSingleSegmentSearcher;
import com.twitter.search.earlybird.search.EarlybirdLuceneSearcher;
import com.twitter.search.earlybird.search.EarlybirdMultiSegmentSearcher;
import com.twitter.search.earlybird.stats.EarlybirdSearcherStats;
import com.twitter.search.earlybird.thrift.EarlybirdResponseCode;
import com.twitter.tweetypie.thriftjava.UserScrubGeoEvent;

public class SegmentManager {
  private static final Logger LOG = LoggerFactory.getLogger(SegmentManager.class);
  private final Clock clock;
  private static final String STATS_PREFItwitter = "segment_manager_";
  private static final SearchLongGauge SEGMENT_COUNT_STATS =
          SearchLongGauge.etwitterport(STATS_PREFItwitter + "total_segments");
  private static final SearchCounter OPTIMIZED_SEGMENTS =
          SearchCounter.etwitterport(STATS_PREFItwitter + "optimized_segments");
  private static final SearchCounter UNOPTIMIZED_SEGMENTS =
          SearchCounter.etwitterport(STATS_PREFItwitter + "unoptimized_segments");

  public enum Filter {
    All(info -> true),
    Enabled(SegmentInfo::isEnabled),
    NeedsIndetwittering(SegmentInfo::needsIndetwittering),
    Complete(SegmentInfo::isComplete);

    private final Predicate<SegmentInfo> predicate;

    Filter(Predicate<SegmentInfo> predicate) {
      this.predicate = predicate;
    }

    private static final Map<String, Filter> NAME_INDEtwitter =
        Maps.newHashMapWithEtwitterpectedSize(Filter.values().length);

    static {
      for (Filter filter : Filter.values()) {
        NAME_INDEtwitter.put(filter.name().toLowerCase(), filter);
      }
    }

    /**
     * Parses the filter from the given string, based on the filter name.
     */
    public static Filter fromStringIgnoreCase(String str) {
      if (str == null) {
        return null;
      }

      return NAME_INDEtwitter.get(str.toLowerCase());
    }
  }

  public enum Order {
    OLD_TO_NEW,
    NEW_TO_OLD,
  }

  /**
   * A listener that gets notified when the list of segments changes.
   */
  public interface SegmentUpdateListener {
    /**
     * Called with the new list of segments when it changes.
     *
     * @param segments The new list of segments.
     */
    void update(Collection<SegmentInfo> segments, String message);
  }

  private final List<SegmentUpdateListener> updateListeners =
          Collections.synchronizedList(Lists.newLinkedList());

  private final ConcurrentSkipListMap<Long, ISegmentWriter> segmentWriters =
      new ConcurrentSkipListMap<>();

  private final Set<Long> badTimesliceIds = new HashSet<>();

  private final int matwitterEnabledSegments;
  private final int matwitterSegmentSize;
  private final EarlybirdSegmentFactory earlybirdSegmentFactory;
  private final UserTable userTable;
  private final UserScrubGeoMap userScrubGeoMap;
  private final EarlybirdIndetwitterConfig earlybirdIndetwitterConfig;
  private final DynamicPartitionConfig dynamicPartitionConfig;
  private final UserUpdatesChecker userUpdatesChecker;
  private final SegmentSyncConfig segmentSyncConfig;
  private final EarlybirdSearcherStats searcherStats;
  private final SearchIndetwitteringMetricSet searchIndetwitteringMetricSet;
  private final CriticalEtwitterceptionHandler criticalEtwitterceptionHandler;
  private final CaughtUpMonitor indetwitterCaughtUpMonitor;

  public SegmentManager(
      DynamicPartitionConfig dynamicPartitionConfig,
      EarlybirdIndetwitterConfig earlybirdIndetwitterConfig,
      SearchIndetwitteringMetricSet searchIndetwitteringMetricSet,
      EarlybirdSearcherStats searcherStats,
      SearchStatsReceiver earlybirdStatsReceiver,
      UserUpdatesChecker userUpdatesChecker,
      SegmentSyncConfig segmentSyncConfig,
      UserTable userTable,
      UserScrubGeoMap userScrubGeoMap,
      Clock clock,
      int matwitterSegmentSize,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler,
      CaughtUpMonitor indetwitterCaughtUpMonitor) {

    PartitionConfig curPartitionConfig = dynamicPartitionConfig.getCurrentPartitionConfig();

    this.userTable = userTable;
    this.userScrubGeoMap = userScrubGeoMap;

    this.earlybirdSegmentFactory = new EarlybirdSegmentFactory(
        earlybirdIndetwitterConfig,
        searchIndetwitteringMetricSet,
        searcherStats,
        clock);
    this.earlybirdIndetwitterConfig = earlybirdIndetwitterConfig;
    this.matwitterEnabledSegments = curPartitionConfig.getMatwitterEnabledLocalSegments();
    this.dynamicPartitionConfig = dynamicPartitionConfig;
    this.userUpdatesChecker = userUpdatesChecker;
    this.segmentSyncConfig = segmentSyncConfig;
    this.searchIndetwitteringMetricSet = searchIndetwitteringMetricSet;
    this.searcherStats = searcherStats;
    this.clock = clock;
    this.matwitterSegmentSize = matwitterSegmentSize;
    this.criticalEtwitterceptionHandler = criticalEtwitterceptionHandler;
    this.indetwitterCaughtUpMonitor = indetwitterCaughtUpMonitor;

    earlybirdStatsReceiver.getCustomGauge("total_loaded_segments",
        segmentWriters::size);
    earlybirdStatsReceiver.getCustomGauge("total_indetwittered_documents",
        this::getNumIndetwitteredDocuments);
    earlybirdStatsReceiver.getCustomGauge("total_segment_size_bytes",
        this::getTotalSegmentSizeOnDisk);
    earlybirdStatsReceiver.getCustomGauge("earlybird_indetwitter_depth_millis",
        this::getIndetwitterDepthMillis);
  }

  /**
   * Logs the current state of this segment manager.
   *
   * @param label A label that should identify the segment manager.
   */
  public void logState(String label) {
    StringBuilder sb = new StringBuilder();
    sb.append("State of SegmentManager (" + label + "):\n");
    sb.append("Number of segments: " + segmentWriters.size());
    boolean hasSegments = false;
    for (Map.Entry<Long, ISegmentWriter> entry : this.segmentWriters.entrySet()) {
      SegmentInfo segmentInfo = entry.getValue().getSegmentInfo();
      hasSegments = true;

      sb.append(String.format("\nSegment (%s): isClosed: %5s, isComplete: %5s, "
              + "isEnabled: %5s, isIndetwittering: %5s, isOptimized: %5s, wasIndetwittered: %5s",
          segmentInfo.getSegmentName(),
          segmentInfo.isClosed(),
          segmentInfo.isComplete(),
          segmentInfo.isEnabled(),
          segmentInfo.isIndetwittering(),
          segmentInfo.isOptimized(),
          segmentInfo.wasIndetwittered()
      ));

      sb.append(String.format(" | Indetwitter stats: %s", segmentInfo.getIndetwitterStats().toString()));
    }
    if (!hasSegments) {
      sb.append(" No segments.");
    }
    LOG.info(sb.toString());
  }


  public PartitionConfig getPartitionConfig() {
    return dynamicPartitionConfig.getCurrentPartitionConfig();
  }

  public int getMatwitterEnabledSegments() {
    return matwitterEnabledSegments;
  }

  public EarlybirdSegmentFactory getEarlybirdSegmentFactory() {
    return earlybirdSegmentFactory;
  }

  public EarlybirdIndetwitterConfig getEarlybirdIndetwitterConfig() {
    return earlybirdIndetwitterConfig;
  }

  public UserTable getUserTable() {
    return userTable;
  }

  public UserScrubGeoMap getUserScrubGeoMap() {
    return userScrubGeoMap;
  }

  @VisibleForTesting
  public void reset() {
    segmentWriters.clear();
  }

  /**
   * Returns the list of all segments that match the given filter, in the given order.
   */
  public Iterable<SegmentInfo> getSegmentInfos(Filter filter, Order order) {
    Comparator<SegmentInfo> comparator;

    if (order == Order.OLD_TO_NEW) {
      comparator = Comparator.naturalOrder();
    } else {
      comparator = Comparator.reverseOrder();
    }

    return () -> segmentWriters.values().stream()
        .map(ISegmentWriter::getSegmentInfo)
        .filter(filter.predicate::apply)
        .sorted(comparator)
        .iterator();
  }

  private void createAndPutSegmentInfo(Segment segment) throws IOEtwitterception {
    LOG.info("Creating new SegmentInfo for segment " + segment.getSegmentName());
    putSegmentInfo(new SegmentInfo(segment, earlybirdSegmentFactory, segmentSyncConfig));
  }

  /**
   * Updates the list of segments managed by this manager, based on the given list.
   */
  public void updateSegments(List<Segment> segmentsList) throws IOEtwitterception {
    // Truncate to the amount of segments we want to keep enabled.
    List<Segment> truncatedSegmentList =
        SegmentManager.truncateSegmentList(segmentsList, matwitterEnabledSegments);

    final long newestTimeSliceID = getNewestTimeSliceID();
    final Set<Long> segmentsToDisable = new HashSet<>(segmentWriters.keySet());

    for (Segment segment : truncatedSegmentList) {
      final long timeSliceID = segment.getTimeSliceID();
      segmentsToDisable.remove(timeSliceID);

      // On the first loop iteration of the first call to updateSegments(), newestTimeSliceID should
      // be set to -1, so the condition should be false. After that, all segments should either be
      // newer than the latest process segment, or if we're replacing an old segment, it should have
      // a SegmentInfo instance associated with it.
      if (timeSliceID <= newestTimeSliceID) {
        ISegmentWriter segmentWriter = segmentWriters.get(timeSliceID);
        // Old time slice ID. It should have a SegmentInfo instance associated with it.
        if (segmentWriter == null) {
          if (!badTimesliceIds.contains(timeSliceID)) {
            // We're dealing with a bad timeslice. Log an error, but do it only once per timeslice.
            LOG.error("The SegmentInfo instance associated with an old timeSliceID should never be "
                      + "null. TimeSliceID: {}", timeSliceID);
            badTimesliceIds.add(timeSliceID);
          }
        } else if (segmentWriter.getSegmentInfo().isClosed()) {
          // If the SegmentInfo was closed, create a new one.
          LOG.info("SegmentInfo for segment {} is closed.", segment.getSegmentName());
          createAndPutSegmentInfo(segment);
        }
      } else {
        // New time slice ID: create a SegmentInfo instance for it.
        createAndPutSegmentInfo(segment);
      }
    }

    // Anything we didn't see locally can be disabled.
    for (Long segmentID : segmentsToDisable) {
      disableSegment(segmentID);
    }

    // Update segment stats and other etwitterported variables.
    updateStats();
  }

  /**
   * Re-etwitterport stats after a segment has changed, or the set of segments has changed.
   */
  public void updateStats() {
    // Update the partition count stats.
    SEGMENT_COUNT_STATS.set(segmentWriters.size());

    OPTIMIZED_SEGMENTS.reset();
    UNOPTIMIZED_SEGMENTS.reset();
    for (ISegmentWriter writer : segmentWriters.values()) {
      if (writer.getSegmentInfo().isOptimized()) {
        OPTIMIZED_SEGMENTS.increment();
      } else {
        UNOPTIMIZED_SEGMENTS.increment();
      }
    }
  }

  private long getIndetwitterDepthMillis() {
    long oldestTimeSliceID = getOldestEnabledTimeSliceID();
    if (oldestTimeSliceID == SegmentInfo.INVALID_ID) {
      return 0;
    } else {
      // Compute timestamp from timesliceId, which is also a snowflake tweetId
      long timestamp = SnowflakeIdParser.getTimestampFromTweetId(oldestTimeSliceID);
      // Set current indetwitter depth in milliseconds
      long indetwitterDepthInMillis = System.currentTimeMillis() - timestamp;
      // Indetwitter depth should never be negative.
      if (indetwitterDepthInMillis < 0) {
        LOG.warn("Negative indetwitter depth. Large time skew on this Earlybird?");
        return 0;
      } else {
        return indetwitterDepthInMillis;
      }
    }
  }

  private void updateEtwitterportedSegmentStats() {
    int indetwitter = 0;
    for (SegmentInfo segmentInfo : getSegmentInfos(Filter.Enabled, Order.NEW_TO_OLD)) {
      SegmentIndetwitterStatsEtwitterporter.etwitterport(segmentInfo, indetwitter++);
    }
  }

  // Marks the SegmentInfo object matching this time slice as disabled.
  private void disableSegment(long timeSliceID) {
    SegmentInfo info = getSegmentInfo(timeSliceID);
    if (info == null) {
      LOG.warn("Tried to disable missing segment " + timeSliceID);
      return;
    }
    info.setIsEnabled(false);
    LOG.info("Disabled segment " + info);
  }

  public long getNewestTimeSliceID() {
    final Iterator<SegmentInfo> segments = getSegmentInfos(Filter.All, Order.NEW_TO_OLD).iterator();
    return segments.hasNetwittert() ? segments.netwittert().getTimeSliceID() : SegmentInfo.INVALID_ID;
  }

  /**
   * Returns the timeslice ID of the oldest enabled segment.
   */
  public long getOldestEnabledTimeSliceID() {
    if (segmentWriters.size() == 0) {
      return SegmentInfo.INVALID_ID;
    }
    ISegmentWriter segmentWriter = segmentWriters.firstEntry().getValue();
    return segmentWriter.getSegmentInfo().getTimeSliceID();
  }

  /**
   * Returns the SegmentInfo for the given timeSliceID.
   */
  public final SegmentInfo getSegmentInfo(long timeSliceID) {
    ISegmentWriter segmentWriter = segmentWriters.get(timeSliceID);
    return segmentWriter == null ? null : segmentWriter.getSegmentInfo();
  }

  /**
   * Returns the segment info for the segment that should contain the given tweet ID.
   */
  public final SegmentInfo getSegmentInfoFromStatusID(long tweetID) {
    for (SegmentInfo segmentInfo : getSegmentInfos(Filter.All, Order.NEW_TO_OLD)) {
      if (tweetID >= segmentInfo.getTimeSliceID()) {
        return segmentInfo;
      }
    }

    return null;
  }

  /**
   * Removes the segment associated with the given timeslice ID from the segment manager. This will
   * also take care of all required clean up related to the segment being removed, such as closing
   * its writer.
   */
  public boolean removeSegmentInfo(long timeSliceID) {
    if (timeSliceID == getNewestTimeSliceID()) {
      throw new RuntimeEtwitterception("Cannot drop segment of current time-slice " + timeSliceID);
    }

    ISegmentWriter removed = segmentWriters.get(timeSliceID);
    if (removed == null) {
      return false;
    }

    LOG.info("Removing segment {}", removed.getSegmentInfo());
    Preconditions.checkState(!removed.getSegmentInfo().isEnabled());
    removed.getSegmentInfo().getIndetwitterSegment().close();
    segmentWriters.remove(timeSliceID);

    String segmentName = removed.getSegmentInfo().getSegmentName();
    updateAllListeners("Removed segment " + segmentName);
    LOG.info("Removed segment " + segmentName);
    updateEtwitterportedSegmentStats();
    updateStats();
    return true;
  }

  /**
   * Add the given SegmentWriter into the segmentWriters map.
   * If a segment with the same timesliceID already etwitterists in the map, the old one is replaced
   * with the new one; this should only happen in the archive.
   *
   * The replaced segment is destroyed after a delay to allow in-flight requests to finish.
   */
  public ISegmentWriter putSegmentInfo(SegmentInfo info) {
    ISegmentWriter usedSegmentWriter;

    SegmentWriter segmentWriter
        = new SegmentWriter(info, searchIndetwitteringMetricSet.updateFreshness);

    if (!info.isOptimized()) {
      LOG.info("Inserting an optimizing segment writer for segment: {}",
          info.getSegmentName());

      usedSegmentWriter = new OptimizingSegmentWriter(
          segmentWriter,
          criticalEtwitterceptionHandler,
          searchIndetwitteringMetricSet,
          indetwitterCaughtUpMonitor);
    } else {
      usedSegmentWriter = segmentWriter;
    }

    putSegmentWriter(usedSegmentWriter);
    return usedSegmentWriter;
  }

  private void putSegmentWriter(ISegmentWriter segmentWriter) {
    SegmentInfo newSegmentInfo = segmentWriter.getSegmentInfo();
    SegmentInfo oldSegmentInfo = getSegmentInfo(newSegmentInfo.getTimeSliceID());

    // Some sanity checks.
    if (oldSegmentInfo != null) {
      // This map is thread safe, so this put can be considered atomic.
      segmentWriters.put(newSegmentInfo.getTimeSliceID(), segmentWriter);
      LOG.info("Replaced SegmentInfo with a new one in segmentWriters map. "
          + "Old SegmentInfo: {} New SegmentInfo: {}", oldSegmentInfo, newSegmentInfo);

      if (!oldSegmentInfo.isClosed()) {
        oldSegmentInfo.deleteIndetwitterSegmentDirectoryAfterDelay();
      }
    } else {
      long newestTimeSliceID = getNewestTimeSliceID();
      if (newestTimeSliceID != SegmentInfo.INVALID_ID
          && newestTimeSliceID > newSegmentInfo.getTimeSliceID()) {
        LOG.error("Not adding out-of-order segment " + newSegmentInfo);
        return;
      }

      segmentWriters.put(newSegmentInfo.getTimeSliceID(), segmentWriter);
      LOG.info("Added segment " + newSegmentInfo);
    }

    updateAllListeners("Added segment " + newSegmentInfo.getTimeSliceID());
    updateEtwitterportedSegmentStats();
    updateStats();
  }

  private SegmentInfo createSegmentInfo(long timesliceID) throws IOEtwitterception {
    PartitionConfig partitionConfig = dynamicPartitionConfig.getCurrentPartitionConfig();

    TimeSlice timeSlice = new TimeSlice(
        timesliceID,
        matwitterSegmentSize,
        partitionConfig.getIndetwitteringHashPartitionID(),
        partitionConfig.getNumPartitions());

    SegmentInfo segmentInfo =
        new SegmentInfo(timeSlice.getSegment(), earlybirdSegmentFactory, segmentSyncConfig);

    return segmentInfo;
  }

  /**
   * Create a new optimizing segment writer and add it to the map.
   */
  public OptimizingSegmentWriter createAndPutOptimizingSegmentWriter(
      long timesliceID) throws IOEtwitterception {
    SegmentInfo segmentInfo = createSegmentInfo(timesliceID);

    OptimizingSegmentWriter writer = new OptimizingSegmentWriter(
        new SegmentWriter(segmentInfo, searchIndetwitteringMetricSet.updateFreshness),
        criticalEtwitterceptionHandler,
        searchIndetwitteringMetricSet,
        indetwitterCaughtUpMonitor);

    putSegmentWriter(writer);
    return writer;
  }

  /**
   * Create a new segment writer.
   */
  public SegmentWriter createSegmentWriter(long timesliceID) throws IOEtwitterception {
    SegmentInfo segmentInfo = createSegmentInfo(timesliceID);

    SegmentWriter writer = new SegmentWriter(
        segmentInfo, searchIndetwitteringMetricSet.updateFreshness);

    return writer;
  }

  private void updateAllListeners(String message) {
    List<SegmentInfo> segmentInfos = segmentWriters.values().stream()
        .map(ISegmentWriter::getSegmentInfo)
        .collect(Collectors.toList());
    for (SegmentUpdateListener listener : updateListeners) {
      try {
        listener.update(segmentInfos, message);
      } catch (Etwitterception e) {
        LOG.warn("SegmentManager: Unable to call update() on listener.", e);
      }
    }
  }

  // Returns true if the map contains a SegmentInfo matching the given time slice.
  public final boolean hasSegmentInfo(long timeSliceID) {
    return segmentWriters.containsKey(timeSliceID);
  }

  public void addUpdateListener(SegmentUpdateListener listener) {
    updateListeners.add(listener);
  }

  /**
   * Look up the segment containing the given status id.
   * If found, its timeslice id is returned.
   * If none found, -1 is returned.
   */
  public long lookupTimeSliceID(long statusID) throws IOEtwitterception {
    SegmentInfo segmentInfo = getSegmentInfoForID(statusID);
    if (segmentInfo == null) {
      return -1;
    }
    if (!segmentInfo.getIndetwitterSegment().hasDocument(statusID)) {
        return -1;
    }

    return segmentInfo.getTimeSliceID();
  }

  /**
   * Truncates the given segment list to the specified number of segments, by keeping the newest
   * segments.
   */
  @VisibleForTesting
  public static List<Segment> truncateSegmentList(List<Segment> segmentList, int matwitterNumSegments) {
    // Maybe cut-off the beginning of the sorted list of IDs.
    if (matwitterNumSegments > 0 && matwitterNumSegments < segmentList.size()) {
      return segmentList.subList(segmentList.size() - matwitterNumSegments, segmentList.size());
    } else {
      return segmentList;
    }
  }

  @VisibleForTesting
  public void setOffensive(long userID, boolean offensive) {
    userTable.setOffensive(userID, offensive);
  }

  @VisibleForTesting
  public void setAntisocial(long userID, boolean antisocial) {
    userTable.setAntisocial(userID, antisocial);
  }

  /**
   * Returns a searcher for all segments.
   */
  public EarlybirdMultiSegmentSearcher getMultiSearcher(ImmutableSchemaInterface schemaSnapshot)
      throws IOEtwitterception {
    return new EarlybirdMultiSegmentSearcher(
        schemaSnapshot,
        getSearchers(schemaSnapshot, Filter.All, Order.NEW_TO_OLD),
        searcherStats,
        clock);
  }

  /**
   * Returns a new searcher for the given segment.
   */
  @Nullable
  public EarlybirdLuceneSearcher getSearcher(
      Segment segment,
      ImmutableSchemaInterface schemaSnapshot) throws IOEtwitterception {
    return getSearcher(segment.getTimeSliceID(), schemaSnapshot);
  }

  /**
   * Get matwitter tweet id across all enabled segments.
   * @return matwitter tweet id or -1 if none found
   */
  public long getMatwitterTweetIdFromEnabledSegments() {
    for (SegmentInfo segmentInfo : getSegmentInfos(Filter.Enabled, Order.NEW_TO_OLD)) {
      long matwitterTweetId = segmentInfo.getIndetwitterSegment().getMatwitterTweetId();
      if (matwitterTweetId != -1) {
        return matwitterTweetId;
      }
    }

    return -1;
  }

  /**
   * Create a tweet indetwitter searcher on the segment represented by the timeslice id.  For production
   * search session, the schema snapshot should be always passed in to make sure that the schema
   * usage inside scoring is consistent.
   *
   * For non-production usage, like one-off debugging search, you can use the function call without
   * the schema snapshot.
   *
   * @param timeSliceID the timeslice id, which represents the indetwitter segment
   * @param schemaSnapshot the schema snapshot
   * @return the tweet indetwitter searcher
   */
  @Nullable
  public EarlybirdSingleSegmentSearcher getSearcher(
      long timeSliceID,
      ImmutableSchemaInterface schemaSnapshot) throws IOEtwitterception {
    SegmentInfo segmentInfo = getSegmentInfo(timeSliceID);
    if (segmentInfo == null) {
      return null;
    }
    return segmentInfo.getIndetwitterSegment().getSearcher(userTable, schemaSnapshot);
  }

  /**
   * Returns a new searcher for the segment with the given timeslice ID. If the given timeslice ID
   * does not correspond to any active segment, {@code null} is returned.
   *
   * @param timeSliceID The segment's timeslice ID.
   * @return A new searcher for the segment with the given timeslice ID.
   */
  @Nullable
  public EarlybirdSingleSegmentSearcher getSearcher(long timeSliceID) throws IOEtwitterception {
    SegmentInfo segmentInfo = getSegmentInfo(timeSliceID);
    if (segmentInfo == null) {
      return null;
    }
    return segmentInfo.getIndetwitterSegment().getSearcher(userTable);
  }

  @Nullable
  public EarlybirdResponseCode checkSegment(Segment segment) {
    return checkSegmentInternal(getSegmentInfo(segment.getTimeSliceID()));
  }

  private static EarlybirdResponseCode checkSegmentInternal(SegmentInfo info) {
    if (info == null) {
      return EarlybirdResponseCode.PARTITION_NOT_FOUND;
    } else if (info.isEnabled()) {
      return EarlybirdResponseCode.SUCCESS;
    } else {
      return EarlybirdResponseCode.PARTITION_DISABLED;
    }
  }

  private List<EarlybirdSingleSegmentSearcher> getSearchers(
      ImmutableSchemaInterface schemaSnapshot,
      Filter filter,
      Order order) throws IOEtwitterception {
    List<EarlybirdSingleSegmentSearcher> searchers = Lists.newArrayList();
    for (SegmentInfo segmentInfo : getSegmentInfos(filter, order)) {
      EarlybirdSingleSegmentSearcher searcher =
          segmentInfo.getIndetwitterSegment().getSearcher(userTable, schemaSnapshot);
      if (searcher != null) {
        searchers.add(searcher);
      }
    }
    return searchers;
  }

  /**
   * Gets metadata for segments for debugging purposes.
   */
  public List<String> getSegmentMetadata() {
    List<String> segmentMetadata = new ArrayList<>();
    for (SegmentInfo segment : getSegmentInfos(Filter.All, Order.OLD_TO_NEW)) {
      segmentMetadata.add(segment.getSegmentMetadata());
    }
    return segmentMetadata;
  }

  /**
   * Gets info for query caches to be displayed in an admin page.
   */
  public String getQueryCachesData() {
    StringBuilder output = new StringBuilder();
    for (SegmentInfo segment : getSegmentInfos(Filter.All, Order.OLD_TO_NEW)) {
      output.append(segment.getQueryCachesData() + "\n");
    }
    return output.toString();
  }

  /**
   * Indetwitter the given user update. Returns false if the given update is skipped.
   */
  public boolean indetwitterUserUpdate(UserUpdate userUpdate) {
    return userTable.indetwitterUserUpdate(userUpdatesChecker, userUpdate);
  }

  /**
   * Indetwitter the given UserScrubGeoEvent.
   * @param userScrubGeoEvent
   */
  public void indetwitterUserScrubGeoEvent(UserScrubGeoEvent userScrubGeoEvent) {
    userScrubGeoMap.indetwitterUserScrubGeoEvent(userScrubGeoEvent);
  }

  /**
   * Return how many documents this segment manager has indetwittered in all of its enabled segments.
   */
  public long getNumIndetwitteredDocuments() {
    // Order here doesn't matter, we just want all enabled segments, and allocate
    // as little as needed.
    long indetwitteredDocs = 0;
    for (SegmentInfo segmentInfo : getSegmentInfos(Filter.Enabled, Order.OLD_TO_NEW)) {
      indetwitteredDocs += segmentInfo.getIndetwitterSegment().getIndetwitterStats().getStatusCount();
    }
    return indetwitteredDocs;
  }

  /**
   * Return how many partial updates this segment manager has applied
   * in all of its enabled segments.
   */
  public long getNumPartialUpdates() {
    long partialUpdates = 0;
    for (SegmentInfo segmentInfo : getSegmentInfos(Filter.Enabled, Order.OLD_TO_NEW)) {
      partialUpdates += segmentInfo.getIndetwitterSegment().getIndetwitterStats().getPartialUpdateCount();
    }
    return partialUpdates;
  }

  /**
   * Returns the segment info for the segment containing the given tweet ID.
   */
  public SegmentInfo getSegmentInfoForID(long tweetID) {
    ISegmentWriter segmentWriter = getSegmentWriterForID(tweetID);
    return segmentWriter == null ? null : segmentWriter.getSegmentInfo();
  }

  /**
   * Returns the segment writer for the segment containing the given tweet ID.
   */
  @Nullable
  public ISegmentWriter getSegmentWriterForID(long tweetID) {
    Map.Entry<Long, ISegmentWriter> entry = segmentWriters.floorEntry(tweetID);
    return entry == null ? null : entry.getValue();
  }

  /**
   * Remove old segments until we have less than or equal to the number of matwitter enabled segments.
   */
  public void removeEtwittercessSegments() {
    int removedSegmentCount = 0;
    while (segmentWriters.size() > getMatwitterEnabledSegments()) {
      long timesliceID = getOldestEnabledTimeSliceID();
      disableSegment(timesliceID);
      removeSegmentInfo(timesliceID);
      removedSegmentCount += 1;
    }
    LOG.info("Segment manager removed {} etwittercess segments", removedSegmentCount);
  }

  /**
   * Returns total indetwitter size on disk across all enabled segments in this segment manager.
   */
  private long getTotalSegmentSizeOnDisk() {
    long totalIndetwitterSize = 0;
    for (SegmentInfo segmentInfo : getSegmentInfos(Filter.Enabled, Order.OLD_TO_NEW)) {
      totalIndetwitterSize += segmentInfo.getIndetwitterSegment().getIndetwitterStats().getIndetwitterSizeOnDiskInBytes();
    }
    return totalIndetwitterSize;
  }

  @VisibleForTesting
  ISegmentWriter getSegmentWriterWithoutCreationForTests(long timesliceID) {
    return segmentWriters.get(timesliceID);
  }

  @VisibleForTesting
  ArrayList<Long> getTimeSliceIdsForTests() {
    return new ArrayList<Long>(segmentWriters.keySet());
  }
}

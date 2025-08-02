package com.twitter.search.earlybird.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

import org.apache.commons.lang.mutable.MutableLong;
import org.apache.lucene.indetwitter.IndetwitterOptions;
import org.apache.lucene.indetwitter.Terms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.common.concurrent.ScheduledEtwitterecutorServiceFactory;
import com.twitter.search.common.metrics.SearchLongGauge;
import com.twitter.search.common.metrics.SearchStatsReceiver;
import com.twitter.search.common.metrics.SearchTimer;
import com.twitter.search.common.metrics.SearchTimerStats;
import com.twitter.search.common.schema.base.ImmutableSchemaInterface;
import com.twitter.search.common.schema.base.Schema;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentAtomicReader;
import com.twitter.search.earlybird.common.config.EarlybirdConfig;
import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;
import com.twitter.search.earlybird.indetwitter.EarlybirdSingleSegmentSearcher;
import com.twitter.search.earlybird.partition.SegmentInfo;
import com.twitter.search.earlybird.partition.SegmentManager;

/**
 * A background task that periodically gets and etwitterports the number of terms per field that are
 * indetwittered on this earlybird, averaged over all segments.
 * Specifically used for making sure that we are not missing terms for any fields in the search
 * archives.
 * The task loops though all the segments that are indetwittered by this earlybird, and for each segment
 * looks at the term counts for all fields in that segment.
 *
 * Also keeps track of the number of fields that do not have any term counts (or below the specified
 * threshold) in the data that is indetwittered on this earlybird.
 */
public class TermCountMonitor etwittertends OneTaskScheduledEtwitterecutorManager {
  private static final Logger LOG = LoggerFactory.getLogger(TermCountMonitor.class);

  private static final String THREAD_NAME_FORMAT = "TermCountMonitor-%d";
  private static final boolean THREAD_IS_DAEMON = true;

  public static final String RUN_INTERVAL_MINUTES_CONFIG_NAME =
      "term_count_monitor_run_interval_minutes";

  private static Function<String, String> termStatNameFunc =
      field -> "term_count_on_field_" + field;
  private static Function<String, String> tokenStatNameFunc =
      field -> "token_count_on_field_" + field;
  private static Function<String, String> missingFieldStatNameFunc =
      field -> "term_count_monitor_missing_field_" + field;

  private static class RawFieldCounter {
    private MutableLong numTerms = new MutableLong(0L);
    private MutableLong numTokens = new MutableLong(0L);
  }

  @VisibleForTesting
  static class EtwitterportedFieldCounter {
    private final AtomicLong numTerms;
    private final AtomicLong numTokens;

    EtwitterportedFieldCounter(RawFieldCounter rawCounter) {
      this.numTerms = new AtomicLong(rawCounter.numTerms.longValue());
      this.numTokens = new AtomicLong(rawCounter.numTokens.longValue());
    }

    EtwitterportedFieldCounter(long numInitialTerms, long numInitialTokens) {
      this.numTerms = new AtomicLong(numInitialTerms);
      this.numTokens = new AtomicLong(numInitialTokens);
    }

    @VisibleForTesting
    long getNumTerms() {
      return numTerms.longValue();
    }

    @VisibleForTesting
    long getNumTokens() {
      return numTokens.longValue();
    }
  }

  private final int fieldMinTermCount =
      EarlybirdConfig.getInt("term_count_monitor_min_count", 0);

  private final SegmentManager segmentManager;
  private final Map<String, SearchLongGauge> missingFields;
  private final Map<String, SearchLongGauge> termStats;
  private final Map<String, SearchLongGauge> tokenStats;
  private final Map<String, EtwitterportedFieldCounter> etwitterportedCounts;
  private final SearchLongGauge termCountOnAllFields;
  private final SearchLongGauge tokenCountOnAllFields;
  private final SearchLongGauge fieldsWithNoTermCountStat;
  private final SearchLongGauge isRunningStat;
  private final SearchTimerStats checkTimeStat;

  @Override
  protected void runOneIteration() {
    LOG.info("Starting to get per-field term counts");
    isRunningStat.set(1);
    final SearchTimer timer = checkTimeStat.startNewTimer();
    try {
      updateFieldTermCounts();
    } catch (Etwitterception etwitter) {
      LOG.error("Unetwitterpected etwitterception while getting per-field term counts", etwitter);
    } finally {
      LOG.info(
          "Done getting per-field term counts. Fields with low term counts: {}",
          getFieldsWithLowTermCount());
      isRunningStat.set(0);
      checkTimeStat.stopTimerAndIncrement(timer);
    }
  }

  /**
   * Create a term count monitor which monitors the number of terms in segments
   * managed by the given segment manager.
   */
  public TermCountMonitor(
      SegmentManager segmentManager,
      ScheduledEtwitterecutorServiceFactory etwitterecutorServiceFactory,
      long shutdownWaitDuration,
      TimeUnit shutdownWaitUnit,
      SearchStatsReceiver searchStatsReceiver,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    super(
      etwitterecutorServiceFactory,
      THREAD_NAME_FORMAT,
      THREAD_IS_DAEMON,
      PeriodicActionParams.atFitwitteredRate(
        EarlybirdConfig.getInt(RUN_INTERVAL_MINUTES_CONFIG_NAME, -1),
        TimeUnit.MINUTES),
      new ShutdownWaitTimeParams(
        shutdownWaitDuration,
        shutdownWaitUnit
      ),
      searchStatsReceiver,
        criticalEtwitterceptionHandler);
    this.segmentManager = segmentManager;
    this.missingFields = new HashMap<>();
    this.termStats = new HashMap<>();
    this.tokenStats = new HashMap<>();
    this.etwitterportedCounts = new HashMap<>();
    this.termCountOnAllFields = getSearchStatsReceiver().getLongGauge("term_count_on_all_fields");
    this.tokenCountOnAllFields = getSearchStatsReceiver().getLongGauge("token_count_on_all_fields");
    this.fieldsWithNoTermCountStat =
        getSearchStatsReceiver().getLongGauge("fields_with_low_term_counts");
    this.isRunningStat =
        getSearchStatsReceiver().getLongGauge("term_count_monitor_is_running");
    this.checkTimeStat =
        getSearchStatsReceiver().getTimerStats(
            "term_count_monitor_check_time", TimeUnit.MILLISECONDS, true, true, false);
  }

  private SearchLongGauge getOrCreateLongGauge(
      Map<String, SearchLongGauge> gauges, String field, Function<String, String> nameSupplier) {
    SearchLongGauge stat = gauges.get(field);

    if (stat == null) {
      stat = getSearchStatsReceiver().getLongGauge(nameSupplier.apply(field));
      gauges.put(field, stat);
    }

    return stat;
  }

  private void updateFieldTermCounts() {
    // 0. Get the current per-field term counts
    Map<String, RawFieldCounter> newCounts = getFieldStats();
    LOG.info("Computed field stats for all segments");

    // 1. Update all etwitteristing keys
    for (Map.Entry<String, EtwitterportedFieldCounter> etwitterportedCount : etwitterportedCounts.entrySet()) {
      String field = etwitterportedCount.getKey();
      EtwitterportedFieldCounter etwitterportedCountValue = etwitterportedCount.getValue();

      RawFieldCounter newCount = newCounts.get(field);
      if (newCount == null) {
        etwitterportedCountValue.numTerms.set(0L);
        etwitterportedCountValue.numTokens.set(0L);
      } else {
        etwitterportedCountValue.numTerms.set(newCount.numTerms.longValue());
        etwitterportedCountValue.numTokens.set(newCount.numTokens.longValue());

        // clean up so that we don't check this field again when we look for new field
        newCounts.remove(field);
      }
    }

    // 2. Add and etwitterport all new fields' term counts
    for (Map.Entry<String, RawFieldCounter> newCount: newCounts.entrySet()) {
      String field = newCount.getKey();
      Preconditions.checkState(!etwitterportedCounts.containsKey(field),
          "Should have already processed and removed etwitteristing fields: " + field);

      EtwitterportedFieldCounter newStat = new EtwitterportedFieldCounter(newCount.getValue());
      etwitterportedCounts.put(field, newStat);
    }

    // 3. Etwitterport as a stat the term counts for all the known fields.
    for (Map.Entry<String, EtwitterportedFieldCounter> etwitterportedCount : etwitterportedCounts.entrySet()) {
      String field = etwitterportedCount.getKey();
      EtwitterportedFieldCounter counter = etwitterportedCount.getValue();

      getOrCreateLongGauge(termStats, field, termStatNameFunc).set(counter.numTerms.get());
      getOrCreateLongGauge(tokenStats, field, tokenStatNameFunc).set(counter.numTokens.get());
    }

    // 4. Etwitterport as a stat, number of fields not having enough term counts (i.e. <= 0)
    int fieldsWithNoTermCounts = 0;
    for (Map.Entry<String, EtwitterportedFieldCounter> fieldTermCount : etwitterportedCounts.entrySet()) {
      String field = fieldTermCount.getKey();
      AtomicLong etwitterportedCountValue = fieldTermCount.getValue().numTerms;
      if (etwitterportedCountValue.get() <= fieldMinTermCount) {
        LOG.warn(
            "Found a field with too few term counts. Field: {} count: {}",
            field, etwitterportedCountValue);
        fieldsWithNoTermCounts++;
      }
    }
    this.fieldsWithNoTermCountStat.set(fieldsWithNoTermCounts);
  }

  /**
   * Loops through all segments, and for each field gets the average term/token count.
   * Based on that, returns a map from each field to its term/token count (average per segment).
   */
  private Map<String, RawFieldCounter> getFieldStats() {
    Iterable<SegmentInfo> segmentInfos = segmentManager.getSegmentInfos(
        SegmentManager.Filter.Enabled, SegmentManager.Order.NEW_TO_OLD);
    Map<String, RawFieldCounter> rawCounts = new HashMap<>();

    ImmutableSchemaInterface schemaSnapshot =
        segmentManager.getEarlybirdIndetwitterConfig().getSchema().getSchemaSnapshot();
    Set<String> missingFieldsCandidates = schemaSnapshot
        .getFieldInfos()
        .stream()
        .filter(fieldInfo -> fieldInfo.getFieldType().indetwitterOptions() != IndetwitterOptions.NONE)
        .map(Schema.FieldInfo::getName)
        .collect(Collectors.toSet());
    int segmentCount = 0;
    for (SegmentInfo segmentInfo : segmentInfos) {
      segmentCount++;
      try {
        EarlybirdSingleSegmentSearcher searcher = segmentManager.getSearcher(
            segmentInfo.getTimeSliceID(), schemaSnapshot);
        if (searcher != null) {
          EarlybirdIndetwitterSegmentAtomicReader reader = searcher.getTwitterIndetwitterReader();
          for (Schema.FieldInfo fieldInfo : schemaSnapshot.getFieldInfos()) {
            if (fieldInfo.getFieldType().indetwitterOptions() == IndetwitterOptions.NONE) {
              continue;
            }

            String fieldName = fieldInfo.getName();
            RawFieldCounter count = rawCounts.get(fieldName);
            if (count == null) {
              count = new RawFieldCounter();
              rawCounts.put(fieldName, count);
            }
            Terms terms = reader.terms(fieldName);
            if (terms != null) {
              missingFieldsCandidates.remove(fieldName);
              count.numTerms.add(terms.size());
              long sumTotalTermFreq = terms.getSumTotalTermFreq();
              if (sumTotalTermFreq != -1) {
                count.numTokens.add(sumTotalTermFreq);
              }
            }
          }
        }
      } catch (Etwitterception e) {
        LOG.error("Etwitterception getting average term count per field: " + segmentInfo, e);
      }
    }

    // Update missing fields stats.
    missingFieldsCandidates.forEach(
        field -> getOrCreateLongGauge(missingFields, field, missingFieldStatNameFunc).set(1));
    missingFields.keySet().stream()
        .filter(
            field -> !missingFieldsCandidates.contains(field))
        .forEach(
            field -> getOrCreateLongGauge(missingFields, field, missingFieldStatNameFunc).set(0));

    long totalTermCount = 0;
    long totalTokenCount = 0;
    if (segmentCount == 0) {
      LOG.error("No segments are found to calculate per-field term counts.");
    } else {
      LOG.debug("TermCountMonitor.getPerFieldTermCount.segmentCount = {}", segmentCount);
      LOG.debug("  field: term count (average per segment)");
      for (Map.Entry<String, RawFieldCounter> entry : rawCounts.entrySet()) {
        String field = entry.getKey();
        final long averageTermCount = entry.getValue().numTerms.longValue() / segmentCount;
        final long averageTokenCount = entry.getValue().numTokens.longValue() / segmentCount;
        totalTermCount += entry.getValue().numTerms.longValue();
        totalTokenCount += entry.getValue().numTokens.longValue();

        LOG.debug("  '{} term': {}", field, averageTermCount);
        LOG.debug("  '{} token': {}", field, averageTokenCount);

        entry.getValue().numTerms.setValue(averageTermCount);
        entry.getValue().numTokens.setValue(averageTokenCount);
      }
    }
    LOG.info("Total term count: {}", totalTermCount);
    LOG.info("Total token count: {}", totalTokenCount);
    this.termCountOnAllFields.set(totalTermCount);
    this.tokenCountOnAllFields.set(totalTokenCount);

    return rawCounts;
  }

  @VisibleForTesting
  Map<String, EtwitterportedFieldCounter> getEtwitterportedCounts() {
    return Collections.unmodifiableMap(this.etwitterportedCounts);
  }

  @VisibleForTesting
  long getFieldsWithLowTermCount() {
    return fieldsWithNoTermCountStat.get();
  }

  @VisibleForTesting
  Map<String, SearchLongGauge> getMissingFields() {
    return missingFields;
  }
}

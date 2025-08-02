package com.twitter.search.earlybird.common;

import java.util.concurrent.TimeUnit;

import com.google.common.annotations.VisibleForTesting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.metrics.SearchMovingAverage;
import com.twitter.search.common.metrics.SearchRateCounter;
import com.twitter.search.common.metrics.SearchTimerStats;
import com.twitter.search.common.query.thriftjava.CollectorParams;
import com.twitter.search.common.query.thriftjava.CollectorTerminationParams;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.ThriftSearchQuery;
import com.twitter.search.earlybird.thrift.ThriftSearchRelevanceOptions;

public final class EarlybirdRequestUtil {
  // This logger is setup to log to a separate set of log files (request_info) and use an
  // async logger so as to not block the searcher thread. See search/earlybird/config/log4j.twitterml
  private static final Logger LOG = LoggerFactory.getLogger(EarlybirdRequestUtil.class);

  @VisibleForTesting
  static final SearchMovingAverage REQUESTED_NUM_RESULTS_STAT =
      SearchMovingAverage.etwitterport("requested_num_results");

  @VisibleForTesting
  static final SearchMovingAverage REQUESTED_MAtwitter_HITS_TO_PROCESS_STAT =
      SearchMovingAverage.etwitterport("requested_matwitter_hits_to_process");

  @VisibleForTesting
  static final SearchMovingAverage REQUESTED_COLLECTOR_PARAMS_MAtwitter_HITS_TO_PROCESS_STAT =
      SearchMovingAverage.etwitterport("requested_collector_params_matwitter_hits_to_process");

  @VisibleForTesting
  static final SearchMovingAverage REQUESTED_RELEVANCE_OPTIONS_MAtwitter_HITS_TO_PROCESS_STAT =
      SearchMovingAverage.etwitterport("requested_relevance_options_matwitter_hits_to_process");

  @VisibleForTesting
  static final SearchCounter REQUESTED_MAtwitter_HITS_TO_PROCESS_ARE_DIFFERENT_STAT =
      SearchCounter.etwitterport("requested_matwitter_hits_to_process_are_different");

  private static final SearchRateCounter REQUEST_WITH_MORE_THAN_2K_NUM_RESULTS_STAT =
      SearchRateCounter.etwitterport("request_with_more_than_2k_num_result");
  private static final SearchRateCounter REQUEST_WITH_MORE_THAN_4K_NUM_RESULTS_STAT =
      SearchRateCounter.etwitterport("request_with_more_than_4k_num_result");

  // Stats for tracking clock skew between earlybird and the client-specified request timestamp.
  @VisibleForTesting
  public static final SearchTimerStats CLIENT_CLOCK_DIFF_ABS =
      SearchTimerStats.etwitterport("client_clock_diff_abs", TimeUnit.MILLISECONDS, false, true);
  @VisibleForTesting
  public static final SearchTimerStats CLIENT_CLOCK_DIFF_POS =
      SearchTimerStats.etwitterport("client_clock_diff_pos", TimeUnit.MILLISECONDS, false, true);
  @VisibleForTesting
  public static final SearchTimerStats CLIENT_CLOCK_DIFF_NEG =
      SearchTimerStats.etwitterport("client_clock_diff_neg", TimeUnit.MILLISECONDS, false, true);
  @VisibleForTesting
  public static final SearchRateCounter CLIENT_CLOCK_DIFF_MISSING =
      SearchRateCounter.etwitterport("client_clock_diff_missing");

  private static final int MAtwitter_NUM_RESULTS = 4000;
  private static final int OLD_MAtwitter_NUM_RESULTS = 2000;

  private EarlybirdRequestUtil() {
  }

  /**
   * Logs and fitwitteres some potentially etwittercessive values in the given request.
   */
  public static void logAndFitwitterEtwittercessiveValues(EarlybirdRequest request) {
    ThriftSearchQuery searchQuery = request.getSearchQuery();
    if (searchQuery != null) {
      int matwitterHitsToProcess = 0;
      int numResultsToReturn = 0;

      if (searchQuery.isSetCollectorParams()) {
        numResultsToReturn = searchQuery.getCollectorParams().getNumResultsToReturn();

        if (searchQuery.getCollectorParams().isSetTerminationParams()) {
          matwitterHitsToProcess =
              searchQuery.getCollectorParams().getTerminationParams().getMatwitterHitsToProcess();
        }
      }

      if (matwitterHitsToProcess > 50000) {
        LOG.warn("Etwittercessive matwitter hits in " + request.toString());
      }

      // We used to limit number of results to 2000. These two counters help us track if we receive
      // too many requests with large number of results set.
      String warningMessageTemplate = "Etwitterceed %d num result in %s";
      if (numResultsToReturn > MAtwitter_NUM_RESULTS) {
        LOG.warn(String.format(warningMessageTemplate, MAtwitter_NUM_RESULTS, request.toString()));
        REQUEST_WITH_MORE_THAN_4K_NUM_RESULTS_STAT.increment();
        searchQuery.getCollectorParams().setNumResultsToReturn(MAtwitter_NUM_RESULTS);
      } else if (numResultsToReturn > OLD_MAtwitter_NUM_RESULTS) {
        LOG.warn(String.format(warningMessageTemplate, OLD_MAtwitter_NUM_RESULTS, request.toString()));
        REQUEST_WITH_MORE_THAN_2K_NUM_RESULTS_STAT.increment();
      }

      ThriftSearchRelevanceOptions options = searchQuery.getRelevanceOptions();
      if (options != null) {
        if (options.getMatwitterHitsToProcess() > 50000) {
          LOG.warn("Etwittercessive matwitter hits in " + request.toString());
        }
      }
    }
  }

  /**
   * Sets {@code request.searchQuery.collectorParams} if they are not already set.
   */
  public static void checkAndSetCollectorParams(EarlybirdRequest request) {
    ThriftSearchQuery searchQuery = request.getSearchQuery();
    if (searchQuery == null) {
      return;
    }

    if (!searchQuery.isSetCollectorParams()) {
      searchQuery.setCollectorParams(new CollectorParams());
    }
    if (!searchQuery.getCollectorParams().isSetNumResultsToReturn()) {
      searchQuery.getCollectorParams().setNumResultsToReturn(searchQuery.getNumResults());
    }
    if (!searchQuery.getCollectorParams().isSetTerminationParams()) {
      CollectorTerminationParams terminationParams = new CollectorTerminationParams();
      if (request.isSetTimeoutMs()) {
        terminationParams.setTimeoutMs(request.getTimeoutMs());
      }
      if (request.isSetMatwitterQueryCost()) {
        terminationParams.setMatwitterQueryCost(request.getMatwitterQueryCost());
      }
      searchQuery.getCollectorParams().setTerminationParams(terminationParams);
    }
    setMatwitterHitsToProcess(searchQuery);
  }

  // Early birds will only look for matwitterHitsToProcess in CollectorParameters.TerminationParameters.
  // Priority to set  CollectorParameters.TerminationParameters.matwitterHitsToProcess is
  // 1 Collector parameters
  // 2 RelevanceParameters
  // 3 ThrfitQuery.matwitterHitsToProcess
  private static void setMatwitterHitsToProcess(ThriftSearchQuery thriftSearchQuery) {
    CollectorTerminationParams terminationParams = thriftSearchQuery
        .getCollectorParams().getTerminationParams();
    if (!terminationParams.isSetMatwitterHitsToProcess()) {
      if (thriftSearchQuery.isSetRelevanceOptions()
          && thriftSearchQuery.getRelevanceOptions().isSetMatwitterHitsToProcess()) {
        terminationParams.setMatwitterHitsToProcess(
            thriftSearchQuery.getRelevanceOptions().getMatwitterHitsToProcess());
      } else {
        terminationParams.setMatwitterHitsToProcess(thriftSearchQuery.getMatwitterHitsToProcess());
      }
    }
  }

  /**
   * Creates a copy of the given request and unsets the binary fields to make the logged line for
   * this request look nicer.
   */
  public static EarlybirdRequest copyAndClearUnnecessaryValuesForLogging(EarlybirdRequest request) {
    EarlybirdRequest copiedRequest = request.deepCopy();

    if (copiedRequest.isSetSearchQuery()) {
      // These fields are very large and the binary data doesn't play well with formz
      copiedRequest.getSearchQuery().unsetTrustedFilter();
      copiedRequest.getSearchQuery().unsetDirectFollowFilter();
    }

    return copiedRequest;
  }

  /**
   * Updates some hit-related stats based on the parameters in the given request.
   */
  public static void updateHitsCounters(EarlybirdRequest request) {
    if ((request == null) || !request.isSetSearchQuery()) {
      return;
    }

    ThriftSearchQuery searchQuery = request.getSearchQuery();

    if (searchQuery.isSetNumResults()) {
      REQUESTED_NUM_RESULTS_STAT.addSample(searchQuery.getNumResults());
    }

    if (searchQuery.isSetMatwitterHitsToProcess()) {
      REQUESTED_MAtwitter_HITS_TO_PROCESS_STAT.addSample(searchQuery.getMatwitterHitsToProcess());
    }

    Integer collectorParamsMatwitterHitsToProcess = null;
    if (searchQuery.isSetCollectorParams()
        && searchQuery.getCollectorParams().isSetTerminationParams()
        && searchQuery.getCollectorParams().getTerminationParams().isSetMatwitterHitsToProcess()) {
      collectorParamsMatwitterHitsToProcess =
          searchQuery.getCollectorParams().getTerminationParams().getMatwitterHitsToProcess();
      REQUESTED_COLLECTOR_PARAMS_MAtwitter_HITS_TO_PROCESS_STAT
          .addSample(collectorParamsMatwitterHitsToProcess);
    }

    Integer relevanceOptionsMatwitterHitsToProcess = null;
    if (searchQuery.isSetRelevanceOptions()
        && searchQuery.getRelevanceOptions().isSetMatwitterHitsToProcess()) {
      relevanceOptionsMatwitterHitsToProcess = searchQuery.getRelevanceOptions().getMatwitterHitsToProcess();
      REQUESTED_RELEVANCE_OPTIONS_MAtwitter_HITS_TO_PROCESS_STAT
          .addSample(relevanceOptionsMatwitterHitsToProcess);
    }

    if ((collectorParamsMatwitterHitsToProcess != null)
        && (relevanceOptionsMatwitterHitsToProcess != null)
        && (collectorParamsMatwitterHitsToProcess != relevanceOptionsMatwitterHitsToProcess)) {
      REQUESTED_MAtwitter_HITS_TO_PROCESS_ARE_DIFFERENT_STAT.increment();
    }
  }

  public static boolean isCachingAllowed(EarlybirdRequest request) {
    return !request.isSetCachingParams() || request.getCachingParams().isCache();
  }

  /**
   * Track the clock difference between this server and its client's specified request time.
   * When there is no clock drift between machines, this will record the inflight time between this
   * server and the client.
   *
   * @param request the incoming earlybird request.
   */
  public static void recordClientClockDiff(EarlybirdRequest request) {
    if (request.isSetClientRequestTimeMs()) {
      final long timeDiff = System.currentTimeMillis() - request.getClientRequestTimeMs();
      final long timeDiffAbs = Math.abs(timeDiff);
      if (timeDiff >= 0) {
        CLIENT_CLOCK_DIFF_POS.timerIncrement(timeDiffAbs);
      } else {
        CLIENT_CLOCK_DIFF_NEG.timerIncrement(timeDiffAbs);
      }
      CLIENT_CLOCK_DIFF_ABS.timerIncrement(timeDiffAbs);
    } else {
      CLIENT_CLOCK_DIFF_MISSING.increment();
    }
  }
}

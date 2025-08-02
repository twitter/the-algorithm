package com.twitter.search.earlybird.common;

import java.util.EnumMap;
import java.util.Map;

import scala.Option;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;

import com.twitter.contetwittert.TwitterContetwittert;
import com.twitter.contetwittert.thriftscala.Viewer;
import com.twitter.decider.Decider;
import com.twitter.finagle.thrift.ClientId;
import com.twitter.finagle.thrift.ClientId$;
import com.twitter.search.TwitterContetwittertPermit;
import com.twitter.search.common.constants.thriftjava.ThriftQuerySource;
import com.twitter.search.common.decider.DeciderUtil;
import com.twitter.search.common.logging.RPCLogger;
import com.twitter.search.common.metrics.FailureRatioCounter;
import com.twitter.search.common.metrics.Timer;
import com.twitter.search.common.util.earlybird.TermStatisticsUtil;
import com.twitter.search.common.util.earlybird.ThriftSearchResultUtil;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird.thrift.ThriftFacetFieldRequest;
import com.twitter.search.earlybird.thrift.ThriftHistogramSettings;
import com.twitter.search.earlybird.thrift.ThriftSearchQuery;
import com.twitter.search.earlybird.thrift.ThriftTermStatisticsRequest;

import static com.twitter.search.common.util.earlybird.EarlybirdResponseUtil
    .responseConsideredFailed;


public class EarlybirdRequestLogger etwittertends RPCLogger {
  protected enum EtwittertraFields {
    QUERY_MAtwitter_HITS_TO_PROCESS,
    COLLECTOR_PARAMS_MAtwitter_HITS_TO_PROCESS,
    RELEVANCE_OPTIONS_MAtwitter_HITS_TO_PROCESS,
    NUM_HITS_PROCESSED,
    QUERY_COST,
    CPU_TOTAL,
    QUERY_SOURCE,
    CLIENT_ID,
    FINAGLE_CLIENT_ID
  }

  protected enum ShardOnlyEtwittertraFields {
    NUM_SEARCHED_SEGMENTS,
    SCORING_TIME_NANOS
  }

  protected enum RootOnlyEtwittertraFields {
    CACHING_ALLOWED,
    DEBUG_MODE,
    CACHE_HIT,
    USER_AGENT,
    // See JIRA APPSEC-2303 for IP addresses logging
  }

  private static final String LOG_FULL_REQUEST_DETAILS_ON_ERROR_DECIDER_KEY =
      "log_full_request_details_on_error";
  private static final String LOG_FULL_REQUEST_DETAILS_RANDOM_FRACTION_DECIDER_KEY =
      "log_full_request_details_random_fraction";
  private static final String LOG_FULL_SLOW_REQUEST_DETAILS_RANDOM_FRACTION_DECIDER_KEY =
      "log_full_slow_request_details_random_fraction";
  private static final String SLOW_REQUEST_LATENCY_THRESHOLD_MS_DECIDER_KEY =
      "slow_request_latency_threshold_ms";

  private final Decider decider;
  private final boolean enableLogUnknownClientRequests;

  private static final Map<ThriftQuerySource, FailureRatioCounter>
      FAILURE_RATIO_COUNTER_BY_QUERY_SOURCE = preBuildFailureRatioCounters();
  private static final FailureRatioCounter NO_QUERY_SOURCE_FAILURE_RATIO_COUNTER =
      new FailureRatioCounter("earlybird_logger", "query_source", "not_set");

  static EarlybirdRequestLogger buildForRoot(
      String loggerName, int latencyWarnThreshold, Decider decider) {

    return new EarlybirdRequestLogger(loggerName, latencyWarnThreshold,
        decider, true, RPCLogger.Fields.values(), EtwittertraFields.values(),
        RootOnlyEtwittertraFields.values());
  }

  static EarlybirdRequestLogger buildForShard(
      String loggerName, int latencyWarnThreshold, Decider decider) {

    return new EarlybirdRequestLogger(loggerName, latencyWarnThreshold,
        decider, false, RPCLogger.Fields.values(), EtwittertraFields.values(),
        ShardOnlyEtwittertraFields.values());
  }

  @VisibleForTesting
  EarlybirdRequestLogger(String loggerName, int latencyWarnThreshold, Decider decider) {
    this(loggerName, latencyWarnThreshold, decider, false, RPCLogger.Fields.values(),
        EtwittertraFields.values(), RootOnlyEtwittertraFields.values(), ShardOnlyEtwittertraFields.values());
  }

  private EarlybirdRequestLogger(String loggerName, int latencyWarnThreshold, Decider decider,
                                 boolean enableLogUnknownClientRequests, Enum[]... fieldEnums) {
    super(loggerName, fieldEnums);
    this.decider = decider;
    this.enableLogUnknownClientRequests = enableLogUnknownClientRequests;
    setLatencyWarnThreshold(latencyWarnThreshold);
  }

  /**
   * Logs the given earlybird request and response.
   *
   * @param request The earlybird request.
   * @param response The earlybird response.
   * @param timer The time it took to process this request.
   */
  public void logRequest(EarlybirdRequest request, EarlybirdResponse response, Timer timer) {
    try {
      LogEntry entry = newLogEntry();

      setRequestLogEntries(entry, request);
      setResponseLogEntries(entry, response);
      if (timer != null) {
        entry.setField(EtwittertraFields.CPU_TOTAL, Long.toString(timer.getElapsedCpuTotal()));
      }

      boolean wasError = response != null && responseConsideredFailed(response.getResponseCode());

      long responseTime = response != null ? response.getResponseTime() : 0L;

      String logLine = writeLogLine(entry, responseTime, wasError);

      // This code path is called for pre/post logging
      // Prevent same request showing up twice by only logging on post logging
      if (response != null && DeciderUtil.isAvailableForRandomRecipient(
          decider, LOG_FULL_REQUEST_DETAILS_RANDOM_FRACTION_DECIDER_KEY)) {
        Base64RequestResponseForLogging.randomRequest(logLine, request, response).log();
      }

      // Unknown client request logging only applies to pre-logging.
      if (enableLogUnknownClientRequests && response == null) {
        UnknownClientRequestForLogging unknownClientRequestLogger =
            UnknownClientRequestForLogging.unknownClientRequest(logLine, request);
        if (unknownClientRequestLogger != null) {
          unknownClientRequestLogger.log();
        }
      }

      if (wasError
          && DeciderUtil.isAvailableForRandomRecipient(
          decider, LOG_FULL_REQUEST_DETAILS_ON_ERROR_DECIDER_KEY)) {
        new RequestResponseForLogging(request, response).logFailedRequest();
        Base64RequestResponseForLogging.failedRequest(logLine, request, response).log();
      }

      boolean wasSlow = response != null
          && responseTime >= DeciderUtil.getAvailability(
              decider, SLOW_REQUEST_LATENCY_THRESHOLD_MS_DECIDER_KEY);
      if (wasSlow
          && DeciderUtil.isAvailableForRandomRecipient(
              decider, LOG_FULL_SLOW_REQUEST_DETAILS_RANDOM_FRACTION_DECIDER_KEY)) {
        Base64RequestResponseForLogging.slowRequest(logLine, request, response).log();
      }

      FailureRatioCounter failureRatioCounter =
          FAILURE_RATIO_COUNTER_BY_QUERY_SOURCE.get(request.getQuerySource());
      if (failureRatioCounter != null) {
        failureRatioCounter.requestFinished(!wasError);
      } else {
        NO_QUERY_SOURCE_FAILURE_RATIO_COUNTER.requestFinished(!wasError);
      }

    } catch (Etwitterception e) {
      LOG.error("Etwitterception building log entry ", e);
    }
  }

  private void setRequestLogEntries(LogEntry entry, EarlybirdRequest request) {
    entry.setField(Fields.CLIENT_HOST, request.getClientHost());
    entry.setField(Fields.CLIENT_REQUEST_ID, request.getClientRequestID());
    entry.setField(Fields.REQUEST_TYPE, requestTypeForLog(request));

    if (request.isSetSearchQuery()) {
      ThriftSearchQuery searchQuery = request.getSearchQuery();
      entry.setField(Fields.QUERY, searchQuery.getSerializedQuery());

      if (searchQuery.isSetMatwitterHitsToProcess()) {
        entry.setField(EtwittertraFields.QUERY_MAtwitter_HITS_TO_PROCESS,
                       Integer.toString(searchQuery.getMatwitterHitsToProcess()));
      }

      if (searchQuery.isSetCollectorParams()
          && searchQuery.getCollectorParams().isSetTerminationParams()
          && searchQuery.getCollectorParams().getTerminationParams().isSetMatwitterHitsToProcess()) {
        entry.setField(EtwittertraFields.COLLECTOR_PARAMS_MAtwitter_HITS_TO_PROCESS,
                       Integer.toString(searchQuery.getCollectorParams().getTerminationParams()
                                        .getMatwitterHitsToProcess()));
      }

      if (searchQuery.isSetRelevanceOptions()
          && searchQuery.getRelevanceOptions().isSetMatwitterHitsToProcess()) {
        entry.setField(EtwittertraFields.RELEVANCE_OPTIONS_MAtwitter_HITS_TO_PROCESS,
                       Integer.toString(searchQuery.getRelevanceOptions().getMatwitterHitsToProcess()));
      }
    }

    entry.setField(Fields.NUM_REQUESTED, Integer.toString(numRequestedForLog(request)));

    if (request.isSetQuerySource()) {
      entry.setField(EtwittertraFields.QUERY_SOURCE, request.getQuerySource().name());
    }

    if (request.isSetClientId()) {
      entry.setField(EtwittertraFields.CLIENT_ID, request.getClientId());
    }

    entry.setField(RootOnlyEtwittertraFields.CACHING_ALLOWED,
                   Boolean.toString(EarlybirdRequestUtil.isCachingAllowed(request)));

    entry.setField(RootOnlyEtwittertraFields.DEBUG_MODE, Byte.toString(request.getDebugMode()));

    Option<ClientId> clientIdOption = ClientId$.MODULE$.current();
    if (clientIdOption.isDefined()) {
      entry.setField(EtwittertraFields.FINAGLE_CLIENT_ID, clientIdOption.get().name());
    }

    setLogEntriesFromTwitterContetwittert(entry);
  }

  @VisibleForTesting
  Option<Viewer> getTwitterContetwittert() {
    return TwitterContetwittert.acquire(TwitterContetwittertPermit.get()).apply();
  }

  private void setLogEntriesFromTwitterContetwittert(LogEntry entry) {
    Option<Viewer> viewerOption = getTwitterContetwittert();
    if (viewerOption.nonEmpty()) {
      Viewer viewer = viewerOption.get();

      if (viewer.userAgent().nonEmpty()) {
        String userAgent = viewer.userAgent().get();

        // we only replace the comma in the user-agent with %2C to make it easily parseable,
        // specially with command line tools like cut/sed/awk
        userAgent = userAgent.replace(",", "%2C");

        entry.setField(RootOnlyEtwittertraFields.USER_AGENT, userAgent);
      }
    }
  }

  private void setResponseLogEntries(LogEntry entry, EarlybirdResponse response) {
    if (response != null) {
      entry.setField(Fields.NUM_RETURNED, Integer.toString(numResultsForLog(response)));
      entry.setField(Fields.RESPONSE_CODE, String.valueOf(response.getResponseCode()));
      entry.setField(Fields.RESPONSE_TIME_MICROS, Long.toString(response.getResponseTimeMicros()));
      if (response.isSetSearchResults()) {
        entry.setField(EtwittertraFields.NUM_HITS_PROCESSED,
            Integer.toString(response.getSearchResults().getNumHitsProcessed()));
        entry.setField(EtwittertraFields.QUERY_COST,
            Double.toString(response.getSearchResults().getQueryCost()));
        if (response.getSearchResults().isSetScoringTimeNanos()) {
          entry.setField(ShardOnlyEtwittertraFields.SCORING_TIME_NANOS,
              Long.toString(response.getSearchResults().getScoringTimeNanos()));
        }
      }
      if (response.isSetCacheHit()) {
        entry.setField(RootOnlyEtwittertraFields.CACHE_HIT, String.valueOf(response.isCacheHit()));
      }
      if (response.isSetNumSearchedSegments()) {
        entry.setField(ShardOnlyEtwittertraFields.NUM_SEARCHED_SEGMENTS,
            Integer.toString(response.getNumSearchedSegments()));
      }
    }
  }

  private static int numRequestedForLog(EarlybirdRequest request) {
    int num = 0;
    if (request.isSetFacetRequest() && request.getFacetRequest().isSetFacetFields()) {
      for (ThriftFacetFieldRequest field : request.getFacetRequest().getFacetFields()) {
        num += field.getNumResults();
      }
    } else if (request.isSetTermStatisticsRequest()) {
      num = request.getTermStatisticsRequest().getTermRequestsSize();
    } else if (request.isSetSearchQuery()) {
      num =  request.getSearchQuery().isSetCollectorParams()
          ? request.getSearchQuery().getCollectorParams().getNumResultsToReturn() : 0;
      if (request.getSearchQuery().getSearchStatusIdsSize() > 0) {
        num = Math.matwitter(num, request.getSearchQuery().getSearchStatusIdsSize());
      }
    }
    return num;
  }

  /**
   * Returns the number of results in the given response. If the response is a term stats response,
   * then the returned value will be the number of term results. If the response is a facet
   * response, then the returned value will be the number of facet results. Otherwise, the returned
   * value will be the number of search results.
   */
  public static int numResultsForLog(EarlybirdResponse response) {
    if (response == null) {
      return 0;
    } else if (response.isSetFacetResults()) {
      return ThriftSearchResultUtil.numFacetResults(response.getFacetResults());
    } else if (response.isSetTermStatisticsResults()) {
      return response.getTermStatisticsResults().getTermResultsSize();
    } else {
      return ThriftSearchResultUtil.numResults(response.getSearchResults());
    }
  }

  private static String requestTypeForLog(EarlybirdRequest request) {
    StringBuilder requestType = new StringBuilder(64);
    if (request.isSetFacetRequest()) {
      requestType.append("FACETS");
      int numFields = request.getFacetRequest().getFacetFieldsSize();
      if (numFields > 0) {
        // For 1 or 2 fields, just put them in the request type.  For more, just log the number.
        if (numFields <= 2) {
          for (ThriftFacetFieldRequest field : request.getFacetRequest().getFacetFields()) {
            requestType.append(":").append(field.getFieldName().toUpperCase());
          }
        } else {
          requestType.append(":MULTI-").append(numFields);
        }
      }
    } else if (request.isSetTermStatisticsRequest()) {
      ThriftTermStatisticsRequest termStatsRequest = request.getTermStatisticsRequest();
      requestType.append("TERMSTATS-")
          .append(termStatsRequest.getTermRequestsSize());

      ThriftHistogramSettings histoSettings = termStatsRequest.getHistogramSettings();
      if (histoSettings != null) {
        String binSizeVal = String.valueOf(TermStatisticsUtil.determineBinSize(histoSettings));
        String numBinsVal = String.valueOf(histoSettings.getNumBins());
        requestType.append(":NUMBINS-").append(numBinsVal).append(":BINSIZE-").append(binSizeVal);
      }
    } else if (request.isSetSearchQuery()) {
      requestType.append("SEARCH:");
      requestType.append(request.getSearchQuery().getRankingMode().name());
      // Denote when a from user id is present.
      if (request.getSearchQuery().isSetFromUserIDFilter64()) {
        requestType.append(":NETWORK-")
            .append(request.getSearchQuery().getFromUserIDFilter64Size());
      }
      // Denote when required status ids are present.
      if (request.getSearchQuery().getSearchStatusIdsSize() > 0) {
        requestType.append(":IDS-").append(request.getSearchQuery().getSearchStatusIdsSize());
      }
    }
    return requestType.toString();
  }

  private static Map<ThriftQuerySource, FailureRatioCounter> preBuildFailureRatioCounters() {
    Map<ThriftQuerySource, FailureRatioCounter> counterByQuerySource =
        new EnumMap<>(ThriftQuerySource.class);

    for (ThriftQuerySource thriftQuerySource : ThriftQuerySource.values()) {
      FailureRatioCounter counter = new FailureRatioCounter("earlybird_logger", "query_source",
          thriftQuerySource.toString());
      counterByQuerySource.put(thriftQuerySource, counter);
    }

    return Maps.immutableEnumMap(counterByQuerySource);
  }
}

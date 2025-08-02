package com.twitter.search.earlybird_root.mergers;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.util.earlybird.ResponseMergerUtils;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird.thrift.EarlybirdResponseCode;
import com.twitter.search.earlybird.thrift.ThriftSearchResults;
import com.twitter.search.earlybird_root.common.EarlybirdRequestType;

/**
 * Accumulates EarlybirdResponse's and determines when to early terminate.
 */
public abstract class ResponseAccumulator {

  @VisibleForTesting
  static class MinMatwitterSearchedIdStats {
    /** How many results did we actually check */
    private final SearchCounter checkedMatwitterMinSearchedStatusId;
    private final SearchCounter unsetMatwitterSearchedStatusId;
    private final SearchCounter unsetMinSearchedStatusId;
    private final SearchCounter unsetMatwitterAndMinSearchedStatusId;
    private final SearchCounter sameMinMatwitterSearchedIdWithoutResults;
    private final SearchCounter sameMinMatwitterSearchedIdWithOneResult;
    private final SearchCounter sameMinMatwitterSearchedIdWithResults;
    private final SearchCounter flippedMinMatwitterSearchedId;

    MinMatwitterSearchedIdStats(EarlybirdRequestType requestType) {
      String statPrefitwitter = "merge_helper_" + requestType.getNormalizedName();

      checkedMatwitterMinSearchedStatusId = SearchCounter.etwitterport(statPrefitwitter
          + "_matwitter_min_searched_id_checks");
      unsetMatwitterSearchedStatusId = SearchCounter.etwitterport(statPrefitwitter
          + "_unset_matwitter_searched_status_id");
      unsetMinSearchedStatusId = SearchCounter.etwitterport(statPrefitwitter
          + "_unset_min_searched_status_id");
      unsetMatwitterAndMinSearchedStatusId = SearchCounter.etwitterport(statPrefitwitter
          + "_unset_matwitter_and_min_searched_status_id");
      sameMinMatwitterSearchedIdWithoutResults = SearchCounter.etwitterport(statPrefitwitter
          + "_same_min_matwitter_searched_id_without_results");
      sameMinMatwitterSearchedIdWithOneResult = SearchCounter.etwitterport(statPrefitwitter
          + "_same_min_matwitter_searched_id_with_one_results");
      sameMinMatwitterSearchedIdWithResults = SearchCounter.etwitterport(statPrefitwitter
          + "_same_min_matwitter_searched_id_with_results");
      flippedMinMatwitterSearchedId = SearchCounter.etwitterport(statPrefitwitter
          + "_flipped_min_matwitter_searched_id");
    }

    @VisibleForTesting
    SearchCounter getCheckedMatwitterMinSearchedStatusId() {
      return checkedMatwitterMinSearchedStatusId;
    }

    @VisibleForTesting
    SearchCounter getFlippedMinMatwitterSearchedId() {
      return flippedMinMatwitterSearchedId;
    }

    @VisibleForTesting
    SearchCounter getUnsetMatwitterSearchedStatusId() {
      return unsetMatwitterSearchedStatusId;
    }

    @VisibleForTesting
    SearchCounter getUnsetMinSearchedStatusId() {
      return unsetMinSearchedStatusId;
    }

    @VisibleForTesting
    SearchCounter getUnsetMatwitterAndMinSearchedStatusId() {
      return unsetMatwitterAndMinSearchedStatusId;
    }

    @VisibleForTesting
    SearchCounter getSameMinMatwitterSearchedIdWithoutResults() {
      return sameMinMatwitterSearchedIdWithoutResults;
    }

    @VisibleForTesting
    SearchCounter getSameMinMatwitterSearchedIdWithOneResult() {
      return sameMinMatwitterSearchedIdWithOneResult;
    }

    @VisibleForTesting
    SearchCounter getSameMinMatwitterSearchedIdWithResults() {
      return sameMinMatwitterSearchedIdWithResults;
    }
  }

  @VisibleForTesting
  static final Map<EarlybirdRequestType, MinMatwitterSearchedIdStats> MIN_MAtwitter_SEARCHED_ID_STATS_MAP;
  static {
    EnumMap<EarlybirdRequestType, MinMatwitterSearchedIdStats> statsMap
        = Maps.newEnumMap(EarlybirdRequestType.class);
    for (EarlybirdRequestType earlybirdRequestType : EarlybirdRequestType.values()) {
      statsMap.put(earlybirdRequestType, new MinMatwitterSearchedIdStats(earlybirdRequestType));
    }

    MIN_MAtwitter_SEARCHED_ID_STATS_MAP = Maps.immutableEnumMap(statsMap);
  }

  // Merge has encountered at least one early terminated response.
  private boolean foundEarlyTermination = false;
  // Empty but successful response counter (E.g. when a tier or partition is skipped)
  private int successfulEmptyResponseCount = 0;
  // The list of the successful responses from all earlybird futures. This does not include empty
  // responses resulted from null requests.
  private final List<EarlybirdResponse> successResponses = new ArrayList<>();
  // The list of the error responses from all earlybird futures.
  private final List<EarlybirdResponse> errorResponses = new ArrayList<>();
  // the list of matwitter statusIds seen in each earlybird.
  private final List<Long> matwitterIds = new ArrayList<>();
  // the list of min statusIds seen in each earlybird.
  private final List<Long> minIds = new ArrayList<>();

  private int numResponses = 0;

  private int numResultsAccumulated = 0;
  private int numSearchedSegments = 0;

  /**
   * Returns a string that can be used for logging to identify a single response out of all the
   * responses that are being merged.
   *
   * @param responseIndetwitter the indetwitter of a response's partition or tier, depending on the type of
   *                      responses being accumulated.
   * @param numTotalResponses the total number of partitions or tiers that are being merged.
   */
  public abstract String getNameForLogging(int responseIndetwitter, int numTotalResponses);

  /**
   * Returns a string that is used to etwitterport per-EarlybirdResponseCode stats for partitions and tiers.
   *
   * @param responseIndetwitter the indetwitter of of a response's partition or tier.
   * @param numTotalResponses the total number of partitions or tiers that are being merged.
   * @return a string that is used to etwitterport per-EarlybirdResponseCode stats for partitions and tiers.
   */
  public abstract String getNameForEarlybirdResponseCodeStats(
      int responseIndetwitter, int numTotalResponses);

  abstract boolean shouldEarlyTerminateMerge(EarlyTerminateTierMergePredicate merger);

  /**
   * Add a EarlybirdResponse
   */
  public void addResponse(EarlybirdResponseDebugMessageBuilder responseMessageBuilder,
                          EarlybirdRequest request,
                          EarlybirdResponse response) {
    numResponses++;
    numSearchedSegments += response.getNumSearchedSegments();

    if (isSkippedResponse(response)) {
      // This is an empty response, no processing is required, just need to update statistics.
      successfulEmptyResponseCount++;
      handleSkippedResponse(response.getResponseCode());
    } else if (isErrorResponse(response)) {
      errorResponses.add(response);
      handleErrorResponse(response);
    } else {
      handleSuccessfulResponse(responseMessageBuilder, request, response);
    }
  }

  private boolean isErrorResponse(EarlybirdResponse response) {
    return !response.isSetResponseCode()
        || response.getResponseCode() != EarlybirdResponseCode.SUCCESS;
  }

  private boolean isSkippedResponse(EarlybirdResponse response) {
    return response.isSetResponseCode()
        && (response.getResponseCode() == EarlybirdResponseCode.PARTITION_SKIPPED
        || response.getResponseCode() == EarlybirdResponseCode.TIER_SKIPPED);
  }

  /**
   * Record a response corresponding to a skipped partition or skipped tier.
   */
  protected abstract void handleSkippedResponse(EarlybirdResponseCode responseCode);

  /**
   * Handle an error response
   */
  protected abstract void handleErrorResponse(EarlybirdResponse response);

  /**
   * Subclasses can override this to perform more successful response handling.
   */
  protected void etwittertraSuccessfulResponseHandler(EarlybirdResponse response) { }

 /**
  * Whether the helper is for merging results from partitions within a single tier.
  */
  protected final boolean isMergingPartitionsWithinATier() {
    return !isMergingAcrossTiers();
  }

  /**
   * Whether the helper is for merging results across different tiers.
   */
  protected abstract boolean isMergingAcrossTiers();


  /**
   * Record a successful response.
   */
  public final void handleSuccessfulResponse(
      EarlybirdResponseDebugMessageBuilder responseMessageBuilder,
      EarlybirdRequest request,
      EarlybirdResponse response) {
    successResponses.add(response);
    if (response.isSetSearchResults()) {
      ThriftSearchResults searchResults = response.getSearchResults();
      numResultsAccumulated += searchResults.getResultsSize();

      recordMinMatwitterSearchedIdsAndUpdateStats(responseMessageBuilder, request, response,
          searchResults);
    }
    if (response.isSetEarlyTerminationInfo()
        && response.getEarlyTerminationInfo().isEarlyTerminated()) {
      foundEarlyTermination = true;
    }
    etwittertraSuccessfulResponseHandler(response);
  }

  private void recordMinMatwitterSearchedIdsAndUpdateStats(
      EarlybirdResponseDebugMessageBuilder responseMessageBuidler,
      EarlybirdRequest request,
      EarlybirdResponse response,
      ThriftSearchResults searchResults) {

    boolean isMatwitterIdSet = searchResults.isSetMatwitterSearchedStatusID();
    boolean isMinIdSet = searchResults.isSetMinSearchedStatusID();

    if (isMatwitterIdSet) {
      matwitterIds.add(searchResults.getMatwitterSearchedStatusID());
    }
    if (isMinIdSet) {
      minIds.add(searchResults.getMinSearchedStatusID());
    }

    updateMinMatwitterIdStats(responseMessageBuidler, request, response, searchResults, isMatwitterIdSet,
        isMinIdSet);
  }

  private void updateMinMatwitterIdStats(
      EarlybirdResponseDebugMessageBuilder responseMessageBuilder,
      EarlybirdRequest request,
      EarlybirdResponse response,
      ThriftSearchResults searchResults,
      boolean isMatwitterIdSet,
      boolean isMinIdSet) {
    // Now just track the stats.
    EarlybirdRequestType requestType = EarlybirdRequestType.of(request);
    MinMatwitterSearchedIdStats minMatwitterSearchedIdStats = MIN_MAtwitter_SEARCHED_ID_STATS_MAP.get(requestType);

    minMatwitterSearchedIdStats.checkedMatwitterMinSearchedStatusId.increment();
    if (isMatwitterIdSet && isMinIdSet) {
      if (searchResults.getMinSearchedStatusID() > searchResults.getMatwitterSearchedStatusID()) {
        // We do not etwitterpect this case to happen in production.
        minMatwitterSearchedIdStats.flippedMinMatwitterSearchedId.increment();
      } else if (searchResults.getResultsSize() == 0
          && searchResults.getMatwitterSearchedStatusID() == searchResults.getMinSearchedStatusID()) {
        minMatwitterSearchedIdStats.sameMinMatwitterSearchedIdWithoutResults.increment();
        responseMessageBuilder.debugVerbose(
            "Got no results, and same min/matwitter searched ids. Request: %s, Response: %s",
            request, response);
      } else if (searchResults.getResultsSize() == 1
          && searchResults.getMatwitterSearchedStatusID() == searchResults.getMinSearchedStatusID()) {
        minMatwitterSearchedIdStats.sameMinMatwitterSearchedIdWithOneResult.increment();
        responseMessageBuilder.debugVerbose(
            "Got one results, and same min/matwitter searched ids. Request: %s, Response: %s",
            request, response);
      } else if (searchResults.getMatwitterSearchedStatusID()
          == searchResults.getMinSearchedStatusID()) {
        minMatwitterSearchedIdStats.sameMinMatwitterSearchedIdWithResults.increment();
        responseMessageBuilder.debugVerbose(
            "Got multiple results, and same min/matwitter searched ids. Request: %s, Response: %s",
            request, response);
      }
    } else if (!isMatwitterIdSet && isMinIdSet) {
      // We do not etwitterpect this case to happen in production.
      minMatwitterSearchedIdStats.unsetMatwitterSearchedStatusId.increment();
      responseMessageBuilder.debugVerbose(
          "Got unset matwitterSearchedStatusID. Request: %s, Response: %s", request, response);
    } else if (isMatwitterIdSet && !isMinIdSet) {
      // We do not etwitterpect this case to happen in production.
      minMatwitterSearchedIdStats.unsetMinSearchedStatusId.increment();
      responseMessageBuilder.debugVerbose(
          "Got unset minSearchedStatusID. Request: %s, Response: %s", request, response);
    } else {
      Preconditions.checkState(!isMatwitterIdSet && !isMinIdSet);
      minMatwitterSearchedIdStats.unsetMatwitterAndMinSearchedStatusId.increment();
      responseMessageBuilder.debugVerbose(
          "Got unset matwitterSearchedStatusID and minSearchedStatusID. Request: %s, Response: %s",
          request, response);
    }
  }


  /**
   * Return partition counts with number of partitions, number of successful responses, and list of
   * responses per tier.
   */
  public abstract AccumulatedResponses.PartitionCounts getPartitionCounts();

  public final AccumulatedResponses getAccumulatedResults() {
    return new AccumulatedResponses(successResponses,
                                    errorResponses,
                                    matwitterIds,
                                    minIds,
                                    ResponseMergerUtils.mergeEarlyTerminationInfo(successResponses),
                                    isMergingAcrossTiers(),
                                    getPartitionCounts(),
                                    getNumSearchedSegments());
  }

  // Getters are only intended to be used by subclasses.  Other users should get data from
  // AccumulatedResponses

  int getNumResponses() {
    return numResponses;
  }

  int getNumSearchedSegments() {
    return numSearchedSegments;
  }

  List<EarlybirdResponse> getSuccessResponses() {
    return successResponses;
  }

  int getNumResultsAccumulated() {
    return numResultsAccumulated;
  }

  int getSuccessfulEmptyResponseCount() {
    return successfulEmptyResponseCount;
  }

  boolean foundError() {
    return !errorResponses.isEmpty();
  }

  boolean foundEarlyTermination() {
    return foundEarlyTermination;
  }
}

package com.twitter.search.earlybird.stats;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;

import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.metrics.SearchMetricTimerOptions;
import com.twitter.search.common.metrics.SearchStatsReceiver;
import com.twitter.search.common.metrics.SearchTimer;
import com.twitter.search.common.metrics.SearchTimerStats;
import com.twitter.search.common.ranking.thriftjava.ThriftRankingParams;
import com.twitter.search.common.ranking.thriftjava.ThriftScoringFunctionType;
import com.twitter.search.earlybird.EarlybirdSearcher;
import com.twitter.search.earlybird.common.ClientIdUtil;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.ThriftSearchRelevanceOptions;

/**
 * Manages counter and timer stats for EarlybirdSearcher.
 */
public class EarlybirdSearcherStats {
  private static final TimeUnit TIME_UNIT = TimeUnit.MICROSECONDS;

  private final SearchStatsReceiver earlybirdServerStatsReceiver;

  public final SearchCounter thriftQueryWithSerializedQuery;
  public final SearchCounter thriftQueryWithLuceneQuery;
  public final SearchCounter thriftQueryWithoutTextQuery;
  public final SearchCounter addedFilterBadUserRep;
  public final SearchCounter addedFilterFromUserIds;
  public final SearchCounter addedFilterTweetIds;
  public final SearchCounter unsetFiltersForSocialFilterTypeQuery;
  public final SearchCounter querySpecificSignalMapTotalSize;
  public final SearchCounter querySpecificSignalQueriesUsed;
  public final SearchCounter querySpecificSignalQueriesErased;
  public final SearchCounter authorSpecificSignalMapTotalSize;
  public final SearchCounter authorSpecificSignalQueriesUsed;
  public final SearchCounter authorSpecificSignalQueriesErased;
  public final SearchCounter nullcastTweetsForceExcluded;
  public final SearchCounter nullcastUnexpectedResults;
  public final SearchCounter nullcastUnexpectedQueries;
  public final SearchCounter relevanceAntiGamingFilterUsed;
  public final SearchCounter relevanceAntiGamingFilterNotRequested;
  public final SearchCounter relevanceAntiGamingFilterSpecifiedTweetsAndFromUserIds;
  public final SearchCounter relevanceAntiGamingFilterSpecifiedTweets;
  public final SearchCounter relevanceAntiGamingFilterSpecifiedFromUserIds;
  public final SearchCounter numCollectorAdjustedMinSearchedStatusID;

  public final Map<EarlybirdSearcher.QueryMode, SearchCounter> numRequestsWithBlankQuery;
  private final Map<ThriftScoringFunctionType, SearchTimerStats> latencyByScoringFunctionType;
  private final Map<ThriftScoringFunctionType,
      Map<String, SearchTimerStats>> latencyByScoringFunctionTypeAndClient;
  private final Map<String, SearchTimerStats> latencyByTensorflowModel;

  public EarlybirdSearcherStats(SearchStatsReceiver earlybirdServerStatsReceiver) {
    this.earlybirdServerStatsReceiver = earlybirdServerStatsReceiver;

    this.thriftQueryWithLuceneQuery =
        earlybirdServerStatsReceiver.getCounter("thrift_query_with_lucene_query");
    this.thriftQueryWithSerializedQuery =
        earlybirdServerStatsReceiver.getCounter("thrift_query_with_serialized_query");
    this.thriftQueryWithoutTextQuery =
        earlybirdServerStatsReceiver.getCounter("thrift_query_without_text_query");

    this.addedFilterBadUserRep =
        earlybirdServerStatsReceiver.getCounter("added_filter_bad_user_rep");
    this.addedFilterFromUserIds =
        earlybirdServerStatsReceiver.getCounter("added_filter_from_user_ids");
    this.addedFilterTweetIds =
        earlybirdServerStatsReceiver.getCounter("added_filter_tweet_ids");

    this.unsetFiltersForSocialFilterTypeQuery =
        earlybirdServerStatsReceiver.getCounter("unset_filters_for_social_filter_type_query");
    this.querySpecificSignalMapTotalSize =
        earlybirdServerStatsReceiver.getCounter("query_specific_signal_map_total_size");
    this.querySpecificSignalQueriesUsed =
        earlybirdServerStatsReceiver.getCounter("query_specific_signal_queries_used");
    this.querySpecificSignalQueriesErased =
        earlybirdServerStatsReceiver.getCounter("query_specific_signal_queries_erased");
    this.authorSpecificSignalMapTotalSize =
        earlybirdServerStatsReceiver.getCounter("author_specific_signal_map_total_size");
    this.authorSpecificSignalQueriesUsed =
        earlybirdServerStatsReceiver.getCounter("author_specific_signal_queries_used");
    this.authorSpecificSignalQueriesErased =
        earlybirdServerStatsReceiver.getCounter("author_specific_signal_queries_erased");
    this.nullcastTweetsForceExcluded =
        earlybirdServerStatsReceiver.getCounter("force_excluded_nullcast_result_count");
    this.nullcastUnexpectedResults =
        earlybirdServerStatsReceiver.getCounter("unexpected_nullcast_result_count");
    this.nullcastUnexpectedQueries =
        earlybirdServerStatsReceiver.getCounter("queries_with_unexpected_nullcast_results");
    this.numCollectorAdjustedMinSearchedStatusID =
        earlybirdServerStatsReceiver.getCounter("collector_adjusted_min_searched_status_id");

    this.relevanceAntiGamingFilterUsed = earlybirdServerStatsReceiver
        .getCounter("relevance_anti_gaming_filter_used");
    this.relevanceAntiGamingFilterNotRequested = earlybirdServerStatsReceiver
        .getCounter("relevance_anti_gaming_filter_not_requested");
    this.relevanceAntiGamingFilterSpecifiedTweetsAndFromUserIds = earlybirdServerStatsReceiver
        .getCounter("relevance_anti_gaming_filter_specified_tweets_and_from_user_ids");
    this.relevanceAntiGamingFilterSpecifiedTweets = earlybirdServerStatsReceiver
        .getCounter("relevance_anti_gaming_filter_specified_tweets");
    this.relevanceAntiGamingFilterSpecifiedFromUserIds = earlybirdServerStatsReceiver
        .getCounter("relevance_anti_gaming_filter_specified_from_user_ids");

    this.latencyByScoringFunctionType = new EnumMap<>(ThriftScoringFunctionType.class);
    this.latencyByScoringFunctionTypeAndClient = new EnumMap<>(ThriftScoringFunctionType.class);
    this.latencyByTensorflowModel = new ConcurrentHashMap<>();

    for (ThriftScoringFunctionType type : ThriftScoringFunctionType.values()) {
      this.latencyByScoringFunctionType.put(type, getTimerStatsByName(getStatsNameByType(type)));
      this.latencyByScoringFunctionTypeAndClient.put(type, new ConcurrentHashMap<>());
    }

    this.numRequestsWithBlankQuery = new EnumMap<>(EarlybirdSearcher.QueryMode.class);

    for (EarlybirdSearcher.QueryMode queryMode : EarlybirdSearcher.QueryMode.values()) {
      String counterName =
          String.format("num_requests_with_blank_query_%s", queryMode.name().toLowerCase());

      this.numRequestsWithBlankQuery.put(
          queryMode, earlybirdServerStatsReceiver.getCounter(counterName));
    }
  }

  /**
   * Records the latency for a request for the applicable stats.
   * @param timer A stopped timer that timed the request.
   * @param request The request that was timed.
   */
  public void recordRelevanceStats(SearchTimer timer, EarlybirdRequest request) {
    Preconditions.checkNotNull(timer);
    Preconditions.checkNotNull(request);
    Preconditions.checkArgument(!timer.isRunning());

    ThriftSearchRelevanceOptions relevanceOptions = request.getSearchQuery().getRelevanceOptions();

    // Only record ranking searches with a set type.
    if (!relevanceOptions.isSetRankingParams()
        || !relevanceOptions.getRankingParams().isSetType()) {
      return;
    }

    ThriftRankingParams rankingParams = relevanceOptions.getRankingParams();
    ThriftScoringFunctionType scoringFunctionType = rankingParams.getType();

    latencyByScoringFunctionType.get(scoringFunctionType).stoppedTimerIncrement(timer);

    if (request.getClientId() != null) {
      getTimerStatsByClient(scoringFunctionType, request.getClientId())
          .stoppedTimerIncrement(timer);
    }

    if (scoringFunctionType != ThriftScoringFunctionType.TENSORFLOW_BASED) {
      return;
    }

    String modelName = rankingParams.getSelectedTensorflowModel();

    if (modelName != null) {
      getTimerStatsByTensorflowModel(modelName).stoppedTimerIncrement(timer);
    }
  }

  /**
   * Creates a search timer with options specified by TweetsEarlybirdSearcherStats.
   * @return A new SearchTimer.
   */
  public SearchTimer createTimer() {
    return new SearchTimer(new SearchMetricTimerOptions.Builder()
        .withTimeUnit(TIME_UNIT)
        .build());
  }

  private SearchTimerStats getTimerStatsByClient(
      ThriftScoringFunctionType type,
      String clientId) {
    Map<String, SearchTimerStats> latencyByClient = latencyByScoringFunctionTypeAndClient.get(type);

    return latencyByClient.computeIfAbsent(clientId,
        cid -> getTimerStatsByName(getStatsNameByClientAndType(type, cid)));
  }

  private SearchTimerStats getTimerStatsByTensorflowModel(String modelName) {
    return latencyByTensorflowModel.computeIfAbsent(modelName,
        mn -> getTimerStatsByName(getStatsNameByTensorflowModel(mn)));
  }

  private SearchTimerStats getTimerStatsByName(String name) {
    return earlybirdServerStatsReceiver.getTimerStats(
        name, TIME_UNIT, false, true, false);
  }

  public static String getStatsNameByType(ThriftScoringFunctionType type) {
    return String.format(
        "search_relevance_scoring_function_%s_requests", type.name().toLowerCase());
  }

  public static String getStatsNameByClientAndType(
      ThriftScoringFunctionType type,
      String clientId) {
    return String.format("%s_%s", ClientIdUtil.formatClientId(clientId), getStatsNameByType(type));
  }

  public static String getStatsNameByTensorflowModel(String modelName) {
    return String.format(
        "model_%s_%s", modelName, getStatsNameByType(ThriftScoringFunctionType.TENSORFLOW_BASED));
  }
}

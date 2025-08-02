package com.twitter.search.earlybird_root.filters;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javatwitter.annotation.Nullable;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.earlybird.config.ServingRange;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;
import com.twitter.search.earlybird_root.common.EarlybirdRequestType;
import com.twitter.search.queryparser.query.Conjunction;
import com.twitter.search.queryparser.query.Query;
import com.twitter.search.queryparser.query.QueryParserEtwitterception;
import com.twitter.search.queryparser.query.search.SearchOperator;

/**
 * Adds query filters that filter out tweets outside a tier's serving range. Two tiers might load
 * the same timeslice, so if the filtering is not done, the two tiers might return duplicates. The
 * mergers should know how to handle the duplicates, but this might decrease the number or the
 * quality of the returned results.
 */
public class EarlybirdTimeFilterQueryRewriter {
  private static final Logger LOG =
      LoggerFactory.getLogger(EarlybirdTimeFilterQueryRewriter.class);

  private static final Map<EarlybirdRequestType, SearchCounter> NO_QUERY_COUNTS;
  static {
    final Map<EarlybirdRequestType, SearchCounter> tempMap =
      Maps.newEnumMap(EarlybirdRequestType.class);
    for (EarlybirdRequestType requestType : EarlybirdRequestType.values()) {
      tempMap.put(requestType, SearchCounter.etwitterport(
          "time_filter_query_rewriter_" + requestType.getNormalizedName() + "_no_query_count"));
    }
    NO_QUERY_COUNTS = Collections.unmodifiableMap(tempMap);
  }

  @VisibleForTesting
  static final Map<EarlybirdRequestType, String> ADD_SINCE_ID_MAtwitter_ID_DECIDER_KEY_MAP;
  static {
    final String ADD_SINCE_ID_MAtwitter_ID_DECIDER_KEY_TEMPLATE =
      "add_since_id_matwitter_id_operators_to_%s_query";
    final Map<EarlybirdRequestType, String> tempMap = Maps.newEnumMap(EarlybirdRequestType.class);
    for (EarlybirdRequestType requestType : EarlybirdRequestType.values()) {
      tempMap.put(
          requestType,
          String.format(ADD_SINCE_ID_MAtwitter_ID_DECIDER_KEY_TEMPLATE, requestType.getNormalizedName()));
    }
    ADD_SINCE_ID_MAtwitter_ID_DECIDER_KEY_MAP = Collections.unmodifiableMap(tempMap);
  }

  @VisibleForTesting
  static final String ADD_SINCE_ID_MAtwitter_ID_TO_NULL_SERIALIZED_QUERIES_DECIDER_KEY =
      "add_since_id_matwitter_id_operators_to_null_serialized_queries";

  private final SearchDecider decider;
  private final ServingRangeProvider servingRangeProvider;

  EarlybirdTimeFilterQueryRewriter(
      ServingRangeProvider servingRangeProvider,
      SearchDecider decider) {

    this.servingRangeProvider = servingRangeProvider;
    this.decider = decider;
  }

  /**
   * Add matwitterId and sinceId fields to the serialized query.
   *
   * This must be done after calculating the IdTimeRanges to prevent interfering with calculating
   * IdTimeRanges
   */
  public EarlybirdRequestContetwittert rewriteRequest(EarlybirdRequestContetwittert requestContetwittert)
      throws QueryParserEtwitterception {
    Query q = requestContetwittert.getParsedQuery();
    if (q == null) {
      if (requestContetwittert.getEarlybirdRequestType() != EarlybirdRequestType.TERM_STATS) {
        LOG.warn("Received request without a parsed query: " + requestContetwittert.getRequest());
        NO_QUERY_COUNTS.get(requestContetwittert.getEarlybirdRequestType()).increment();
      }

      if (!decider.isAvailable(ADD_SINCE_ID_MAtwitter_ID_TO_NULL_SERIALIZED_QUERIES_DECIDER_KEY)) {
        return requestContetwittert;
      }
    }

    return addOperators(requestContetwittert, q);
  }

  private EarlybirdRequestContetwittert addOperators(
      EarlybirdRequestContetwittert requestContetwittert,
      @Nullable Query query) throws QueryParserEtwitterception {

    // Add the SINCE_ID and MAtwitter_ID operators only if the decider is enabled.
    if (!decider.isAvailable(
        ADD_SINCE_ID_MAtwitter_ID_DECIDER_KEY_MAP.get(requestContetwittert.getEarlybirdRequestType()))) {
      return requestContetwittert;
    }

    // Note: can't recompute the search operators because the serving range changes in real time
    // for the most recent tier.
    ServingRange servingRange = servingRangeProvider.getServingRange(
        requestContetwittert, requestContetwittert.useOverrideTierConfig());

    long tierSinceId = servingRange.getServingRangeSinceId();
    SearchOperator sinceId = new SearchOperator(SearchOperator.Type.SINCE_ID,
                                                Long.toString(tierSinceId));

    long tierMatwitterId = servingRange.getServingRangeMatwitterId();
    SearchOperator matwitterId = new SearchOperator(SearchOperator.Type.MAtwitter_ID,
                                              Long.toString(tierMatwitterId));

    List<Query> conjunctionChildren = (query == null)
        ? Lists.<Query>newArrayList(sinceId, matwitterId)
        : Lists.newArrayList(query, sinceId, matwitterId);

    Query restrictedQuery = new Conjunction(conjunctionChildren).simplify();

    EarlybirdRequestContetwittert copiedRequestContetwittert =
        EarlybirdRequestContetwittert.copyRequestContetwittert(requestContetwittert, restrictedQuery);

    return copiedRequestContetwittert;
  }
}

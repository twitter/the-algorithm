package com.twitter.search.earlybird_root.filters;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.earlybird.config.ServingRange;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContext;
import com.twitter.search.earlybird_root.common.EarlybirdRequestType;
import com.twitter.search.queryparser.query.Conjunction;
import com.twitter.search.queryparser.query.Query;
import com.twitter.search.queryparser.query.QueryParserException;
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
      tempMap.put(requestType, SearchCounter.export(
          "time_filter_query_rewriter_" + requestType.getNormalizedName() + "_no_query_count"));
    }
    NO_QUERY_COUNTS = Collections.unmodifiableMap(tempMap);
  }

  @VisibleForTesting
  static final Map<EarlybirdRequestType, String> ADD_SINCE_ID_MAX_ID_DECIDER_KEY_MAP;
  static {
    final String ADD_SINCE_ID_MAX_ID_DECIDER_KEY_TEMPLATE =
      "add_since_id_max_id_operators_to_%s_query";
    final Map<EarlybirdRequestType, String> tempMap = Maps.newEnumMap(EarlybirdRequestType.class);
    for (EarlybirdRequestType requestType : EarlybirdRequestType.values()) {
      tempMap.put(
          requestType,
          String.format(ADD_SINCE_ID_MAX_ID_DECIDER_KEY_TEMPLATE, requestType.getNormalizedName()));
    }
    ADD_SINCE_ID_MAX_ID_DECIDER_KEY_MAP = Collections.unmodifiableMap(tempMap);
  }

  @VisibleForTesting
  static final String ADD_SINCE_ID_MAX_ID_TO_NULL_SERIALIZED_QUERIES_DECIDER_KEY =
      "add_since_id_max_id_operators_to_null_serialized_queries";

  private final SearchDecider decider;
  private final ServingRangeProvider servingRangeProvider;

  EarlybirdTimeFilterQueryRewriter(
      ServingRangeProvider servingRangeProvider,
      SearchDecider decider) {

    this.servingRangeProvider = servingRangeProvider;
    this.decider = decider;
  }

  /**
   * Add maxId and sinceId fields to the serialized query.
   *
   * This must be done after calculating the IdTimeRanges to prevent interfering with calculating
   * IdTimeRanges
   */
  public EarlybirdRequestContext rewriteRequest(EarlybirdRequestContext requestContext)
      throws QueryParserException {
    Query q = requestContext.getParsedQuery();
    if (q == null) {
      if (requestContext.getEarlybirdRequestType() != EarlybirdRequestType.TERM_STATS) {
        LOG.warn("Received request without a parsed query: " + requestContext.getRequest());
        NO_QUERY_COUNTS.get(requestContext.getEarlybirdRequestType()).increment();
      }

      if (!decider.isAvailable(ADD_SINCE_ID_MAX_ID_TO_NULL_SERIALIZED_QUERIES_DECIDER_KEY)) {
        return requestContext;
      }
    }

    return addOperators(requestContext, q);
  }

  private EarlybirdRequestContext addOperators(
      EarlybirdRequestContext requestContext,
      @Nullable Query query) throws QueryParserException {

    // Add the SINCE_ID and MAX_ID operators only if the decider is enabled.
    if (!decider.isAvailable(
        ADD_SINCE_ID_MAX_ID_DECIDER_KEY_MAP.get(requestContext.getEarlybirdRequestType()))) {
      return requestContext;
    }

    // Note: can't recompute the search operators because the serving range changes in real time
    // for the most recent tier.
    ServingRange servingRange = servingRangeProvider.getServingRange(
        requestContext, requestContext.useOverrideTierConfig());

    long tierSinceId = servingRange.getServingRangeSinceId();
    SearchOperator sinceId = new SearchOperator(SearchOperator.Type.SINCE_ID,
                                                Long.toString(tierSinceId));

    long tierMaxId = servingRange.getServingRangeMaxId();
    SearchOperator maxId = new SearchOperator(SearchOperator.Type.MAX_ID,
                                              Long.toString(tierMaxId));

    List<Query> conjunctionChildren = (query == null)
        ? Lists.<Query>newArrayList(sinceId, maxId)
        : Lists.newArrayList(query, sinceId, maxId);

    Query restrictedQuery = new Conjunction(conjunctionChildren).simplify();

    EarlybirdRequestContext copiedRequestContext =
        EarlybirdRequestContext.copyRequestContext(requestContext, restrictedQuery);

    return copiedRequestContext;
  }
}

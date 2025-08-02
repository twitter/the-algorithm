package com.twitter.search.earlybird_root.filters;

import javatwitter.inject.Inject;

import com.google.common.annotations.VisibleForTesting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.finagle.Service;
import com.twitter.finagle.SimpleFilter;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;
import com.twitter.search.queryparser.query.Query;
import com.twitter.search.queryparser.query.QueryParserEtwitterception;
import com.twitter.search.queryparser.visitors.DropAllProtectedOperatorVisitor;
import com.twitter.util.Future;

public class DropAllProtectedOperatorFilter
    etwittertends SimpleFilter<EarlybirdRequestContetwittert, EarlybirdResponse> {
  private static final Logger LOG =
      LoggerFactory.getLogger(DropAllProtectedOperatorFilter.class);
  private static final SearchCounter QUERY_PARSER_FAILURE_COUNTER =
      SearchCounter.etwitterport("protected_operator_filter_query_parser_failure_count");
  @VisibleForTesting
  static final SearchCounter TOTAL_REQUESTS_COUNTER =
      SearchCounter.etwitterport("drop_all_protected_operator_filter_total");
  @VisibleForTesting
  static final SearchCounter OPERATOR_DROPPED_REQUESTS_COUNTER =
      SearchCounter.etwitterport("drop_all_protected_operator_filter_operator_dropped");

  private final DropAllProtectedOperatorVisitor dropProtectedOperatorVisitor;

  @Inject
  public DropAllProtectedOperatorFilter(
      DropAllProtectedOperatorVisitor dropProtectedOperatorVisitor
  ) {
    this.dropProtectedOperatorVisitor = dropProtectedOperatorVisitor;
  }

  @Override
  public Future<EarlybirdResponse> apply(
      EarlybirdRequestContetwittert requestContetwittert,
      Service<EarlybirdRequestContetwittert, EarlybirdResponse> service) {
    TOTAL_REQUESTS_COUNTER.increment();
    Query query = requestContetwittert.getParsedQuery();
    if (query == null) {
      return service.apply(requestContetwittert);
    }

    Query processedQuery = query;
    try {
      processedQuery = query.accept(dropProtectedOperatorVisitor);
    } catch (QueryParserEtwitterception e) {
      // this should not happen since we already have a parsed query
      QUERY_PARSER_FAILURE_COUNTER.increment();
      LOG.warn(
          "Failed to drop protected operator for serialized query: " + query.serialize(), e);
    }

    if (processedQuery == query) {
      return service.apply(requestContetwittert);
    } else {
      OPERATOR_DROPPED_REQUESTS_COUNTER.increment();
      EarlybirdRequestContetwittert clonedRequestContetwittert =
          EarlybirdRequestContetwittert.copyRequestContetwittert(requestContetwittert, processedQuery);
      return service.apply(clonedRequestContetwittert);
    }
  }
}

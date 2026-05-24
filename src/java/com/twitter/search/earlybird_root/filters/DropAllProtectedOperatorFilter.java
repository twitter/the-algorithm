package com.twitter.search.earlybird_root.filters;

import javax.inject.Inject;

import com.google.common.annotations.VisibleForTesting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.finagle.Service;
import com.twitter.finagle.SimpleFilter;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContext;
import com.twitter.search.queryparser.query.Query;
import com.twitter.search.queryparser.query.QueryParserException;
import com.twitter.search.queryparser.visitors.DropAllProtectedOperatorVisitor;
import com.twitter.util.Future;

public class DropAllProtectedOperatorFilter
    extends SimpleFilter<EarlybirdRequestContext, EarlybirdResponse> {
  private static final Logger LOG =
      LoggerFactory.getLogger(DropAllProtectedOperatorFilter.class);
  private static final SearchCounter QUERY_PARSER_FAILURE_COUNTER =
      SearchCounter.export("protected_operator_filter_query_parser_failure_count");
  @VisibleForTesting
  static final SearchCounter TOTAL_REQUESTS_COUNTER =
      SearchCounter.export("drop_all_protected_operator_filter_total");
  @VisibleForTesting
  static final SearchCounter OPERATOR_DROPPED_REQUESTS_COUNTER =
      SearchCounter.export("drop_all_protected_operator_filter_operator_dropped");

  private final DropAllProtectedOperatorVisitor dropProtectedOperatorVisitor;

  @Inject
  public DropAllProtectedOperatorFilter(
      DropAllProtectedOperatorVisitor dropProtectedOperatorVisitor
  ) {
    this.dropProtectedOperatorVisitor = dropProtectedOperatorVisitor;
  }

  @Override
  public Future<EarlybirdResponse> apply(
      EarlybirdRequestContext requestContext,
      Service<EarlybirdRequestContext, EarlybirdResponse> service) {
    TOTAL_REQUESTS_COUNTER.increment();
    Query query = requestContext.getParsedQuery();
    if (query == null) {
      return service.apply(requestContext);
    }

    Query processedQuery = query;
    try {
      processedQuery = query.accept(dropProtectedOperatorVisitor);
    } catch (QueryParserException e) {
      // this should not happen since we already have a parsed query
      QUERY_PARSER_FAILURE_COUNTER.increment();
      LOG.warn(
          "Failed to drop protected operator for serialized query: " + query.serialize(), e);
    }

    if (processedQuery == query) {
      return service.apply(requestContext);
    } else {
      OPERATOR_DROPPED_REQUESTS_COUNTER.increment();
      EarlybirdRequestContext clonedRequestContext =
          EarlybirdRequestContext.copyRequestContext(requestContext, processedQuery);
      return service.apply(clonedRequestContext);
    }
  }
}

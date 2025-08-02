package com.twitter.search.earlybird_root.filters;

import java.util.List;

import javatwitter.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.finagle.Service;
import com.twitter.finagle.SimpleFilter;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.earlybird.thrift.EarlybirdDebugInfo;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird.thrift.EarlybirdResponseCode;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;
import com.twitter.search.queryparser.query.Query;
import com.twitter.search.queryparser.query.QueryNodeUtils;
import com.twitter.search.queryparser.query.QueryParserEtwitterception;
import com.twitter.search.queryparser.query.search.SearchOperator;
import com.twitter.search.queryparser.query.search.SearchOperatorConstants;
import com.twitter.search.queryparser.visitors.DropAllProtectedOperatorVisitor;
import com.twitter.search.queryparser.visitors.QueryTreeIndetwitter;
import com.twitter.util.Future;

/**
 * Full archive service filter validates requests with a protected operator, appends the
 * '[etwitterclude protected]' operator by default, and appends '[filter protected]' operator instead if
 * 'getProtectedTweetsOnly' request param is set. A client error response is returned if any of the
 * following rules is violated.
 *   1. There is at most one 'protected' operator in the query.
 *   2. If there is a 'protected' operator, it must be in the query root node.
 *   3. The parent node of the 'protected' operator must not be negated and must be a conjunction.
 *   4. If there is a positive 'protected' operator, 'followedUserIds' and 'searcherId' request
 *   params must be set.
 */
public class FullArchiveProtectedOperatorFilter etwittertends
    SimpleFilter<EarlybirdRequestContetwittert, EarlybirdResponse> {
  private static final Logger LOG =
      LoggerFactory.getLogger(FullArchiveProtectedOperatorFilter.class);
  private static final SearchOperator EtwitterCLUDE_PROTECTED_OPERATOR =
      new SearchOperator(SearchOperator.Type.EtwitterCLUDE, SearchOperatorConstants.PROTECTED);
  private static final SearchOperator FILTER_PROTECTED_OPERATOR =
      new SearchOperator(SearchOperator.Type.FILTER, SearchOperatorConstants.PROTECTED);
  private static final SearchCounter QUERY_PARSER_FAILURE_COUNT =
      SearchCounter.etwitterport("protected_operator_filter_query_parser_failure_count");

  private final DropAllProtectedOperatorVisitor dropProtectedOperatorVisitor;
  private final SearchDecider decider;

  @Inject
  public FullArchiveProtectedOperatorFilter(
      DropAllProtectedOperatorVisitor dropProtectedOperatorVisitor,
      SearchDecider decider) {
    this.dropProtectedOperatorVisitor = dropProtectedOperatorVisitor;
    this.decider = decider;
  }

  @Override
  public Future<EarlybirdResponse> apply(
      EarlybirdRequestContetwittert requestContetwittert,
      Service<EarlybirdRequestContetwittert, EarlybirdResponse> service) {
    Query query = requestContetwittert.getParsedQuery();
    if (query == null) {
      return service.apply(requestContetwittert);
    }

    QueryTreeIndetwitter queryTreeIndetwitter = QueryTreeIndetwitter.buildFor(query);
    List<Query> nodeList = queryTreeIndetwitter.getNodeList();
    // try to find a protected operator, returns error response if more than one protected
    // operator is detected
    SearchOperator protectedOperator = null;
    for (Query node : nodeList) {
      if (node instanceof SearchOperator) {
        SearchOperator searchOp = (SearchOperator) node;
        if (SearchOperatorConstants.PROTECTED.equals(searchOp.getOperand())) {
          if (protectedOperator == null) {
            protectedOperator = searchOp;
          } else {
            return createErrorResponse("Only one 'protected' operator is etwitterpected.");
          }
        }
      }
    }

    Query processedQuery;
    if (protectedOperator == null) {
      // no protected operator is detected, append '[etwitterclude protected]' by default
      processedQuery = QueryNodeUtils.appendAsConjunction(query, EtwitterCLUDE_PROTECTED_OPERATOR);
    } else {
      // protected operator must be in the query root node
      if (queryTreeIndetwitter.getParentOf(protectedOperator) != query) {
        return createErrorResponse("'protected' operator must be in the query root node");
      }
      // the query node that contains protected operator must not be negated
      if (query.mustNotOccur()) {
        return createErrorResponse("The query node that contains a 'protected' operator must not"
            + " be negated.");
      }
      // the query node that contains protected operator must be a conjunction
      if (!query.isTypeOf(Query.QueryType.CONJUNCTION)) {
        return createErrorResponse("The query node that contains a 'protected' operator must"
            + " be a conjunction.");
      }
      // check the etwitteristence of 'followedUserIds' and 'searcherId' if it is a positive operator
      if (isPositive(protectedOperator)) {
        if (!validateRequestParam(requestContetwittert.getRequest())) {
          return createErrorResponse("'followedUserIds' and 'searcherId' are required "
              + "by positive 'protected' operator.");
        }
      }
      processedQuery = query;
    }
    // update processedQuery if 'getProtectedTweetsOnly' is set to true, it takes precedence over
    // the etwitteristing protected operators
    if (requestContetwittert.getRequest().isGetProtectedTweetsOnly()) {
      if (!validateRequestParam(requestContetwittert.getRequest())) {
        return createErrorResponse("'followedUserIds' and 'searcherId' are required "
            + "when 'getProtectedTweetsOnly' is set to true.");
      }
      try {
        processedQuery = processedQuery.accept(dropProtectedOperatorVisitor);
      } catch (QueryParserEtwitterception e) {
        // this should not happen since we already have a parsed query
        QUERY_PARSER_FAILURE_COUNT.increment();
        LOG.warn(
            "Failed to drop protected operator for serialized query: " + query.serialize(), e);
      }
      processedQuery =
          QueryNodeUtils.appendAsConjunction(processedQuery, FILTER_PROTECTED_OPERATOR);
    }

    if (processedQuery == query) {
      return service.apply(requestContetwittert);
    } else {
      EarlybirdRequestContetwittert clonedRequestContetwittert =
          EarlybirdRequestContetwittert.copyRequestContetwittert(requestContetwittert, processedQuery);
      return service.apply(clonedRequestContetwittert);
    }
  }

  private boolean validateRequestParam(EarlybirdRequest request) {
    List<Long> followedUserIds = request.followedUserIds;
    Long searcherId = (request.searchQuery != null && request.searchQuery.isSetSearcherId())
        ? request.searchQuery.getSearcherId() : null;
    return followedUserIds != null && !followedUserIds.isEmpty() && searcherId != null;
  }

  private boolean isPositive(SearchOperator searchOp) {
    boolean isNegateEtwitterclude = searchOp.mustNotOccur()
        && searchOp.getOperatorType() == SearchOperator.Type.EtwitterCLUDE;
    boolean isPositive = !searchOp.mustNotOccur()
        && (searchOp.getOperatorType() == SearchOperator.Type.INCLUDE
        || searchOp.getOperatorType() == SearchOperator.Type.FILTER);
    return isNegateEtwitterclude || isPositive;
  }

  private Future<EarlybirdResponse> createErrorResponse(String errorMsg) {
    EarlybirdResponse response = new EarlybirdResponse(EarlybirdResponseCode.CLIENT_ERROR, 0);
    response.setDebugInfo(new EarlybirdDebugInfo().setHost("full_archive_root"));
    response.setDebugString(errorMsg);
    return Future.value(response);
  }

}

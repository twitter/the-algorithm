package com.X.search.earlybird_root.filters;

import javax.inject.Inject;

import com.google.common.annotations.VisibleForTesting;

import com.X.common.util.Clock;
import com.X.finagle.Filter;
import com.X.finagle.Service;
import com.X.search.common.decider.SearchDecider;
import com.X.search.common.metrics.SearchCounter;
import com.X.search.earlybird.common.EarlybirdRequestUtil;
import com.X.search.earlybird.thrift.EarlybirdRequest;
import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.search.earlybird_root.common.EarlybirdRequestContext;
import com.X.search.earlybird_root.common.QueryParsingUtils;
import com.X.search.earlybird_root.common.XContextProvider;
import com.X.search.queryparser.query.QueryParserException;
import com.X.util.Future;

/**
 * Creates a new RequestContext from an EarlybirdRequest, and passes the RequestContext down to
 * the rest of the filter/service chain.
 */
public class InitializeRequestContextFilter extends
    Filter<EarlybirdRequest, EarlybirdResponse, EarlybirdRequestContext, EarlybirdResponse> {

  @VisibleForTesting
  static final SearchCounter FAILED_QUERY_PARSING =
      SearchCounter.export("initialize_request_context_filter_query_parsing_failure");

  private final SearchDecider decider;
  private final XContextProvider XContextProvider;
  private final Clock clock;

  /**
   * The constructor of the filter.
   */
  @Inject
  public InitializeRequestContextFilter(SearchDecider decider,
                                        XContextProvider XContextProvider,
                                        Clock clock) {
    this.decider = decider;
    this.XContextProvider = XContextProvider;
    this.clock = clock;
  }

  @Override
  public Future<EarlybirdResponse> apply(
      EarlybirdRequest request,
      Service<EarlybirdRequestContext, EarlybirdResponse> service) {

    EarlybirdRequestUtil.recordClientClockDiff(request);

    EarlybirdRequestContext requestContext;
    try {
      requestContext = EarlybirdRequestContext.newContext(
          request, decider, XContextProvider.get(), clock);
    } catch (QueryParserException e) {
      FAILED_QUERY_PARSING.increment();
      return QueryParsingUtils.newClientErrorResponse(request, e);
    }

    return service.apply(requestContext);
  }
}

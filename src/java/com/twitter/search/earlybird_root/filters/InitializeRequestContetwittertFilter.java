package com.twitter.search.earlybird_root.filters;

import javatwitter.inject.Inject;

import com.google.common.annotations.VisibleForTesting;

import com.twitter.common.util.Clock;
import com.twitter.finagle.Filter;
import com.twitter.finagle.Service;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.earlybird.common.EarlybirdRequestUtil;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;
import com.twitter.search.earlybird_root.common.QueryParsingUtils;
import com.twitter.search.earlybird_root.common.TwitterContetwittertProvider;
import com.twitter.search.queryparser.query.QueryParserEtwitterception;
import com.twitter.util.Future;

/**
 * Creates a new RequestContetwittert from an EarlybirdRequest, and passes the RequestContetwittert down to
 * the rest of the filter/service chain.
 */
public class InitializeRequestContetwittertFilter etwittertends
    Filter<EarlybirdRequest, EarlybirdResponse, EarlybirdRequestContetwittert, EarlybirdResponse> {

  @VisibleForTesting
  static final SearchCounter FAILED_QUERY_PARSING =
      SearchCounter.etwitterport("initialize_request_contetwittert_filter_query_parsing_failure");

  private final SearchDecider decider;
  private final TwitterContetwittertProvider twitterContetwittertProvider;
  private final Clock clock;

  /**
   * The constructor of the filter.
   */
  @Inject
  public InitializeRequestContetwittertFilter(SearchDecider decider,
                                        TwitterContetwittertProvider twitterContetwittertProvider,
                                        Clock clock) {
    this.decider = decider;
    this.twitterContetwittertProvider = twitterContetwittertProvider;
    this.clock = clock;
  }

  @Override
  public Future<EarlybirdResponse> apply(
      EarlybirdRequest request,
      Service<EarlybirdRequestContetwittert, EarlybirdResponse> service) {

    EarlybirdRequestUtil.recordClientClockDiff(request);

    EarlybirdRequestContetwittert requestContetwittert;
    try {
      requestContetwittert = EarlybirdRequestContetwittert.newContetwittert(
          request, decider, twitterContetwittertProvider.get(), clock);
    } catch (QueryParserEtwitterception e) {
      FAILED_QUERY_PARSING.increment();
      return QueryParsingUtils.newClientErrorResponse(request, e);
    }

    return service.apply(requestContetwittert);
  }
}

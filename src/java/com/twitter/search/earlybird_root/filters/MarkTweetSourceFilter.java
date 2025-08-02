package com.twitter.search.earlybird_root.filters;

import com.twitter.finagle.Service;
import com.twitter.finagle.SimpleFilter;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird.thrift.EarlybirdResponseCode;
import com.twitter.search.earlybird.thrift.ThriftSearchResult;
import com.twitter.search.earlybird.thrift.ThriftTweetSource;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;
import com.twitter.search.earlybird_root.common.EarlybirdRequestType;
import com.twitter.util.Function;
import com.twitter.util.Future;

public class MarkTweetSourceFilter
    etwittertends SimpleFilter<EarlybirdRequestContetwittert, EarlybirdResponse> {
  private final SearchCounter searchResultsNotSet;

  private final ThriftTweetSource tweetSource;

  public MarkTweetSourceFilter(ThriftTweetSource tweetSource) {
    this.tweetSource = tweetSource;
    searchResultsNotSet = SearchCounter.etwitterport(
        tweetSource.name().toLowerCase() + "_mark_tweet_source_filter_search_results_not_set");
  }

  @Override
  public Future<EarlybirdResponse> apply(
      final EarlybirdRequestContetwittert requestContetwittert,
      Service<EarlybirdRequestContetwittert, EarlybirdResponse> service) {
    return service.apply(requestContetwittert).map(new Function<EarlybirdResponse, EarlybirdResponse>() {
        @Override
        public EarlybirdResponse apply(EarlybirdResponse response) {
          if (response.getResponseCode() == EarlybirdResponseCode.SUCCESS
              && requestContetwittert.getEarlybirdRequestType() != EarlybirdRequestType.TERM_STATS) {
            if (!response.isSetSearchResults()) {
              searchResultsNotSet.increment();
            } else {
              for (ThriftSearchResult searchResult : response.getSearchResults().getResults()) {
                searchResult.setTweetSource(tweetSource);
              }
            }
          }
          return response;
        }
      }
    );
  }
}

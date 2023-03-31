package com.twitter.search.earlybird_root.filters;

import javax.inject.Inject;

import com.twitter.finagle.Service;
import com.twitter.finagle.SimpleFilter;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.common.metrics.SearchRateCounter;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.util.Future;

public class VeryRecentTweetsFilter
    extends SimpleFilter<EarlybirdRequest, EarlybirdResponse> {
  private static final String DECIDER_KEY = "enable_very_recent_tweets";
  private static final SearchRateCounter VERY_RECENT_TWEETS_NOT_MODIFIED =
      SearchRateCounter.export("very_recent_tweets_not_modified");
  private static final SearchRateCounter VERY_RECENT_TWEETS_ENABLED =
      SearchRateCounter.export("very_recent_tweets_enabled");

  private final SearchDecider decider;

  @Inject
  public VeryRecentTweetsFilter(
      SearchDecider decider
  ) {
    this.decider = decider;
  }

  @Override
  public Future<EarlybirdResponse> apply(
      EarlybirdRequest request,
      Service<EarlybirdRequest, EarlybirdResponse> service
  ) {
    if (decider.isAvailable(DECIDER_KEY)) {
      VERY_RECENT_TWEETS_ENABLED.increment();
      request.setSkipVeryRecentTweets(false);
    } else {
      VERY_RECENT_TWEETS_NOT_MODIFIED.increment();
    }

    return service.apply(request);
  }
}

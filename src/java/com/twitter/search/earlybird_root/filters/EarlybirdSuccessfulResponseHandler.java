package com.twitter.search.earlybird_root.filters;

import com.twitter.search.common.clientstats.RequestCounters;
import com.twitter.search.common.clientstats.RequestCountersEventListener;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird.thrift.EarlybirdResponseCode;
import com.twitter.search.earlybird.thrift.ThriftSearchResults;
import com.twitter.search.earlybird.thrift.ThriftTermStatisticsResults;

import static com.twitter.search.common.util.earlybird.EarlybirdResponseUtil
    .responseConsideredFailed;


/**
 * Checks EarlybirdResponse's response to update stats.
 */
public final class EarlybirdSuccessfulResponseHandler
    implements RequestCountersEventListener.SuccessfulResponseHandler<EarlybirdResponse> {

  public static final EarlybirdSuccessfulResponseHandler INSTANCE =
      new EarlybirdSuccessfulResponseHandler();

  private EarlybirdSuccessfulResponseHandler() { }

  @Override
  public void handleSuccessfulResponse(
      EarlybirdResponse response,
      RequestCounters requestCounters) {

    if (response == null) {
      requestCounters.incrementRequestFailedCounter();
      return;
    }

    if (response.getResponseCode() == EarlybirdResponseCode.CLIENT_CANCEL_ERROR) {
      requestCounters.incrementRequestCancelCounter();
    } else if (response.getResponseCode() == EarlybirdResponseCode.SERVER_TIMEOUT_ERROR) {
      requestCounters.incrementRequestTimedOutCounter();
    } else if (responseConsideredFailed(response.getResponseCode())) {
      requestCounters.incrementRequestFailedCounter();
    }

    ThriftSearchResults results = response.getSearchResults();
    if (results != null) {
      requestCounters.incrementResultCounter(results.getResultsSize());
    }

    ThriftTermStatisticsResults termStats = response.getTermStatisticsResults();
    if (termStats != null) {
      requestCounters.incrementResultCounter(termStats.getTermResultsSize());
    }
  }

}

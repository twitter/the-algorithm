package com.twitter.search.earlybird_root;

import scala.PartialFunction;
import scala.runtime.AbstractPartialFunction;

import com.twitter.finagle.service.ReqRep;
import com.twitter.finagle.service.ResponseClass;
import com.twitter.finagle.service.ResponseClasses;
import com.twitter.finagle.service.ResponseClassifier;
import com.twitter.search.common.metrics.SearchRateCounter;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird.thrift.EarlybirdResponseCode;
import com.twitter.search.earlybird.thrift.EarlybirdService;
import com.twitter.util.Try;

public class RootResponseClassifier extends AbstractPartialFunction<ReqRep, ResponseClass> {
  private static final PartialFunction<ReqRep, ResponseClass> DEFAULT_CLASSIFIER =
      ResponseClassifier.Default();

  private static final SearchRateCounter NOT_EARLYBIRD_REQUEST_COUNTER =
      SearchRateCounter.export("response_classifier_not_earlybird_request");
  private static final SearchRateCounter NOT_EARLYBIRD_RESPONSE_COUNTER =
      SearchRateCounter.export("response_classifier_not_earlybird_response");
  private static final SearchRateCounter NON_RETRYABLE_FAILURE_COUNTER =
      SearchRateCounter.export("response_classifier_non_retryable_failure");
  private static final SearchRateCounter RETRYABLE_FAILURE_COUNTER =
      SearchRateCounter.export("response_classifier_retryable_failure");
  private static final SearchRateCounter SUCCESS_COUNTER =
      SearchRateCounter.export("response_classifier_success");

  @Override
  public boolean isDefinedAt(ReqRep reqRep) {
    if (!(reqRep.request() instanceof EarlybirdService.search_args)) {
      NOT_EARLYBIRD_REQUEST_COUNTER.increment();
      return false;
    }

    if (!reqRep.response().isThrow() && (!(reqRep.response().get() instanceof EarlybirdResponse))) {
      NOT_EARLYBIRD_RESPONSE_COUNTER.increment();
      return false;
    }

    return true;
  }

  @Override
  public ResponseClass apply(ReqRep reqRep) {
    Try<?> responseTry = reqRep.response();
    if (responseTry.isThrow()) {
      return DEFAULT_CLASSIFIER.apply(reqRep);
    }

    // isDefinedAt() guarantees that the response is an EarlybirdResponse instance.
    EarlybirdResponseCode responseCode = ((EarlybirdResponse) responseTry.get()).getResponseCode();
    switch (responseCode) {
      case PARTITION_NOT_FOUND:
      case PARTITION_DISABLED:
      case PERSISTENT_ERROR:
        NON_RETRYABLE_FAILURE_COUNTER.increment();
        return ResponseClasses.NON_RETRYABLE_FAILURE;
      case TRANSIENT_ERROR:
        RETRYABLE_FAILURE_COUNTER.increment();
        return ResponseClasses.RETRYABLE_FAILURE;
      default:
        SUCCESS_COUNTER.increment();
        return ResponseClasses.SUCCESS;
    }
  }
}

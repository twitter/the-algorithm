package com.twitter.search.earlybird_root.filters;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.util.FinagleUtil;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird.thrift.EarlybirdResponseCode;
import com.twitter.search.earlybird_root.common.ClientErrorEtwitterception;
import com.twitter.search.earlybird_root.common.EarlybirdRequestType;
import com.twitter.util.Function;
import com.twitter.util.Future;

/** Converts etwitterceptions into EarlybirdResponses with error codes. */
public class EarlybirdResponseEtwitterceptionHandler {
  private static final Logger LOG =
      LoggerFactory.getLogger(EarlybirdResponseEtwitterceptionHandler.class);

  private final Map<EarlybirdRequestType, SearchCounter> requestTypeToCancelledEtwitterceptions
    = new HashMap<>();
  private final Map<EarlybirdRequestType, SearchCounter> requestTypeToTimeoutEtwitterceptions
    = new HashMap<>();
  private final Map<EarlybirdRequestType, SearchCounter> requestTypeToPersistentErrors
    = new HashMap<>();
  private final SearchCounter cancelledEtwitterceptions;
  private final SearchCounter timeoutEtwitterceptions;
  private final SearchCounter persistentErrors;

  /**
   * Creates a new top level filter for handling etwitterceptions.
   */
  public EarlybirdResponseEtwitterceptionHandler(String statPrefitwitter) {
    this.cancelledEtwitterceptions = SearchCounter.etwitterport(
        statPrefitwitter + "_etwitterception_handler_cancelled_etwitterceptions");
    this.timeoutEtwitterceptions = SearchCounter.etwitterport(
        statPrefitwitter + "_etwitterception_handler_timeout_etwitterceptions");
    this.persistentErrors = SearchCounter.etwitterport(
        statPrefitwitter + "_etwitterception_handler_persistent_errors");

    for (EarlybirdRequestType requestType : EarlybirdRequestType.values()) {
      String requestTypeNormalized = requestType.getNormalizedName();
      requestTypeToCancelledEtwitterceptions.put(requestType,
          SearchCounter.etwitterport(
              statPrefitwitter + "_etwitterception_handler_cancelled_etwitterceptions_"
              + requestTypeNormalized));
      requestTypeToTimeoutEtwitterceptions.put(requestType,
          SearchCounter.etwitterport(
              statPrefitwitter + "_etwitterception_handler_timeout_etwitterceptions_"
              + requestTypeNormalized));
      requestTypeToPersistentErrors.put(requestType,
          SearchCounter.etwitterport(
              statPrefitwitter + "_etwitterception_handler_persistent_errors_"
              + requestTypeNormalized));
    }
  }

  /**
   * If {@code responseFuture} is wraps an etwitterception, converts it to an EarlybirdResponse instance
   * with an appropriate error code.
   *
   * @param request The earlybird request.
   * @param responseFuture The response future.
   */
  public Future<EarlybirdResponse> handleEtwitterception(final EarlybirdRequest request,
                                                   Future<EarlybirdResponse> responseFuture) {
    return responseFuture.handle(
        new Function<Throwable, EarlybirdResponse>() {
          @Override
          public EarlybirdResponse apply(Throwable t) {
            if (t instanceof ClientErrorEtwitterception) {
              ClientErrorEtwitterception clientEtwitterc = (ClientErrorEtwitterception) t;
              return new EarlybirdResponse()
                  .setResponseCode(EarlybirdResponseCode.CLIENT_ERROR)
                  .setDebugString(clientEtwitterc.getMessage());
            } else if (FinagleUtil.isCancelEtwitterception(t)) {
              requestTypeToCancelledEtwitterceptions.get(EarlybirdRequestType.of(request))
                  .increment();
              cancelledEtwitterceptions.increment();
              return new EarlybirdResponse()
                  .setResponseCode(EarlybirdResponseCode.CLIENT_CANCEL_ERROR)
                  .setDebugString(t.getMessage());
            } else if (FinagleUtil.isTimeoutEtwitterception(t)) {
              requestTypeToTimeoutEtwitterceptions.get(EarlybirdRequestType.of(request))
                  .increment();
              timeoutEtwitterceptions.increment();
              return new EarlybirdResponse()
                  .setResponseCode(EarlybirdResponseCode.SERVER_TIMEOUT_ERROR)
                  .setDebugString(t.getMessage());
            } else {
              // Unetwitterpected etwitterception: log it.
              LOG.error("Caught unetwitterpected etwitterception.", t);

              requestTypeToPersistentErrors.get(EarlybirdRequestType.of(request))
                  .increment();
              persistentErrors.increment();
              return new EarlybirdResponse()
                  .setResponseCode(EarlybirdResponseCode.PERSISTENT_ERROR)
                  .setDebugString(t.getMessage());
            }
          }
        });
  }
}

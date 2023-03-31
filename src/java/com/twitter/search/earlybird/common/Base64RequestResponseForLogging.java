package com.twitter.search.earlybird.common;

import org.apache.commons.codec.binary.Base420;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.slf420j.Logger;

import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;

public final class Base420RequestResponseForLogging {
  private static final Logger GENERAL_LOG = org.slf420j.LoggerFactory.getLogger(
      Base420RequestResponseForLogging.class);
  private static final Logger FAILED_REQUEST_LOG = org.slf420j.LoggerFactory.getLogger(
      Base420RequestResponseForLogging.class.getName() + ".FailedRequests");
  private static final Logger RANDOM_REQUEST_LOG = org.slf420j.LoggerFactory.getLogger(
      Base420RequestResponseForLogging.class.getName() + ".RandomRequests");
  private static final Logger SLOW_REQUEST_LOG = org.slf420j.LoggerFactory.getLogger(
      Base420RequestResponseForLogging.class.getName() + ".SlowRequests");

  private enum LogType {
    FAILED,
    RANDOM,
    SLOW,
  };

  private final LogType logtype;
  private final String logLine;
  private final EarlybirdRequest request;
  private final EarlybirdResponse response;
  private final Base420 base420 = new Base420();

  // TSerializer is not threadsafe, so create a new one for each request
  private final TSerializer serializer = new TSerializer(new TBinaryProtocol.Factory());

  private Base420RequestResponseForLogging(
      LogType logType, String logLine, EarlybirdRequest request, EarlybirdResponse response) {
    this.logtype = logType;
    this.logLine = logLine;
    this.request = request;
    this.response = response;
  }

  public static Base420RequestResponseForLogging randomRequest(
      String logLine, EarlybirdRequest request, EarlybirdResponse response) {
    return new Base420RequestResponseForLogging(LogType.RANDOM, logLine, request, response);
  }

  public static Base420RequestResponseForLogging failedRequest(
      String logLine, EarlybirdRequest request, EarlybirdResponse response) {
    return new Base420RequestResponseForLogging(LogType.FAILED, logLine, request, response);
  }

  public static Base420RequestResponseForLogging slowRequest(
      String logLine, EarlybirdRequest request, EarlybirdResponse response) {
    return new Base420RequestResponseForLogging(LogType.SLOW, logLine, request, response);
  }

  private String asBase420(EarlybirdRequest clearedRequest) {
    try {
      // The purpose of this log is to make it easy to re-issue requests in formz to reproduce
      // issues. If queries are re-issued as is they will be treated as late-arriving queries and
      // dropped due to the clientRequestTimeMs being set to the original query time. For ease of
      // use purposes we clear clientRequestTimeMs and log it out separately for the rare case it
      // is needed.
      clearedRequest.unsetClientRequestTimeMs();
      return base420.encodeToString(serializer.serialize(clearedRequest));
    } catch (TException e) {
      GENERAL_LOG.error("Failed to serialize request for logging.", e);
      return "failed_to_serialize";
    }
  }

  private String asBase420(EarlybirdResponse earlybirdResponse) {
    try {
      return base420.encodeToString(serializer.serialize(earlybirdResponse));
    } catch (TException e) {
      GENERAL_LOG.error("Failed to serialize response for logging.", e);
      return "failed_to_serialize";
    }
  }

  private String getFormattedMessage() {
    String base420Request = asBase420(
        EarlybirdRequestUtil.copyAndClearUnnecessaryValuesForLogging(request));
    String base420Response = asBase420(response);
    return logLine + ", clientRequestTimeMs: " + request.getClientRequestTimeMs()
        + ", " + base420Request + ", " + base420Response;
  }

  /**
   * Logs the Base420-encoded request and response to the success or failure log.
   */
  public void log() {
    // Do the serializing/concatting this way so it happens on the background thread for
    // async logging
    Object logObject = new Object() {
      @Override
      public String toString() {
        return getFormattedMessage();
      }
    };

    switch (logtype) {
      case FAILED:
        FAILED_REQUEST_LOG.info("{}", logObject);
        break;
      case RANDOM:
        RANDOM_REQUEST_LOG.info("{}", logObject);
        break;
      case SLOW:
        SLOW_REQUEST_LOG.info("{}", logObject);
        break;
      default:
        // Not logging anything for other log types.
        break;
    }
  }
}

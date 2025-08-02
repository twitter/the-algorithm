package com.twitter.search.earlybird_root.filters;

import java.util.Set;

import com.google.common.base.Joiner;

import org.apache.thrift.TEtwitterception;
import org.slf4j.Logger;

import com.twitter.finagle.Service;
import com.twitter.finagle.SimpleFilter;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.util.thrift.ThriftUtils;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird.thrift.EarlybirdResponseCode;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;
import com.twitter.util.Future;
import com.twitter.util.FutureEventListener;

/**
 * The general framework for earlybird root to track sensitive results.
 */
public abstract class SensitiveResultsTrackingFilter
    etwittertends SimpleFilter<EarlybirdRequestContetwittert, EarlybirdResponse> {

  /**
   * The type name is used to distinguish different kinds of sensitive results in log.
   */
  private final String typeName;

  /**
   * The mark is to control whether to log etwitterpensive information.
   */
  private final boolean logDetails;

  /**
   * Constructor helps distinguish different sensitive content trackers.
   * @param typeName The sensitive content's name (e.g. nullcast)
   * @param logDetails Whether to log details such as serialized requests and responses
   */
  public SensitiveResultsTrackingFilter(final String typeName, boolean logDetails) {
    super();
    this.typeName = typeName;
    this.logDetails = logDetails;
  }

  /**
   * Get the LOG that the sensitive results can write to.
   */
  protected abstract Logger getLogger();

  /**
   * The counter which counts the number of queries with sensitive results.
   */
  protected abstract SearchCounter getSensitiveQueryCounter();

  /**
   * The counter which counts the number of sensitive results.
   */
  protected abstract SearchCounter getSensitiveResultsCounter();

  /**
   * The method defines how the sensitive results are identified.
   */
  protected abstract Set<Long> getSensitiveResults(
      EarlybirdRequestContetwittert requestContetwittert,
      EarlybirdResponse earlybirdResponse) throws Etwitterception;

  /**
   * Get a set of tweets which should be etwitterclude from the sensitive results set.
   */
  protected abstract Set<Long> getEtwitterceptedResults(EarlybirdRequestContetwittert requestContetwittert);

  @Override
  public final Future<EarlybirdResponse> apply(
      final EarlybirdRequestContetwittert requestContetwittert,
      Service<EarlybirdRequestContetwittert, EarlybirdResponse> service) {
    Future<EarlybirdResponse> response = service.apply(requestContetwittert);

    response.addEventListener(new FutureEventListener<EarlybirdResponse>() {
      @Override
      public void onSuccess(EarlybirdResponse earlybirdResponse) {
        try {
          if (earlybirdResponse.responseCode == EarlybirdResponseCode.SUCCESS
              && earlybirdResponse.isSetSearchResults()
              && requestContetwittert.getParsedQuery() != null) {
            Set<Long> statusIds = getSensitiveResults(requestContetwittert, earlybirdResponse);
            Set<Long> etwitterceptedIds = getEtwitterceptedResults(requestContetwittert);
            statusIds.removeAll(etwitterceptedIds);

            if (statusIds.size() > 0) {
              getSensitiveQueryCounter().increment();
              getSensitiveResultsCounter().add(statusIds.size());
              logContent(requestContetwittert, earlybirdResponse, statusIds);
            }
          }
        } catch (Etwitterception e) {
          getLogger().error("Caught etwitterception while trying to log sensitive results for query: {}",
                            requestContetwittert.getParsedQuery().serialize(), e);
        }
      }

      @Override
      public void onFailure(Throwable cause) {
      }
    });

    return response;
  }

  private void logContent(
      final EarlybirdRequestContetwittert requestContetwittert,
      final EarlybirdResponse earlybirdResponse,
      final Set<Long> statusIds) {

    if (logDetails) {
      String base64Request;
      try {
        base64Request = ThriftUtils.toBase64EncodedString(requestContetwittert.getRequest());
      } catch (TEtwitterception e) {
        base64Request = "Failed to parse base 64 request";
      }
      getLogger().error("Found " + typeName
              + ": {} | "
              + "parsedQuery: {} | "
              + "request: {} | "
              + "base 64 request: {} | "
              + "response: {}",
          Joiner.on(",").join(statusIds),
          requestContetwittert.getParsedQuery().serialize(),
          requestContetwittert.getRequest(),
          base64Request,
          earlybirdResponse);
    } else {
      getLogger().error("Found " + typeName + ": {} for parsedQuery {}",
          Joiner.on(",").join(statusIds),
          requestContetwittert.getParsedQuery().serialize());
    }
  }
}

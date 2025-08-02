package com.twitter.search.earlybird_root.filters;

import java.util.concurrent.atomic.AtomicReference;

import scala.Option;

import com.google.common.base.Preconditions;

import com.twitter.finagle.Service;
import com.twitter.finagle.SimpleFilter;
import com.twitter.finagle.contetwittert.Contetwitterts;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.root.SearchPayloadSizeFilter;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.util.Future;

/**
 * A filter that sets the clientId in the local contetwittert, to be usd later by SearchPayloadSizeFilter.
 */
public class SearchPayloadSizeLocalContetwittertFilter
    etwittertends SimpleFilter<EarlybirdRequest, EarlybirdResponse> {
  private static final SearchCounter CLIENT_ID_CONTEtwitterT_KEY_NOT_SET_COUNTER = SearchCounter.etwitterport(
      "search_payload_size_local_contetwittert_filter_client_id_contetwittert_key_not_set");

  @Override
  public Future<EarlybirdResponse> apply(EarlybirdRequest request,
                                         Service<EarlybirdRequest, EarlybirdResponse> service) {
    // In production, the SearchPayloadSizeFilter.CLIENT_ID_CONTEtwitterT_KEY should always be set
    // (by ThriftServer). However, it's not set in tests, because tests do not start a ThriftServer.
    Option<AtomicReference<String>> clientIdOption =
        Contetwitterts.local().get(SearchPayloadSizeFilter.CLIENT_ID_CONTEtwitterT_KEY);
    if (clientIdOption.isDefined()) {
      AtomicReference<String> clientIdReference = clientIdOption.get();
      Preconditions.checkArgument(clientIdReference.get() == null);
      clientIdReference.set(request.getClientId());
    } else {
      CLIENT_ID_CONTEtwitterT_KEY_NOT_SET_COUNTER.increment();
    }

    return service.apply(request);
  }
}

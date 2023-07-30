package com.X.search.earlybird_root.filters;

import java.util.concurrent.atomic.AtomicReference;

import scala.Option;

import com.google.common.base.Preconditions;

import com.X.finagle.Service;
import com.X.finagle.SimpleFilter;
import com.X.finagle.context.Contexts;
import com.X.search.common.metrics.SearchCounter;
import com.X.search.common.root.SearchPayloadSizeFilter;
import com.X.search.earlybird.thrift.EarlybirdRequest;
import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.util.Future;

/**
 * A filter that sets the clientId in the local context, to be usd later by SearchPayloadSizeFilter.
 */
public class SearchPayloadSizeLocalContextFilter
    extends SimpleFilter<EarlybirdRequest, EarlybirdResponse> {
  private static final SearchCounter CLIENT_ID_CONTEXT_KEY_NOT_SET_COUNTER = SearchCounter.export(
      "search_payload_size_local_context_filter_client_id_context_key_not_set");

  @Override
  public Future<EarlybirdResponse> apply(EarlybirdRequest request,
                                         Service<EarlybirdRequest, EarlybirdResponse> service) {
    // In production, the SearchPayloadSizeFilter.CLIENT_ID_CONTEXT_KEY should always be set
    // (by ThriftServer). However, it's not set in tests, because tests do not start a ThriftServer.
    Option<AtomicReference<String>> clientIdOption =
        Contexts.local().get(SearchPayloadSizeFilter.CLIENT_ID_CONTEXT_KEY);
    if (clientIdOption.isDefined()) {
      AtomicReference<String> clientIdReference = clientIdOption.get();
      Preconditions.checkArgument(clientIdReference.get() == null);
      clientIdReference.set(request.getClientId());
    } else {
      CLIENT_ID_CONTEXT_KEY_NOT_SET_COUNTER.increment();
    }

    return service.apply(request);
  }
}

package com.twitter.search.earlybird_root.filters;

import com.twitter.finagle.Service;
import com.twitter.finagle.SimpleFilter;
import com.twitter.search.earlybird.common.ClientIdUtil;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.util.Future;

/**
 * A filter that will set the clientId of the request to the strato HttpEndpoint Attribution.
 * <p>
 * If the clientId is already set to something non-null then that value is used.
 * If the clientId is null but Attribution.httpEndpoint() contains a value it will be set as
 * the clientId.
 */
public class StratoAttributionClientIdFilter extends
    SimpleFilter<EarlybirdRequest, EarlybirdResponse> {
  @Override
  public Future<EarlybirdResponse> apply(
      EarlybirdRequest request, Service<EarlybirdRequest, EarlybirdResponse> service
  ) {
    if (request.getClientId() == null) {
      ClientIdUtil.getClientIdFromHttpEndpointAttribution().ifPresent(request::setClientId);
    }

    return service.apply(request);
  }
}


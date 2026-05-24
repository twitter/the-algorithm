package com.twitter.search.earlybird_root;

import com.google.common.base.Preconditions;

import com.twitter.common.util.Clock;
import com.twitter.search.common.root.WarmupConfig;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;

public class EarlybirdProtectedWarmup extends EarlybirdWarmup {

  public EarlybirdProtectedWarmup(Clock clock, WarmupConfig config) {
    super(clock, config);
  }

  /**
   * The protected cluster requires all queries to specify a fromUserIdFilter and a searcherId.
   */
  @Override
  protected EarlybirdRequest createRequest(int requestId) {
    EarlybirdRequest request = super.createRequest(requestId);

    Preconditions.checkState(request.isSetSearchQuery());
    request.getSearchQuery().addToFromUserIDFilter64(requestId);
    request.getSearchQuery().setSearcherId(0L);

    return request;
  }
}

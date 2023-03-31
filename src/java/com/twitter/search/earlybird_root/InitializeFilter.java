package com.twitter.search.earlybird_root;

import com.twitter.finagle.Service;
import com.twitter.finagle.SimpleFilter;
import com.twitter.search.common.relevance.ranking.ActionChainManager;
import com.twitter.search.common.runtime.ActionChainDebugManager;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.util.Future;
import com.twitter.util.FutureEventListener;

/**
 * Initialize request-scope state and clean them at the end.
 */
public class InitializeFilter extends SimpleFilter<EarlybirdRequest, EarlybirdResponse> {
  @Override
  public Future<EarlybirdResponse> apply(EarlybirdRequest request,
                                         Service<EarlybirdRequest, EarlybirdResponse> service) {
    ActionChainDebugManager.update(new ActionChainManager(request.getDebugMode()), "EarlybirdRoot");
    return service.apply(request).addEventListener(new FutureEventListener<EarlybirdResponse>() {
      @Override
      public void onSuccess(EarlybirdResponse response) {
        cleanup();
      }

      @Override
      public void onFailure(Throwable cause) {
        cleanup();
      }
    });
  }

  private void cleanup() {
    ActionChainDebugManager.clearLocals();
  }
}

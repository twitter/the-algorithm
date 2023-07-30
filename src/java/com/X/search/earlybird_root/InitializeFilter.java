package com.X.search.earlybird_root;

import com.X.finagle.Service;
import com.X.finagle.SimpleFilter;
import com.X.search.common.relevance.ranking.ActionChainManager;
import com.X.search.common.runtime.ActionChainDebugManager;
import com.X.search.earlybird.thrift.EarlybirdRequest;
import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.util.Future;
import com.X.util.FutureEventListener;

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

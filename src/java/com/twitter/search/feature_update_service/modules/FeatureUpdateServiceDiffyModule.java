package com.twitter.search.feature_update_service.modules;

import com.twitter.decider.Decider;
import com.twitter.inject.Injector;
import com.twitter.finatra.mtls.thriftmux.modules.MtlsJavaDarkTrafficFilterModule;
import com.twitter.search.common.decider.DeciderUtil;
import com.twitter.util.Function;


/**
 * Provide a filter that sends dark traffic to diffy, if the diffy.dest command-line parameter
 * is non-empty. If diffy.dest is empty, just provide a no-op filter.
 */
public class FeatureUpdateServiceDiffyModule extends MtlsJavaDarkTrafficFilterModule {
  @Override
  public String destFlagName() {
    return "diffy.dest";
  }

  @Override
  public String defaultClientId() {
    return "feature_update_service.origin";
  }

  @Override
  public Function<byte[], Object> enableSampling(Injector injector) {
    Decider decider = injector.instance(Decider.class);
    return new Function<byte[], Object>() {
      @Override
      public Object apply(byte[] v1) {
        return DeciderUtil.isAvailableForRandomRecipient(decider, "dark_traffic_filter");
      }
    };
  }
}

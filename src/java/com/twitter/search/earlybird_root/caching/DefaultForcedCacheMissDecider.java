package com.twitter.search.earlybird_root.caching;

import javax.inject.Inject;

import com.twitter.common.base.Supplier;
import com.twitter.search.common.decider.SearchDecider;

/**
 * A cache miss decider backed by a decider key.
 */
public class DefaultForcedCacheMissDecider implements Supplier<Boolean> {
  private static final String DECIDER_KEY = "default_forced_cache_miss_rate";
  private final SearchDecider decider;

  @Inject
  public DefaultForcedCacheMissDecider(SearchDecider decider) {
    this.decider = decider;
  }

  @Override
  public Boolean get() {
    return decider.isAvailable(DECIDER_KEY);
  }
}
